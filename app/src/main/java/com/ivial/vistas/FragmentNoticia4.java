package com.ivial.vistas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.ivial.R;
import com.ivial.Util.Utils;

public class FragmentNoticia4 extends Fragment {
    View view;
    private RelativeLayout btnr_regresar;
    private  MainActivity mainActivity;
    private  PrincipalFragment principalFragment;

    public FragmentNoticia4() {
    }
    public FragmentNoticia4(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstacianeStatus) {
        view = inflater.inflate(R.layout.fragment_noticia4, container, false);
        initVariables();
        initEvents();
        return view;
    }
    private void initVariables(){
        btnr_regresar = view.findViewById(R.id.btnr_regresar);
    }
    private  void initEvents(){
        btnr_regresar.setOnClickListener(v -> Utils.loadFragment(mainActivity, new PrincipalFragment(mainActivity),"atras"));
    }

}