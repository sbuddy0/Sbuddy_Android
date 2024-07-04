package com.sbuddy.sbdApp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView

class LankSearchActivity : AppCompatActivity() {

    private lateinit var searchBtn: TextView
    private lateinit var bottomNavigation: BottomNavigationView
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_lank_search)

        bottomNavigation = findViewById(R.id.bottomNavigationView);
        bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.fragment_home -> {
                    val intent = Intent(this, MainActivity::class.java)
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


    }
}