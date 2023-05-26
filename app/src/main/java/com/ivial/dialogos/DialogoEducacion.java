package com.ivial.dialogos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

import com.ivial.R;
import com.ivial.vistas.PrincipalFragment;

public class DialogoEducacion extends DialogFragment {
    private View view;
    private PrincipalFragment principalFragment;
    private ImageView btn_contenedor_señales_preventivas;
    private ConstraintLayout contenedor_de_curvas;
    private ConstraintLayout contenedor_de_altos;
    private  ImageView btn_contenedor_señales_resctrictivas;
    private ConstraintLayout contenedor_de_servicios;
    private  ImageView btn_contenedor_señales_servicios;


    public DialogoEducacion(PrincipalFragment principalFragment) {
        this.principalFragment = principalFragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.alert_dialogo_educacion, container);
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.borders_trasnparente);
        init();
        initEvent();
        return view;
    }
    private void init(){
        btn_contenedor_señales_preventivas= view.findViewById(R.id.btn_contenedor_señales_preventivas);
        contenedor_de_curvas= view.findViewById(R.id.contenedor_de_curvas);
        contenedor_de_altos= view.findViewById(R.id.contenedor_de_altos);
        btn_contenedor_señales_resctrictivas = view.findViewById(R.id.btn_contenedor_señales_resctrictivas);
        contenedor_de_servicios = view.findViewById(R.id.contenedor_de_servicios);
        btn_contenedor_señales_servicios= view.findViewById(R.id.btn_contenedor_señales_servicios);


    }
    private void initEvent(){
        btn_contenedor_señales_preventivas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contenedor_de_curvas.getVisibility() == View.GONE){
                    contenedor_de_curvas.setVisibility(View.VISIBLE);
                }else {
                    contenedor_de_curvas.setVisibility(View.GONE);
                }
            }
        });
        btn_contenedor_señales_resctrictivas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contenedor_de_altos.getVisibility() == View.GONE){
                    contenedor_de_altos.setVisibility(View.VISIBLE);
                }else {
                    contenedor_de_altos.setVisibility(View.GONE);
                }
            }
        });

        btn_contenedor_señales_servicios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contenedor_de_servicios.getVisibility() == View.GONE){
                    contenedor_de_servicios.setVisibility(View.VISIBLE);
                }else {
                    contenedor_de_servicios.setVisibility(View.GONE);
                }

            }
        });

    }
}
