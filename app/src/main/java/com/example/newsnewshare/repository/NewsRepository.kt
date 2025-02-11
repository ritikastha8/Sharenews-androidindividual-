package com.example.newsnewshare.repository


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

}