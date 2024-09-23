package com.example.gratitude.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    val fabClicked = MutableLiveData<Boolean>()
}