package com.injackskitchen;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class SampleCrypto {

    public static final int DES_MODE_ECB = 1;
    public static final int DES_MODE_CBC = 2;
    public static final int DES_MODE_CFB = 3;
    public static final int DES_MODE_OFB = 4;

    public static void main(String[] args) {
        String card = "4811111111111111";

        byte key24Bytes[] = new byte[24];
        for (int i = 0; i < 24; i++) {
            key24Bytes[i] = (byte) i;
        }
        byte[] encryptedCardBytes = DESedeEncrypt(card.getBytes(), key24Bytes, 1);
        byte[] decryptedBytes = DESedeDecrypt(encryptedCardBytes, key24Bytes, 1);
        System.out.println("decryptedBytesAsString = " + new String(decryptedBytes));

        byte key16Bytes[] = new byte[16];
        for (int i = 0; i < 16; i++) {
            key24Bytes[i] = (byte) i;
        }
        encryptedCardBytes = DESedeEncrypt(card.getBytes(), key16Bytes, 1);
        decryptedBytes = DESedeDecrypt(encryptedCardBytes, key16Bytes, 1);
        System.out.println("decryptedBytesAsString = " + new String(decryptedBytes));

    }

    public static byte[] DESedeEncrypt(byte[] input, byte[] key, int mode) {
        SecretKeySpec tripleDesKey = createSecretKeySpec(key);
        try {
            Cipher cipher;
            IvParameterSpec iv = new IvParameterSpec(new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00});
            if (mode == DES_MODE_CBC) {
                cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
                cipher.init(Cipher.ENCRYPT_MODE, tripleDesKey, iv);
                return cipher.doFinal(input);
            } else if (mode == DES_MODE_ECB) {
                cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
                cipher.init(Cipher.ENCRYPT_MODE, tripleDesKey);
                return cipher.doFinal(input);
            } else if (mode == DES_MODE_CFB) {
                cipher = Cipher.getInstance("DESede/CFB8/PKCS5Padding");
                cipher.init(Cipher.ENCRYPT_MODE, tripleDesKey, iv);
                return cipher.doFinal(input);
            } else if (mode == DES_MODE_OFB) {
                cipher = Cipher.getInstance("DESede/OFB8/PKCS5Padding");
                cipher.init(Cipher.ENCRYPT_MODE, tripleDesKey, iv);
                return cipher.doFinal(input);
            }
        } catch (Exception e) {
            throw new RuntimeException("Encryption error: " + e.getMessage());
        }
        throw new RuntimeException("Encryption error");
    }

    public static byte[] DESedeDecrypt(byte[] input, byte[] key, int mode) {
        SecretKeySpec tripleDesKey = createSecretKeySpec(key);
        try {
            Cipher cipher;
            IvParameterSpec iv = new IvParameterSpec(new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00});
            if (mode == DES_MODE_CBC) {
                cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
                cipher.init(Cipher.DECRYPT_MODE, tripleDesKey, iv);
                return cipher.doFinal(input);
            } else if (mode == DES_MODE_ECB) {
                cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
                cipher.init(Cipher.DECRYPT_MODE, tripleDesKey);
                return cipher.doFinal(input);
            } else if (mode == DES_MODE_CFB) {
                cipher = Cipher.getInstance("DESede/CFB8/PKCS5Padding");
                cipher.init(Cipher.DECRYPT_MODE, tripleDesKey, iv);
                return cipher.doFinal(input);
            } else if (mode == DES_MODE_OFB) {
                cipher = Cipher.getInstance("DESede/OFB8/PKCS5Padding");
                cipher.init(Cipher.DECRYPT_MODE, tripleDesKey, iv);
                return cipher.doFinal(input);
            }
        } catch (Exception e) {
            throw new RuntimeException("Encryption error: " + e.getMessage());
        }
        throw new RuntimeException("Encryption error");
    }

    private static SecretKeySpec createSecretKeySpec(byte[] key) {
        SecretKeySpec tripleDesKey;
        if (key.length == 16) {
            byte key24[] = new byte[24];
            System.arraycopy(key, 0, key24, 0, 16);
            System.arraycopy(key, 0, key24, 16, 8);
            tripleDesKey = new SecretKeySpec(key24, "DESede");
        } else if (key.length == 24) {
            tripleDesKey = new SecretKeySpec(key, "DESede");
        } else {
            throw new RuntimeException("Illegal Key Length: " + key.length);
        }
        return tripleDesKey;
    }
}