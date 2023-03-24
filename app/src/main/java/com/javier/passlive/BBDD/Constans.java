package com.javier.passlive.BBDD;

public class Constans {

    //Nombre de bases de datos, versión y nombre de tabla

    public static final String BD_NAME="PASSLIVE_BBDD";
    public static final int BD_VERSION = 1;
    public static final String TABLE_NAME="PASSLIVE_TABLE";

    //Establecemos campos de tablas

    public static final String C_ID = "ID";
    public static final String C_TITTLE = "TITULO";
    public static final String C_ACCOUNT = "CUENTA";
    public static final String C_USERNAME = "NOMBRE DE USUARIO";
    public static final String C_PASSWORD = "CONTRASEÑA";
    public static final String C_WEBSITES = "SITIO WEB";
    public static final String C_NOTES = "NOTAS";

    public static final String C_IMAGE = "IMÁGENES";
    public static final String C_RECORD_TIME = "TIEMPO DE REGISTRO";
    public static final String C_UPDATE_TIME = "TIEMPO DE ACTUALIZACION";


    //Realizamos consulta para crear la tabla

    public static final String CREATE_TABLE = "CREATE TABLE"+ TABLE_NAME
            + "("
            + C_ID + "INTEGER PRIMARY KEY AUTOINCREMENT, "
            + C_TITTLE + " TEXT, "
            + C_ACCOUNT + " TEXT, "
            + C_USERNAME + " TEXT, "
            + C_PASSWORD + " TEXT, "
            + C_WEBSITES + " TEXT, "
            + C_NOTES + " TEXT, "
            + C_IMAGE + " TEXT, "
            + C_RECORD_TIME + " TEXT, "
            + C_UPDATE_TIME + " TEXT "
            + ")";
}
