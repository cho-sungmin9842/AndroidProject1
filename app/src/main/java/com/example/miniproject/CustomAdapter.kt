package com.example.miniproject

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.miniproject.databinding.ItemLayoutBinding

class CustomAdapter(val list: ArrayList<Array>):RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
    //View를 담아두는 상자(ViewHolder)
    inner class ViewHolder(private val binding: ItemLayoutBinding):RecyclerView.ViewHolder(binding.root){
        fun setContents(pos:Int)
        {
            binding.textView.text=list.get(pos).title
            binding.textView2.text=list.get(pos).point
        }
    }
    //ViewHolder 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater=LayoutInflater.from(parent.context)
        val binding=ItemLayoutBinding.inflate(layoutInflater,parent,false)
        val viewHolder=ViewHolder(binding)
        return viewHolder
    }
    //ViewHolder에 데이터 연결
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setContents(position)
    }
    //항목의 개수 리턴
    override fun getItemCount()=list.size
}