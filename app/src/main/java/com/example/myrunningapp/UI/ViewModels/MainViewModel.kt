package com.example.myrunningapp.UI.ViewModels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.myrunningapp.di.runRespository

class MainViewModel @ViewModelInject constructor(
    var respository: runRespository
) : ViewModel() {
}