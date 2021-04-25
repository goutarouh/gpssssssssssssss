package com.github.goutarouh.samplegpsapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.my_search_button).setOnClickListener {
            onMapSearchButton(it)
        }
    }

    fun onMapSearchButton(view: View) {
        val editWord = findViewById<EditText>(R.id.edit_text)
        val word = editWord.text.toString()

        val uriStr = "geo:0.0?q=${word}"

        val uri = Uri.parse(uriStr)

        val intent = Intent(Intent.ACTION_VIEW, uri)

        startActivity(intent)
    }
}