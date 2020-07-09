package com.example.myrunningapp.services

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_LOW
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Build
import android.os.Looper
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.myrunningapp.Constants
import com.example.myrunningapp.R
import com.example.myrunningapp.UI.MainActivity
import com.example.myrunningapp.Utilities
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.processNextEventInCurrentThread
import timber.log.Timber

typealias Polyline = MutableList<LatLng>
typealias PolyLines =  MutableList<Polyline>

class TrackingService : LifecycleService(){

    var isFirstRun = true
    lateinit var fusedLocationProviderClient : FusedLocationProviderClient
    companion object{
       var  isTracking = MutableLiveData<Boolean>()
        val pathPoints = MutableLiveData<PolyLines>()



    }

    override fun onCreate() {
        super.onCreate()
        isTracking.postValue(false)
        pathPoints.postValue(mutableListOf())

        fusedLocationProviderClient = FusedLocationProviderClient(this)
        isTracking.observe(this, Observer {
            updateLocation(it)
        })
    }

    @SuppressLint("MissingPermission")
    private fun updateLocation(isTracking : Boolean){
        if(isTracking){
            if(Utilities.CheckPermssions(this)){
                var locationRequest = LocationRequest().apply {
                    interval = Constants.LOCATION_UPDATE_INTERVAL
                    fastestInterval = Constants.FASTEST_UPDATE_INTERVAL
                    priority = PRIORITY_HIGH_ACCURACY
                }

                fusedLocationProviderClient.requestLocationUpdates(locationRequest,
                getLocationResult, Looper.getMainLooper())
            }

        }else {
            fusedLocationProviderClient.removeLocationUpdates(getLocationResult)
        }
    }
    var getLocationResult = object : LocationCallback(){
        override fun onLocationResult(locationResult: LocationResult?) {
            super.onLocationResult(locationResult)
            if(isTracking.value!!){
                locationResult?.locations.let {
                    for(location in it!!){
                        AddPathPoints(location)
                    }
                }
            }
        }
    }
    private fun AddPathPoints(location : Location?){
        location?.let {
            var position = LatLng(location.altitude,location.longitude)
            pathPoints?.value?.apply {
                last().add(position)
                pathPoints.postValue(this)
            }
        }
    }
    private fun AddEmptyPolylines() = pathPoints.value?.apply {
        add(mutableListOf())
        pathPoints.postValue(this)
    } ?: pathPoints.postValue(mutableListOf(mutableListOf()))

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        intent?.let {
            when(it.action){
               Constants.SERVICE_START_RESUME ->{
                   if(isFirstRun){
                       createForegroundNotification()
                       isFirstRun = false
                   }else{
                       Timber.d("Not for the first run")
                   }
                  
               }
                Constants.ACTION_PAUSE_SERVICE ->{
                   Timber.d("Service PAUSE")
                }
                Constants.ACTION_STOP_SERVICE ->{
                    Timber.d("SERVICE STOP ")
                }
            }
        }

        return Service.START_NOT_STICKY
    }

    private fun createForegroundNotification(){
        AddEmptyPolylines()
        isTracking.postValue(true)
        var notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createNotificationChannel(notificationManager)
        }

        var channel = NotificationCompat.Builder(this,Constants.NOTIFICATION_CHANNEL_ID)
            .setAutoCancel(false) //preventing the notification from being autocancelled or removed by the user
            .setOngoing(true) //notification can't be swiped when set to true
            .setSmallIcon(R.drawable.ic_baseline_directions_run_24)
            .setContentTitle("Running..")
            .setContentText("00:00:00")
            .setContentIntent(createPendingIntent())

       startForeground(Constants.NOTIFICATION_ID,channel.build())




    }

    private fun createPendingIntent() = PendingIntent.getActivity(this,
    1,Intent(this,MainActivity::class.java).also {
            it.action = Constants.NOTIFICATION_SHOW_ACTION
        }, FLAG_UPDATE_CURRENT)

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager){
        var notificationChannel = NotificationChannel(Constants.NOTIFICATION_CHANNEL_ID,
        Constants.NOTIFICATION_CHANNEL_NAME,IMPORTANCE_LOW)
        notificationManager.createNotificationChannel(notificationChannel)
    }
}