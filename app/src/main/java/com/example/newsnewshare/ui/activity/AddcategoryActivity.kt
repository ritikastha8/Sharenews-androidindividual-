package com.example.newsnewshare.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.newsnewshare.R
import com.example.newsnewshare.adapter.CategoryAdapter
import com.example.newsnewshare.adapter.dashboardAdapter
import com.example.newsnewshare.databinding.ActivityAddcategoryBinding
import com.example.newsnewshare.model.CategoryModel
import com.example.newsnewshare.repository.CategoryRepositoryImpl
import com.example.newsnewshare.viewmodel.CategoryViewModel
import com.google.firebase.database.FirebaseDatabase
import java.util.Locale.Category

class AddcategoryActivity : AppCompatActivity() {
    lateinit var addcategorybinding: ActivityAddcategoryBinding

    lateinit var categoryViewModel : CategoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        addcategorybinding = ActivityAddcategoryBinding.inflate(layoutInflater)
        setContentView(addcategorybinding.root)


        var repo = CategoryRepositoryImpl(FirebaseDatabase.getInstance())
        categoryViewModel = CategoryViewModel(repo)


        addcategorybinding.btnaddcategorytt.setOnClickListener {


            var categroryyid = addcategorybinding.categoryid.text.toString().toInt()
            var categoryname = addcategorybinding.categoryname.text.toString()

            var model = CategoryModel("",categroryyid, categoryname)

            categoryViewModel.addCategory(model){

                    success,message->
                if(success){
                    Toast.makeText(this@AddcategoryActivity,
                        message, Toast.LENGTH_LONG).show()
                    val intent= Intent(
                        this@AddcategoryActivity,
                        DashboardActivity::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this@AddcategoryActivity,
                        message, Toast.LENGTH_LONG).show()

                }
            }
        }
        addcategorybinding.btncancel.setOnClickListener {
            Toast.makeText(this, "Add category cancelled", Toast.LENGTH_SHORT).show()
            val intent= Intent(
                this@AddcategoryActivity,
                DashboardActivity::class.java)
            startActivity(intent)

        }
        addcategorybinding.btnback.setOnClickListener {
            val intent= Intent(
                this@AddcategoryActivity,
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