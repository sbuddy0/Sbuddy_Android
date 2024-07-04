package com.sbuddy.sbdApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView

class SearchViewActivity : AppCompatActivity() {

    private lateinit var beforeBtn: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_view)

        beforeBtn = findViewById(R.id.before_btn)
        beforeBtn.setOnClickListener(View.OnClickListener {
            onBackPressed()
        })
    }
}