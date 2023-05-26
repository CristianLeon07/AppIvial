package com.ivial.vistas;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.ivial.BD.AdminSQLiteOpenHelper;
import com.ivial.R;
import com.ivial.Util.Utils;

public class AccesoFragment extends Fragment {

    View view;

    private TextView tvRegistro;

    private EditText etUsuario;
    private EditText etContraseña;
    private Button iniciarSesion;
    private AdminSQLiteOpenHelper db;
    private SQLiteDatabase baseDatos;
    private TextView tvNombreApp;
    public static String nombreusuario;
    private String contrasena = "";

    public static String emailUser = "";
    private Utils metodoReutilizable = new Utils();
    private MainActivity mainActivity;
    private boolean verifyData = false;
    private Animation anim;

    //region declaracion de constructores

    /**
     * declaracion de constructor del fragment vacio
     */
    public AccesoFragment() {
    }

    /**
     * declaracion del constructor con parametros del fragment
     */

    public AccesoFragment(MainActivity accesoActivity) {
        this.mainActivity = accesoActivity;
    }
    //endregion

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstacianeStatus) {
        view = inflater.inflate(R.layout.fragment_acceso, container, false);
        initVariables();
        animacionLogo();
        initEvents();
        return view;
    }

    //region metodo donde se inicializan y bindean elementos visuales
    private void initVariables() {
        tvRegistro = view.findViewById(R.id.tvRegistro);
        tvNombreApp = view.findViewById(R.id.tvNombreApp);
        etUsuario = view.findViewById(R.id.etUsuario);
        etContraseña = view.findViewById(R.id.etContraseña);
        iniciarSesion = view.findViewById(R.id.iniciarSesion);
        db = new AdminSQLiteOpenHelper(getContext());
        baseDatos = db.getWritableDatabase();

    }
    // endregion

    // region metodo logica animacion logo
    private void animacionLogo (){
        anim = new AlphaAnimation(0.0f, 1.0f); anim.setDuration(500); //You can manage the time of the blink with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        tvNombreApp.startAnimation(anim);
    }
    // end region



    // region metodo init, donde esta la logica de las consultas, validaciones etc.
    private void init() {

        db = new AdminSQLiteOpenHelper(getContext());
        String email = etUsuario.getText().toString();
        String pass = etContraseña.getText().toString();
        if (email.equals("") || pass.equals("")) {
            metodoReutilizable.mostrarAlertDialogo("Campos vacios, por favor ingrese los datos", false, this);

        } else {
            verifyData = db.verificarCredenciales(email, pass);
            if (verifyData) {
                emailUser = email;
                metodoReutilizable.mostrarAlertDialogo("Ingreso Exitoso!!", true, this);
                Utils.emailRegistrado = email;
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Utils.loadFragment(mainActivity, new PrincipalFragment(mainActivity, AccesoFragment.this, emailUser), "home");
                    }
                }, 3000);

                etUsuario.setText("");
                etContraseña.setText("");
            } else if (email != emailUser) {

                metodoReutilizable.mostrarAlertDialogo("Verifica los datos ingresados. E intente nuevamente", false, this);

            }
        }
    }
    // endregion

    // region metodo donde estan todos los eventos de escucha ejemplo motones

    private void initEvents() {
        iniciarSesion.setOnClickListener(v -> {
            init();
        });

        tvRegistro.setOnClickListener(v -> {
            Utils.loadFragment(mainActivity, new RegistroUsuarioFragment(mainActivity, AccesoFragment.this), "IrRegistro");
        });
    }
    // endregion

}
