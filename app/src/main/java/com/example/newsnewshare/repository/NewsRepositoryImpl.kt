package com.example.newsnewshare.repository

import com.example.newsnewshare.model.CategoryModel
import com.example.newsnewshare.model.NewsModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class NewsRepositoryImpl:NewsRepository {
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val ref: DatabaseReference =database.reference.child("news")

    override fun addNews(newsModel: NewsModel, callback: (Boolean, String) -> Unit) {
        var id = ref.push().key.toString()
        newsModel.newsidd=id

        ref.child(id).setValue(newsModel).addOnCompleteListener {
            if(it.isSuccessful){
                callback(true,"News Added successfully")
            }else{
                callback(false,"${it.exception?.message}")
            }
        }
    }

    override fun updateNews(
        newsidd: String,
        data: MutableMap<String, Any>,
        callback: (Boolean, String) -> Unit
    ) {
        ref.child(newsidd).updateChildren(data).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "News updated successfully")

            } else {
                callback(false, "${it.exception?.message}")
            }
        }
    }

    override fun deleteNews(newsidd: String, callback: (Boolean, String) -> Unit) {
        ref.child(newsidd).removeValue().addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "News deleted successfully")

            } else {
                callback(false, "${it.exception?.message}")
            }
        }
    }

    override fun getNewsById(newsidd: String, callback: (NewsModel?, Boolean, String) -> Unit) {

        ref.child(newsidd).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var model = snapshot.getValue(NewsModel::class.java)
                    callback(model,true,"News fetched successfully")
                }

            }

            override fun onCancelled(error: DatabaseError) {
                callback(null,false,error.message)
            }


        })
    }

    override fun getAllNewss(callback: (List<NewsModel>?, Boolean, String) -> Unit) {
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    var news = mutableListOf<NewsModel>()
                    for (eachData in snapshot.children) {
                        var model = eachData.getValue(NewsModel::class.java)
                        if (model != null) {
                            news.add(model)
                        }
                    }
                    callback(news, true, "News fetched success")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null,false,error.message)
            }
        })
    }
}