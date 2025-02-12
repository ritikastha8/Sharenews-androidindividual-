package com.example.newsnewshare.repository


import android.content.Context
import android.net.Uri
import com.example.newsnewshare.model.NewsModel

interface NewsRepository {
    fun addNews(newsModel: NewsModel,
                    callback:(Boolean,String)->Unit)
    fun updateNews(newsidd:String,data:MutableMap<String,Any>,
                       callback:(Boolean,String)->Unit)


    fun deleteNews(newsidd: String,
                       callback:(Boolean,String)->Unit)


    fun getNewsById(newsidd: String,
                        callback:(NewsModel?, Boolean, String)->Unit)


    fun getAllNewss(callback:(List<NewsModel>?, Boolean, String)->Unit)

    fun uploadImage(context: Context, imageUri: Uri, callback: (String?) -> Unit)

    fun getFileNameFromUri(context: Context, uri: Uri): String?
}