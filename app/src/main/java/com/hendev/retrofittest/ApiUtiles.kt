package com.hendev.retrofittest

class ApiUtiles {
    companion object {
        val BASE_URL ="https://jhenimex.000webhostapp.com/"
        fun getNotlarDaoInterface():NotlarDaoInterface {
            return RetrofitClient.getClient(BASE_URL).create(NotlarDaoInterface::class.java)
        }
    }
}