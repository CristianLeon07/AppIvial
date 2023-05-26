package com.ivial.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {
    //creamos la base de datos
    public AdminSQLiteOpenHelper(Context context) {
        super(context, "ivial.db",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table usuarios( usuario text primary key ,nombre text,correo text,contrasena text,conficontraseña text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        onCreate(db);
    }
    public boolean registrarUsuario(String usuario,String nombre, String correo , String contraseña,String conficontraseña)  {
        //creamos un metodo registrar, donde le pasamos todos los parametros que va a recibir para registrarlos en la tabla.
        //con el getwritabledatabase obtenemos la base de datos en modo escritura
        //creamos un objeto de content values, llamado registro, que es quien va a retener los datos.
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put("usuario", usuario);
        registro.put("nombre", nombre);
        registro.put("correo", correo);
        registro.put("contrasena", contraseña);
        registro.put("conficontraseña", conficontraseña);

        //usuario text primary key,correo text,nidentidad text,nombre text,apellidos text,telefono text,contrasena text
        //cuando insertamos guardamos en un objeto tipo Long,mandamos la tabla, y null para en caso que el contentvalues este vacio le mande null
        //si el devuelve un numero igu a -1 retorne falso, si es diferente retorne true
        Long results = db.insert("usuarios", null, registro);
        if (results==-1){
            return false;
        }else
            return true;
    }
    //para login verificar usuario existente

    public boolean verificarUsuario(String usuario){
        //creamos un metodo verificarUsuario que recibe como parametro el usuario
        //creamos una estrucutra de control cursor para recorrer la base de datos y con el rawquery hacemos la consulta
        //nos aseguramos que existe al menos un reigstro con el > 0
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from usuarios where usuario=?",new String[]{usuario});
        if (cursor.getCount()>0){
            return true;
        }else{
            return false;
        }
    }
    //para login verificar las credenciales
    public boolean verificarCredenciales(String usuario, String contrasena){
        SQLiteDatabase bd = this.getWritableDatabase();
        Cursor cursor = bd.rawQuery("select * from usuarios where usuario=? and contrasena=?",new String[]{usuario,contrasena});
        if (cursor.getCount()>0){
            return true;
        }else{
            return false;
        }
    }
   /* public boolean mostrarContraseña(String contrasena){
        SQLiteDatabase bd = this.getWritableDatabase();
        Cursor cursor = bd.rawQuery("select * from usuarios where contrasena=?",new String[]{contrasena});
        if (cursor.getCount()>0){
            return true;
        }else{
            return false;
        }
    }

    */
   public String consultarUsuario(String campo, String usuario) {
       try {
           String retorno = "";
           SQLiteDatabase db = this.getWritableDatabase();

           Cursor c = db.rawQuery("SELECT "+campo+" FROM usuarios WHERE usuario = '" + usuario + "'", null);
           if (c.getCount() > 0) {
               c.moveToFirst();
               retorno = c.getString(0);
           }
           return retorno;

       } catch (Exception e) {
           return "0";
       }
   }
    public void cerrarSesion() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.close();
    }

}
