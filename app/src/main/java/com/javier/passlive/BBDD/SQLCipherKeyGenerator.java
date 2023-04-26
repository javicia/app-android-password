package com.javier.passlive.BBDD;

import android.content.Context;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;

import java.nio.charset.StandardCharsets;
import java.security.KeyStore;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class SQLCipherKeyGenerator {
    private static final String ANDROID_KEYSTORE = "AndroidKeyStore";
    private static final String KEY_ALIAS = "AliasPassLife";
    private static final String ENCRYPTION_ALGORITHM = KeyProperties.KEY_ALGORITHM_AES;
    private static final String BLOCK_MODE = KeyProperties.BLOCK_MODE_CBC;
    private static final String PADDING = KeyProperties.ENCRYPTION_PADDING_PKCS7;
    private static final int KEY_SIZE = 256;

    //Método para obtener clave
    public static SecretKey getSecretKey() throws Exception {
        KeyStore keyStore = KeyStore.getInstance(ANDROID_KEYSTORE);
        keyStore.load(null);

        if (!keyStore.containsAlias(KEY_ALIAS)) {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(ENCRYPTION_ALGORITHM, ANDROID_KEYSTORE);
            KeyGenParameterSpec keySpec = new KeyGenParameterSpec.Builder(KEY_ALIAS, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(BLOCK_MODE)
                    .setEncryptionPaddings(PADDING)
                    .setKeySize(KEY_SIZE)
                    .build();
            keyGenerator.init(keySpec);
            keyGenerator.generateKey();
        }

        return (SecretKey) keyStore.getKey(KEY_ALIAS, null);
    }
    //Método para encriptar clave
    public static byte[] encrypt(Context context, byte[] data) throws Exception {
        SecretKey secretKey = getSecretKey();

        Cipher cipher = Cipher.getInstance(String.format("%s/%s/%s", ENCRYPTION_ALGORITHM, BLOCK_MODE, PADDING));
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] iv = cipher.getIV();
        byte[] encryptedData = cipher.doFinal(data);

        byte[] result = new byte[iv.length + encryptedData.length];
        System.arraycopy(iv, 0, result, 0, iv.length);
        System.arraycopy(encryptedData, 0, result, iv.length, encryptedData.length);

        return result;
    }
    //Método para desencriptar clave
    public static byte[] decrypt(Context context, byte[] encryptedData) throws Exception {
        SecretKey secretKey = getSecretKey();

        Cipher cipher = Cipher.getInstance(String.format("%s/%s/%s", ENCRYPTION_ALGORITHM, BLOCK_MODE, PADDING));

        int ivLength = cipher.getBlockSize();
        byte[] iv = new byte[ivLength];
        System.arraycopy(encryptedData, 0, iv, 0, ivLength);

        byte[] data = new byte[encryptedData.length - ivLength];
        System.arraycopy(encryptedData, ivLength, data, 0, data.length);

        cipher.init(Cipher.DECRYPT_MODE, secretKey, new javax.crypto.spec.IvParameterSpec(iv));
        byte[] decryptedData = cipher.doFinal(data);

        return decryptedData;
    }

    public static byte[] encryptString(Context context, String text) throws Exception {
        return encrypt(context, text.getBytes(StandardCharsets.UTF_8));
    }

    public static String decryptString(Context context, byte[] encryptedData) throws Exception {
        byte[] decryptedData = decrypt(context, encryptedData);
        return new String(decryptedData, StandardCharsets.UTF_8);
    }
}



