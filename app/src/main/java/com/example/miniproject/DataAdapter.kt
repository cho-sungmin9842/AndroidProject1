package com.example.miniproject

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.miniproject.databinding.SearchItemLayoutBinding

class ViewHolder(private val binding: SearchItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(list: MutableList<Array>, pos: Int) {
        var totalPoint = 0
        var i = 0
        while (i <= pos) {
            totalPoint += Integer.parseInt(list[i].point)
            i++
        }
        binding.textView.text = list[pos].title
        binding.textView2.text = "${list.get(pos).point} P"
        binding.textView3.text = list[pos].date
        binding.textView4.text = "합계 ${totalPoint} P"

        binding.root.setOnClickListener {
            AlertDialog.Builder(it.context).apply {
                setTitle("클릭한 항목 자세히 보기")
                if (it.parent != null) {
                    (it.parent as ViewGroup).removeView(it)
                }
                setView(it)
                show()
            }
        }
    }
}
class DataAdapter : ListAdapter<Array, ViewHolder>(diffUtil) {
    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Array>() {
            override fun areContentsTheSame(oldItem: Array, newItem: Array) =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: Array, newItem: Array) =
                oldItem.title == newItem.title
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            SearchItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList, position)
    }
}