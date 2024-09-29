package com.example.gratitude.ViewModels

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

//class ImageViewModel: ViewModel() {
//    private val _imageUris = MutableLiveData<MutableList<Uri>>(mutableListOf())
//    val imageUris: LiveData<MutableList<Uri>> get() = _imageUris
//    fun addImage(uri: Uri) {
//        _imageUris.value?.add(uri)
//        _imageUris.value = _imageUris.value // Notify observers
//    }
//}