package com.htkj.bluetooth;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import java.math.BigInteger;
import java.util.List;

public class HexUtil {

    private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private static final char[] DIGITS_UPPER = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static char[] encodeHex(byte[] data) {
        return encodeHex(data, true);
    }

    public static char[] encodeHex(byte[] data, boolean toLowerCase) {
        return encodeHex(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
    }

    protected static char[] encodeHex(byte[] data, char[] toDigits) {
        if (data == null)
            return null;
        int l = data.length;
        char[] out = new char[l << 1];
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = toDigits[(0xF0 & data[i]) >>> 4];
            out[j++] = toDigits[0x0F & data[i]];
        }
        return out;
    }


    public static String encodeHexStr(byte[] data) {
        return encodeHexStr(data, true);
    }

    public static String encodeHexStr(byte[] data, boolean toLowerCase) {
        return encodeHexStr(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
    }


    protected static String encodeHexStr(byte[] data, char[] toDigits) {
        return new String(encodeHex(data, toDigits));
    }

    public static String formatHexString(byte[] data) {
        return formatHexString(data, false);
    }

    public static String formatHexString(byte[] data, boolean addSpace) {
        if (data == null || data.length < 1)
            return null;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            String hex = Integer.toHexString(data[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex);
            if (addSpace)
                sb.append(" ");
        }
        return sb.toString().trim().toUpperCase();
    }

    public static String formatHexString(byte data){
        String hex = Integer.toHexString(data & 0xFF);
        if (hex.length() == 1) {
            hex = '0' + hex;
        }
        return hex;
    }

    public static String formatHexString(List<Byte> data, boolean addSpace) {
        if (data == null || data.size() < 1)
            return null;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.size(); i++) {
            String hex = Integer.toHexString(data.get(i) & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex);
            if (addSpace)
                sb.append(" ");
        }
        return sb.toString().trim().toUpperCase();
    }





    public static byte[] decodeHex(char[] data) {

        int len = data.length;

        if ((len & 0x01) != 0) {
            throw new RuntimeException("Odd number of characters.");
        }

        byte[] out = new byte[len >> 1];

        // two characters form the hex value.
        for (int i = 0, j = 0; j < len; i++) {
            int f = toDigit(data[j], j) << 4;
            j++;
            f = f | toDigit(data[j], j);
            j++;
            out[i] = (byte) (f & 0xFF);
        }

        return out;
    }


    protected static int toDigit(char ch, int index) {
        int digit = Character.digit(ch, 16);
        if (digit == -1) {
            throw new RuntimeException("Illegal hexadecimal character " + ch
                    + " at index " + index);
        }
        return digit;
    }


    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.trim();
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    public static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public static String extractData(byte[] data, int position) {
        return HexUtil.formatHexString(new byte[]{data[position]});
    }

    public static String decimalToHex(int decimal) {
        String hex = "";
        while(decimal != 0) {
            int hexValue = decimal % 16;
            hex = toHexChar(hexValue) + hex;
            decimal = decimal / 16;
        }
        return  hex;
    }

    //将0~15的十进制数转换成0~F的十六进制数
    public static char toHexChar(int hexValue) {
        if(hexValue <= 9 && hexValue >= 0)
            return (char)(hexValue + '0');
        else
            return (char)(hexValue - 10 + 'A');
    }

    //16进制String z转int
    public static int hex16StringToInt10(@NonNull String hexString) {
        String  noZero= removeZero(hexString);
        if(!TextUtils.isEmpty(noZero)){
            BigInteger mBigInteger=new BigInteger(noZero,16);
            return mBigInteger.intValue();
        }
        return 0;
    }


    //16进制String z转int
    public static int hex16BytesToInt10(@NonNull byte[] hexString) {
        String  noZero= removeZero(HexUtil.formatHexString(hexString));
        if(!TextUtils.isEmpty(noZero)){
            BigInteger mBigInteger=new BigInteger(noZero,16);
            return mBigInteger.intValue();
        }
        return 0;
    }

    public static String removeZero(String string){
        char[] chars= string.toCharArray();
        for (int i=0;i<chars.length;i++){
            if( Character.digit(chars[i], 16)>0){
                return string.substring(i,chars.length);
            }
        }
        return null;
    }


    public static String byteArrayToAscii(byte[] byteArray) {
        StringBuilder sb = new StringBuilder();
        for (byte b : byteArray) {
            if (b !=0x00) {
                sb.append((char) b);
            }
        }
        return sb.toString();
    }




}
