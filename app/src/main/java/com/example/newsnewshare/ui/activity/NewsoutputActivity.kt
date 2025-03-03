package com.example.newsnewshare.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsnewshare.R
import com.example.newsnewshare.adapter.CategoryAdapter
import com.example.newsnewshare.adapter.newsAdapter
import com.example.newsnewshare.databinding.ActivityNewsoutputBinding
import com.example.newsnewshare.databinding.ActivityOutputBinding
import com.example.newsnewshare.repository.CategoryRepositoryImpl
import com.example.newsnewshare.repository.NewsRepositoryImpl
import com.example.newsnewshare.viewmodel.CategoryViewModel
import com.example.newsnewshare.viewmodel.NewsViewModel

class NewsoutputActivity : AppCompatActivity() {


    lateinit var newsViewModel: NewsViewModel
    lateinit var adapter: newsAdapter
    lateinit var binding: ActivityNewsoutputBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityNewsoutputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var repo = NewsRepositoryImpl()
        newsViewModel=NewsViewModel(repo)
        adapter = newsAdapter(this@NewsoutputActivity,ArrayList())

        newsViewModel.getAllNewss()
//        newAdapter = newsAdapter(
//            this@NewsoutputActivity,
//            ArrayList()
//        )


        newsViewModel.allnews.observe(this){news->
            news?.let{
                adapter.updateData(it)
            }

        }

        newsViewModel.loadingState.observe(this){loading->
            if(loading){
                binding.progressBar4.visibility = View.VISIBLE
            }else{
                binding.progressBar4.visibility = View.GONE
            }
        }

        binding.recyclerView.adapter=adapter
        binding.recyclerView.layoutManager= LinearLayoutManager(this)

        ItemTouchHelper(object:
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                var id = adapter.getnewsId(viewHolder.adapterPosition)
                newsViewModel.deleteNews(id){
                        success,message->
                    if(success){
                        Toast.makeText(this@NewsoutputActivity,
                            message, Toast.LENGTH_LONG).show()

                    }else{
                        Toast.makeText(this@NewsoutputActivity,
                            message, Toast.LENGTH_LONG).show()
                    }
                }
            }

        }).attachToRecyclerView(binding.recyclerView)

        binding.bbtnback1.setOnClickListener {

            val intent= Intent(
                this@NewsoutputActivity,
                DashboardActivity::class.java)
            startActivity(intent)

        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}