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
import com.example.newsnewshare.databinding.ActivityOutputBinding
import com.example.newsnewshare.repository.CategoryRepositoryImpl
import com.example.newsnewshare.viewmodel.CategoryViewModel
import com.google.firebase.database.FirebaseDatabase

class OutputActivity : AppCompatActivity() {

    lateinit var categoryViewModel: CategoryViewModel
    lateinit var adapter: CategoryAdapter
    lateinit var binding:ActivityOutputBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityOutputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var repo = CategoryRepositoryImpl(FirebaseDatabase.getInstance())
        categoryViewModel=CategoryViewModel(repo)

        adapter = CategoryAdapter(this@OutputActivity,ArrayList())

        categoryViewModel.getAllCategories()

        categoryViewModel.allcategories.observe(this){categories->
            categories?.let{
                adapter.updateData(it)
            }

        }
        categoryViewModel.loadingState.observe(this){loading->
            if(loading){
                binding.progressBar.visibility = View.VISIBLE
            }else{
                binding.progressBar.visibility = View.GONE
            }
        }



        binding.recyclerView.adapter=adapter
        binding.recyclerView.layoutManager= LinearLayoutManager(this)

        ItemTouchHelper(object:ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                var id = adapter.getcategoryId(viewHolder.adapterPosition)
                categoryViewModel.deleteCategory(id){
                        success,message->
                    if(success){
                        Toast.makeText(this@OutputActivity,
                            message, Toast.LENGTH_LONG).show()

                    }else{
                        Toast.makeText(this@OutputActivity,
                            message, Toast.LENGTH_LONG).show()
                    }
                }
            }

        }).attachToRecyclerView(binding.recyclerView)

        binding.bbtnback.setOnClickListener {

            val intent= Intent(
                this@OutputActivity,
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
