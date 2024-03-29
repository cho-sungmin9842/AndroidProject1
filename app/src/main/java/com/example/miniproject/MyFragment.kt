package com.example.miniproject

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.miniproject.databinding.*
import com.example.miniproject.databinding.HomefragmentBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Random

class HomeFragment : Fragment() {
    private val randomArray = arrayOf(
        randomNumCreate(), randomNumCreate(), randomNumCreate(), randomNumCreate()
    )
    private val contentArray = arrayOf("수학문제", "야구퀴즈", "넌센스퀴즈", "사자성어")
    private val pointArray = arrayOf(1, 2, 3, 4)

    private fun randomNumCreate(): Int {
        return Random().nextInt(100000)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.homefragment, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Firebase.firestore.collection("history").get().addOnSuccessListener {
            var sum = 0
            for (doc in it) {
                sum += Integer.parseInt(doc.data["point"].toString())
            }
            (requireActivity() as AppCompatActivity).supportActionBar?.title = "${sum}P"
        }
        val binding = HomefragmentBinding.bind(view)
        binding.recyclerView.adapter = HomeAdapter(
            requireContext(),
            layoutInflater,
            (requireActivity() as AppCompatActivity).supportActionBar,
            randomArray,
            contentArray,
            pointArray
        )
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.addItemDecoration(CustomDecoration(20, Color.rgb(220, 218, 205)))
        binding.recyclerView.setHasFixedSize(true)
    }
}

class QuizFragment : Fragment() {
    private val historyCollectionRef = Firebase.firestore.collection("history")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.quizfragment, container, false)
    }

    private fun updateActionTitle(actionBar: ActionBar?) {
        Firebase.firestore.collection("history").addSnapshotListener { value, error ->
            var sum = 0
            value?.documentChanges?.forEach { doc ->
                sum += Integer.parseInt(doc.document.data["point"].toString())
            }
            actionBar?.title = "${sum}P"
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        updateActionTitle((requireActivity() as AppCompatActivity).supportActionBar)
        val binding = QuizfragmentBinding.bind(view)
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        binding.os.setOnClickListener {
            AlertDialog.Builder(requireContext()).apply {
                setCancelable(false)
                val bind = InputTextLayoutBinding.inflate(layoutInflater)
                setView(bind.root)
                bind.tv.text =
                    "${resources.getString(R.string.osexplanation1)} 입력후 Enter 입력해주세요.\n(한글로 입력하세요)"
                bind.answer.setOnKeyListener { view, i, keyEvent ->
                    if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) {
                        imm.hideSoftInputFromWindow(bind.answer.windowToken, 0)
                        if (bind.answer.text.toString() == "스왑영역" || bind.answer.text.toString() == "스왑 영역") {
                            val itemMap = hashMapOf(
                                "content" to "os문제1",
                                "point" to 3,
                                "date" to LocalDateTime.now()
                                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                            )
                            historyCollectionRef.add(itemMap).addOnSuccessListener {}
                                .addOnFailureListener {}
                            updateActionTitle((requireActivity() as AppCompatActivity).supportActionBar)
                            Snackbar.make(view, "정답입니다.", Snackbar.LENGTH_SHORT).show()
                            AlertDialog.Builder(requireContext()).apply {
                                setMessage("문제를 더 풀까요?")
                                setPositiveButton("네") { _, _ ->
                                    AlertDialog.Builder(requireContext()).apply {
                                        setCancelable(false)
                                        val binding = InputTextLayoutBinding.inflate(layoutInflater)
                                        setView(binding.root)
                                        binding.tv.text =
                                            "${resources.getString(R.string.osexplanation2)} 입력후 Enter 입력해주세요.\n(한글로 입력하세요)"
                                        binding.answer.setOnKeyListener { view, i, keyEvent ->
                                            if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) {
                                                imm.hideSoftInputFromWindow(
                                                    bind.answer.windowToken,
                                                    0
                                                )
                                                if (binding.answer.text.toString() == "가상 메모리 기법" || binding.answer.text.toString() == "가상메모리기법") {
                                                    Snackbar.make(
                                                        view,
                                                        "정답입니다.",
                                                        Snackbar.LENGTH_SHORT
                                                    ).show()
                                                    val itemMap = hashMapOf(
                                                        "content" to "os문제2",
                                                        "point" to 1,
                                                        "date" to LocalDateTime.now()
                                                            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                                                    )
                                                    historyCollectionRef.add(itemMap)
                                                        .addOnSuccessListener {}
                                                        .addOnFailureListener {}
                                                    updateActionTitle((requireActivity() as AppCompatActivity).supportActionBar)
                                                } else {
                                                    Snackbar.make(
                                                        view,
                                                        "오답입니다.",
                                                        Snackbar.LENGTH_SHORT
                                                    ).show()
                                                }
                                            }
                                            false
                                        }
                                        setNegativeButton("닫기") { DialogInterface, Int ->
                                            DialogInterface.dismiss()
                                        }
                                        show()
                                    }
                                }
                                setNegativeButton("아니요") { DialogInterface, Int ->
                                    DialogInterface.dismiss()
                                }
                                show()
                            }
                        } else {
                            Snackbar.make(view, "오답입니다.", Snackbar.LENGTH_SHORT).show()
                        }
                    }
                    false
                }
                setNegativeButton("닫기") { DialogInterface, Int ->
                    DialogInterface.dismiss()
                }
                show()
            }
        }
        binding.sw.setOnClickListener {
            AlertDialog.Builder(requireContext()).apply {
                setCancelable(false)
                val bind = InputTextLayoutBinding.inflate(layoutInflater)
                setView(bind.root)
                bind.tv.text =
                    "${resources.getString(R.string.swexplanation1)} 입력후 Enter 입력해주세요.\n(한글로 입력하세요)"
                bind.answer.setOnKeyListener { view, i, keyEvent ->
                    if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) {
                        imm.hideSoftInputFromWindow(bind.answer.windowToken, 0)
                        if (bind.answer.text.toString() == "유형") {
                            Snackbar.make(view, "정답입니다.", Snackbar.LENGTH_SHORT).show()
                            val itemMap = hashMapOf(
                                "content" to "sw문제1",
                                "point" to 3,
                                "date" to LocalDateTime.now()
                                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                            )
                            historyCollectionRef.add(itemMap).addOnSuccessListener {}
                                .addOnFailureListener {}
                            updateActionTitle((requireActivity() as AppCompatActivity).supportActionBar)
                            AlertDialog.Builder(requireContext()).apply {
                                setMessage("문제를 더 풀까요?")
                                setPositiveButton("네") { _, _ ->
                                    AlertDialog.Builder(requireContext()).apply {
                                        setCancelable(false)
                                        val binding = InputTextLayoutBinding.inflate(layoutInflater)
                                        setView(binding.root)
                                        binding.tv.text =
                                            "${resources.getString(R.string.swexplanation2)} 입력후 Enter 입력해주세요.\n(한글로 입력하세요)"
                                        binding.answer.setOnKeyListener { view, i, keyEvent ->
                                            if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) {
                                                imm.hideSoftInputFromWindow(
                                                    bind.answer.windowToken,
                                                    0
                                                )
                                                if (binding.answer.text.toString() == "레벨") {
                                                    val itemMap = hashMapOf(
                                                        "content" to "sw문제2",
                                                        "point" to 3,
                                                        "date" to LocalDateTime.now()
                                                            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                                                    )
                                                    historyCollectionRef.add(itemMap)
                                                        .addOnSuccessListener {}
                                                        .addOnFailureListener {}
                                                    Snackbar.make(
                                                        view,
                                                        "정답입니다.",
                                                        Snackbar.LENGTH_SHORT
                                                    ).show()
                                                    updateActionTitle((requireActivity() as AppCompatActivity).supportActionBar)
                                                } else {
                                                    Snackbar.make(
                                                        view,
                                                        "오답입니다.",
                                                        Snackbar.LENGTH_SHORT
                                                    ).show()
                                                }
                                            }
                                            false
                                        }
                                        setNegativeButton("닫기") { DialogInterface, Int ->
                                            DialogInterface.dismiss()
                                        }
                                        show()
                                    }
                                }
                                setNegativeButton("아니요") { DialogInterface, Int ->
                                    DialogInterface.dismiss()
                                }
                                show()
                            }
                        } else {
                            Snackbar.make(view, "오답입니다.", Snackbar.LENGTH_SHORT).show()
                        }
                    }
                    false
                }
                setNegativeButton("닫기") { DialogInterface, Int ->
                    DialogInterface.dismiss()
                }
                show()
            }
        }
        binding.android.setOnClickListener {
            AlertDialog.Builder(requireContext()).apply {
                val binding = InputTextLayoutBinding.inflate(layoutInflater)
                setView(binding.root)
                binding.tv.text =
                    "${resources.getString(R.string.androidexplanation1)} 입력후 Enter 입력해주세요.\n(한글로 입력하세요)"
                binding.answer.setOnKeyListener { view, i, keyEvent ->
                    if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) {
                        imm.hideSoftInputFromWindow(binding.answer.windowToken, 0)
                        if (binding.answer.text.toString() == "인텐트") {
                            Snackbar.make(view, "정답입니다.", Snackbar.LENGTH_SHORT).show()
                            val itemMap = hashMapOf(
                                "content" to "안드로이드 문제1",
                                "point" to 3,
                                "date" to LocalDateTime.now()
                                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                            )
                            historyCollectionRef.add(itemMap).addOnSuccessListener {}
                                .addOnFailureListener {}
                            updateActionTitle((requireActivity() as AppCompatActivity).supportActionBar)
                            AlertDialog.Builder(requireContext()).apply {
                                setMessage("문제를 더 풀까요?")
                                setPositiveButton("네") { _, _ ->
                                    AlertDialog.Builder(requireContext()).apply {
                                        setCancelable(false)
                                        val binding = InputTextLayoutBinding.inflate(layoutInflater)
                                        setView(binding.root)
                                        binding.tv.text =
                                            "${resources.getString(R.string.androidexplanation2)} 입력후 Enter 입력해주세요.\n(영어로 입력하세요)"
                                        binding.answer.setOnKeyListener { view, i, keyEvent ->
                                            if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) {
                                                imm.hideSoftInputFromWindow(
                                                    binding.answer.windowToken,
                                                    0
                                                )
                                                if (binding.answer.text.toString() == "dialog" || binding.answer.text.toString() == "Dialog") {
                                                    val itemMap = hashMapOf(
                                                        "content" to "안드로이드 문제2",
                                                        "point" to 3,
                                                        "date" to LocalDateTime.now()
                                                            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                                                    )
                                                    historyCollectionRef.add(itemMap)
                                                        .addOnSuccessListener {}
                                                        .addOnFailureListener {}
                                                    updateActionTitle((requireActivity() as AppCompatActivity).supportActionBar)
                                                    Snackbar.make(
                                                        view,
                                                        "정답입니다.",
                                                        Snackbar.LENGTH_SHORT
                                                    ).show()
                                                } else {
                                                    Snackbar.make(
                                                        view,
                                                        "오답입니다.",
                                                        Snackbar.LENGTH_SHORT
                                                    ).show()
                                                }
                                            }
                                            false
                                        }
                                        setNegativeButton("닫기") { DialogInterface, Int ->
                                            DialogInterface.dismiss()
                                        }
                                        show()
                                    }
                                }
                                setNegativeButton("아니요") { DialogInterface, Int ->
                                    DialogInterface.dismiss()
                                }
                                show()
                            }
                        } else {
                            Snackbar.make(view, "오답입니다.", Snackbar.LENGTH_SHORT).show()
                        }
                    }
                    false
                }
                setNegativeButton("닫기") { DialogInterface, Int ->
                    DialogInterface.dismiss()
                }
                show()
            }
        }
        binding.java.setOnClickListener {
            AlertDialog.Builder(requireContext()).apply {
                val binding = InputTextLayoutBinding.inflate(layoutInflater)
                setView(binding.root)
                binding.tv.text =
                    "${resources.getString(R.string.javaexplanation1)} 입력후 Enter 입력해주세요.\n(영어로 입력하세요)"
                binding.answer.setOnKeyListener { view, i, keyEvent ->
                    if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) {
                        imm.hideSoftInputFromWindow(binding.answer.windowToken, 0)
                        if (binding.answer.text.toString() == "extends") {
                            Snackbar.make(view, "정답입니다.", Snackbar.LENGTH_SHORT).show()
                            val itemMap = hashMapOf(
                                "content" to "자바 문제1",
                                "point" to 3,
                                "date" to LocalDateTime.now()
                                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                            )
                            historyCollectionRef.add(itemMap).addOnSuccessListener {}
                                .addOnFailureListener {}
                            updateActionTitle((requireActivity() as AppCompatActivity).supportActionBar)
                            AlertDialog.Builder(requireContext()).apply {
                                setMessage("문제를 더 풀까요?")
                                setPositiveButton("네") { _, _ ->
                                    AlertDialog.Builder(requireContext()).apply {
                                        setCancelable(false)
                                        val binding = InputTextLayoutBinding.inflate(layoutInflater)
                                        setView(binding.root)
                                        binding.tv.text =
                                            "${resources.getString(R.string.javaexplanation2)} 입력후 Enter 입력해주세요.\n(한글로 입력하세요)"
                                        binding.answer.setOnKeyListener { view, i, keyEvent ->
                                            if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) {
                                                imm.hideSoftInputFromWindow(
                                                    binding.answer.windowToken,
                                                    0
                                                )
                                                if (binding.answer.text.toString() == "다형성") {
                                                    val itemMap = hashMapOf(
                                                        "content" to "자바 문제2",
                                                        "point" to 3,
                                                        "date" to LocalDateTime.now()
                                                            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                                                    )
                                                    historyCollectionRef.add(itemMap)
                                                        .addOnSuccessListener {}
                                                        .addOnFailureListener {}
                                                    Snackbar.make(
                                                        view,
                                                        "정답입니다.",
                                                        Snackbar.LENGTH_SHORT
                                                    ).show()
                                                    updateActionTitle((requireActivity() as AppCompatActivity).supportActionBar)
                                                } else {
                                                    Snackbar.make(
                                                        view,
                                                        "오답입니다.",
                                                        Snackbar.LENGTH_SHORT
                                                    ).show()
                                                }
                                            }
                                            false
                                        }
                                        setNegativeButton("닫기") { DialogInterface, Int ->
                                            DialogInterface.dismiss()
                                        }
                                        show()
                                    }
                                }
                                setNegativeButton("아니요") { DialogInterface, Int ->
                                    DialogInterface.dismiss()
                                }
                                show()
                            }
                        } else {
                            Snackbar.make(view, "오답입니다.", Snackbar.LENGTH_SHORT).show()
                        }
                    }
                    false
                }
                setNegativeButton("닫기") { DialogInterface, Int ->
                    DialogInterface.dismiss()
                }
                show()
            }
        }
    }
}

class SearchFragment : Fragment() {
    private val historyCollectionRef = Firebase.firestore.collection("history")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.searchfragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        historyCollectionRef.get().addOnSuccessListener {
            var sum = 0
            for (doc in it) {
                sum += Integer.parseInt(doc.data["point"].toString())
            }
            (requireActivity() as AppCompatActivity).supportActionBar?.title = "${sum}P"
        }
        historyCollectionRef.orderBy("date", Query.Direction.DESCENDING).get()
            .addOnSuccessListener {
                val items = mutableListOf<Array>()
                for (i in it) {
                    items.add(
                        Array(
                            i.data["content"].toString(),
                            i.data["point"].toString(),
                            i.data["date"].toString()
                        )
                    )
                }
                val binding = SearchfragmentBinding.bind(view)
                // ListAdapter를 이용한 adapter
                val adapter=DataAdapter()
                adapter.submitList(items.toList().reversed())
                // RecyclerView.Adapter를 이용한 adapter
//                val adapter = CustomAdapter(items.toList().reversed())
                binding.recyclerView.adapter = adapter//recyclerview 와 adapter(customAdapter) 연결
                binding.recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), 1))
                binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
                binding.recyclerView.setHasFixedSize(true)
                // recyclerView의 마지막 아이템위치로 이동
                binding.recyclerView.scrollToPosition(adapter.itemCount - 1)
            }.addOnFailureListener {}
    }
}

class ShopFragment : Fragment() {
    private val historyCollectionRef = Firebase.firestore.collection("history")

    private fun updateActionTitle(actionBar: ActionBar?) {
        Firebase.firestore.collection("history").addSnapshotListener { value, error ->
            var sum = 0
            value?.documentChanges?.forEach { doc ->
                sum += Integer.parseInt(doc.document.data["point"].toString())
            }
            actionBar?.title = "${sum}P"
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.shopfragment, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        updateActionTitle((requireActivity() as AppCompatActivity).supportActionBar)
        val actionTitle =
            (requireActivity() as AppCompatActivity).supportActionBar?.title?.toString()
        val point = Integer.parseInt(actionTitle?.substring(0, actionTitle?.length - 1))
        val binding = ShopfragmentBinding.bind(view)
        binding.cake.setOnClickListener {
            AlertDialog.Builder(requireContext()).apply {
                setCancelable(false)
                setTitle("결제창")
                setMessage("2포인트 차감됩니다.구매하시면 하단에 OK버튼을 눌러주세요.")
                setPositiveButton("OK") { _, _ ->
                    if (point >= 2) {
                        val itemMap = hashMapOf(
                            "content" to "케이크 구매",
                            "point" to -2,
                            "date" to LocalDateTime.now()
                                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                        )
                        historyCollectionRef.add(itemMap).addOnSuccessListener {}
                            .addOnFailureListener {}
                        Snackbar.make(view, "구매완료!", Snackbar.LENGTH_SHORT).show()
                        updateActionTitle((requireActivity() as AppCompatActivity).supportActionBar)
                    } else {
                        Snackbar.make(view, "포인트가 부족함!", Snackbar.LENGTH_SHORT).show()
                    }
                }
                setNegativeButton("CANCEL") { dialog, i ->
                    dialog.dismiss()
                }
                show()
            }
        }
        binding.coffee.setOnClickListener {
            AlertDialog.Builder(requireContext()).apply {
                setCancelable(false)
                setTitle("결제창")
                setMessage("1포인트 차감됩니다.구매하시면 하단에 OK버튼을 눌러주세요.")
                setPositiveButton("OK") { _, i ->
                    if (point >= 1) {
                        val itemMap = hashMapOf(
                            "content" to "커피 구매",
                            "point" to -1,
                            "date" to LocalDateTime.now()
                                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                        )
                        historyCollectionRef.add(itemMap).addOnSuccessListener {}
                            .addOnFailureListener {}
                        Snackbar.make(view, "구매완료!", Snackbar.LENGTH_SHORT).show()
                        updateActionTitle((requireActivity() as AppCompatActivity).supportActionBar)
                    } else {
                        Snackbar.make(view, "포인트가 부족함!", Snackbar.LENGTH_SHORT).show()
                    }
                }
                setNegativeButton("CANCEL") { dialog, i ->
                    dialog.dismiss()
                }
                show()
            }
        }
        binding.movie.setOnClickListener {
            AlertDialog.Builder(requireContext()).apply {
                setCancelable(false)
                setTitle("결제창")
                setMessage("3포인트 차감됩니다.구매하시면 하단에 OK버튼을 눌러주세요.")
                setPositiveButton("OK") { _, i ->
                    if (point >= 3) {
                        val itemMap = hashMapOf(
                            "content" to "영화티켓 구매",
                            "point" to -3,
                            "date" to LocalDateTime.now()
                                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                        )
                        historyCollectionRef.add(itemMap).addOnSuccessListener {}
                            .addOnFailureListener {}
                        Snackbar.make(view, "구매완료!", Snackbar.LENGTH_SHORT).show()
                        updateActionTitle((requireActivity() as AppCompatActivity).supportActionBar)
                    } else {
                        Snackbar.make(view, "포인트가 부족함!", Snackbar.LENGTH_SHORT).show()
                    }
                }
                setNegativeButton("CANCEL") { dialog, i ->
                    dialog.dismiss()
                }
                show()
            }
        }
        binding.smartphone.setOnClickListener {
            AlertDialog.Builder(requireContext()).apply {
                setCancelable(false)
                setTitle("결제창")
                setMessage("5포인트 차감됩니다.구매하시면 하단에 OK버튼을 눌러주세요.")
                setPositiveButton("OK") { _, i ->
                    if (point >= 5) {
                        val itemMap = hashMapOf(
                            "content" to "스마트폰 구매",
                            "point" to -5,
                            "date" to LocalDateTime.now()
                                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                        )
                        historyCollectionRef.add(itemMap).addOnSuccessListener {}
                            .addOnFailureListener {}
                        Snackbar.make(view, "구매완료!", Snackbar.LENGTH_SHORT).show()
                        updateActionTitle((requireActivity() as AppCompatActivity).supportActionBar)
                    } else {
                        Snackbar.make(view, "포인트가 부족함!", Snackbar.LENGTH_SHORT).show()
                    }
                }
                setNegativeButton("CANCEL") { dialog, i ->
                    dialog.dismiss()
                }
                show()
            }
        }
        binding.wine.setOnClickListener {
            AlertDialog.Builder(requireContext()).apply {
                setCancelable(false)
                setTitle("결제창")
                setMessage("3포인트 차감됩니다.구매하시면 하단에 OK버튼을 눌러주세요.")
                setPositiveButton("OK") { _, i ->
                    if (point >= 3) {
                        val itemMap = hashMapOf(
                            "content" to "와인 구매",
                            "point" to -3,
                            "date" to LocalDateTime.now()
                                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                        )
                        historyCollectionRef.add(itemMap).addOnSuccessListener {}
                            .addOnFailureListener {}
                        Snackbar.make(view, "구매완료!", Snackbar.LENGTH_SHORT).show()
                        updateActionTitle((requireActivity() as AppCompatActivity).supportActionBar)
                    } else {
                        Snackbar.make(view, "포인트가 부족함!", Snackbar.LENGTH_SHORT).show()
                    }
                }
                setNegativeButton("CANCEL") { dialog, i ->
                    dialog.dismiss()
                }
                show()
            }
        }
        binding.bike.setOnClickListener {
            AlertDialog.Builder(requireContext()).apply {
                setCancelable(false)
                setTitle("결제창")
                setMessage("2포인트 차감됩니다.구매하시면 하단에 OK버튼을 눌러주세요.")
                setPositiveButton("OK") { dialog, i ->
                    if (point >= 2) {
                        val itemMap = hashMapOf(
                            "content" to "자전거 구매",
                            "point" to -2,
                            "date" to LocalDateTime.now()
                                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                        )
                        historyCollectionRef.add(itemMap).addOnSuccessListener {
                        }.addOnFailureListener {
                        }
                        Snackbar.make(view, "구매완료!", Snackbar.LENGTH_SHORT).show()
                        updateActionTitle((requireActivity() as AppCompatActivity).supportActionBar)
                    } else {
                        Snackbar.make(view, "포인트가 부족함!", Snackbar.LENGTH_SHORT).show()
                    }
                }
                setNegativeButton("CANCEL") { dialog, i ->
                    dialog.dismiss()
                }
                show()
            }
        }
    }
}