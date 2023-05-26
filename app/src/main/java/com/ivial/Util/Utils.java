package com.ivial.Util;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.ivial.R;

public class Utils {

    public  static  String emailRegistrado = "";

    // region metodo donde esta la estructura de cargar los fragments

    public static void loadFragment (FragmentActivity activity, Fragment fragment, String tag) {
        if (fragment != null) {
            activity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contenedor_acceso, fragment, tag)
                    .addToBackStack(tag)
                    .commitAllowingStateLoss();
        }
    }
    public void mostrarAlertDialogo(String titulo, boolean exitoso, Fragment fragment) {
        AlertDialogo alertDialogo = new AlertDialogo(titulo, exitoso);
        alertDialogo.setCancelable(true);
        alertDialogo.show(fragment.getFragmentManager(), "opciones dialog");
    }
}
