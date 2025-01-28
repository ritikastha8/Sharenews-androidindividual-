package com.example.newsnewshare.viewmodel

import android.util.Log
import com.example.newsnewshare.model.UserModel

import com.example.newsnewshare.repository.UserRepository
import com.google.firebase.auth.FirebaseUser

class UserViewModel (var repo:UserRepository) {
    fun login(email:String,password:String,
              callback:(Boolean,String)->Unit){
        repo.login(email,password,callback)

    }

    fun signup(email:String,password:String,
               callback:(Boolean,String,String)->Unit){
        Log.d("checkpoint",email)
        repo.signup(email,password,callback)
    }

    fun addUserToDatabase(userId:String, userModel: UserModel,
                          callback: (Boolean, String) -> Unit){
        repo.addUserToDatabase(userId,userModel,callback)

    }
    fun forgotPassword(email: String,callback: (Boolean, String) -> Unit){
        repo.forgetPassword(email,callback)
    }

    fun getCurrentUser() : FirebaseUser?{
        return repo.getCurrentUser()
    }

}