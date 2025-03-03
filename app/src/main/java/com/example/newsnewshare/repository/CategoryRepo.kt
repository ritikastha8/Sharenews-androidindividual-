package com.example.newsnewshare.repository

import com.example.newsnewshare.model.CategoryModel

interface CategoryRepo {
    fun addCategory(categoryModel: CategoryModel,
                    callback:(Boolean,String)->Unit)
}