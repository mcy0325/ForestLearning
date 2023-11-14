package com.example.forestlearning

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.forestlearning.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(){
    lateinit var binding : ActivityMainBinding
    lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val nevController = binding.frgNavi.getFragment<NavHostFragment>().navController
        appBarConfiguration = AppBarConfiguration(
           // nevController.graph, binding.drawerLayout
            setOf(R.id.calendarFragment),
            binding.drawerLayout
        )
        setupActionBarWithNavController(nevController,appBarConfiguration)
        //binding.bottomNavi.setupWithNavController(nevController)
        binding.drawerNav.setupWithNavController(nevController)
        setContentView(binding.root)
    }

    override fun onSupportNavigateUp(): Boolean{
        val navController = binding.frgNavi.getFragment<NavHostFragment>().navController
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}