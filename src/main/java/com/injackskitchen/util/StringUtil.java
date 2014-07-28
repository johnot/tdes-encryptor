package com.injackskitchen.util;

public class StringUtil {

    public static byte[] createBytesFromString(String string) {
        byte[] bKey = new byte[string.length() / 2];
        int b = 0;
        for (int i = 0; i < string.length(); i += 2) {
            String s = string.substring(i, i + 2);
            bKey[b] = Integer.valueOf(s, 16).byteValue();
            b++;
        }
        return bKey;
    }

    public static byte[] createConvertedBytesFromString(String string) {
        byte[] bKey = new byte[string.length()];
        int b = 0;
        for (int i = 0; i < string.length(); i++) {
            String s = string.substring(i, i + 1);
            bKey[b] = Character.isDigit(s.charAt(0)) ? Integer.valueOf(s, 16).byteValue() : (byte) s.charAt(0);
            b++;
        }
        return bKey;
    }

    public static String createStringFromBytes(byte[] bytes) {
        String sKey = new String("");

        for (int i = 0; i < bytes.length; i++) {
            int val = bytes[i];
            // add the 0
            String s = Integer.toHexString(0x10000 | val).substring(1).toUpperCase();
            s = s.substring(s.length() - 2, s.length());
            sKey += s;
        }
        return sKey;
    }

    public static String createStringWithSpaces(String inValue) {
        StringBuilder buf = new StringBuilder();

        for (int i = 0; i < inValue.length(); i++) {
            buf.append(inValue.charAt(i));
            if (i > 0 && (i + 1) % 2 == 0) {
                buf.append(" ");
            }
        }
        return buf.toString();
    }
}
