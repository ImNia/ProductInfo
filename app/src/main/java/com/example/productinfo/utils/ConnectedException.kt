package com.example.productinfo.utils

class ConnectedException: Exception() {
    override val message: String = "Connect failed"
}