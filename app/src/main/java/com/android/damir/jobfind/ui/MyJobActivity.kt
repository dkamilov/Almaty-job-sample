package com.android.damir.jobfind.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.damir.jobfind.R

class MyJobActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_job)
    }

    override fun finish() {
        super.finish()
        this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

}
