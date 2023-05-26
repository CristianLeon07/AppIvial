package com.ivial.dialogos;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.ivial.R;
import com.ivial.vistas.PrincipalFragment;

public class DialogoPrevencion extends DialogFragment {
    private View view;
    private PrincipalFragment principalFragment;
    private VideoView videoApp;
    private MediaController mediaController;
    private int position =0;

    public DialogoPrevencion(PrincipalFragment principalFragment) {
        this.principalFragment = principalFragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.alert_dialogo_prevencion, container);
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.borders_trasnparente);
        initVariables();
        init();
        initEvent();
        return view;
    }
    private void initVariables(){
        videoApp = view.findViewById(R.id.videoApp);
    }
    private void init(){


        String videop= "android.resource://" + getContext().getPackageName() + "/" + R.raw.video_rafa;
        Uri uri=Uri.parse(videop);
        videoApp.setVideoURI(uri);
        videoApp.start();
    }
    private void initEvent(){

    }
}
