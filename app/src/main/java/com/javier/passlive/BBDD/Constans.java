package com.javier.passlive.BBDD;

public class Constans {

    //Nombre de bases de datos, versión y nombre de tabla
    public static final String BD_NAME= "PASSLIVE_BBDD";
    public static final int BD_VERSION = 2;
    public static final String TABLE_NAME= "ACCOUNT";

    //Establecemos campos de tablas

    public static final String C_ID = "ID";
    public static final String C_TITTLE = "TITULO";
    public static final String C_ACCOUNT = "CUENTA";
    public static final String C_USERNAME = "NOMBRE_USUARIO";
    public static final String C_PASSWORD = "CONTRASEÑA";
    public static final String C_WEBSITES = "SITIO_WEB";
    public static final String C_NOTES = "NOTA";
    public static final String C_IMAGE = "IMAGENES";
    public static final String C_RECORD_TIME = "TIEMPO_REGISTRO";
    public static final String C_UPDATE_TIME = "TIEMPO_ACTUALIZACION";


    //Realizamos consulta para crear la tabla

    public static final String CREATE_TABLE = "CREATE TABLE"+ TABLE_NAME
            + "("
            + C_ID + "INTEGER PRIMARY KEY AUTOINCREMENT, "
            + C_TITTLE + " TEXT,"
            + C_ACCOUNT + " TEXT,"
            + C_USERNAME + " TEXT,"
            + C_PASSWORD + " TEXT,"
            + C_WEBSITES + " TEXT,"
            + C_NOTES + " TEXT,"
            + C_IMAGE + " TEXT,"
            + C_RECORD_TIME + " TEXT,"
            + C_UPDATE_TIME + " TEXT"
            + ")";
}
