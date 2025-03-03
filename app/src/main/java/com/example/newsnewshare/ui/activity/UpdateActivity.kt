package com.example.newsnewshare.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.newsnewshare.R
import com.example.newsnewshare.databinding.ActivityUpdateBinding
import com.example.newsnewshare.repository.CategoryRepositoryImpl
import com.example.newsnewshare.viewmodel.CategoryViewModel
import com.google.firebase.database.FirebaseDatabase

class UpdateActivity : AppCompatActivity() {

    lateinit var bindinng:ActivityUpdateBinding
    lateinit var categoryviewHolder: CategoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        bindinng=ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(bindinng.root)


        var repo = CategoryRepositoryImpl(FirebaseDatabase.getInstance())
        categoryviewHolder=CategoryViewModel(repo)





        var id : String= intent.getStringExtra("categoryIddd").toString()
       //To get code
        categoryviewHolder.getCategoryById(id)
        categoryviewHolder.categories.observe(this){
            bindinng.editcategorid.setText(it?.categoryId.toString())
            bindinng.editcategorynamett.setText(it?.categoryNamee.toString())
        }

        //To update code
        bindinng.btneditcategory.setOnClickListener {
            val newCategoryId = bindinng.editcategorid.text.toString().toInt()
            val newCategoryName = bindinng.editcategorynamett.text.toString()



            var updateMap= mutableMapOf<String,Any>()
            updateMap["categoryId"]=newCategoryId
            updateMap["categoryNamee"]=newCategoryName


            categoryviewHolder.updateCategory(id,updateMap){
                    success,message->
                if(success){
                    Toast.makeText(this@UpdateActivity,
                        message, Toast.LENGTH_LONG).show()
                    finish()
                }else{
                    Toast.makeText(this@UpdateActivity,
                        message, Toast.LENGTH_LONG).show()
                }
            }

        }

        bindinng.bttnback.setOnClickListener {
            val intent= Intent(
                this@UpdateActivity,
                OutputActivity
                ::class.java)
            startActivity(intent)

        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}