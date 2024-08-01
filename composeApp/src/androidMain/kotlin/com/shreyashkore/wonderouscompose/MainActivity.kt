package com.shreyashkore.wonderouscompose

import App
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

@SuppressLint("StaticFieldLeak")
private var _context: Context? = null
val context get() = _context!!

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        _context = applicationContext
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App()
        }
    }
}

//@Preview
//@Composable
//fun AppAndroidPreview() {
//    App()
//}