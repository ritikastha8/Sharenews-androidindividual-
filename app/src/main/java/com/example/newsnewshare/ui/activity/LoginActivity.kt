package com.example.newsnewshare.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.newsnewshare.R
import com.example.newsnewshare.databinding.ActivityLoginBinding
import com.example.newsnewshare.repository.UserRepositoryImpl
import com.example.newsnewshare.viewmodel.UserViewModel

class LoginActivity : AppCompatActivity() {

    lateinit var binding:ActivityLoginBinding
    lateinit var userViewModel:UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding=ActivityLoginBinding.inflate(layoutInflater)
        setContentView( binding.root)

        var repo = UserRepositoryImpl()
        userViewModel = UserViewModel(repo)

        binding.btnlogin.setOnClickListener {
            var email = binding.edittabusernamee. text.toString()
            var password = binding.edittabpassword.text.toString()

            if (email.isEmpty()){
               binding.edittabusernamee.error = "Email can't be empty"
            }else if(password.isEmpty()){
                binding.edittabpassword.error = "Password can't be empty"
            }else{
                userViewModel.login(email,password){success,message ->
                    if(success){
                        var intent = Intent(this@LoginActivity,
                            DashboardActivity::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this@LoginActivity,
                            message, Toast.LENGTH_LONG).show()
                    }
                }
            }



        }

        binding.btnregister.setOnClickListener{
            val intent= Intent(
                this@LoginActivity,
                SignupActivity::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}