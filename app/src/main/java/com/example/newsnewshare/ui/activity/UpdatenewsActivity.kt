package com.example.newsnewshare.ui.activity

import android.content.Intent
import android.net.Uri
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
import com.example.newsnewshare.utils.ImageUtils
import com.example.newsnewshare.viewmodel.CategoryViewModel
import com.example.newsnewshare.viewmodel.NewsViewModel
import com.squareup.picasso.Picasso

class UpdatenewsActivity : AppCompatActivity() {

    lateinit var bindinng: ActivityUpdatenewsBinding

//    lateinit var imageUtils: ImageUtils


    lateinit var imageUtils: ImageUtils
    lateinit var newsViewModel: NewsViewModel

    //image store
    var imageUri: Uri? = null
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


        var id: String = intent.getStringExtra("newsidd").toString()
        // Load existing news data
        newsViewModel.getNewsById(id)
        newsViewModel.news.observe(this) {
            bindinng.editnewsname.setText(it?.newsNamee.toString())
            bindinng.edittcategorynamet.setText(it?.categoryNamme.toString())
            bindinng.editdescription.setText(it?.descrription.toString())
            Picasso.get().load(it?.imageUrl.toString()).into(bindinng.imagetoedit)
//            bindinng.imageurltoedit.setText(it?.imageUrl.toString())
        }
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


        bindinng.btnnedittcategory.setOnClickListener {
            uploadImage(id)

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
        val newCategoryyName = bindinng.edittcategorynamet.text.toString()
        val newNewsDescription = bindinng.editdescription.text.toString()
//            val newNewsImageurl = bindinng.imageurltoedit.text.toString()


        var updateMap = mutableMapOf<String, Any>()
        updateMap["newsNamee"] = newNewsName
        updateMap["categoryNamme"] = newCategoryyName
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
}