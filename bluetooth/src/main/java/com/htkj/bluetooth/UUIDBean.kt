package com.htkj.bluetooth

import java.util.UUID

data class UUIDBean(var mac:String?=null, var serveUuid: UUID?=null, var writeUuid: UUID?=null, var readUuid: UUID?=null){
    fun isRead(): Boolean {
        return serveUuid != null && readUuid != null
    }

    override fun toString(): String {
        return "serveUuid=${serveUuid} writeUuid${writeUuid} readUuid${readUuid}"
    }
}
