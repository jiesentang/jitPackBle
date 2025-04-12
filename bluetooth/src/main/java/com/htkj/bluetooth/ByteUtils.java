package com.htkj.bluetooth;



import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ByteUtils {

    //inputstream转byte[]
    public static byte[] stream2Bytes(InputStream input) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int n = 0;
        try {
            while (-1 != (n = input.read(buffer))) {
                output.write(buffer, 0, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toByteArray();
    }

    public static String toHexIntString(byte[] bytes) {
        if (bytes == null)
            return "null";
        int iMax = bytes.length - 1;
        if (iMax == -1)
            return "[]";
        StringBuilder b = new StringBuilder();
        b.append('[');
        for (int i = 0; ; i++) {
            b.append(String.format("%s", bytes[i] & 0xFF));
            if (i == iMax)
                return b.append(']').toString();
            b.append(", ");
        }
    }

    //将字节数组转换为short类型，即统计字符串长度
    public static short bytes2Short2(byte[] b) {
        short i = (short) (((b[1] & 0xff) << 8) | b[0] & 0xff);
        return i;
    }

    /**
     * 以字符串表示形式返回字节数组的内容
     *
     * @param bytes 字节数组
     * @return 字符串形式的 <tt>bytes</tt>
     * [01, fe, 08, 35, f1, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00]
     */
    public static String toHexString(byte[] bytes) {
        if (bytes == null)
            return "null";
        int iMax = bytes.length - 1;
        if (iMax == -1)
            return "[]";
        StringBuilder b = new StringBuilder();
        b.append('[');
        for (int i = 0; ; i++) {
            b.append(String.format("%02x", bytes[i] & 0xFF));
            if (i == iMax)
                return b.append(']').toString();
            b.append(", ");
        }
    }

    /**
     * 将字节数组转换为16进制字符串
     * @param bytes
     * @return  01FE0835F1000000000000000000000000000000
     */
    public static String bytes2HexStr(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        StringBuilder b = new StringBuilder();
        for (int i = 0; i<bytes.length; i++) {
            b.append(String.format("%02x", bytes[i] & 0xFF));
        }
        return b.toString();
    }

    /**
     * 将字节数组转换为16进制字符串
     * @param bytes
     * @return  01FE0835F1000000000000000000000000000000
     */
    public static String bytes2HexStr(byte bytes) {
        StringBuilder b = new StringBuilder();
        b.append(String.format("%02x", bytes & 0xFF));
        return b.toString();
    }


    public static byte[] hexStr2Bytes(String str) {
        if (str == null) {
            return null;
        }
        if (str.length() == 0) {
            return new byte[0];
        }
        byte[] byteArray = new byte[str.length() / 2];
        for (int i = 0; i < byteArray.length; i++) {
            String subStr = str.substring(2 * i, 2 * i + 2);
            byteArray[i] = ((byte) Integer.parseInt(subStr, 16));
        }
        return byteArray;
    }

    //3.short转换为byte数组
    public static byte[] short2Bytes(short value) {
        byte[] data = new byte[2];
        data[0] = (byte) (value >> 8 & 0xff);
        data[1] = (byte) (value & 0xFF);
        return data;
    }

    /**
     * 将int转化成byte[]
     *
     * @param res 要转化的整数
     * @return 对应的byte[]
     */
    public static byte[] int2byte(int res) {
        byte[] targets = new byte[4];
        targets[0] = (byte) (res & 0xff);// 最低位
        targets[1] = (byte) ((res >> 8) & 0xff);// 次低位
        targets[2] = (byte) ((res >> 16) & 0xff);// 次高位
        targets[3] = (byte) (res >>> 24);// 最高位,无符号右移。
        return targets;
    }

    public static byte[] int2byteGao(int res) {
        byte[] targets = new byte[4];
        targets[3] = (byte) (res & 0xff);// 最低位
        targets[2] = (byte) ((res >> 8) & 0xff);// 次低位
        targets[1] = (byte) ((res >> 16) & 0xff);// 次高位
        targets[0] = (byte) (res >>> 24);// 最高位,无符号右移。
        return targets;
    }

    public static byte[] int1byteGao(int res) {
        byte[] targets = new byte[2];
        targets[1] = (byte) (res & 0xff);// 最低位
        targets[0] = (byte) ((res >> 8) & 0xff);// 次低位
//        targets[1] = (byte) ((res >> 16) & 0xff);// 次高位
//        targets[0] = (byte) (res >>> 24);// 最高位,无符号右移。
        return targets;
    }

    /**
     * 将byte[]转化成int
     *
     * @param res 要转化的byte[]
     * @return 对应的整数
     */
    public static int byte2int(byte[] res) {
        int targets = (res[0] & 0xff) | ((res[1] << 8) & 0xff00) | ((res[2] << 24) >>> 8) | (res[3] << 24);
        return targets;
    }

    public static int byte2intGao(byte[] res) {
        int targets = (res[0] & 0xff) | ((res[1] << 8) & 0xff00) ;
        return targets;
    }



    /**
     * 以字节数组的形式返回指定的布尔值
     *
     * @param data 一个布尔值
     * @return 长度为 1 的字节数组
     */
    public static byte[] getBytes(boolean data) {
        byte[] bytes = new byte[1];
        bytes[0] = (byte) (data ? 1 : 0);
        return bytes;
    }

    /**
     * 以字节数组的形式返回指定的 16 位有符号整数值
     *
     * @param data 要转换的数字
     * @return 长度为 2 的字节数组
     */
    public static byte[] getBytes(short data) {
        byte[] bytes = new byte[2];
        if (isLittleEndian()) {
            bytes[0] = (byte) (data & 0xff);
            bytes[1] = (byte) ((data & 0xff00) >> 8);
        } else {
            bytes[1] = (byte) (data & 0xff);
            bytes[0] = (byte) ((data & 0xff00) >> 8);
        }
        return bytes;
    }

    /**
     * 以字节数组的形式返回指定的 Unicode 字符值
     *
     * @param data 要转换的字符
     * @return 长度为 2 的字节数组
     */
    public static byte[] getBytes(char data) {
        byte[] bytes = new byte[2];
        if (isLittleEndian()) {
            bytes[0] = (byte) (data);
            bytes[1] = (byte) (data >> 8);
        } else {
            bytes[1] = (byte) (data);
            bytes[0] = (byte) (data >> 8);
        }
        return bytes;
    }

    /**
     * 以字节数组的形式返回指定的 32 位有符号整数值
     *
     * @param data 要转换的数字
     * @return 长度为 4 的字节数组
     */
    public static byte[] getBytes(int data) {
        byte[] bytes = new byte[4];
        if (isLittleEndian()) {
            bytes[0] = (byte) (data & 0xff);
            bytes[1] = (byte) ((data & 0xff00) >> 8);
            bytes[2] = (byte) ((data & 0xff0000) >> 16);
            bytes[3] = (byte) ((data & 0xff000000) >> 24);
        } else {
            bytes[3] = (byte) (data & 0xff);
            bytes[2] = (byte) ((data & 0xff00) >> 8);
            bytes[1] = (byte) ((data & 0xff0000) >> 16);
            bytes[0] = (byte) ((data & 0xff000000) >> 24);
        }
        return bytes;
    }

    /**
     * 以字节数组的形式返回指定的 64 位有符号整数值
     *
     * @param data 要转换的数字
     * @return 长度为 8 的字节数组
     */
    public static byte[] getBytes(long data) {
        byte[] bytes = new byte[8];
        if (isLittleEndian()) {
            bytes[0] = (byte) (data & 0xff);
            bytes[1] = (byte) ((data >> 8) & 0xff);
            bytes[2] = (byte) ((data >> 16) & 0xff);
            bytes[3] = (byte) ((data >> 24) & 0xff);
            bytes[4] = (byte) ((data >> 32) & 0xff);
            bytes[5] = (byte) ((data >> 40) & 0xff);
            bytes[6] = (byte) ((data >> 48) & 0xff);
            bytes[7] = (byte) ((data >> 56) & 0xff);
        } else {
            bytes[7] = (byte) (data & 0xff);
            bytes[6] = (byte) ((data >> 8) & 0xff);
            bytes[5] = (byte) ((data >> 16) & 0xff);
            bytes[4] = (byte) ((data >> 24) & 0xff);
            bytes[3] = (byte) ((data >> 32) & 0xff);
            bytes[2] = (byte) ((data >> 40) & 0xff);
            bytes[1] = (byte) ((data >> 48) & 0xff);
            bytes[0] = (byte) ((data >> 56) & 0xff);
        }
        return bytes;
    }

    /**
     * 以字节数组的形式返回指定的单精度浮点值
     *
     * @param data 要转换的数字
     * @return 长度为 4 的字节数组
     */
    public static byte[] getBytes(float data) {
        return getBytes(Float.floatToIntBits(data));
    }

    /**
     * 以字节数组的形式返回指定的双精度浮点值
     *
     * @param data 要转换的数字
     * @return 长度为 8 的字节数组
     */
    public static byte[] getBytes(double data) {
        return getBytes(Double.doubleToLongBits(data));
    }

    /**
     * 将指定字符串中的所有字符编码为一个字节序列
     *
     * @param data 包含要编码的字符的字符串
     * @return 一个字节数组，包含对指定的字符集进行编码的结果
     */
    public static byte[] getBytes(String data) {
        return data.getBytes(Charset.forName("UTF-8"));
    }

    /**
     * 将指定字符串中的所有字符编码为一个字节序列
     *
     * @param data        包含要编码的字符的字符串
     * @param charsetName 字符集编码
     * @return 一个字节数组，包含对指定的字符集进行编码的结果
     */
    public static byte[] getBytes(String data, String charsetName) {
        return data.getBytes(Charset.forName(charsetName));
    }

    /**
     * 返回由字节数组转换来的布尔值
     *
     * @param bytes 字节数组
     * @return 布尔值
     */
    public static boolean toBoolean(byte[] bytes) {
        return bytes[0] == 0 ? false : true;
    }

    /**
     * 返回由字节数组中的指定的一个字节转换来的布尔值
     *
     * @param bytes      字节数组
     * @param startIndex 起始下标
     * @return 布尔值
     */
    public static boolean toBoolean(byte[] bytes, int startIndex) {
        return toBoolean(copyFrom(bytes, startIndex, 1));
    }

    /**
     * 返回由字节数组转换来的 16 位有符号整数
     *
     * @param bytes 字节数组
     * @return 由两个字节构成的 16 位有符号整数
     */
    public static short toShort(byte[] bytes) {
        if (isLittleEndian()) {
            return (short) ((0xff & bytes[0]) | (0xff00 & (bytes[1] << 8)));
        } else {
            return (short) ((0xff & bytes[1]) | (0xff00 & (bytes[0] << 8)));
        }
    }

    /**
     * 返回由字节数组中的指定的两个字节转换来的 16 位有符号整数
     *
     * @param bytes      字节数组
     * @param startIndex 起始下标
     * @return 由两个字节构成的 16 位有符号整数
     */
    public static short toShort(byte[] bytes, int startIndex) {
        return toShort(copyFrom(bytes, startIndex, 2));
    }

    /**
     * 返回由字节数组转换来的 Unicode 字符
     *
     * @param bytes 字节数组
     * @return 由两个字节构成的字符
     */
    public static char toChar(byte[] bytes) {
        if (isLittleEndian()) {
            return (char) ((0xff & bytes[0]) | (0xff00 & (bytes[1] << 8)));
        } else {
            return (char) ((0xff & bytes[1]) | (0xff00 & (bytes[0] << 8)));
        }
    }

    /**
     * 返回由字节数组中的指定的两个字节转换来的 Unicode 字符
     *
     * @param bytes      字节数组
     * @param startIndex 起始下标
     * @return 由两个字节构成的字符
     */
    public static char toChar(byte[] bytes, int startIndex) {
        return toChar(copyFrom(bytes, startIndex, 2));
    }

    /**
     * 返回由字节数组转换来的 32 位有符号整数
     *
     * @param bytes 字节数组
     * @return 由四个字节构成的 32 位有符号整数
     */
    public static int toInt(byte[] bytes) {
        if (isLittleEndian()) {
            return (0xff & bytes[0])
                    | (0xff00 & (bytes[1] << 8))
                    | (0xff0000 & (bytes[2] << 16))
                    | (0xff000000 & (bytes[3] << 24));
        } else {
            return (0xff & bytes[3])
                    | (0xff00 & (bytes[2] << 8))
                    | (0xff0000 & (bytes[1] << 16))
                    | (0xff000000 & (bytes[0] << 24));
        }
    }

    /**
     * 返回由字节数组中的指定的四个字节转换来的 32 位有符号整数
     *
     * @param bytes      字节数组
     * @param startIndex 起始下标
     * @return 由四个字节构成的 32 位有符号整数
     */
    public static int toInt(byte[] bytes, int startIndex) {
        return toInt(copyFrom(bytes, startIndex, 4));
    }

    /**
     * 返回由字节数组转换来的 64 位有符号整数
     *
     * @param bytes 字节数组
     * @return 由八个字节构成的 64 位有符号整数
     */
    public static long toLong(byte[] bytes) {
        if (isLittleEndian()) {
            return (0xffL & (long) bytes[0])
                    | (0xff00L & ((long) bytes[1] << 8))
                    | (0xff0000L & ((long) bytes[2] << 16))
                    | (0xff000000L & ((long) bytes[3] << 24))
                    | (0xff00000000L & ((long) bytes[4] << 32))
                    | (0xff0000000000L & ((long) bytes[5] << 40))
                    | (0xff000000000000L & ((long) bytes[6] << 48))
                    | (0xff00000000000000L & ((long) bytes[7] << 56));
        } else {
            return (0xffL & (long) bytes[7])
                    | (0xff00L & ((long) bytes[6] << 8))
                    | (0xff0000L & ((long) bytes[5] << 16))
                    | (0xff000000L & ((long) bytes[4] << 24))
                    | (0xff00000000L & ((long) bytes[3] << 32))
                    | (0xff0000000000L & ((long) bytes[2] << 40))
                    | (0xff000000000000L & ((long) bytes[1] << 48))
                    | (0xff00000000000000L & ((long) bytes[0] << 56));
        }
    }

    /**
     * 返回由字节数组中的指定的八个字节转换来的 64 位有符号整数
     *
     * @param bytes      字节数组
     * @param startIndex 起始下标
     * @return 由八个字节构成的 64 位有符号整数
     */
    public static long toLong(byte[] bytes, int startIndex) {
        return toLong(copyFrom(bytes, startIndex, 8));
    }

    /**
     * 返回由字节数组转换来的单精度浮点数
     *
     * @param bytes 字节数组
     * @return 由四个字节构成的单精度浮点数
     */
    public static float toFloat(byte[] bytes) {
        return Float.intBitsToFloat(toInt(bytes));
    }

    /**
     * 返回由字节数组中的指定的四个字节转换来的单精度浮点数
     *
     * @param bytes      字节数组
     * @param startIndex 起始下标
     * @return 由四个字节构成的单精度浮点数
     */
    public static float toFloat(byte[] bytes, int startIndex) {
        return Float.intBitsToFloat(toInt(copyFrom(bytes, startIndex, 4)));
    }

    /**
     * 返回由字节数组转换来的双精度浮点数
     *
     * @param bytes 字节数组
     * @return 由八个字节构成的双精度浮点数
     */
    public static double toDouble(byte[] bytes) {
        return Double.longBitsToDouble(toLong(bytes));
    }

    /**
     * 返回由字节数组中的指定的八个字节转换来的双精度浮点数
     *
     * @param bytes      字节数组
     * @param startIndex 起始下标
     * @return 由八个字节构成的双精度浮点数
     */
    public static double toDouble(byte[] bytes, int startIndex) {
        return Double.longBitsToDouble(toLong(copyFrom(bytes, startIndex, 8)));
    }

    /**
     * 返回由字节数组转换来的字符串
     *
     * @param bytes 字节数组
     * @return 字符串
     */
    public static String toString(byte[] bytes) {
        return new String(bytes, Charset.forName("UTF-8"));
    }

    /**
     * 返回由字节数组转换来的字符串
     *
     * @param bytes       字节数组
     * @param charsetName 字符集编码
     * @return 字符串
     */
    public static String toString(byte[] bytes, String charsetName) {
        return new String(bytes, Charset.forName(charsetName));
    }

    // --------------------------------------------------------------------------------------------


    /**
     * 数组拷贝。
     *
     * @param src 字节数组。
     * @param off 起始下标。
     * @param len 拷贝长度。
     * @return 指定长度的字节数组。
     */
    private static byte[] copyFrom(byte[] src, int off, int len) {
        // return Arrays.copyOfRange(src, off, off + len);
        byte[] bits = new byte[len];
        for (int i = off, j = 0; i < src.length && j < len; i++, j++) {
            bits[j] = src[i];
        }
        return bits;
    }

    /**
     * 判断 CPU Endian 是否为 Little
     *
     * @return 判断结果
     */
    private static boolean isLittleEndian() {
        return ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN;
    }

    /**
     * 拼接两个字节数组
     * @return
     */
    public static byte[] concat(byte[] first, byte[] second){
        if(first!=null&&second!=null){
            byte[] result = Arrays.copyOf(first, first.length + second.length);
            System.arraycopy(second, 0, result, first.length, second.length);
            return result;
        }else{
            if(first!=null){
                return first;
            }
            if(second!=null){
                return second;
            }
        }
        return null;

    }

    /**
     * 截取byte 数组
     * @param startIndex 起始位置
     * @param endIndex  结束位置
     * @param content 数组
     * @return
     */
    public static byte[] subByteArray(int startIndex, int endIndex, byte[] content) {
        if(endIndex<=startIndex){
            return new byte[]{};
        }

        byte[] msg = new byte[endIndex - startIndex];
        int index = 0;
        for (int i = startIndex; i < endIndex; i++) {
            msg[index] = content[i];
            index++;
        }
        return msg;
    }


    /**
     * 截取byte 数组
     * @param startIndex 起始位置
     * @param endIndex  结束位置
     * @param content 数组
     * @return
     */
    public static char[] subCharArray(int startIndex, int endIndex, char[] content) {
        char[] msg = new char[endIndex - startIndex];
        int index = 0;
        for (int i = startIndex; i < endIndex; i++) {
            msg[index] = content[i];
            index++;
        }
        return msg;
    }


    public static int bytesToInt(byte[] content){
        if(content!=null&&content.length>0){
            String hex=  HexUtil.formatHexString(content);
            BigInteger bi = new BigInteger(hex, 16);
            return bi.intValue();
        }
        return 0;

    }

    /**
     * 以字节数组的形式返回指定的 32 位有符号整数值
     *
     * @param data 要转换的数字
     * @return 长度为 4 的字节数组
     */
    public static byte getBytes(int data,int position) {
        byte[] bytes = new byte[4];
        if (isLittleEndian()) {
            bytes[0] = (byte) (data & 0xff);
            bytes[1] = (byte) ((data & 0xff00) >> 8);
            bytes[2] = (byte) ((data & 0xff0000) >> 16);
            bytes[3] = (byte) ((data & 0xff000000) >> 24);
        } else {
            bytes[3] = (byte) (data & 0xff);
            bytes[2] = (byte) ((data & 0xff00) >> 8);
            bytes[1] = (byte) ((data & 0xff0000) >> 16);
            bytes[0] = (byte) ((data & 0xff000000) >> 24);
        }
        if(position>0&&bytes.length>position){
            return bytes[position];
        }

        return 0;
    }


    /**
     * byte 转 int
     * @param b
     * @return
     */
    public static int byte2Int(byte b){



        return (int)(b & 0xff);
    }
    /**
     * byte 转 int
     * @param b
     * @return
     */
    public static int byte16Int10(byte b){
        // 将字节转换为16进制字符串
        String hexString = String.format("%02X", b);

        // 将16进制字符串转换为10进制整数
        int decimalValue = Integer.parseInt(hexString, 16);
        return decimalValue;
    }

    /**
     * byte 转 int
     * @param b
     * @return
     */
    public static byte int2Byte(int b){
        return (byte) (b & 0xff);
    }

    /**
     * byte 转 int
     * @param bytes
     * @return
     */
    public static int byte2Int2(byte[] bytes){
//        if (isLittleEndian()) {
//            return ((0xff & bytes[0]) | (0xff00 & (bytes[1] << 8)));
//        } else {
            return ((0xff & bytes[1]) | (0xff00 & (bytes[0] << 8)));
//        }
    }



    public static String intToString(int s){
        return HexUtil.formatHexString(new byte[]{(byte) ((s & 0xff00) >> 8),(byte) (s & 0xff)});
    }



    /**
     * 把byte数组转化成2进制字符串
     * @param bArr
     * @return
     */
    public static String getBinaryStrFromByteArr(byte[] bArr){
        String result ="";
        for(byte b:bArr ){
            result += getBinaryStrFromByte(b);
        }
        return result;
    }
    /**
     * 把byte转化成2进制字符串
     * @param b
     * @return
     */
    public static String getBinaryStrFromByte(byte b){
        String result ="";
        byte a = b; ;
        for (int i = 0; i < 8; i++){
            byte c=a;
            a=(byte)(a>>1);//每移一位如同将10进制数除以2并去掉余数。
            a=(byte)(a<<1);
            if(a==c){
                result="0"+result;
            }else{
                result="1"+result;
            }
            a=(byte)(a>>1);
        }
        return result;
    }




    /**
     * 把byte数组转化成2进制字符串
     * @param bArr
     * @return
     */
    public static List<Byte> getBinaryIntFromByteArr(int start,byte[] bArr){
        List<Byte> integerList=new ArrayList<>();
        if(bArr==null||bArr.length==0){
            return integerList;
        }
        for (int i=0;i<bArr.length;i++){
            byte b = bArr[i];
            byte a = b;
            List<Byte> byte1List=new ArrayList<>();
            for (int j=0;j<8;j++){
                byte c=a;
                a=(byte)(a>>1);//每移一位如同将10进制数除以2并去掉余数。
                a=(byte)(a<<1);
                if(a!=c){
                    int index=(i*8+(8-j)+start);
                    byte1List.add(0, (byte) index);
                }
                a=(byte)(a>>1);
            }
            integerList.addAll(byte1List);
        }
        return integerList;
    }


    /**
     * 把byte数组转化成2进制字符串
     * @param bArr
     * @return
     */
    public static List<Integer> getBinaryIntFrom(int start,byte[] bArr){
        List<Integer> integerList=new ArrayList<>();
        if(bArr==null||bArr.length==0){
            return integerList;
        }
        for (int i=0;i<bArr.length;i++){
            byte b = bArr[i];
            byte a = b;
            List<Integer> byte1List=new ArrayList<>();
            for (int j=0;j<8;j++){
                byte c=a;
                a=(byte)(a>>1);//每移一位如同将10进制数除以2并去掉余数。
                a=(byte)(a<<1);
                if(a!=c){
                    int index=(i*8+(8-j)+start);
                    byte1List.add(0,  index);
                }
                a=(byte)(a>>1);
            }
            integerList.addAll(byte1List);
        }
        return integerList;
    }


    /**
     * 把byte数组转化成2进制字符串
     * @param bArr
     * @return
     */
    public static List<Integer> getBinaryFromIntArr(byte[] bArr){
        List<Integer> integerList=new ArrayList<>();
        if(bArr==null||bArr.length==0){
            return integerList;
        }
        for (int i=0;i<bArr.length;i++){
            byte b = bArr[i];
            byte a = b;
            List<Integer> byte1List=new ArrayList<>();
            for (int j=0;j<8;j++){
                byte c=a;
                a=(byte)(a>>1);//每移一位如同将10进制数除以2并去掉余数。
                a=(byte)(a<<1);
                byte1List.add(0, a!=c?1:0);
                a=(byte)(a>>1);
            }
            integerList.addAll(byte1List);
        }
        return integerList;
    }


    /**
     * 把byte数组转化成2进制字符串
     * @param bArr
     * @return
     */
    public static List<Integer> getBinaryFromIntArrReverseOrder(byte[] bArr){
        List<Integer> integerList=new ArrayList<>();
        if(bArr==null||bArr.length==0){
            return integerList;
        }
        for (int i=0;i<bArr.length;i++){
            byte b = bArr[i];
            byte a = b;
            List<Integer> byte1List=new ArrayList<>();
            for (int j=0;j<8;j++){
                byte c=a;
                a=(byte)(a>>1);//每移一位如同将10进制数除以2并去掉余数。
                a=(byte)(a<<1);
                byte1List.add( a!=c?1:0);
                a=(byte)(a>>1);
            }
            integerList.addAll(byte1List);
        }
        return integerList;
    }



    /**
     * 二进制转十进制
     * @param number
     * @return
     */
    public static int binary2Decimal(String number) {
        return scale2Decimal(number, 2);
    }

    /**
     * 其他进制转十进制
     * @param number
     * @return
     */
    public static int scale2Decimal(String number, int scale) {
        checkNumber(number);
        if (2 > scale || scale > 32) {
            throw new IllegalArgumentException("scale is not in range");
        }
        // 不同其他进制转十进制,修改这里即可
        int total = 0;
        String[] ch = number.split("");
        int chLength = ch.length;
        for (int i = 0; i < chLength; i++) {
            total += Integer.valueOf(ch[i]) * Math.pow(scale, chLength - 1 - i);
        }
        return total;

    }

    /**
     * 二进制转十进制
     * @param number
     * @return
     */
    public static String decimal2Binary(int number) {
        return decimal2Scale(number, 2);
    }

    /**
     * 十进制转其他进制
     * @param number
     * @param scale
     * @return
     */
    public static String decimal2Scale(int number, int scale) {
        if (2 > scale || scale > 32) {
            throw new IllegalArgumentException("scale is not in range");
        }
        String result = "";
        while (0 != number) {
            result = number % scale + result;
            number = number / scale;
        }

        return result;
    }

    public static void checkNumber(String number) {
        String regexp = "^\\d+$";
        if (null == number || !number.matches(regexp)) {
            throw new IllegalArgumentException("input is not a number");
        }
    }


}
