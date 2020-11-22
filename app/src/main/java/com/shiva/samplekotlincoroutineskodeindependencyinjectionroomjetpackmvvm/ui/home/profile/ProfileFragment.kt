package com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.ui.home.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.R
import com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.databinding.ProfileFragmentBinding
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class ProfileFragment : Fragment(), KodeinAware {

    // From kodein we will get instance of ProfileViewModelFactory which we declare in the class MVVMApplication.
    override val kodein by kodein()

    private lateinit var viewModel: ProfileViewModel
    private val factory: ProfileViewModelFactory by instance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Creating binding instance
        val binding: ProfileFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.profile_fragment, container, false)
        viewModel = ViewModelProvider(this, factory).get(ProfileViewModel::class.java)

        // Setting BindingViewModel as our ViewModel i.e. binding the data to UI
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

//        return inflater.inflate(R.layout.profile_fragment, container, false)
        return binding.root
    }
}
