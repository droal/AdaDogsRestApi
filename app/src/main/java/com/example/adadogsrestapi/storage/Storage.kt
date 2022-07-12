package com.example.adadogsrestapi.storage

interface Storage {
    fun saveToken(token: String)

    fun getToken(): String?

    fun clear()
}