package com.example.newsnewshare.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.newsnewshare.R
import com.example.newsnewshare.databinding.ActivityLogoutBinding

class LogoutActivity : AppCompatActivity() {
    lateinit var logoutBinding: ActivityLogoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        logoutBinding =ActivityLogoutBinding.inflate(layoutInflater)
        setContentView(logoutBinding.root)


        logoutBinding.btnyes.setOnClickListener{
            Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()
            val intent= Intent(
                this@LogoutActivity,
                LoginActivity::class.java)
            startActivity(intent)
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}