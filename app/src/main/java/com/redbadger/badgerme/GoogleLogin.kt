package com.redbadger.badgerme

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.gms.auth.api.signin.*


class GoogleLogin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_login)
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        // Build a GoogleSignInClient with the options specified by googleSignInOptions.
        val googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)

        findViewById<ConstraintLayout>(R.id.sign_in_button).setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            signInActivityResultLauncher.launch(signInIntent)
        }
    }

    override fun onStart() {
        super.onStart()
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        val account = GoogleSignIn.getLastSignedInAccount(this)
        updateUI(account)
    }

    private val signInActivityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if (result.resultCode == Activity.RESULT_OK){
                Toast.makeText(this@GoogleLogin, "Accepted", Toast.LENGTH_SHORT).show()
            }
        }

    private fun updateUI(account: GoogleSignInAccount?) {
        account?.let {
            findViewById<ConstraintLayout>(R.id.sign_in_button).apply{
                this.visibility = View.INVISIBLE
            }
        }
    }
}