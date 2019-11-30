package com.android.damir.jobfind.ui

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.android.damir.jobfind.R
import com.android.damir.jobfind.network.isNetworkAvailable
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        cardView.setOnClickListener{
            val intent = Intent(this, AlmatyJobActivity::class.java)
            startActivity(intent)
            this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        cardView2.setOnClickListener{
            val intent = Intent(this, MyJobActivity::class.java)
            startActivity(intent)
            this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        if(!isNetworkAvailable(this)){
            constraint_layout.visibility = View.GONE
            network_error.visibility = View.VISIBLE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.logout -> logout()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun logout(){
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
