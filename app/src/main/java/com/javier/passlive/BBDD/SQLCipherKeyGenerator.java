package com.javier.passlive.BBDD;

import java.security.SecureRandom;
import java.util.Base64;

public class SQLCipherKeyGenerator {
    private final int keyLength;

    public SQLCipherKeyGenerator(int keyLength) {
        this.keyLength = keyLength;
    }

    public String generateKey() {
        // Generamos una clave aleatoria
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[keyLength];
        random.nextBytes(key);

        // Codificamos la clave en base64
        String encodedKey = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            encodedKey = Base64.getEncoder().encodeToString(key);
        }

        return encodedKey;
    }
}

