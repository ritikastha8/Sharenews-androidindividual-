package com.example.newsnewshare.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.newsnewshare.R
import com.example.newsnewshare.databinding.ActivityUserEditprofileBinding
import com.example.newsnewshare.repository.CategoryRepositoryImpl
import com.example.newsnewshare.repository.UserRepositoryImpl
import com.example.newsnewshare.viewmodel.CategoryViewModel
import com.example.newsnewshare.viewmodel.UserViewModel

class UserEditprofileActivity : AppCompatActivity() {
    lateinit var binding:ActivityUserEditprofileBinding
    lateinit var userViewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityUserEditprofileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var repo = UserRepositoryImpl()
        userViewModel=UserViewModel(repo)
        var id:String=intent.getStringExtra("userId").toString()


        //get garne code
        userViewModel.getUserById(id)
        userViewModel._users.observe(this){
            binding.editfirstname.setText(it?.firstName.toString())
            binding.editlastname.setText(it?.lastName.toString())
            binding.editEmail.setText(it?.email.toString())
            binding.editphonenumber.setText(it?.contact.toString())
        }


        //update garna lai code
        binding.btnsave.setOnClickListener {
            val newfirstname = binding.editfirstname.text.toString()
            val newlastname = binding.editlastname.text.toString()
            val newEmail = binding.editEmail.text.toString()
            val newphonenumber = binding.editphonenumber.text.toString().toInt()

            var updateMap= mutableMapOf<String,Any>()

            updateMap["firstname"]=newfirstname
            updateMap["lastname"]=newlastname
            updateMap["email"]=newEmail
            updateMap["contact"]=newphonenumber

            userViewModel.updateUser(id,updateMap){
                    success,message->
                if(success){
                    Toast.makeText(this@UserEditprofileActivity,
                        message, Toast.LENGTH_LONG).show()
                    //finish le tyo app banda garxa, ek step back garxa
                    finish()
                }else{
                    Toast.makeText(this@UserEditprofileActivity,
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