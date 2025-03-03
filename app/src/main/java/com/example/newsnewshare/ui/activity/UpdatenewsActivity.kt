package com.example.newsnewshare.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.newsnewshare.R
import com.example.newsnewshare.adapter.newsAdapter
import com.example.newsnewshare.databinding.ActivityUpdateBinding
import com.example.newsnewshare.databinding.ActivityUpdatenewsBinding
import com.example.newsnewshare.model.NewsModel
import com.example.newsnewshare.repository.CategoryRepositoryImpl
import com.example.newsnewshare.repository.NewsRepository
import com.example.newsnewshare.repository.NewsRepositoryImpl
import com.example.newsnewshare.utils.ImageUtils
import com.example.newsnewshare.viewmodel.CategoryViewModel
import com.example.newsnewshare.viewmodel.NewsViewModel
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class UpdatenewsActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener  {
    lateinit var imageUtils: ImageUtils
    //image store
    var imageUri: Uri? = null
    lateinit var newsViewModel: NewsViewModel
    lateinit var categoryViewModel: CategoryViewModel

    private var newsId = ""

    var category: String = ""
    lateinit var bindinng: ActivityUpdatenewsBinding
//    val cate = arrayOf("Latest","Politics","Sports","Technology","Culture")
//    lateinit var imageUtils: ImageUtils






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        bindinng = ActivityUpdatenewsBinding.inflate(layoutInflater)
        setContentView(bindinng.root)
        // image edit
//        imageUtils = ImageUtils(this)


        imageUtils = ImageUtils(this)
        var repo = NewsRepositoryImpl()
        newsViewModel = NewsViewModel(repo)

        var categoryRepo = CategoryRepositoryImpl(FirebaseDatabase.getInstance())
        categoryViewModel = CategoryViewModel(categoryRepo)
        categoryViewModel.getAllCategories()


//        // Retrieve news data from intent
//        val news: NewsModel? = intent.getParcelableExtra("news")
//        newsId = news?.newsidd.toString()
//        category = news?.categoryNamme.toString()
        newsId = intent.getStringExtra("newsidd") ?: ""
        newsViewModel.getNewsById(newsId)
        categoryViewModel.getAllCategories()

        categoryViewModel.allcategories.observe(this) { data ->
            var categoryName = data?.map { category ->
                category.categoryNamee
            } ?: emptyList()

            var arrayAdapter = ArrayAdapter(
                this@UpdatenewsActivity, android.R.layout.simple_spinner_item, categoryName
            )

            arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
            bindinng.spinnerCategoryUpdate.adapter = arrayAdapter
        }
//        var news :NewsModel? = intent.getParcelableExtra("news")
//
//        category = news?.categoryNamee.toString()
        var id: String = intent.getStringExtra("newsidd").toString()
        // Load existing news data
        newsViewModel.getNewsById(id)

        newsViewModel.news.observe(this) { news ->
            news?.let {
                bindinng.editnewsname.setText(it.newsNamee)
                bindinng.editdescription.setText(it.descrription)
                Picasso.get().load(it.imageUrl).into(bindinng.imagetoedit)

                // Update the category variable with the fetched news data
                category = it.categoryNamme

                // Set spinner selection if categories are already loaded
                val categories = categoryViewModel.allcategories.value
                categories?.let { categoryList ->
                    val categoryNames = categoryList.map { c -> c.categoryNamee }
                    val position = categoryNames.indexOf(category)
                    if (position >= 0) {
                        bindinng.spinnerCategoryUpdate.setSelection(position)
                    }
                }
            }
        }


//        newsViewModel.news.observe(this) { news ->
//            news?.let {
//
//                bindinng.editnewsname.setText(it?.newsNamee.toString())
////            bindinng.edittcategorynamet.setText(it?.categoryNamme.toString())
//                bindinng.editdescription.setText(it?.descrription.toString())
//                Picasso.get().load(it?.imageUrl.toString()).into(bindinng.imagetoedit)
////            bindinng.imageurltoedit.setText(it?.imageUrl.toString())
//            }
//        }
//        newsViewModel.news.observe(this) { news ->
//            if (news != null) {
//                bindinng.editnewsname.setText(news.newsNamee)
//                bindinng.edittcategorynamet.setText(news.categoryNamme)
//                bindinng.editdescription.setText(news.descrription)
////                Picasso.get().load(news.imageUrl).into(bi.imagetoedit)
//            }
//        }



        imageUtils.registerActivity { url ->
            url.let { it ->
                imageUri = it
                Picasso.get().load(it).into(bindinng.imagetoedit)
            }
        }
        //yo edit image
        bindinng.imagetoedit.setOnClickListener {
            imageUtils.launchGallery(this)
        }
        bindinng.bttnbback.setOnClickListener {

            val intent = Intent(
                this@UpdatenewsActivity,
                NewsoutputActivity::class.java
            )
            startActivity(intent)

        }
        categoryViewModel.allcategories.observe(this) { data ->
            data?.let { categories ->
                val categoryNames = categories.map { it.categoryNamee }

                // Set the spinner adapter
                val arrayAdapter = ArrayAdapter(
                    this@UpdatenewsActivity,
                    android.R.layout.simple_spinner_item,
                    categoryNames
                )
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
                bindinng.spinnerCategoryUpdate.adapter = arrayAdapter

                // Set spinner selection if category is already loaded from news
                if (category.isNotEmpty()) {
                    val position = categoryNames.indexOf(category)
                    if (position >= 0) {
                        bindinng.spinnerCategoryUpdate.setSelection(position)
                    }
                }
            }
        }
//        categoryViewModel.allcategories.observe(this){data->
//            var categoryName = data?.map { category->
//                category.categoryNamee
//            } ?: emptyList()
//
//            var arrayAdapter = ArrayAdapter(
//                this@UpdatenewsActivity,android.R.layout.simple_spinner_item,categoryName
//            )
//
//            arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
//            bindinng.spinnerCategoryUpdate.adapter = arrayAdapter
//
//            val defaultPosition = categoryName.indexOf(category)
//            if(defaultPosition >=0){
//                bindinng.spinnerCategoryUpdate.setSelection(defaultPosition)
//            }
//        }



        bindinng.btnnedittcategory.setOnClickListener {
//            uploadImage(id)
            if (imageUri != null) {
                uploadImage(id)
            } else {
                // Use existing image URL if no new image is selected
                val existingImageUrl = newsViewModel.news.value?.imageUrl ?: ""
                updateNews(id, existingImageUrl)
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    //edit image ko lagi
    private fun uploadImage(id: String) {

        imageUri?.let { uri ->
            newsViewModel.uploadImage(this, uri) { imageUrl ->

                if (imageUrl != null) {
                    updateNews(id, imageUrl)
                } else {
//                    Log.e("Upload Error", "Failed to upload image to Cloudinary")
                }
            }
        }
    }

    private fun updateNews(id: String, imageUrl: String) {
        val newNewsName = bindinng.editnewsname.text.toString()
//        val newCategoryyName = bindinng.edittcategorynamet.text.toString()
        val newNewsDescription = bindinng.editdescription.text.toString()
//            val newNewsImageurl = bindinng.imageurltoedit.text.toString()


        var updateMap = mutableMapOf<String, Any>()
        updateMap["newsNamee"] = newNewsName
        updateMap["categoryNamme"] = category
        updateMap["descrription"] = newNewsDescription
        updateMap["imageUrl"] = imageUrl


        newsViewModel.updateNews(id, updateMap) { success, message ->
            if (success) {
                Toast.makeText(
                    this@UpdatenewsActivity,
                    message, Toast.LENGTH_LONG
                ).show()
                finish()
            } else {
                Toast.makeText(
                    this@UpdatenewsActivity,
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