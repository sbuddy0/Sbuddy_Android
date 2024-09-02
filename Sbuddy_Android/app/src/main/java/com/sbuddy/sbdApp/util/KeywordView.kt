package com.sbuddy.sbdApp.util

import android.content.Context
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.sbuddy.sbdApp.R

class KeywordView(context: Context) : androidx.appcompat.widget.AppCompatTextView(context) {

    init {
        this.setPadding(40, 25, 40, 25)
        this.setTextColor(android.graphics.Color.parseColor("#998DFF"))
        this.background = ContextCompat.getDrawable(context, R.drawable.keyword_rounded_corner)
    }
}
