package com.example.myrunningapp.UI.Fragments

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myrunningapp.R
import com.example.myrunningapp.UI.ViewModels.MainViewModel
import com.example.myrunningapp.Utilities
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.runfragmentlayout.*
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.util.jar.Manifest

private const val REQUEST_CODE = 1
private const val RATIONALE = "You need to accept the permission"
@AndroidEntryPoint
class RunFragment : Fragment(R.layout.runfragmentlayout), EasyPermissions.PermissionCallbacks{

    private val mainViewModel : MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fab.setOnClickListener{
            findNavController().navigate(R.id.action_runFragment2_to_trackingFragment)
        }

        arePermissionsGranted()
    }

    private fun arePermissionsGranted(){
        if(Utilities.CheckPermssions(requireContext())){
            //Permissions Granted
           return
        }
        //Show rationale Message to grant the permissiosn again
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.Q){
            EasyPermissions.requestPermissions(this,
            RATIONALE, REQUEST_CODE,
            android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.ACCESS_COARSE_LOCATION)
        }else {
            EasyPermissions.requestPermissions(this,
                RATIONALE, REQUEST_CODE,
                android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.ACCESS_COARSE_LOCATION,
                 android.Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        //the user denied them permanantly
        if(EasyPermissions.somePermissionPermanentlyDenied(this,perms)){
            AppSettingsDialog.Builder(this).build()
        }else {
            //user denied only for the first or second time
           arePermissionsGranted()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this)
    }
}