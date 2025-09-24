package com.example.arcadevaultv4

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream

class GameDetailActivity : AppCompatActivity() {

    private var apkResId: Int = 0
    private lateinit var gameTitle: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_detail)

        gameTitle = intent.getStringExtra("GAME_TITLE") ?: "Unknown"

        val titleText: TextView = findViewById(R.id.game_title)
        val launchButton: Button = findViewById(R.id.launch_button)

        titleText.text = gameTitle

        // look up APK in res/raw by using lowercase name (example: res/raw/taschemon.apk)
        apkResId = resources.getIdentifier(
            gameTitle.lowercase().replace(" ", "_"),
            "raw",
            packageName
        )

        launchButton.setOnClickListener {
            if (apkResId != 0) {
                installFromRaw(apkResId, "$gameTitle.apk")
            } else {
                Toast.makeText(this, "APK not found for $gameTitle", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun installFromRaw(resId: Int, outFileName: String) {
        try {
            // Copy raw APK into cache folder
            val outFile = File(cacheDir, outFileName)
            val inputStream = resources.openRawResource(resId)
            val outputStream = FileOutputStream(outFile)

            inputStream.use { input ->
                outputStream.use { output ->
                    input.copyTo(output)
                }
            }

            // Get URI via FileProvider
            val uri: Uri = FileProvider.getUriForFile(
                this,
                "$packageName.fileprovider",
                outFile
            )

            // Trigger Android install dialog
            val installIntent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(uri, "application/vnd.android.package-archive")
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(installIntent)

        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Failed to install: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}
