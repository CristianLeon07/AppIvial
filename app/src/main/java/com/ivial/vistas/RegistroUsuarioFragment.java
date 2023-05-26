package com.ivial.vistas;

import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.ivial.BD.AdminSQLiteOpenHelper;
import com.ivial.R;
import com.ivial.Util.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistroUsuarioFragment extends Fragment {


    View view;
    /**
     * Declaracion de variable de tipo Utils para tener acceso al dialogo informativo
     */
    private Utils metodoReutilizable = new Utils();

    /**
     * Declaracion de variable de tipo MainActivity donde se soportara el fragment
     */
    private MainActivity mainActivity;

    /**
     * Declaracion de variable de tipo AccesoFragment para obtener la referencia del fragment a donde se redirige una ves echo el registro
     */
    private AccesoFragment accesoFragment;

    private EditText etUsuario, etNombre, etCorreo, etContrasena, etConfirmarContrasena;

    /**
     * Declaracion de variable de tipo Button.
     */
    private Button btnRegistrar;
    private  TextView btnAtras;


    /**
     * Declaracion de variable de tipo String.
     */
    String currentDateandTime = "";

    private AdminSQLiteOpenHelper db;

    // endregion

    // region creacion de costructores del fragment

    public RegistroUsuarioFragment(MainActivity mainActivity, AccesoFragment accesoFragment) {
        this.mainActivity = mainActivity;
        this.accesoFragment = accesoFragment;
    }

    public RegistroUsuarioFragment() {
    }

    // endregion

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstacianeStatus) {
        view = inflater.inflate(R.layout.fragment_registro, container, false);
        initVariables();
        initEvents();
        return view;
    }

    // region metodo inicializacion de variables y obtencion de la fecha en la que se hace el registro
    private void initVariables() {


        etUsuario = view.findViewById(R.id.etUsuario);
        etNombre = view.findViewById(R.id.etNombre);
        etCorreo = view.findViewById(R.id.etCorreo);
        etContrasena = view.findViewById(R.id.etContrasena);
        etConfirmarContrasena = view.findViewById(R.id.etConfirmarContrasena);
        btnRegistrar = view.findViewById(R.id.btnRegistrar);
        btnAtras = view.findViewById(R.id.btnAtras);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        currentDateandTime = simpleDateFormat.format(new Date());

    }
    // endregion

    // region metodo de eventos de escucha
    private void initEvents() {
        btnAtras.setOnClickListener(v -> Utils.loadFragment(mainActivity, new AccesoFragment(mainActivity), "Atras"));

        btnRegistrar.setOnClickListener(v -> registrar());

    }
    // endregion

    // region metodo de registro, donde se inserta datos a la bd y hacen las validacions correspondiente

    private void registrar() {
        db = new AdminSQLiteOpenHelper(getContext());
        String regUsuario = etUsuario.getText().toString().trim();
        String regName = etNombre.getText().toString().trim();
        String regEmail = etCorreo.getText().toString().trim();
        String regPass = etContrasena.getText().toString().trim();
        String regConfiContrasena = etConfirmarContrasena.getText().toString().trim();
        String regCreateAd = currentDateandTime;

        try {
            Pattern patternName = Pattern.compile("[a-z A-Z]{1,30}");
            Matcher matcherName = patternName.matcher(regName);
            boolean nombreRegex = matcherName.matches(); // Devuelve true si el textToSearch coincide exactamente con la expresión regular
            if (!nombreRegex) {
                metodoReutilizable.mostrarAlertDialogo("El campo Nombre no admite valores numericos", false, this);
                throw new Exception("No se puede guardar este nombre");
            }

            if (regUsuario.equals("") || regName.equals("") || regEmail.equals("") || regPass.equals("") || regConfiContrasena.equals("")) {
                metodoReutilizable.mostrarAlertDialogo("Campos vacios, por favor ingrese los datos", false, this);
                return;
            } else {

                boolean verificar = db.verificarUsuario(regEmail);
                if (this.isEmail(etCorreo.getText().toString())) {
                    if (etContrasena.length() >= 5) {
                        if (regPass.equals(regConfiContrasena)) {
                            if (!verificar) {
                                boolean insert = db.registrarUsuario(regUsuario,regName, regEmail, regPass , regConfiContrasena);

                                if (insert) {
                                    metodoReutilizable.mostrarAlertDialogo("Registro Exitoso", true, RegistroUsuarioFragment.this);
                                    etUsuario.setText("");
                                    etNombre.setText("");
                                    etCorreo.setText("");
                                    etContrasena.setText("");
                                    etConfirmarContrasena.setText("");


                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Utils.loadFragment(mainActivity, new AccesoFragment(mainActivity), "Acceso");
                                        }
                                    }, 3500);


                                } else {
                                    metodoReutilizable.mostrarAlertDialogo("Este Usuario no pudo ser registrado intente mas tarde", false, this);
                                }
                            } else {
                                metodoReutilizable.mostrarAlertDialogo("Este Usuario ya eta registrado, intenta con otro cuenta", false, this);
                            }

                        } else {
                            metodoReutilizable.mostrarAlertDialogo("Las contraseñas no coinciden", false, this);
                        }
                    } else {
                        metodoReutilizable.mostrarAlertDialogo("Ingrese una contraseña mas larga", false, this);
                    }
                } else {
                    metodoReutilizable.mostrarAlertDialogo("Ingrese un Correo electronico valido", false, this);
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }


    }
    // endregion

    // region metodo para valicar que el correo ingresado
    public boolean isEmail(String cadena) {
        boolean resultado;
        if (Patterns.EMAIL_ADDRESS.matcher(cadena).matches()) {
            resultado = true;
        } else {
            resultado = false;
        }
        return resultado;
    }
    // endregion

}

