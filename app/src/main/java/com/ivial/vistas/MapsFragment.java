package com.ivial.vistas;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationRequest;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.ivial.R;
import com.ivial.Util.Utils;

public class MapsFragment extends Fragment {
    private double gps_LATITUD;
    private double gps_LONGITUD;
    private EditText tv_ubicacion_actual;
    private ImageView btn_regresar_mapa;
    private MaterialButton btn_continuar;
    private AccesoFragment accesoFragment;
    private PrincipalFragment principalFragment;
    private String tipo;
    private GoogleMap googleMap;
    private MapView mapFragment;
    private MainActivity mainActivity;
    private static final int BLINK_DURATION = 1000; // Duración de cada parpadeo en milisegundos
    private static final float ALPHA_VISIBLE = 1.0f; // Valor de opacidad cuando el marcador es visible
    private static final float ALPHA_INVISIBLE = 0.0f; // Valor de opacidad cuando el marcador es invisible
    private AlphaAnimation mBlinkAnimation;

    public MapsFragment(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public MapsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        mapFragment = (MapView) view.findViewById(R.id.map);
        mapFragment.onCreate(savedInstanceState);
        mapFragment.onResume();
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                try {
                    init_variables();
                    init_event();
                    final com.google.android.gms.location.LocationRequest locationRequest = com.google.android.gms.location.LocationRequest.create();
                    locationRequest.setInterval(10000);
                    locationRequest.setFastestInterval(3000);
                    locationRequest.setPriority(LocationRequest.QUALITY_HIGH_ACCURACY);
                    if (Build.VERSION.SDK_INT >= 33) {
                        principalFragment.checkPermissions(getContext());
                    }

                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    LocationServices.getFusedLocationProviderClient(getContext()).requestLocationUpdates(locationRequest, new LocationCallback() {
                        @Override
                        public void onLocationResult(LocationResult locationResult) {
                            super.onLocationResult(locationResult);
                            LocationServices.getFusedLocationProviderClient(getContext()).removeLocationUpdates(this);
                            if (locationResult != null && locationResult.getLocations().size() > 0) {
                                int position_ultima_location = locationResult.getLocations().size() - 1;
                                gps_LATITUD = locationResult.getLocations().get(position_ultima_location).getLatitude();
                                gps_LONGITUD = locationResult.getLocations().get(position_ultima_location).getLongitude();

                                setupBlinkAnimation();
                               // mMarker.setAnimation(mBlinkAnimation);
                                //mMarker.startAnimation();

                                LatLng ubicacion = new LatLng(gps_LATITUD, gps_LONGITUD);
                                Marker markerr = googleMap.addMarker(new MarkerOptions().position(ubicacion).draggable(true).title("Mi Ubicacion").snippet(""));
                                markerr.showInfoWindow();


                                LatLng ubicacion1 = new LatLng( 8.250525,-73.358201);
                                Marker markerUbicacion1 = googleMap.addMarker(new MarkerOptions().position(ubicacion1).draggable(true).title("Cruce primera de mayo").snippet("Alto flujo de accidente"));
                                markerUbicacion1.showInfoWindow();

                                LatLng ubicacion2 = new LatLng( 8.238524, -73.353341);
                                Marker markerUbicacion2 = googleMap.addMarker(new MarkerOptions().position(ubicacion2).draggable(true).title("San Agustín").snippet("Alto flujo de accidente"));
                                markerUbicacion2.showInfoWindow();

                                LatLng ubicacion3 = new LatLng(8.248713, -73.359173);
                                Marker markerUbicacion3 = googleMap.addMarker(new MarkerOptions().position(ubicacion3).draggable(true).title("Intersección del quiosco").snippet("Precaucion"));
                                markerUbicacion3.showInfoWindow();

                                LatLng ubicacion4 = new LatLng( 8.269472, -73.366316);
                                Marker markerUbicacion4 = googleMap.addMarker(new MarkerOptions().position(ubicacion4).draggable(true).title("Puente de la sal").snippet("Alto flujo de accidente"));
                                markerUbicacion4.showInfoWindow();

                                LatLng ubicacion5 = new LatLng( 8.267454, -73.363865);
                                Marker markerUbicacion5 = googleMap.addMarker(new MarkerOptions().position(ubicacion5).draggable(true).title("Santa Clara").snippet("Alto flujo de accidente"));
                                markerUbicacion5.showInfoWindow();

                                LatLng ubicacio6 = new LatLng(8.259373, -73.359429);
                                Marker markerUbicacion6 = googleMap.addMarker(new MarkerOptions().position(ubicacio6).draggable(true).title("La primavera").snippet("Transite con preacucion"));
                                markerUbicacion6.showInfoWindow();

                                LatLng ubicacion7 = new LatLng( 8.254828, -73.359402);
                                Marker markerUbicacion7 = googleMap.addMarker(new MarkerOptions().position(ubicacion7).draggable(true).title("Avenida Francisco Fernández de contreras").snippet("Sea prudente"));
                                markerUbicacion7.showInfoWindow();

                                LatLng ubicacion8 = new LatLng( 8.249647, -73.357342);
                                Marker markerUbicacion8 = googleMap.addMarker(new MarkerOptions().position(ubicacion8).draggable(true).title("Avenida Circunvalar").snippet("Alto flujo de accidente"));
                                markerUbicacion8.showInfoWindow();

                                LatLng ubicacion9 = new LatLng(8.233812, -73.340798);
                                Marker markerUbicacion9 = googleMap.addMarker(new MarkerOptions().position(ubicacion9).draggable(true).title("Terminal de transporte").snippet("Precaucion"));
                                markerUbicacion9.showInfoWindow();

                                LatLng ubicacion10 = new LatLng( 8.224518, -73.336971);
                                Marker markerUbicacion10 = googleMap.addMarker(new MarkerOptions().position(ubicacion10).draggable(true).title("Acolsure").snippet("Alto flujo de accidente"));
                                markerUbicacion10.showInfoWindow();

                                LatLng ubicacion11 = new LatLng( 8.235728, -73.353509);
                                Marker markerUbicacion11 = googleMap.addMarker(new MarkerOptions().position(ubicacion11).draggable(true).title("Zona céntrica").snippet("Alto flujo de accidente"));
                                markerUbicacion11.showInfoWindow();

                                googleMap.moveCamera(CameraUpdateFactory.newLatLng(ubicacion));
                                googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                            }
                        }
                    }, Looper.myLooper());
                }catch (Exception e){
                    Log.e("error",e.getMessage());
                }
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mapFragment.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapFragment.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapFragment.onDestroy();
    }

    private void init_variables() {
        btn_regresar_mapa = getView().findViewById(R.id.btn_regresar_mapa);

    }

    private void init_event() {

        btn_regresar_mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.loadFragment(mainActivity, new PrincipalFragment(mainActivity), "accedo fragment");
            }
        });
    }
    private void setupBlinkAnimation() {
        mBlinkAnimation = new AlphaAnimation(ALPHA_VISIBLE, ALPHA_INVISIBLE);
        mBlinkAnimation.setDuration(BLINK_DURATION);
        mBlinkAnimation.setRepeatMode(Animation.REVERSE);
        mBlinkAnimation.setRepeatCount(Animation.INFINITE);
    }


}