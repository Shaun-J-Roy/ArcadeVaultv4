package com.example.arcadevaultv4

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private val PREFS = "AppPrefs"
    private val KEY_LAST = "last_opened"

    @SuppressLint("UseKtx")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showToast("onCreate")

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar,
            R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, HomeFragment())
                        .commit()
                }
                R.id.nav_downloads -> {
                    // implicit intent: open website
                    val browser = Intent(Intent.ACTION_VIEW, Uri.parse("https://apkpure.com"))
                    startActivity(browser)
                }
                R.id.nav_settings -> {
                    // explicit intent
                    startActivity(Intent(this, SettingsActivity::class.java))
                }
            }
            drawerLayout.closeDrawers()
            true
        }

        // default fragment
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment())
                .commit()
        }

        // show last opened game (if any)
        val prefs = getSharedPreferences(PREFS, MODE_PRIVATE)
        val last = prefs.getString(KEY_LAST, null)
        if (!last.isNullOrEmpty()) {
            Toast.makeText(this, "${getString(R.string.last_opened)}: $last", Toast.LENGTH_LONG).show()
        }
    }

    // activity lifecycle toasts
    override fun onStart() { super.onStart(); showToast("onStart") }
    override fun onResume() { super.onResume(); showToast("onResume") }
    override fun onPause() { super.onPause(); showToast("onPause") }
    override fun onStop() { super.onStop(); showToast("onStop") }
    override fun onDestroy() { super.onDestroy(); showToast("onDestroy") }

    // helper: save last opened
    fun saveLastOpenedGame(title: String) {
        val prefs = getSharedPreferences(PREFS, MODE_PRIVATE)
        prefs.edit().putString(KEY_LAST, title).apply()
        Toast.makeText(this, "Saved last opened: $title", Toast.LENGTH_SHORT).show()
    }

    private fun showToast(s: String) {
        Toast.makeText(this, "MainActivity: $s", Toast.LENGTH_SHORT).show()
    }
}
