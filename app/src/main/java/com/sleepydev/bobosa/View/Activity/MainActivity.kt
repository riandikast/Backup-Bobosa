package com.sleepydev.bobosa.View.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.sleepydev.bobosa.R
import com.sleepydev.bobosa.databinding.ActivityMainBinding
import com.sleepydev.bobosa.databinding.FragmentHomeBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var _mainBinding: ActivityMainBinding? = null
    private val mainBinding get() = _mainBinding!!

    lateinit var drawer : DrawerLayout
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _mainBinding =  ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)
        drawer = findViewById(R.id.drawer)
        val menu = findViewById<ImageView>(R.id.menu)
        val navView = findViewById<NavigationView>(R.id.nav_view)
        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.fragmentContainerView4
        ) as NavHostFragment

        navController = navHostFragment.navController

        menu.setOnClickListener {
            drawer.openDrawer(GravityCompat.START)

        }
        navView.itemIconTintList = null
        navView.setupWithNavController(navController)
        navView.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { menuItem ->
            val id = menuItem.itemId

            //it's possible to do more actions on several items, if there is a large amount of items I prefer switch(){case} instead of if()

            //This is for maintaining the behavior of the Navigation view
            NavigationUI.onNavDestinationSelected(menuItem, navController)
            drawer.closeDrawer(GravityCompat.START)
            //This is for closing the drawer after acting on it
            true
        })


    }
    fun refreshTitle (){
        val navDest = navController.currentDestination?.id
        if (navDest==R.id.resultFragment){
            mainBinding.apptitle.text = "Result"
        }
        if (navDest==R.id.homeFragment){
            mainBinding.apptitle.text = "Home"
        }
    }
}