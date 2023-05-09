package com.example.miniproject

import android.app.AlertDialog
import android.content.Context
import android.os.Build
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.example.miniproject.databinding.HomeItemLayoutBinding
import com.example.miniproject.databinding.InputTextLayoutBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.Array

class HomeAdapter(
    private val context: Context,
    private val layoutInflater: LayoutInflater,
    private val id: Array<Int>,
    private val content: Array<String>,
    private val point: Array<Int>
) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    //View를 담아두는 상자(ViewHolder)
    class ViewHolder(private val binding: HomeItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun setContents(
            context: Context,
            layoutInflater: LayoutInflater,
            pos: Int,
            id: Array<Int>,
            content: Array<String>,
            point: Array<Int>
        ) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            val db: FirebaseFirestore = Firebase.firestore
            val historyCollectionRef = db.collection("history")
            binding.idTextView.text = "No. ${id[pos]}"
            binding.ContentTextView.text = content[pos]
            binding.img2.text = "${point[pos]}P"
            binding.root.setOnClickListener {
                when (binding.ContentTextView.text.toString()) {
                    "수학문제" -> {
                        AlertDialog.Builder(context).apply {
                            setCancelable(false)
                            setTitle("8/4+6*3+8")
                            val item = arrayOf("16", "20", "24", "28")
                            setItems(item) { _, Int ->
                                when (item[Int]) {
                                    "28" -> {
                                        Snackbar.make(it, "정답입니다.", Snackbar.LENGTH_SHORT).show()
                                        val itemMap = hashMapOf(
                                            "content" to "수학문제",
                                            "point" to 1,
                                            "date" to LocalDateTime.now()
                                                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                                        )
                                        historyCollectionRef.add(itemMap).addOnSuccessListener {
                                            Snackbar.make(
                                                binding.root,
                                                "데이터 저장 성공",
                                                Snackbar.LENGTH_SHORT
                                            ).show()
                                        }.addOnFailureListener {
                                            Snackbar.make(
                                                binding.root,
                                                "데이터 저장 실패",
                                                Snackbar.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                                    else -> Snackbar.make(
                                        binding.root, "오답입니다.", Snackbar.LENGTH_SHORT
                                    ).show()
                                }
                            }
                            show()
                        }
                    }
                    "야구퀴즈" -> {
                        AlertDialog.Builder(context).apply {
                            setCancelable(false)
                            val items = arrayOf("양의지", "정수빈", "오재일", "허경민")
                            setTitle("역대 한국시리즈 MVP가 아닌 야구선수는 누구인가?")
                            setItems(items) { _, which ->
                                if (items[which] == "허경민") {
                                    Snackbar.make(it, "정답입니다.", Snackbar.LENGTH_SHORT).show()
                                    val itemMap = hashMapOf(
                                        "content" to "야구퀴즈",
                                        "point" to 2,
                                        "date" to LocalDateTime.now()
                                            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                                    )
                                    historyCollectionRef.add(itemMap).addOnSuccessListener {
                                        Snackbar.make(
                                            binding.root,
                                            "데이터 저장 성공",
                                            Snackbar.LENGTH_SHORT
                                        ).show()
                                    }.addOnFailureListener {
                                        Snackbar.make(
                                            binding.root,
                                            "데이터 저장 실패",
                                            Snackbar.LENGTH_SHORT
                                        ).show()
                                    }
                                } else {
                                    Snackbar.make(it, "오답입니다.", Snackbar.LENGTH_SHORT).show()
                                }
                            }
                            setNegativeButton("닫기") { DialogInterface, Int ->
                                DialogInterface.dismiss()
                            }
                            show()
                        }
                    }
                    "넌센스퀴즈" -> {
                        AlertDialog.Builder(context).apply {
                            val bind = InputTextLayoutBinding.inflate(layoutInflater)
                            setView(bind.root)
                            bind.tv.text = "우주인들이 술 마시는 곳은?"
                            bind.answer.setOnKeyListener { view, i, keyEvent ->
                                if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) {
                                    imm.hideSoftInputFromWindow(bind.answer.windowToken, 0)
                                    if (bind.answer.text.toString() == "스페이스바" || bind.answer.text.toString() == "spacebar") {
                                        val itemMap = hashMapOf(
                                            "content" to "넌센스퀴즈",
                                            "point" to 3,
                                            "date" to LocalDateTime.now()
                                                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                                        )
                                        historyCollectionRef.add(itemMap).addOnSuccessListener {
                                            Snackbar.make(
                                                binding.root,
                                                "데이터 저장 성공",
                                                Snackbar.LENGTH_SHORT
                                            ).show()
                                        }.addOnFailureListener {
                                            Snackbar.make(
                                                binding.root,
                                                "데이터 저장 실패",
                                                Snackbar.LENGTH_SHORT
                                            ).show()
                                        }
                                        Snackbar.make(view, "정답입니다.", Snackbar.LENGTH_SHORT).show()
                                    } else {
                                        Snackbar.make(view, "오답입니다.", Snackbar.LENGTH_SHORT).show()
                                    }
                                }
                                false
                            }
                            setNegativeButton("닫기") { dialog, which ->
                                dialog.dismiss()
                            }
                            show()
                        }
                    }
                    "사자성어" -> {
                        AlertDialog.Builder(context).apply {
                            setTitle("닭의 무리중 한마리 학이란 뜻으로 많은사람들중 뛰어난 인물의 뜻인 사자성어는?")
                            val item = arrayOf("설상가상", "군계일학", "격세지감", "각골난망")
                            setItems(item) { _, which ->
                                if (item[which] == "군계일학") {
                                    Snackbar.make(it, "정답입니다.", Snackbar.LENGTH_SHORT).show()
                                    val itemMap = hashMapOf(
                                        "content" to "사자성어",
                                        "point" to 4,
                                        "date" to LocalDateTime.now()
                                            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                                    )
                                    historyCollectionRef.add(itemMap).addOnSuccessListener {
                                        Snackbar.make(
                                            binding.root,
                                            "데이터 저장 성공",
                                            Snackbar.LENGTH_SHORT
                                        ).show()
                                    }.addOnFailureListener {
                                        Snackbar.make(
                                            binding.root,
                                            "데이터 저장 실패",
                                            Snackbar.LENGTH_SHORT
                                        ).show()
                                    }
                                } else {
                                    Snackbar.make(it, "오답입니다.", Snackbar.LENGTH_SHORT).show()
                                }
                            }
                            setNegativeButton("닫기") { dialog, which ->
                                dialog.dismiss()
                            }
                            show()
                        }
                    }
                    else -> {
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
        }
    }

    //ViewHolder 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = HomeItemLayoutBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    //ViewHolder에 데이터 연결
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setContents(context, layoutInflater, position, id, content, point)
    }

    //항목의 개수 리턴
    override fun getItemCount(): Int {
        return id.size
    }
}
