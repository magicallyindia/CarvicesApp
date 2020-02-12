package com.vickikbt.carvices.UI

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.vickikbt.carvices.R
import com.vickikbt.carvices.databinding.ActivityRegisteractivityBinding

class Registeractivity : AppCompatActivity() {

    lateinit var binding: ActivityRegisteractivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_registeractivity)
    }
}
