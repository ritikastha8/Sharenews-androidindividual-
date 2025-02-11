package com.example.newsnewshare.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.newsnewshare.model.CategoryModel
import com.example.newsnewshare.model.NewsModel
import com.example.newsnewshare.repository.NewsRepository

class NewsViewModel(val repo:NewsRepository) {
    fun addNews(newsModel: NewsModel,
                    callback:(Boolean,String)->Unit){
        repo.addNews (newsModel,callback)
    }

    fun updateNews(newsidd:String,data:MutableMap<String,Any>,
                       callback:(Boolean,String)->Unit){
        repo.updateNews(newsidd,data, callback)
    }


    fun deleteNews(newsidd: String,callback: (Boolean, String) -> Unit){
        repo.deleteNews(newsidd,callback)
    }
    var _news = MutableLiveData<NewsModel?>()
    var news = MutableLiveData<NewsModel?>()
        get () = _news

    var _allnews = MutableLiveData<List<NewsModel>>()
    var allnews = MutableLiveData<List<NewsModel>>()
        get () = _allnews



    fun getNewsById(newsidd: String){
        repo.getNewsById(newsidd){
                model,success,message ->
            if (success){
                _news.value = model
            }
        }

    }

//    var _loading = MutableLiveData<Boolean>()
//    var loading=MutableLiveData<Boolean>()
//        get()=_loading

    fun getAllNewss(){
//        _loading.value = true
        repo.getAllNewss(){news,success,message->
            if (success){
                _allnews.value = news
//                _loading.value = false
            }
        }

    }
}