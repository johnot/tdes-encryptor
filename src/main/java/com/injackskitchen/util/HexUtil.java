package com.injackskitchen.util;

public final class HexUtil {

    public static void Dump(byte[] arr) {
        if (arr == null || arr.length <= 0) {
            System.out.print("There is nothing to dump out.");
            return;
        }
        System.out.print("\n");
        for (int i = 0; i < arr.length; i++) {
            if (i % 8 == 0 && i != 0)
                System.out.print("\n");

            Byte b = new Byte(arr[i]);
            int ib = b.byteValue();
            String s = toPaddedHexString(ib, 2);

            if (s.length() > 2) {
                s = new String(s.toCharArray(), s.length() - 2, 2);
            }
            System.out.print(s + " ");
        }
        System.out.print("\n");
    }

    public static String toPaddedHexString(int integer, int digits) {
        String s = new String(Integer.toHexString(integer).toUpperCase());

        for (int i = digits - s.length(); i > 0; i--) {
            s = "0" + s;
        }
        // This piece of code removes the leading FFs
        return s.substring(s.length() - digits);
    }
}
