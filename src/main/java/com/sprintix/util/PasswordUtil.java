package com.sprintix.util;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;

public class PasswordUtil {

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;

    public static String getSalt(int length) {
        StringBuilder returnValue = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return new String(returnValue);
    }

    public static byte[] hash(char[] password, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        Arrays.fill(password, Character.MIN_VALUE);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } finally {
            spec.clearPassword();
        }
    }

    public static String generateSecurePassword(String password, String salt) {
        String returnValue = null;
        byte[] securePassword = hash(password.toCharArray(), salt.getBytes());
        returnValue = Base64.getEncoder().encodeToString(securePassword);
        return returnValue;
    }

    public static boolean verifyUserPassword(String providedPassword, String securedPassword, String salt) {
        boolean returnValue = false;
        
        // El formato guardado en BD es "salt:hash"
        // Si el salt viene vacío, intentamos extraerlo de la contraseña guardada
        String newSecurePassword = "";
        String[] parts = securedPassword.split(":");
        
        if (parts.length == 2) {
            String saltDb = parts[0];
            String hashDb = parts[1];
            // Generar hash con el salt extraído y la password provista
            newSecurePassword = generateSecurePassword(providedPassword, saltDb);
            // Comparar solo la parte del hash (o el string completo reconstruido)
            returnValue = newSecurePassword.equalsIgnoreCase(hashDb);
        } else {
            // Fallback si el formato no es salt:hash (no debería pasar con tu DB actual)
            newSecurePassword = generateSecurePassword(providedPassword, salt);
            returnValue = newSecurePassword.equalsIgnoreCase(securedPassword);
        }
        
        return returnValue;
    }
}