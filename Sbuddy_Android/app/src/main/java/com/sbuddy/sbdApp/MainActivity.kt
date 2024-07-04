package com.sbuddy.sbdApp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : ComponentActivity() {

    private lateinit var logo: TextView
    private lateinit var searchBtn: TextView
    private lateinit var bottomNavigation: BottomNavigationView
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main)

        searchBtn = findViewById(R.id.search)
        searchBtn.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, SearchViewActivity::class.java)
            startActivity(intent)
        })

        bottomNavigation = findViewById(R.id.bottomNavigationView);
        bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.fragment_search -> {
                    val intent = Intent(this, LankSearchActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.fragment_favorite -> {
                    // Favorite 메뉴 아이템에 대한 동작 구현
                    true
                }
                R.id.fragment_settings -> {
                    // Settings 메뉴 아이템에 대한 동작 구현
                    true
                }
                else -> false
            }
        }

        logo = findViewById(R.id.logo)
        logo.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        })

    }
}

