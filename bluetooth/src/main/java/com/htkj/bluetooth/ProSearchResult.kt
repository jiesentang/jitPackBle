package com.htkj.bluetooth

import com.inuker.bluetooth.library.search.SearchResult


data class ProSearchResult(val searchResult: SearchResult, var mac:String, var mConnectState:ConnectState,var mUUIDBean:UUIDBean?=null){
    override fun toString(): String {
        return "mac=${mac}  mConnectState=${mConnectState}"
    }
}

enum  class ConnectState{
    NO_CONNECT, //未连接
    NO_NOTICE,//未开通知
    RUN_CONNECT,//连接中
    FAIL_CONNECT,//连接失败
    SUCCESS_CONNECT,//成功连接
}
