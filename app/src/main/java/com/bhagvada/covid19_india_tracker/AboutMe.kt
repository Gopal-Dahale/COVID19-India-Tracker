package com.bhagvada.covid19_india_tracker

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_about_me.*

class AboutMe : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_me)
        backButton.setOnClickListener{
            finish()
        }
    }

    fun openBrowser(view: View) {
        val url = view.getTag().toString()

        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.addCategory(Intent.CATEGORY_BROWSABLE)

        //pass the url to intent data

        //pass the url to intent data
        intent.data = Uri.parse(url)

        startActivity(intent)
    }
}
