package com.javier.passlive.BBDD;

public class Constans {

    //Nombre de bases de datos, versión y nombre de tabla
    public static final String BD_NAME= "PASSLIVE_BBDD";
    public static final int BD_VERSION = 2;



    //Campos de gategoría
    public static final String TABLE_CATEGORY = "CATEGORIA";
    public static final String ID_CATEGORY ="ID";
    public static final String ACCOUNT_WEB ="SITIOS_WEB";
    public static final String ACCOUNT_BANK ="CUENTAS_BANCARIAS";
    public static final String CARD ="TARJETAS_CREDITO";




    //Establecemos campos de tablas CUENTAS WEB
    public static final String TABLE_ACCOUNT_WEB= "ACCOUNT_WEB";
    public static final String W_ID = "ID";
    public static final String W_TITTLE = "TITULO";
    public static final String W_ACCOUNT = "CUENTA";
    public static final String W_USERNAME = "NOMBRE_USUARIO";
    public static final String W_PASSWORD = "CONTRASEÑA";
    public static final String W_WEBSITES = "SITIO_WEB";
    public static final String W_NOTES = "NOTA";
    public static final String W_IMAGE = "IMAGENES";
    public static final String W_RECORD_TIME = "TIEMPO_REGISTRO";
    public static final String W_UPDATE_TIME = "TIEMPO_ACTUALIZACION";
    public static final String W_ID_CATEGORY = "ID_CATEGORIA";

    //Campos cuentas bancarias
    public static final String TABLE_ACCOUNT_BANK= "ACCOUNT_BANK";
    public static final String B_ID_BANK ="ID";
    public static final String B_TITLE_BANK ="TITULO";
    public static final String B_BANK = "BANCO";
    public static final String B_ACCOUNT_BANK ="CUENTA_BANCARIA";
    public static final String B_NUMBER = "NUMERO_CUENTA";
    public static final String B_WEBSITES = "SITIO_WEB";
    public static final String B_NOTES ="NOTAS";
    public static final String B_IMAGE ="IMAGENES";
    public static final String B_RECORD_TIME = "TIEMPO_REGISTRO";
    public static final String B_UPDATE_TIME = "TIEMPO_ACTUALIZACION";
    public static final String B_ID_CATEGORY = "ID_CATEGORIA_BANCO";



    //Campos tarjetas de crédito
    public static final String TABLE_CARD= "TARJETA_CREDITO";
    public static final String ID_CARD ="ID";
    public static final String C_TITLE_CARD ="TITULO";
    public static final String C_USERNAME="NOMBRE_DEL_TITULAR";
    public static final String C_NUMBER ="NUMERO_TARJETA";
    public static final String C_DATE ="FECHA_CADUCIDAD";
    public static final String C_CVC ="CODIGO_SEGURIDAD";
    public static final String C_NOTES ="NOTAS";
    public static final String C_IMAGE ="IMAGENES";
    public static final String C_RECORD_TIME = "TIEMPO_REGISTRO";
    public static final String C_UPDATE_TIME = "TIEMPO_ACTUALIZACION";
    public static final String C_ID_CATEGORY = "ID_CATEGORIA_TARJETA";




    //Consulta tabla categorias
    public static final String CREATE_TABLE_CATEGORY = "CREATE TABLE " + TABLE_CATEGORY
            +"("
            +ID_CATEGORY + "INTEGER PRIMARY KEY, "
            +ACCOUNT_WEB + " TEXT, "
            +ACCOUNT_BANK + " TEXT, "
            +CARD + " INTEGER "
            + ")";




    //Realizamos consulta para crear la tabla Account_web
    public static final String CREATE_TABLE_ACCOUNT_WEB = "CREATE TABLE " + TABLE_ACCOUNT_WEB
            + "("
            + W_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + W_TITTLE + " TEXT,"
            + W_ACCOUNT + " TEXT,"
            + W_USERNAME + " TEXT,"
            + W_PASSWORD + " TEXT,"
            + W_WEBSITES + " TEXT,"
            + W_NOTES + " TEXT,"
            + W_IMAGE + " TEXT,"
            + W_RECORD_TIME + " TEXT,"
            + W_UPDATE_TIME + " TEXT,"
            + W_ID_CATEGORY + " INTEGER, "
            + "FOREIGN KEY(" + W_ID_CATEGORY + ") REFERENCES " + TABLE_CATEGORY + "(" + ID_CATEGORY + ")"
            + ")";

    //Realizamos consulta para crear la tabla Account_bank
    public static final String CREATE_TABLE_ACCOUNT_BANK = "CREATE TABLE " + TABLE_ACCOUNT_BANK
            + "("
            + B_ID_BANK + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + B_TITLE_BANK + " TEXT,"
            + B_BANK + " TEXT,"
            + B_ACCOUNT_BANK + " TEXT,"
            + B_NUMBER + " TEXT,"
            + B_WEBSITES + " TEXT,"
            + B_NOTES + " TEXT,"
            + B_IMAGE + " TEXT,"
            + B_RECORD_TIME + " TEXT, "
            + B_UPDATE_TIME + " TEXT,"
            + B_ID_CATEGORY + " INTEGER, "
            + "FOREIGN KEY(" + B_ID_CATEGORY + ") REFERENCES " + TABLE_CATEGORY + "(" + ID_CATEGORY + ")"
            + ")";

    //Realizamos consulta para crear la tabla CARD
    public static final String CREATE_TABLE_CARD = "CREATE TABLE " + TABLE_CARD
            + "("
            + ID_CARD +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + C_TITLE_CARD + " TEXT,"
            + C_USERNAME + " TEXT,"
            + C_NUMBER + " TEXT,"
            + C_DATE + " TEXT,"
            + C_CVC + " TEXT,"
            + C_NOTES + " TEXT,"
            + C_IMAGE + " TEXT,"
            + C_RECORD_TIME + " TEXT,"
            + C_UPDATE_TIME + " TEXT,"
            + C_ID_CATEGORY  + " INTEGER, "
            + "FOREIGN KEY(" +  C_ID_CATEGORY + ") REFERENCES " + TABLE_CATEGORY + "(" + ID_CATEGORY + ")"
            + ")";
}
