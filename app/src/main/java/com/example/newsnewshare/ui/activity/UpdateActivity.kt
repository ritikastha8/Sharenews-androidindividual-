package com.example.newsnewshare.ui.activity

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

class UpdateActivity : AppCompatActivity() {

    lateinit var bindinng:ActivityUpdateBinding
    lateinit var categoryViewModel: CategoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        bindinng=ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(bindinng.root)


        var repo = CategoryRepositoryImpl()
        categoryViewModel=CategoryViewModel(repo)

        //
        var id : String= intent.getStringExtra("categoryiddd").toString()

        categoryViewModel.getCategoryById(id)
        categoryViewModel.categories.observe(this){
            bindinng.editcategorid.setText(it?.categoryId.toString())
            bindinng.editcategorynamett.setText(it?.categoryNamee.toString())
        }


        bindinng.btneditcategory.setOnClickListener {
            val newCategoryId = bindinng.editcategorid.text.toString().toInt()
            val newCategoryName = bindinng.editcategorynamett.text.toString()



            var updateMap= mutableMapOf<String,Any>()
            updateMap["categoryId"]=newCategoryId
            updateMap["categoryNamee"]=newCategoryName


            categoryViewModel.updateCategory(id,updateMap){
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

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}