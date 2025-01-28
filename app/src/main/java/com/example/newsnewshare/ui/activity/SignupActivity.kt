package com.example.newsnewshare.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.newsnewshare.R
import com.example.newsnewshare.databinding.ActivitySignupBinding
import com.example.newsnewshare.model.UserModel

import com.example.newsnewshare.repository.UserRepositoryImpl
import com.example.newsnewshare.viewmodel.UserViewModel

class SignupActivity : AppCompatActivity() {
    lateinit var binding:ActivitySignupBinding
    lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var repo = UserRepositoryImpl()
        userViewModel=UserViewModel(repo)



        binding.btnsignup.setOnClickListener {
            // to get
            var email = binding.editEmail.text.toString()
            var password = binding.editPassword.text.toString()
            var firstName = binding.editFName.text.toString()
            var lastName = binding.editLName.text.toString()
            var contact = binding.editContact.text.toString()

           if(firstName.isEmpty()){
               binding.editFName.error = "First name can't be empty"
           }else if(lastName.isEmpty()){
               binding.editLName.error = "Last name can't be empty"
           }else if(email.isEmpty()){
               binding.editEmail.error = "Email Address can't be empty"
           }else if(contact.isEmpty()){
               binding.editContact.error = "Phone Number can't be empty"
           }else if(password.isEmpty()){
               binding.editPassword.error = "Password can't be empty"
           }else{
               userViewModel.signup(email,password){
                       success,message,userId ->

                   if(success){
                       var userModel = UserModel(
                           userId,
                           firstName, lastName,
                           contact, email
                       )
                       userViewModel.addUserToDatabase(userId,userModel){
                               success,message->
                           if (success){
                               Toast.makeText(
                                   this@SignupActivity,
                                   message, Toast.LENGTH_LONG
                               ).show()
                           }else{
                               Toast.makeText(
                                   this@SignupActivity,
                                   message, Toast.LENGTH_LONG
                               ).show()
                           }
                       }

                   }else{
                       Toast.makeText(
                           this@SignupActivity,message
                           ,Toast.LENGTH_LONG
                       ).show()

                   }
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