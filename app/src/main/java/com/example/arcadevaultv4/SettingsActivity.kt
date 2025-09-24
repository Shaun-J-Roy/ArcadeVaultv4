package com.example.arcadevaultv4

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {

    private val PREFS = "AppPrefs"
    private val KEY_USERNAME = "username"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val edit = findViewById<EditText>(R.id.editTextUsername)
        val btn = findViewById<Button>(R.id.buttonSave)

        val prefs = getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        edit.setText(prefs.getString(KEY_USERNAME, ""))

        btn.setOnClickListener {
            val name = edit.text.toString()
            prefs.edit().putString(KEY_USERNAME, name).apply()
            Toast.makeText(this, "Saved username: $name", Toast.LENGTH_SHORT).show()
        }

        Toast.makeText(this, "Settings onCreate", Toast.LENGTH_SHORT).show()
    }
}
