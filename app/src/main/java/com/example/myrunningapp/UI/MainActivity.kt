package com.example.myrunningapp.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myrunningapp.Constants
import com.example.myrunningapp.R
import com.example.myrunningapp.services.TrackingService.Companion.pathPoints
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        providedPendignIntentIfNeeded(intent)
        setSupportActionBar(toolbar)
        bottomNavigationView.setupWithNavController(NavHostFragment.findNavController())
        NavHostFragment.findNavController().addOnDestinationChangedListener(object : NavController.OnDestinationChangedListener{
            override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {
                when(destination.id){
                    R.id.runFragment2,R.id.settingsFragment2,R.id.statsFragment ->
                        bottomNavigationView.visibility = View.VISIBLE
                    else ->
                        bottomNavigationView.visibility = View.INVISIBLE
                }
            }
        })
    }

    private fun providedPendignIntentIfNeeded(intent : Intent?){
      if(intent?.action == Constants.ACION_SHOW_TRACKING_FRAGMEMTN){
           NavHostFragment.findNavController().navigate(R.id.trackingFragment)
      }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        providedPendignIntentIfNeeded(intent)
    }
}