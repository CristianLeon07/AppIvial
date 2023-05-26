package com.ivial.Util;

import static com.ivial.vistas.PrincipalFragment.ACCESS_FINE_LOCATION_INTENT;
import static com.ivial.vistas.PrincipalFragment.BROADCAST_ACTION;
import static com.ivial.vistas.PrincipalFragment.REQUEST_CHECK_SETTINGS;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.Objects;

public class PermisosGPS extends Fragment {

    @RequiresApi(api = 33)
    public void checkPermissions(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestLocationPermission();
            } else {
                openDialog(context);
            }
        } else {
            Toast.makeText(context, "GPS está habilitado en su dispositivo", Toast.LENGTH_SHORT).show();
            openDialog(context);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestNotificationPermission();
            }
        }
    }

    @RequiresApi(api = 33)
    private void requestNotificationPermission() {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.POST_NOTIFICATIONS)) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.POST_NOTIFICATIONS}, 3);
        }
    }

    private void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    ACCESS_FINE_LOCATION_INTENT);

        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    ACCESS_FINE_LOCATION_INTENT);
        }
    }

    public void openDialog(Context context) {
        LocationRequest mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(30 * 1000);
        mLocationRequest.setFastestInterval(5 * 1000); //5 sec Time interval for location update
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY); //Setting priority of Location request to high

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);

        Task<LocationSettingsResponse> task = LocationServices.getSettingsClient(context).checkLocationSettings(builder.build());

        task.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(Task<LocationSettingsResponse> task) {
                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    // All location settings are satisfied. The client can initialize location
                    // requests here.
                } catch (ApiException exception) {
                    switch (exception.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            // Location settings are not satisfied. But could be fixed by showing the
                            // user a dialog.
                            try {
                                // Cast to a resolvable exception.
                                ResolvableApiException resolvable = (ResolvableApiException) exception;
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult(). fghgffx
                                resolvable.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS);
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            } catch (ClassCastException e) {
                                // Ignore, should be an impossible error.
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            Toast.makeText(context, "GPS está habilitado en su dispositivo", Toast.LENGTH_SHORT).show();
                            // Location settings are not satisfied. However, we have no way to fix the
                            // settings so we won't show the dialog.

                            break;
                    }
                }
            }
        });
    }

    // Broadcast  para verificar el estado del GPSon
    public BroadcastReceiver gpsLocationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Objects.requireNonNull(intent.getAction()).matches(BROADCAST_ACTION)) {
                LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                // Compruebe si el GPS está activado o desactivado
                if (locationManager != null) {
                    if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        Toast.makeText(context, "GPS está habilitado en su dispositivo", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "GPS está habilitado en su dispositivo", Toast.LENGTH_SHORT).show();
                        // Si el GPS está apagado, muestre el diálogo de ubicación
                        new Handler().postDelayed(sendUpdatesToUI, 10);
                    }
                }
            }
        }
    };
    private Runnable sendUpdatesToUI = new Runnable() {
        public void run() {
            openDialog(getContext());
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        // Todos los cambios requeridos se realizaron con éxito.
                        Toast.makeText(getContext(), "GPS habilitado", Toast.LENGTH_LONG).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        // Se le pidió al usuario que cambiara la configuración, pero decidió no hacerlo.
                        Toast.makeText(getContext(), "Pailas", Toast.LENGTH_LONG).show();
                        Toast.makeText(getContext(), "GPS no esta habilitado", Toast.LENGTH_LONG).show();
                        //openDialog();
                        break;
                    default:
                        break;
                }
                Toast.makeText(getContext(), "GPS no esta habilitado", Toast.LENGTH_LONG).show();
                break;
        }
    }

}

