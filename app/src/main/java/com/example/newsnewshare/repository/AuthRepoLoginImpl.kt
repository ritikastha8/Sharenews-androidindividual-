package com.example.newsnewshare.repository

import com.google.firebase.auth.FirebaseAuth

class AuthRepoLoginImpl(var auth: FirebaseAuth):AuthRepoLogin {
    override fun login(email: String, password: String, callback: (Boolean, String) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    callback(true, "Login Successfull")
                } else {
                    callback(false, it.exception?.message.toString())
                }
            }
    }
}