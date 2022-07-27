package com.chiheb.fizzbuzz.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chiheb.fizzbuzz.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}