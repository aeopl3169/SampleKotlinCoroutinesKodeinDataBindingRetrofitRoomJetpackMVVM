package com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.R
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Toolbar, Navigation Host and Navigation View are required for Navigation Drawer.
        // For Navigation Host, navigation graph is required
        // For Navigation View, navigation menu is required
        // setting the toolbar by giving the xml id
        setSupportActionBar(toolbar)
        // navigation controller
        val navController = Navigation.findNavController(this, R.id.fragment)
        NavigationUI.setupWithNavController(nav_view, navController)
        // Actionbar with navigation controller to change the fragment title automatically in the top.
        NavigationUI.setupActionBarWithNavController(this, navController, drawer_layout)
    }
    // Setup the navigation backbutton(top left corner of the screen) click

    override fun onSupportNavigateUp(): Boolean {
        super.onSupportNavigateUp()
        return NavigationUI.navigateUp(
            Navigation.findNavController(this, R.id.fragment),
            drawer_layout
        )
    }
}
