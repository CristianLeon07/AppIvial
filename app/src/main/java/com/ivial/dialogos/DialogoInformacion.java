package com.ivial.dialogos;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.ivial.Util.Utils;
import com.ivial.vistas.FragmentNoticia;
import com.ivial.vistas.FragmentNoticia1;
import com.ivial.vistas.FragmentNoticia2;
import com.ivial.vistas.FragmentNoticia3;
import com.ivial.vistas.FragmentNoticia4;
import com.ivial.R;
import com.ivial.vistas.MainActivity;
import com.ivial.vistas.PrincipalFragment;

import org.imaginativeworld.whynotimagecarousel.CarouselItem;
import org.imaginativeworld.whynotimagecarousel.ImageCarousel;
import org.imaginativeworld.whynotimagecarousel.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class DialogoInformacion extends DialogFragment {
    private View view;

    private PrincipalFragment principalFragment;
    List<CarouselItem> list = new ArrayList<>();
    private ImageCarousel caruselNoticias;
    private MainActivity mainActivity;

    public DialogoInformacion(MainActivity mainActivity,PrincipalFragment principalFragment) {
        this.mainActivity =mainActivity;
        this.principalFragment = principalFragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.alert_dialogo_informacion, container);
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.borders_trasnparente);
        initVariables();
        init();
        initEvent();
        return view;
    }
    private void initVariables(){
        caruselNoticias= view.findViewById(R.id.caruselNoticias);
    }
    private void init(){
        list.add(new CarouselItem(R.drawable.accidente));
        list.add(new CarouselItem(R.drawable.accidente2));
        list.add(new CarouselItem(R.drawable.accidente3));
        list.add(new CarouselItem(R.drawable.accidente4));
        list.add(new CarouselItem(R.drawable.accidente5));
        caruselNoticias.addData(list);
    }
    private void initEvent(){
        caruselNoticias.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(int i, @NonNull CarouselItem carouselItem) {
                //Intent intent = new Intent(Intent.ACTION_DIAL);
                switch (i){
                    case 0:
                        Utils.loadFragment(mainActivity, new FragmentNoticia(mainActivity),"noticia");
                        break;
                    case 1:
                        Utils.loadFragment(mainActivity, new FragmentNoticia1(mainActivity),"noticia1");
                        break;
                    case 2:
                        Utils.loadFragment(mainActivity, new FragmentNoticia2(mainActivity),"noticia2");

                        break;
                    case 3:
                        Utils.loadFragment(mainActivity, new FragmentNoticia3(mainActivity),"noticia3");
                        break;
                    case 4:
                        Utils.loadFragment(mainActivity, new FragmentNoticia4(mainActivity),"noticia4");
                        break;
                }
                dismiss();
                //startActivity(intent);

            }

            @Override
            public void onLongClick(int i, @NonNull CarouselItem carouselItem) {

            }
        });

    }
}
