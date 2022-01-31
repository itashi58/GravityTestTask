package com.example.gravitytesttask.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.gravitytesttask.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, LoadingFragment())
            .commit()

        viewModel.url.observe(this) { url ->
            if (url.isNotEmpty()) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, WebViewFragment(url))
                    .commit()
            }
        }
    }
}