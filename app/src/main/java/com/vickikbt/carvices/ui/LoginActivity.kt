package com.vickikbt.carvices.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.vickikbt.carvices.R
import com.vickikbt.carvices.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    val firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.buttonLogin.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.linearLayoutRegister.setOnClickListener {
           startActivity(Intent(this, Registeractivity::class.java))
        }
    }

    private fun loginUser() {
        val email = binding.editTextEmailLogin.text.toString()
        val password = binding.editTextPasswordLogin.text.toString()

        when {
            email.isEmpty() -> Toast.makeText(this, "Enter Email!", Toast.LENGTH_SHORT).show()
            password.isEmpty() -> Toast.makeText(this, "Enter Password!", Toast.LENGTH_SHORT).show()
        }

        if (email.isEmpty() || password.isEmpty()) {
            return
        }

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to login because: ${it.message}", Toast.LENGTH_SHORT)
                    .show()
            }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

}
