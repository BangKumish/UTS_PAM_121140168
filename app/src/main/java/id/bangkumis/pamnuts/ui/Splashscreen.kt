package id.bangkumis.pamnuts.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import id.bangkumis.pamnuts.R
import id.bangkumis.pamnuts.activity.LoginActivity

class Splashscreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splashscreen)

        supportActionBar?.hide()

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }, 3000)
    }
}