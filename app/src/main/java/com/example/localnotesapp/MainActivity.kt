  package com.example.localnotesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.example.localnotesapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

//Sebagai parents fragment dari aplikasi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

  private var _binding: ActivityMainBinding? = null
  private val binding get() = _binding!!
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    _binding = ActivityMainBinding.inflate(layoutInflater)
    setSupportActionBar(binding.toolbar.root)

    setContentView(binding.root)
  }
  fun setToolbar(title: String) {
    supportActionBar?.title = title
  }
}