package com.example.newsnewshare.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
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

    //update


    fun updateUser(userId: String,data:MutableMap<String,Any>,
                   callback: (Boolean, String) -> Unit){
        repo.updateUser(userId, data, callback)
    }


    // edit usereditprofileactivity
    //get

    var _users = MutableLiveData<UserModel?>()
    var users = MutableLiveData<UserModel?>()
        get () = _users

    var _allusers = MutableLiveData<List<UserModel>>()
    var allusers = MutableLiveData<List<UserModel>>()
        get () = _allusers

    fun getUserById(userId: String){
        repo.getUserById(userId){
                model,success,message ->
            if (success){
                _users.value = model
            }
        }

    }
    fun getAllUsers(){

        repo.getAllUsers{user,success,message->
            if (success){
                _allusers.value = user

            }
        }

    }


}