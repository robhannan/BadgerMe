package com.redbadger.badgerme

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.NavHostFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        // Build a GoogleSignInClient with the options specified by googleSignInOptions.
        val googleSignInClient = GoogleSignIn.getClient(activity?.applicationContext!!, googleSignInOptions)

        view.findViewById<ConstraintLayout>(R.id.sign_in_button).setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            signInActivityResultLauncher.launch(signInIntent)
        }

        return view
    }

    override fun onStart() {
        super.onStart()
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        val account = GoogleSignIn.getLastSignedInAccount(activity?.applicationContext!!)
        updateUI(account)
    }

    private val signInActivityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            val navHostFragment =
                activity?.supportFragmentManager?.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            val navController = navHostFragment.navController
            val action = LoginFragmentDirections.actionLoginFragmentToInterestFragment()
            navController.navigate(action)
        }

    private fun updateUI(account: GoogleSignInAccount?) {
        account?.let {
            view?.findViewById<ConstraintLayout>(R.id.sign_in_button)?.apply{
                this.visibility = View.INVISIBLE
            }
        }
    }
}