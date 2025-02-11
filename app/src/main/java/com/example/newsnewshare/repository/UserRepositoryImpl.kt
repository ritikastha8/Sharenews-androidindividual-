package com.example.newsnewshare.repository

import com.example.newsnewshare.model.UserModel

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UserRepositoryImpl : UserRepository {


    var auth: FirebaseAuth = FirebaseAuth.getInstance()

    //
    var database: FirebaseDatabase = FirebaseDatabase.getInstance()


    var ref: DatabaseReference = database.reference.child("users")


    override fun login(email: String, password: String, callback: (Boolean, String) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    callback(true, "Login success")
                } else {
                    callback(false, it.exception?.message.toString())
                }
            }

    }

    override fun signup(
        email: String,
        password: String,
        callback: (Boolean, String, String) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(
                    true, "Registration success",
                    auth.currentUser?.uid.toString()
                )
            } else {
                callback(false, it.exception?.message.toString(), "")
            }
        }
    }


    override fun addUserToDatabase(
        userId: String,
        userModel: UserModel,
        callback: (Boolean, String) -> Unit
    ) {
        ref.child(userId.toString()).setValue(userModel).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Registration success")


            } else {
                callback(false, it.exception?.message.toString())


            }
        }
    }

    override fun forgetPassword(email:String,callback:(Boolean,String)->Unit){
        auth.sendPasswordResetEmail(email).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Reset email sent to $email")
            } else {
                callback(false, it.exception?.message.toString())
            }
        }
    }

    //update editprofile
    override fun updateUser(
        userId: String,
        data: MutableMap<String, Any>,
        callback: (Boolean, String) -> Unit
    ) {

        ref.child(userId).updateChildren(data).addOnCompleteListener {
            if(it.isSuccessful){
                callback(true,"Users updated successfully")

            }else{
                callback(false,"${it.exception?.message}")
            }
        }

    }


    override fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }


    //editprofile get
    override fun getUserById(userId: String, callback: (UserModel?, Boolean, String) -> Unit) {
        ref.child(userId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var model = snapshot.getValue(UserModel::class.java)
                    callback(model,true,"Users details fetched successfully")
                }

            }

            override fun onCancelled(error: DatabaseError) {
                callback(null,false,error.message)
            }


        })
    }
// get all for editprofile
    override fun getAllUsers(callback: (List<UserModel>?, Boolean, String) -> Unit) {
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    var users = mutableListOf<UserModel>()
                    for (eachData in snapshot.children) {
                        var model = eachData.getValue(UserModel::class.java)
                        if (model != null) {
                            users.add(model)
                        }
                    }
                    callback(users, true, "user fetched success")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }


}