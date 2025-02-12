package com.example.newsnewshare.repository

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.OpenableColumns
import com.cloudinary.Cloudinary
import com.cloudinary.utils.ObjectUtils
import com.example.newsnewshare.model.CategoryModel
import com.example.newsnewshare.model.NewsModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.io.InputStream
import java.util.concurrent.Executors

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
    // edit image
    private val cloudinary = Cloudinary(
        mapOf(
            "cloud_name" to "dcqrpymx4",
            "api_key" to "676266168861658",
            "api_secret" to "EE1Rc1bnfjlL9ZuRfiDlRF63CJ0"
        )
    )
    override fun uploadImage(context: Context, imageUri: Uri, callback: (String?) -> Unit) {
        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            try {
                val inputStream: InputStream? = context.contentResolver.openInputStream(imageUri)
                var fileName = getFileNameFromUri(context, imageUri)

                fileName = fileName?.substringBeforeLast(".") ?: "uploaded_image"

                val response = cloudinary.uploader().upload(
                    inputStream, ObjectUtils.asMap(
                        "public_id", fileName,
                        "resource_type", "image"
                    )
                )

                var imageUrl = response["url"] as String?

                imageUrl = imageUrl?.replace("http://", "https://")

                Handler(Looper.getMainLooper()).post {
                    callback(imageUrl)
                }

            } catch (e: Exception) {
                e.printStackTrace()
                Handler(Looper.getMainLooper()).post {
                    callback(null)
                }
            }
        }
    }

    override fun getFileNameFromUri(context: Context, uri: Uri): String? {
        var fileName: String? = null
        val cursor: Cursor? = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (nameIndex != -1) {
                    fileName = it.getString(nameIndex)
                }
            }
        }
        return fileName
    }
}