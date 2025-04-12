package com.htkj.bluetooth;

public interface OnAnalysisCallback {
    /**
     * 解析成功
     * @param message 完整数据
     */
    void onAnalysisSuccess(byte[] message);

    /**
     * 解析缺失
     * @param deletionMessage
     */
    void onAnalysisDeletion(byte[] deletionMessage);

    /**
     * 解析缺失
     * @param msg
     */
    void onAnalysisFail(String msg);
}
