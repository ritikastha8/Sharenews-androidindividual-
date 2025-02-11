package com.example.newsnewshare.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.newsnewshare.R
import com.example.newsnewshare.databinding.ActivityAddcategoryBinding
import com.example.newsnewshare.databinding.ActivityAddnnewsBinding
import com.example.newsnewshare.model.CategoryModel
import com.example.newsnewshare.model.NewsModel
import com.example.newsnewshare.repository.CategoryRepositoryImpl
import com.example.newsnewshare.repository.NewsRepositoryImpl
import com.example.newsnewshare.viewmodel.CategoryViewModel
import com.example.newsnewshare.viewmodel.NewsViewModel

class AddnnewsActivity : AppCompatActivity() {
    lateinit var addnewsbinding: ActivityAddnnewsBinding

    lateinit var newsViewModel : NewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        addnewsbinding = ActivityAddnnewsBinding.inflate(layoutInflater)
        setContentView(addnewsbinding.root)
        var repo = NewsRepositoryImpl()
        newsViewModel = NewsViewModel(repo)

        addnewsbinding.btnaddnews1.setOnClickListener {


            var newsname = addnewsbinding.newsnamme.text.toString()
            var categorynamme = addnewsbinding.categoryname1.text.toString()
            var descriptiion = addnewsbinding.descriptiion.text.toString()

            var model = NewsModel("",newsname, categorynamme, descriptiion)

            newsViewModel.addNews(model){
                    success,message->
                if(success){
                    Toast.makeText(this@AddnnewsActivity,
                        message, Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(this@AddnnewsActivity,
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