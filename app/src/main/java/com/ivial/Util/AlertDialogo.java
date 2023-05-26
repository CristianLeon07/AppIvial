package com.ivial.Util;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.airbnb.lottie.LottieAnimationView;
import com.ivial.R;
import com.uncopt.android.widget.text.justify.JustifiedTextView;

public class AlertDialogo extends DialogFragment {

    //region declaracion de variables
    /**
     * Declaracion de variable de tipo view que retornara la vista del dialogo
     */
    private View view;

    /**
     * Declaracion de variable de tipo TextView
     */
    private JustifiedTextView tvTitle;

    /**
     * Declaracion de variable de tipo ImageView
     */
    private ImageView btnCancelar;

    /**
     * Declaracion de variable de tipo LottieAnimationView para las animaciones
     */
    private LottieAnimationView lottieExitoso,lottieError;

    /**
     * Declaracion de variable de tipo Stirng
     */
    private String title;

    /**
     * Declaracion de variable de tipo boolean
     */
    private boolean exitoso;

    // endregion

    //region declaracion de constructor

    public AlertDialogo(String title, boolean exitoso) {
        this.title = title;
        this.exitoso = exitoso;
    }
    // endregion


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.alert_dialogo, container);
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.borders_dialogo_trasparente);
        initVariables();
        lottie();
        closed();

        return view;
    }

    // region metod de inicializacion y referencia de variables
    private void initVariables() {
        tvTitle = view.findViewById(R.id.tvTitle);
        lottieExitoso = view.findViewById(R.id.lottieExitoso);
        lottieError = view.findViewById(R.id.lottieError);
        btnCancelar = view.findViewById(R.id.btnCancelar);
        btnCancelar.setVisibility(View.GONE);
        tvTitle.setText(title);
    }
    // endregion

    // region validacion de lottie a mostras en el dialogo
    private void lottie() {
        if (!exitoso) {
            lottieExitoso.setVisibility(View.GONE);
            lottieError.setVisibility(View.VISIBLE);
        } else {
            lottieExitoso.setVisibility(View.VISIBLE);
        }
    }
    // endregion

    // region metodo para cerrar el dialogo despues de cierto tiempo
    private void closed() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (AlertDialogo.this != null) {
                    AlertDialogo.this.dismiss();
                }
            }
        }, 3000);
    }
    // endregion


}
