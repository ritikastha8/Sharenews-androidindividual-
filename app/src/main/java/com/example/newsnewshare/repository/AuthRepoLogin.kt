package com.example.newsnewshare.repository

interface AuthRepoLogin {
    fun login(email:String,password:String,
              callback:(Boolean,String)->Unit)
}