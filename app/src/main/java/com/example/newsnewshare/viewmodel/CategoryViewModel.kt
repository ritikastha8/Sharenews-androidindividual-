package com.example.newsnewshare.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.newsnewshare.model.CategoryModel
import com.example.newsnewshare.repository.CategoryRepository
import com.google.firebase.database.core.Repo

class CategoryViewModel(val repo: CategoryRepository) {

    fun addCategory(categoryModel: CategoryModel,
                    callback:(Boolean,String)->Unit){
        repo.addCategory (categoryModel,callback)
    }

    fun updateCategory(categoryIddd:String,data:MutableMap<String,Any>,
                       callback:(Boolean,String)->Unit){
        repo.updateCategory(categoryIddd,data, callback)
    }


    fun deleteCategory(categoryIddd: String,callback: (Boolean, String) -> Unit){
        repo.deleteCategory(categoryIddd,callback)
    }
    var _categories = MutableLiveData<CategoryModel?>()
    var categories =MutableLiveData<CategoryModel?>()
        get () = _categories

    var _allcategories = MutableLiveData<List<CategoryModel>>()
    var allcategories =MutableLiveData<List<CategoryModel>>()
        get () = _allcategories



    fun getCategoryById(categoryId: String){
        repo.getCategoryById(categoryId){
                model,success,message ->
            if (success){
                _categories.value = model
            }
        }

    }

//    var _loading = MutableLiveData<Boolean>()
//    var loading=MutableLiveData<Boolean>()
//        get()=_loading

    fun getAllCategories(){
//        _loading.value = true
        repo.getAllCategories{category,success,message->
            if (success){
                _allcategories.value = category
//                _loading.value = false
            }
        }

    }
}