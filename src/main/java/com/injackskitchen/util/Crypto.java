package com.injackskitchen.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Crypto {

    public enum DesMode {
        DES_MODE_ECB, DES_MODE_CBC, DES_MODE_CFB, DES_MODE_OFB
    }

    public static byte[] DESedeEncrypt(byte[] input, byte[] key, String padding, DesMode desMode) {
        return encrypt(input, padding, desMode, getKey(key));
    }

    private static SecretKeySpec getKey(byte[] key) {
        switch (key.length) {
            case 16:
                byte key24[] = new byte[24];
                System.arraycopy(key, 0, key24, 0, 16);
                System.arraycopy(key, 0, key24, 16, 8);
                return new SecretKeySpec(key24, "DESede");
            case 24:
                return new SecretKeySpec(key, "DESede");
            default:
                throw new RuntimeException("Illegal Key Length: " + key.length);
        }
    }

    private static byte[] encrypt(byte[] input, String padding, DesMode desMode, SecretKeySpec deskey) {
        IvParameterSpec iv = new IvParameterSpec(new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00});
        Cipher cipher;
        try {
            switch (desMode) {
                case DES_MODE_CBC:
                    cipher = Cipher.getInstance("DESede/CBC/" + padding);
                    cipher.init(Cipher.ENCRYPT_MODE, deskey, iv);
                    break;
                case DES_MODE_ECB:
                    cipher = Cipher.getInstance("DESede/ECB/" + padding);
                    cipher.init(Cipher.ENCRYPT_MODE, deskey);
                    break;
                case DES_MODE_CFB:
                    cipher = Cipher.getInstance("DESede/CFB8/" + padding);
                    cipher.init(Cipher.ENCRYPT_MODE, deskey, iv);
                    break;
                case DES_MODE_OFB:
                    cipher = Cipher.getInstance("DESede/OFB8/" + padding);
                    cipher.init(Cipher.ENCRYPT_MODE, deskey, iv);
                    break;
                default:
                    throw new RuntimeException("Unknown Encryption Mode: " + desMode);
            }
            return cipher.doFinal(getDataToEncrypt(input));
        } catch (Exception e) {
            throw new RuntimeException("Could not encrypt data");
        }
    }

    private static byte[] getDataToEncrypt(byte[] input) {
        byte[] dataToEncrypt;
        if (input.length % 8 != 0) {
            dataToEncrypt = new byte[((input.length / 8 + 1) * 8) + 8];
        } else {
            dataToEncrypt = new byte[input.length + 8];
        }
        System.arraycopy(input, 0, dataToEncrypt, 0, input.length);
        return dataToEncrypt;
    }

}