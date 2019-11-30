package com.android.damir.jobfind.ui

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.damir.jobfind.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar

    private lateinit var auth: FirebaseAuth
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        progressBar = findViewById(R.id.progress_bar)

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        auth = FirebaseAuth.getInstance()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        val signInButton = findViewById<SignInButton>(R.id.google_sign_in)
        signInButton.setOnClickListener{
            signIn()
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == RC_SIGN_IN){
            progressBar.visibility = View.VISIBLE
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try{
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            }catch (e: ApiException){
                e.printStackTrace()
            }
        }
    }

    private fun updateUI(user: FirebaseUser?){
        if(user != null){
            Log.i(TAG, "startActivity")
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }else{
            Log.i(TAG, "not signed")
            findViewById<TextView>(R.id.not_signed).visibility = View.VISIBLE
        }
    }

    private fun signIn(){
        progressBar.visibility = View.VISIBLE
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent,
            RC_SIGN_IN
        )
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount){
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener(this){
            if(it.isSuccessful){
                Log.d(TAG, "signInWithCredential: success")
                val user = auth.currentUser
                updateUI(user)
            }else{
                Log.w(TAG, "signInWithCredential: failure", it.exception)
                updateUI(null)
            }
        }
    }

    companion object{
        private const val TAG = "LoginActivity"
        private const val RC_SIGN_IN = 1
    }
}
