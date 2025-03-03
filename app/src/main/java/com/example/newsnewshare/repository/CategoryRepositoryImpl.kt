package com.example.newsnewshare.repository

import com.example.newsnewshare.model.CategoryModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CategoryRepositoryImpl (val database : FirebaseDatabase):CategoryRepository {


//    val database:FirebaseDatabase=FirebaseDatabase.getInstance()
    val ref:DatabaseReference=database.reference.child("categories")


    override fun addCategory(categoryModel: CategoryModel, callback: (Boolean, String) -> Unit) {
        var id = ref.push().key.toString()
        categoryModel.categoryIddd=id

        ref.child(id).setValue(categoryModel).addOnCompleteListener {
            if(it.isSuccessful){
                callback(true,"Category Added successfully")
            }else{
                callback(false,"${it.exception?.message}")
            }
        }

    }

    override fun updateCategory(
        categoryIddd: String,
        data: MutableMap<String, Any>,
        callback: (Boolean, String) -> Unit
    ) {
        ref.child(categoryIddd).updateChildren(data).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Category updated successfully")

            } else {
                callback(false, "${it.exception?.message}")
            }
        }
    }

    override fun deleteCategory(categoryIddd: String, callback: (Boolean, String) -> Unit) {
        ref.child(categoryIddd).removeValue().addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Category deleted successfully")

            } else {
                callback(false, "${it.exception?.message}")
            }
        }
    }

    override fun getCategoryById(
        categoryIddd: String,
        callback: (CategoryModel?, Boolean, String) -> Unit
    ) {
        ref.child(categoryIddd).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var model = snapshot.getValue(CategoryModel::class.java)
                    callback(model,true,"Category fetched successfully")
                }

            }

            override fun onCancelled(error: DatabaseError) {
                callback(null,false,error.message)
            }


        })

    }

    override fun getAllCategories(callback: (List<CategoryModel>?, Boolean, String) -> Unit) {
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    var category = mutableListOf<CategoryModel>()
                    for (eachData in snapshot.children) {
                        var model = eachData.getValue(CategoryModel::class.java)
                        if (model != null) {
                            category.add(model)
                        }
                    }
                    callback(category, true, "category fetched success")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null,false,error.message)
            }
        })
    }

}