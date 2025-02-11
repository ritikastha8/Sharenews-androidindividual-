package com.example.newsnewshare.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.newsnewshare.R
import com.example.newsnewshare.adapter.newsAdapter
import com.example.newsnewshare.databinding.ActivityUpdateBinding
import com.example.newsnewshare.databinding.ActivityUpdatenewsBinding
import com.example.newsnewshare.repository.CategoryRepositoryImpl
import com.example.newsnewshare.repository.NewsRepository
import com.example.newsnewshare.repository.NewsRepositoryImpl
import com.example.newsnewshare.viewmodel.CategoryViewModel
import com.example.newsnewshare.viewmodel.NewsViewModel

class UpdatenewsActivity : AppCompatActivity() {

    lateinit var bindinng: ActivityUpdatenewsBinding
    lateinit var newsViewHolder: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        bindinng=ActivityUpdatenewsBinding.inflate(layoutInflater)
        setContentView(bindinng.root)
        var repo = NewsRepositoryImpl()
        newsViewHolder=NewsViewModel(repo)

        var id : String= intent.getStringExtra("newsidd").toString()

        newsViewHolder.getNewsById(id)
        newsViewHolder.news.observe(this){
            bindinng.editnewsname.setText(it?.newsNamee.toString())
            bindinng.edittcategorynamet.setText(it?.categoryNamme.toString())
            bindinng.editdescription.setText(it?.descrription.toString())
        }


        bindinng.btnnedittcategory.setOnClickListener {
            val newNewsName = bindinng.editnewsname.text.toString()
            val newCategoryyName = bindinng.edittcategorynamet.text.toString()
            val newNewsDescription = bindinng.editdescription.text.toString()



            var updateMap= mutableMapOf<String,Any>()
            updateMap["newsNamee"]=newNewsName
            updateMap["categoryNamme"]=newCategoryyName
            updateMap["descrription"]=newNewsDescription


            newsViewHolder.updateNews(id,updateMap){
                    success,message->
                if(success){
                    Toast.makeText(this@UpdatenewsActivity,
                        message, Toast.LENGTH_LONG).show()
                    finish()
                }else{
                    Toast.makeText(this@UpdatenewsActivity,
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