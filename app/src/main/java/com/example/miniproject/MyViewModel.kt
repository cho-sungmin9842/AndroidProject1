package com.example.miniproject

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
data class Item(val id: String, val pw: String)
data class Array(val title:String,val point:String)
class MyViewModel: ViewModel() {
    val pointLiveData = MutableLiveData<Int>()
    private val items = ArrayList<Item>()
    init {
        pointLiveData.value = 0
    }
    val itemSize
        get() = items.size
    fun getallitem() = items
    fun addItem(item: Item)
    {
        items.add(item)
    }
}