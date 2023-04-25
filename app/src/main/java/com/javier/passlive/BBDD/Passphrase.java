package com.javier.passlive.BBDD;

import android.content.SharedPreferences;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Base64;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class Passphrase {
    private static final String ALIAS = "aliaskeystore";
    private static final String TRANSFORMATION = "AES/CBC/NoPadding";
    private static final String ANDROID_KEY_STORE = "AndroidKeyStore";
    private static final String IV_KEY = "ivkey";
    private static final String DB_KEY = "dbkey";
    private static final java.nio.charset.Charset CHARSET = java.nio.charset.Charset.forName("ISO-8859-1");
    private SharedPreferences sharedPreferences;
    private byte[] passphrase;

    public Passphrase(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public byte[] getPassphrase() throws UnrecoverableEntryException, NoSuchAlgorithmException, KeyStoreException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, IOException, InvalidAlgorithmParameterException, SignatureException, BadPaddingException, IllegalBlockSizeException, CertificateException {
        if (passphrase != null) {
            return passphrase;
        } else {
            if (this.sharedPreferences.contains(DB_KEY) && this.sharedPreferences.contains(IV_KEY)) {
                String encryptedData = this.sharedPreferences.getString(DB_KEY, null);
                byte[] iv = Base64.decode(this.sharedPreferences.getString(IV_KEY, null), Base64.DEFAULT);
                passphrase = decryptData(encryptedData.getBytes(CHARSET), iv);
                return passphrase;
            } else {
                passphrase = new byte[128];
                new java.util.Random().nextBytes(passphrase);
                String encryptedData = Base64.encodeToString(encrypt(passphrase), Base64.DEFAULT);
                this.sharedPreferences.edit().putString(DB_KEY, encryptedData).apply();
                return passphrase;
            }
        }
    }

    private byte[] encrypt(byte[] byteArray) throws UnrecoverableEntryException, NoSuchAlgorithmException, KeyStoreException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, IOException, BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException, CertificateException {

        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey());
        sharedPreferences.edit().remove(IV_KEY).apply();
        sharedPreferences.edit().putString(IV_KEY, Base64.encodeToString(cipher.getIV(), Base64.DEFAULT)).apply();
        return cipher.doFinal(byteArray);
    }

    public byte[] decryptData(byte[] encryptedData, byte[] encryptionIv) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, IOException, BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException, UnrecoverableEntryException, CertificateException, KeyStoreException {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        IvParameterSpec spec = new IvParameterSpec(encryptionIv);
        cipher.init(Cipher.DECRYPT_MODE, getSecretKey(), spec);
        return cipher.doFinal(encryptedData);
    }

    private SecretKey getSecretKey() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, KeyStoreException, CertificateException, IOException, UnrecoverableEntryException, CertificateException {
        KeyStore ks = KeyStore.getInstance(ANDROID_KEY_STORE);
        ks.load(null);
        if (ks.containsAlias(ALIAS)) {
            KeyStore.Entry entry = ks.getEntry(ALIAS, null);
            if (entry instanceof KeyStore.SecretKeyEntry) {
                KeyStore.SecretKeyEntry secretKeyEntry = (KeyStore.SecretKeyEntry) entry;
                return secretKeyEntry.getSecretKey();
            }
        }
        KeyGenerator keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEY_STORE);
        keyGenerator.init(new KeyGenParameterSpec.Builder(ALIAS, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .build());
        return keyGenerator.generateKey();
    }
}


