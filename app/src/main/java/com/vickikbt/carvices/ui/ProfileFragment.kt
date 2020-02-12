package com.vickikbt.carvices.ui


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.vickikbt.carvices.R
import com.vickikbt.carvices.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)

        loadProfile()

        binding.relativeLayoutLogout.setOnClickListener {
            signOut()
        }

        return binding.root
    }

    private fun loadProfile() {
        val firebaseAuth = FirebaseAuth.getInstance().currentUser
        val uid = firebaseAuth!!.uid

        //TODO(): Add progress dialog to load profile for the first time.
        val databaseReference = FirebaseDatabase.getInstance().reference.child("Users")
        databaseReference.child(uid)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val username = dataSnapshot.child("username").value.toString()
                    val email = dataSnapshot.child("email").value.toString()
                    val phoneNumber = dataSnapshot.child("phoneNumber").value.toString()
                    val profilePic = dataSnapshot.child("profileImageUrl").value.toString()

                    binding.profileUserName.text = username
                    binding.profileEmailAddress.text = email
                    binding.profilePhonenumber.text = phoneNumber
                    Picasso.get().load(profilePic).into(binding.imageViewProfile)

                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Toast.makeText(activity, "Failed to load profile!", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun signOut() {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(activity, LoginActivity::class.java))
    }


}
