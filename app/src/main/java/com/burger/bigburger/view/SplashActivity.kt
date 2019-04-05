package com.burger.bigburger.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.burger.bigburger.R
import com.burger.bigburger.view.burgerslist.MainActivity

class SplashActivity : AppCompatActivity() {

    private val delay = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        // Start MainActivity after 3 secondes
        Handler().postDelayed({
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }, delay.toLong())
    }
}
