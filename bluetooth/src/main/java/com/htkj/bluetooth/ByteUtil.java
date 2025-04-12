package com.htkj.bluetooth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ByteUtil {

    /**
     * 分包
     * @param byteArray
     * @return
     */
    public static List<byte[]> splitByteArray(byte[] byteArray,int size) {
        List<byte[]> splitList = new ArrayList<>();
        int length = byteArray.length;
        int index = 0;

        while (index < length) {
            int endIndex = Math.min(index + size, length);
            byte[] subArray = Arrays.copyOfRange(byteArray, index, endIndex);
            splitList.add(subArray);
            index += size;
        }

        return splitList;
    }






    public static int CRC16_CCITT_FALSE(byte[] data, int length) {
        int wCRCin = 0xFFFF;
        int wCPoly = 0x1021;
        int wChar;
        for (int n = 0; n < length; n++) {
            wChar = data[n] & 0xFF;
            wCRCin ^= (wChar << 8);
            for (int i = 0; i < 8; i++) {
                if ((wCRCin & 0x8000) != 0) {
                    wCRCin = (wCRCin << 1) ^ wCPoly;
                } else {
                    wCRCin = wCRCin << 1;
                }
            }
        }

        return wCRCin;
    }
}
