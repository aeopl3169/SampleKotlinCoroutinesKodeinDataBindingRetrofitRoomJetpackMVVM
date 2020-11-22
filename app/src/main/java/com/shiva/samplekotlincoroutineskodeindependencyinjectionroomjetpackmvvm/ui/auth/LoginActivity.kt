package com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.R
import com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.databinding.ActivityLoginBinding
import com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.ui.home.HomeActivity
import com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.utils.ApiException
import com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.utils.NoInternetException
import com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.utils.snackbar
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class LoginActivity() : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val factory: AuthViewModelFactory by instance()
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: AuthViewModel
    private val TAG = "LoginActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Binding the ViewModel
        // "ActivityLoginBinding" is  generated based on the xml file name (activity_login_first)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        // Getting the ViewModel
//        val viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        viewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)

        // Getting the LoggedIn user if exist
        viewModel.getLoggedInUser().observe(this, Observer { user ->
            if (user != null) {
                Intent(this, HomeActivity::class.java).also {
                    // The below flags to close the loginActivity after successful login
                    // (i.e. it will not store in back stack).
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
        })
        binding.buttonSignIn.setOnClickListener {
            loginUser()
        }
        binding.textViewSignUp.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }

    private fun loginUser() {
        val email = binding.editTextLoginEmail.text.toString().trim()
        val password = binding.editTextLoginPassword.text.toString().trim()

        lifecycleScope.launch {
            try {
                val authResponse = viewModel.userLogin(email, password)

                if (authResponse.user != null) {
                    viewModel.saveLoggedInUser(authResponse.user)
                    Log.i(TAG, "snack1")
                } else {
                    binding.root.snackbar(authResponse.message!!)
                    Log.i(TAG, "snack")
                }
            } catch (e: ApiException) {
                Log.e(TAG, "$e")
            } catch (e: NoInternetException) {
                Log.e(TAG, "$e")
            }
        }
    }
}