package com.example.miniproject

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

data class Item(val id: String, val pw: String)
data class Array(val title: String, val point: String, val date: String)

class MyViewModel : ViewModel() {
    val pointLiveData = MutableLiveData<Int>()

    init {
        pointLiveData.value=0
    }
}