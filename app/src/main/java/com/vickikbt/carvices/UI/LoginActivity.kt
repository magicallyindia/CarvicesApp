package com.vickikbt.carvices.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.vickikbt.carvices.R
import com.vickikbt.carvices.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    lateinit var binding:ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_login)

        binding.buttonLogin.setOnClickListener(){
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}
