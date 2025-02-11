package com.example.newsnewshare.repository

import com.example.newsnewshare.model.UserModel

import com.google.firebase.auth.FirebaseUser

interface UserRepository {

    fun login(email:String,password:String,
              callback:(Boolean,String)->Unit)

    fun signup(email:String,password:String,
               callback:(Boolean,String,String)->Unit)

    fun addUserToDatabase(userId:String,userModel:UserModel,
                          callback: (Boolean, String) -> Unit)
    fun forgetPassword(email:String,
                       callback: (Boolean, String) -> Unit)
    fun getCurrentUser() : FirebaseUser?
    //edit userprofile
    fun getUserById(userId: String,
                       callback:(UserModel?,Boolean,String)->Unit)
// get all-> userprofile
    fun getAllUsers(callback:(List<UserModel>?,Boolean,String)->Unit)

    //update userprofile

    fun updateUser(userId: String,data:MutableMap<String,Any>,
                      callback:(Boolean,String)->Unit){
//        repo.updateUser(userId,data, callback)
    }

}