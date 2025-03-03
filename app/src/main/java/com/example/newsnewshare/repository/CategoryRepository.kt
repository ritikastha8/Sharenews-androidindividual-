package com.example.newsnewshare.repository

import com.example.newsnewshare.model.CategoryModel

interface CategoryRepository {


    fun addCategory(categoryModel:CategoryModel,
                   callback:(Boolean,String)->Unit)
    fun updateCategory(categoryIddd:String,data:MutableMap<String,Any>,
                      callback:(Boolean,String)->Unit)


    fun deleteCategory(categoryIddd: String,
                      callback:(Boolean,String)->Unit)


    fun getCategoryById(categoryIddd: String,
                       callback:(CategoryModel?,Boolean,String)->Unit)


    fun getAllCategories(callback:(List<CategoryModel>?,Boolean,String)->Unit)



}