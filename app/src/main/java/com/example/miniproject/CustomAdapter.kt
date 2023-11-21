package com.example.miniproject

import android.app.AlertDialog
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.annotation.Size
import androidx.recyclerview.widget.RecyclerView
import com.example.miniproject.databinding.SearchItemLayoutBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDate

class CustomAdapter(private val items: List<Array>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
    //View를 담아두는 상자(ViewHolder)
    class ViewHolder(private val binding: SearchItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun setContents(pos: Int, items: List<Array>) {
            var totalPoint = 0
            var i = 0
            while (i <= pos) {
                totalPoint += Integer.parseInt(items[i].point)
                i++
            }
            binding.textView.text = items[pos].title
            binding.textView2.text = "${items.get(pos).point} P"
            binding.textView3.text = items[pos].date
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

    //ViewHolder 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = SearchItemLayoutBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    //ViewHolder에 데이터 연결
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setContents(position, items)
    }

    //항목의 개수 리턴
    override fun getItemCount(): Int {
        return items.size
    }
}