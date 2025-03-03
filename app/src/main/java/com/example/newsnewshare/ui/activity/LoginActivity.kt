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
import com.example.newsnewshare.databinding.ActivityLoginBinding
import com.example.newsnewshare.repository.UserRepositoryImpl
import com.example.newsnewshare.viewmodel.UserViewModel

class LoginActivity : AppCompatActivity() {

    lateinit var binding:ActivityLoginBinding
//    lateinit var userViewModel:UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding=ActivityLoginBinding.inflate(layoutInflater)
        setContentView( binding.root)

//        var repo = UserRepositoryImpl()
//        userViewModel = UserViewModel(repo)

        binding.btnlogin.setOnClickListener {
            var email = binding.edittabusernamee. text.toString()
            var password = binding.edittabpassword.text.toString()

            if (email.isEmpty()){
                binding.displayLoginn.text = "login failed"
                binding.displayLoginn.visibility=View.GONE
               binding.edittabusernamee.error = "Email can't be empty" //  Ensures Email can't be empty
            }else if(password.isEmpty()){
                binding.displayLoginn.text = "login failed"
                binding.displayLoginn.visibility=View.GONE
                binding.edittabpassword.error = "Password can't be empty" //  Ensures Password can't be empty
            } else if (!email.endsWith("@gmail.com")) {
                binding.displayLoginn.text = "login failed"
                binding.displayLoginn.visibility=View.GONE//  Ensures it's a Gmail address
                binding.edittabusernamee.error = "Enter a valid Email address"
            } else {
                //  Admin Credentials
                if (email == "admin11@gmail.com" && password == "admin1234") {
                    Toast.makeText(this, "Admin login successful", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, DashboardActivity::class.java))

                    finish() // Close login activity
                } else{
                    binding.displayLoginn.text = "login failed"
                    binding.displayLoginn.visibility=View.GONE
                    Toast.makeText(this, "Invalid admin credentials", Toast.LENGTH_LONG).show()
                    }
                }
            }





//        binding.btnregister.setOnClickListener{
//            val intent= Intent(
//                this@LoginActivity,
//                SignupActivity::class.java)
//            startActivity(intent)
//        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.outputadmin)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}