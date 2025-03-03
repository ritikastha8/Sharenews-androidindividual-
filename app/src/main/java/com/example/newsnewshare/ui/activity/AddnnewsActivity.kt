package com.example.newsnewshare.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
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
import com.example.newsnewshare.utils.ImageUtils
import com.example.newsnewshare.viewmodel.CategoryViewModel
import com.example.newsnewshare.viewmodel.NewsViewModel
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class AddnnewsActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    // spinner
//    lateinit var spinner: Spinner
    var category: String = ""

    lateinit var addnewsbinding: ActivityAddnnewsBinding

    lateinit var newsViewModel: NewsViewModel
    lateinit var categoryViewModel: CategoryViewModel

    //image
    lateinit var imageUtils: ImageUtils

    //image store
    var imageUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        addnewsbinding = ActivityAddnnewsBinding.inflate(layoutInflater)
        setContentView(addnewsbinding.root)

        var repo = NewsRepositoryImpl()
        newsViewModel = NewsViewModel(repo)


        var repo2 = CategoryRepositoryImpl(FirebaseDatabase.getInstance())
        categoryViewModel = CategoryViewModel(repo2)
//        // spinner
//        spinner= addnewsbinding.spinner
//
//        val adapter = ArrayAdapter(
//            this@AddnnewsActivity,android.R.layout.simple_spinner_item,cate
//        )
//        adapter.setDropDownViewResource(
//            android.R.layout.simple_spinner_dropdown_item
//
//        )
//        spinner.adapter = adapter
//        spinner.onItemSelectedListener = this
        // image edit
        imageUtils = ImageUtils(this)
        //
        addnewsbinding.spinnerCategory.onItemSelectedListener = this

        categoryViewModel.getAllCategories()

        categoryViewModel.allcategories.observe(this) { data ->
            var categoryName = data?.map { category ->
                category.categoryNamee
            } ?: emptyList()

            var arrayAdapter = ArrayAdapter(
                this@AddnnewsActivity, android.R.layout.simple_spinner_item, categoryName
            )

            arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
            addnewsbinding.spinnerCategory.adapter = arrayAdapter
        }


        //edit image ko lagi

        imageUtils.registerActivity { url ->
            url.let { it ->
                imageUri = it
                Picasso.get().load(it).into(addnewsbinding.imageadd)
            }
        }
        //yo edit image
        addnewsbinding.imageadd.setOnClickListener {
            imageUtils.launchGallery(this)
        }
        addnewsbinding.btnaddnews1.setOnClickListener {
            uploadImage()

        }

        addnewsbinding.btnnbacck.setOnClickListener {
            val intent = Intent(
                this@AddnnewsActivity,
                DashboardActivity::class.java
            )
            startActivity(intent)

        }

//        categoryViewModel.categories.observe(this){data->
//            var categoryName = data?.map { category->
//                category.categoryName
//            } ?: emptyList()
//
//            var arrayAdapter = ArrayAdapter(
//                this@AddnnewsActivity,android.R.layout.simple_spinner_item,categoryName
//            )
//
//            arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
//            addnewsbinding.spinner.adapter = arrayAdapter
//        }

        addnewsbinding.btncanccel.setOnClickListener {
            Toast.makeText(
                this@AddnnewsActivity,
                "Add News Cancelled ", Toast.LENGTH_LONG
            ).show()
            val intent = Intent(
                this@AddnnewsActivity,
                DashboardActivity::class.java
            )
            startActivity(intent)

        }
        //


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets


        }
    }

    //edit image ko lagi
    private fun uploadImage() {

        imageUri?.let { uri ->
            newsViewModel.uploadImage(this, uri) { imageUrl ->

                if (imageUrl != null) {
                    addNews(imageUrl)
                } else {
//                    Log.e("Upload Error", "Failed to upload image to Cloudinary")
                }
            }
        }
    }

    private fun addNews(url: String) {


        var newsname = addnewsbinding.newsnamme.text.toString()
        var descriptiion = addnewsbinding.descriptiion.text.toString()

        var model = NewsModel("", newsname, category, descriptiion, url)

        newsViewModel.addNews(model) { success, message ->
            if (success) {
                Toast.makeText(
                    this@AddnnewsActivity,
                    message, Toast.LENGTH_LONG
                ).show()
                val intent = Intent(
                    this@AddnnewsActivity,
                    DashboardActivity::class.java
                )
                startActivity(intent)
            } else {
                Toast.makeText(
                    this@AddnnewsActivity,
                    message, Toast.LENGTH_LONG
                ).show()

            }
        }
    }


    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (parent != null) {
            category = parent.getItemAtPosition(position).toString()
        }
        Log.d("categoy selected", parent?.getItemAtPosition(position).toString())
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

}