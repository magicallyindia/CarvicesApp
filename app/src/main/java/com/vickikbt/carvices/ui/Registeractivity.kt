package com.vickikbt.carvices.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.vickikbt.carvices.R
import com.vickikbt.carvices.databinding.ActivityRegisteractivityBinding
import java.util.*

class Registeractivity : AppCompatActivity() {

    lateinit var binding: ActivityRegisteractivityBinding
    private val firebaseAuth = FirebaseAuth.getInstance()
    var selectedPhotoUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_registeractivity)

        binding.buttonSelectPhoto.setOnClickListener {
            selectProfilePic()
        }

        binding.buttonRegister.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val username = binding.usernameRegister.text.toString()
        val email = binding.emailRegister.text.toString()
        val password = binding.passwordRegister.text.toString()

        when {
            username.isEmpty() -> Toast.makeText(this, "Enter Username!", Toast.LENGTH_SHORT).show()
            email.isEmpty() -> Toast.makeText(this, "Enter Email!", Toast.LENGTH_SHORT).show()
            password.isEmpty() -> Toast.makeText(this, "Enter Password!", Toast.LENGTH_SHORT).show()
            password.length < 8 -> Toast.makeText(
                this,
                "Password is too short!",
                Toast.LENGTH_SHORT
            ).show()
        }

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return
        }

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                uploadImageToFirebase()

                if (!it.isSuccessful) return@addOnCompleteListener
                Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))

            }
            .addOnFailureListener {
                Log.e("Registration", "Failed to create user because: ${it.message}")
                Toast.makeText(
                    this,
                    "Failed to create user because: ${it.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun uploadImageToFirebase() {
        if (selectedPhotoUri == null) return

        val filename = UUID.randomUUID().toString()
        val storageRef = FirebaseStorage.getInstance().getReference("/Profile Pictures/$filename")
        storageRef.putFile(selectedPhotoUri!!)
            .addOnSuccessListener {
                Log.e("RegisterActivity", "Profile Picture Uploaded Successfully")

                storageRef.downloadUrl.addOnSuccessListener {
                    saveUserToFirebaseDatabase(it.toString())
                    Log.e("RegisterActivity", "Uploaded user data.")
                }
                    .addOnFailureListener {
                        Log.e("RegisterActivity", "Failed to upload user data.")
                    }
            }
    }

    private fun saveUserToFirebaseDatabase(profileImageUrl: String) {
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val databaseRef = FirebaseDatabase.getInstance().getReference("/Users/$uid")

        val email = binding.editTextEmailRegister.text.toString()
        val username = binding.editTextUsernameRegister.text.toString()
        val phonenumber = binding.editTextPhoneRegister.text.toString()

        val users = Users(username, email, phonenumber, uid, profileImageUrl)

        databaseRef.setValue(users)
            .addOnSuccessListener {
                Toast.makeText(this, "Registered Successfully!", Toast.LENGTH_SHORT).show()
                binding.progressBarRegister.visibility = View.GONE
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
    }

    private fun selectProfilePic() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "images/*"
        startActivityForResult(intent, 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==0 && resultCode== Activity.RESULT_OK && data!=null){
            selectedPhotoUri = data.data
            val bitMap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)

            binding.selectPhotoImageView.setImageBitmap(bitMap)
            binding.buttonSelectPhoto.alpha = 0f
        }
    }
}
