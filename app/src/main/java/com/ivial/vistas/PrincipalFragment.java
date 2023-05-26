package com.ivial.vistas;

import static com.ivial.vistas.AccesoFragment.nombreusuario;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.LocationManager;
import android.location.LocationRequest;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.ivial.BD.AdminSQLiteOpenHelper;
import com.ivial.R;
import com.ivial.Util.PermisosGPS;
import com.ivial.Util.Utils;
import com.ivial.dialogos.DialogoEducacion;
import com.ivial.dialogos.DialogoInformacion;
import com.ivial.dialogos.DialogoPrevencion;

import java.util.Objects;

public class PrincipalFragment extends PermisosGPS {
    View view;
    private TextView tvInformacion;
    private TextView tvEducacion;
    private TextView tvPrevencion;
    private  TextView tvNameUsuario;
    private TextView tv_salir;
    private ImageView irMapa;
    private GoogleApiClient googleApiClient;
    private AccesoFragment accesoFragment;
    private MainActivity mainActivity;
    private AdminSQLiteOpenHelper db;
    private MapsFragment mapsFragment;
    private TextView tvNumeroPolicia,tvNumeroBomberos,tvNumeroAmbulancia,tvTest;

    private String dateFinish = "",email ="";
    public static final int REQUEST_CHECK_SETTINGS = 100;
    public static final int ACCESS_FINE_LOCATION_INTENT = 3;
    public static final String BROADCAST_ACTION = "android.location.PROVIDERS_CHANGED";


    public PrincipalFragment() {
    }

    public PrincipalFragment(MainActivity accesoActivity, AccesoFragment accesoFragment, String email) {
        this.mainActivity = accesoActivity;
        this.accesoFragment = accesoFragment;
        this.email = email;
    }

    public PrincipalFragment(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }
    @RequiresApi(api = 33)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstacianeStatus) {
        view = inflater.inflate(R.layout.fragment_principal, container, false);
        initVariables();
        init();
        initEvents();
        return view;
    }
    @RequiresApi(api = 33)
    private  void initVariables(){
        tvInformacion = view.findViewById(R.id.tvInformacion);
        tvEducacion = view.findViewById(R.id.tvEducacion);
        tvPrevencion = view.findViewById(R.id.tvPrevencion);
        tvNameUsuario = view.findViewById(R.id.tvNameUsuario);
        tv_salir= view.findViewById(R.id.tv_salir);
        irMapa = view.findViewById(R.id.irMapa);
        tvNumeroPolicia = view.findViewById(R.id.tvNumeroPolicia);
        tvNumeroBomberos =view.findViewById(R.id.tvNumeroBomberos);
        tvNumeroAmbulancia = view.findViewById(R.id.tvNumeroAmbulancia);
        tvTest= view.findViewById(R.id.tvTest);

        initGoogleAPIClient();
        checkPermissions(getContext());

    }
    public void initGoogleAPIClient() {
        // Sin el diálogo de ubicación automática del cliente API de Google no funcionará
        googleApiClient = new GoogleApiClient.Builder(getContext())
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case ACCESS_FINE_LOCATION_INTENT: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openDialog(getContext());
                    // Mostrar el cuadro de diálogo de ubicación
                    if (googleApiClient == null) {
                        initGoogleAPIClient();
                    }
                    openDialog(getContext());
                } else {
                    openDialog(getContext());
                    Toast.makeText(getContext(), "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // Registra el BroadcastReceiver para verificar el estado del GPS
        requireActivity().registerReceiver(gpsLocationReceiver, new IntentFilter(BROADCAST_ACTION));
    }

    private void init(){
        db = new AdminSQLiteOpenHelper(getContext());
        tvNameUsuario.setText(db.consultarUsuario("nombre",email));
       // consultarusuariot();
    }


    private  void initEvents(){
        tvTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://es.educaplay.com/recursos-educativos/5669305-test_4_seguridad_vial.html"; // URL que deseas abrir

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);

                }
        });
        tvNumeroPolicia.setOnClickListener(v -> {
            String phoneNumberPolicia = tvNumeroPolicia.getText().toString();
            dialPhoneNumber(phoneNumberPolicia);
        });
        tvNumeroAmbulancia.setOnClickListener(v -> {
            String phoneNumberAmbulancia = tvNumeroAmbulancia.getText().toString();
            dialPhoneNumber(phoneNumberAmbulancia);
        });
        tvNumeroBomberos.setOnClickListener(v -> {
            String phoneNumberBomberos = tvNumeroBomberos.getText().toString();
            dialPhoneNumber(phoneNumberBomberos);
        });

        tv_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.cerrarSesion();
                Utils.loadFragment(mainActivity, new AccesoFragment(mainActivity), "Atras");
            }
        });

        irMapa.setOnClickListener(v -> Utils.loadFragment(mainActivity, new MapsFragment(mainActivity), "accedo fragment"));

        tvInformacion.setOnClickListener(v -> {
            DialogoInformacion dialogoInformacion = new DialogoInformacion(mainActivity,PrincipalFragment.this);
            dialogoInformacion.setCancelable(true);
            dialogoInformacion.show(getFragmentManager(),"opcion dialogo");
        });

        tvEducacion.setOnClickListener(v -> {
            DialogoEducacion dialogoEducacion = new DialogoEducacion(PrincipalFragment.this);
            dialogoEducacion.setCancelable(true);
            dialogoEducacion.show(getFragmentManager(),"opcion dialogo");
        });

        tvPrevencion.setOnClickListener(v -> {
            DialogoPrevencion dialogoPrevencion = new DialogoPrevencion(PrincipalFragment.this);
            dialogoPrevencion.setCancelable(true);
            dialogoPrevencion.show(getFragmentManager(),"opcion dialogo");
        });

    }

    private void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(intent);
    }

}
