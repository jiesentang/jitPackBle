package com.htkj.bluetooth;

import android.util.Log;

import androidx.annotation.NonNull;


public class ProvingUtil {
    //校验消息头
    public static   boolean isMeassgeHead(byte[] bytes){

        if(bytes!=null&&bytes.length>0) {
            //因为会出现只返回1个数据的清空  0x55
            return ((bytes[0] & 0xFF) == 0xA5);
        }
        return false;
    }

    //校验长度
    public static   boolean isLength(byte[] bytes){
        if(bytes.length> 5) {
            //因为会出现只返回1个数据的清空  0x55
            int length= getLength(bytes);
            return length<=bytes.length;
        }
        return false;
    }

    public static int getLength(byte[] bytes){
        return  ByteUtils.bytesToInt(ByteUtils.subByteArray(3,5,bytes))*2 + 6;
    }



    public static void analysis(byte[] bytes,@NonNull OnAnalysisCallback onAnalysisCallback){
        Log.e("analysis","接收消息"+HexUtil.formatHexString(bytes,true));
        if(isMeassgeHead(bytes)){

            if(isLength(bytes)){
                int length=getLength(bytes);
                byte[] completeData=ByteUtils.subByteArray(0,length,bytes);
                onAnalysisCallback.onAnalysisSuccess(completeData);
//                Log.e("analysis","完整消息"+HexUtil.formatHexString(completeData,true));
//                Log.e("analysis","完整消息长度="+length+"数据长度"+bytes.length);
                if(length<bytes.length){
                    Log.e("analysis","数据长度大于消息长度");
                    analysis(ByteUtils.subByteArray(length,bytes.length,bytes),onAnalysisCallback);
                }
            }else{
                Log.e("analysis","长度不够");
                onAnalysisCallback.onAnalysisDeletion(bytes);
            }
        }else{
            Log.e("analysis","头部错误");
            onAnalysisCallback.onAnalysisFail("数据头部错误");
        }
    }



}
