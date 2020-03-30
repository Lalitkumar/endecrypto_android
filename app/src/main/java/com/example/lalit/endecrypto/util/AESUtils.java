package com.example.lalit.endecrypto.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESUtils {
    public static String encrypt(final String cleartext, final byte[] keyValue)
            throws Exception {
        final byte[] rawKey = getRawKey(keyValue);
        final byte[] result = encrypt(rawKey, cleartext.getBytes());
        return toHex(result);
    }

    public static String decrypt(final String encrypted, final byte[] keyValue)
            throws Exception {

        final byte[] enc = toByte(encrypted);
        byte[] result = decrypt(enc, keyValue);
        return new String(result);
    }

    private static byte[] getRawKey(final byte[] keyValue) throws Exception {
        final SecretKey key = new SecretKeySpec(keyValue, "AES");
        byte[] raw = key.getEncoded();
        return raw;
    }

    private static byte[] encrypt(final byte[] raw, final byte[] clear) throws Exception {
        final SecretKey skeySpec = new SecretKeySpec(raw, "AES");
        final Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        final byte[] encrypted = cipher.doFinal(clear);
        return encrypted;
    }

    private static byte[] decrypt(final byte[] encrypted, final byte[] keyValue)
            throws Exception {
        final SecretKey skeySpec = new SecretKeySpec(keyValue, "AES");
        final Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        final byte[] decrypted = cipher.doFinal(encrypted);
        return decrypted;
    }

    public static byte[] toByte(final String hexString) {
        final int len = hexString.length() / 2;
        final byte[] result = new byte[len];
        for (int i = 0; i < len; i++)
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2),
                    16).byteValue();
        return result;
    }

    public static String toHex(final byte[] buf) {
        if (buf == null)
            return "";
        final StringBuffer result = new StringBuffer(2 * buf.length);
        for (int i = 0; i < buf.length; i++) {
            appendHex(result, buf[i]);
        }
        return result.toString();
    }

    private final static String HEX = "0123456789ABCDEF";

    private static void appendHex(StringBuffer sb, byte b) {
        sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
    }
}

