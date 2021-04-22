package com.example.mapsproject.common

import com.example.mapsproject.remote.Client
import com.example.mapsproject.remote.MyAPI

object Common {

    val BASE_URL = "https://192.168.0.19/mapp/"
//    val BASE_URL = "https://10.0.0.2/mapp/"
    val api: MyAPI
        get() = Client.getClient(BASE_URL).create(MyAPI::class.java)
}