package com.example.miniproject

import android.app.AlertDialog
import android.app.ProgressDialog.show
import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.miniproject.databinding.*
import com.google.android.material.snackbar.Snackbar

private val list=ArrayList<Array>()
class HomeFragment:Fragment(R.layout.homefragment){
    private val viewModel: MyViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = HomefragmentBinding.bind(view)
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        viewModel.pointLiveData.observe(viewLifecycleOwner){
            binding.pointtv.text="$it"
        }
        binding.plus1.setOnClickListener {
            AlertDialog.Builder(requireContext()).apply {
                setCancelable(false)
                setTitle("8/4+6*3+8")
                val item= arrayOf("16","20","24","28")
                setItems(item){DialogInterface, Int->
                    when(item[Int]){
                        "28"->{
                            val a=viewModel.pointLiveData.value?:0
                            viewModel.pointLiveData.value=a+1
                            Snackbar.make(it,"정답입니다.",Snackbar.LENGTH_SHORT).show()
                            list.add(Array("수학문제 정답!(+1)",viewModel.pointLiveData.value.toString()))
                        }
                        else->Snackbar.make(it,"오답입니다.",Snackbar.LENGTH_SHORT).show()
                    }
                }
                setNegativeButton("닫기"){DialogInterface, Int->
                    DialogInterface.dismiss()
                }
                show()
            }
        }
        binding.plus2.setOnClickListener {
            AlertDialog.Builder(requireContext()).apply {
                setCancelable(false)
                val items= arrayOf("양의지","정수빈","오재일","허경민")
                setTitle("역대 한국시리즈 MVP가 아닌 야구선수는 누구인가?")
                setItems(items) {dialog, which->
                    if(items[which]=="허경민")
                    {
                        Snackbar.make(requireView(),"정답입니다.",Snackbar.LENGTH_SHORT).show()
                        val a=viewModel.pointLiveData.value?:0
                        viewModel.pointLiveData.value=a+2
                        list.add(Array("야구퀴즈 정답!(+2)",viewModel.pointLiveData.value.toString()))
                    }
                    else
                    {
                        Snackbar.make(it,"오답입니다.",Snackbar.LENGTH_SHORT).show()
                    }
                }
                setNegativeButton("닫기"){DialogInterface, Int->
                    DialogInterface.dismiss()
                }
                show()
            }
        }
        binding.plus3.setOnClickListener {
            AlertDialog.Builder(requireContext()).apply {
                val bind=InputTextLayoutBinding.inflate(layoutInflater)
                setView(bind.root)
                bind.tv.text="우주인들이 술 마시는 곳은?"
                bind.answer.setOnKeyListener { view, i, keyEvent ->
                    if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER)
                    {
                        imm.hideSoftInputFromWindow(bind.answer.windowToken, 0)
                        if(bind.answer.text.toString()=="스페이스바"||bind.answer.text.toString()=="spacebar")
                        {
                            val a=viewModel.pointLiveData.value?:0
                            viewModel.pointLiveData.value=a+3
                            list.add(Array("넌센스퀴즈 정답!(+3)",viewModel.pointLiveData.value.toString()))
                            Snackbar.make(view,"정답입니다.",Snackbar.LENGTH_SHORT).show()
                        }
                        else
                        {
                            Snackbar.make(view,"오답입니다.",Snackbar.LENGTH_SHORT).show()
                        }
                    }
                    false
                }
                setNegativeButton("닫기"){dialog,which->
                    dialog.dismiss()
                }
                show()
            }
        }
        binding.plus4.setOnClickListener {
            AlertDialog.Builder(requireContext()).apply {
                setTitle("닭의 무리중 한마리 학이란 뜻으로 많은사람들중 뛰어난 인물의 뜻인 사자성어는?")
                val item= arrayOf("설상가상","군계일학","격세지감","각골난망")
                setItems(item){dialog,which->
                    if(item[which]=="군계일학")
                    {
                        Snackbar.make(requireView(),"정답입니다.",Snackbar.LENGTH_SHORT).show()
                        val a=viewModel.pointLiveData.value?:0
                        viewModel.pointLiveData.value=a+4
                        list.add(Array("사자성어퀴즈 정답!(+4)",viewModel.pointLiveData.value.toString()))
                    }
                    else
                    {
                        Snackbar.make(it,"오답입니다.",Snackbar.LENGTH_SHORT).show()
                    }
                }
                setNegativeButton("닫기"){dialog,which->
                    dialog.dismiss()
                }
                show()
            }
        }
    }
}
class QuizFragment:Fragment(R.layout.quizfragment){
    private val viewModel: MyViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding=QuizfragmentBinding.bind(view)
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        binding.os.setOnClickListener {
            AlertDialog.Builder(requireContext()).apply {
                setCancelable(false)
                val bind=InputTextLayoutBinding.inflate(layoutInflater)
                setView(bind.root)
                bind.tv.text="${resources.getString(R.string.osexplanation1)} 입력후 Enter 입력해주세요.\n(한글로 입력하세요)"
                bind.answer.setOnKeyListener { view, i, keyEvent ->
                    if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER)
                    {
                        imm.hideSoftInputFromWindow(bind.answer.windowToken, 0)
                        if(bind.answer.text.toString()=="스왑영역"||bind.answer.text.toString()=="스왑 영역")
                        {
                            val a=viewModel.pointLiveData.value?:0
                            viewModel.pointLiveData.value=a+3
                            list.add(Array("os문제1정답!(+3)",viewModel.pointLiveData.value.toString()))
                            Snackbar.make(view,"정답입니다.",Snackbar.LENGTH_SHORT).show()
                            AlertDialog.Builder(requireContext()).apply {
                                setMessage("문제를 더 풀까요?")
                                setPositiveButton("네"){DialogInterface, Int->
                                    AlertDialog.Builder(requireContext()).apply {
                                        setCancelable(false)
                                        val binding=InputTextLayoutBinding.inflate(layoutInflater)
                                        setView(binding.root)
                                        binding.tv.text="${resources.getString(R.string.osexplanation2)} 입력후 Enter 입력해주세요.\n(한글로 입력하세요)"
                                        binding.answer.setOnKeyListener { view, i, keyEvent ->
                                            if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER)
                                            {
                                                imm.hideSoftInputFromWindow(bind.answer.windowToken, 0)
                                                if(binding.answer.text.toString()=="가상 메모리 기법"||binding.answer.text.toString()=="가상메모리기법")
                                                {
                                                    val a=viewModel.pointLiveData.value?:0
                                                    viewModel.pointLiveData.value=a+3
                                                    Snackbar.make(view,"정답입니다.",Snackbar.LENGTH_SHORT).show()
                                                    list.add(Array("os문제2정답!(+3)",viewModel.pointLiveData.value.toString()))
                                                }
                                                else
                                                {
                                                    Snackbar.make(view,"오답입니다.",Snackbar.LENGTH_SHORT).show()
                                                }
                                            }
                                            false
                                        }
                                        setNegativeButton("닫기"){DialogInterface, Int->
                                            DialogInterface.dismiss()
                                        }
                                        show()
                                    }
                                }
                                setNegativeButton("아니요"){DialogInterface, Int->
                                    DialogInterface.dismiss()
                                }
                                show()
                            }
                        }
                        else
                        {
                            Snackbar.make(view,"오답입니다.",Snackbar.LENGTH_SHORT).show()
                        }
                    }
                    false
                }
                setNegativeButton("닫기"){DialogInterface, Int->
                    DialogInterface.dismiss()
                }
                show()
            }
        }
        binding.sw.setOnClickListener {
            AlertDialog.Builder(requireContext()).apply {
                setCancelable(false)
                val bind=InputTextLayoutBinding.inflate(layoutInflater)
                setView(bind.root)
                bind.tv.text="${resources.getString(R.string.swexplanation1)} 입력후 Enter 입력해주세요.\n(한글로 입력하세요)"
                bind.answer.setOnKeyListener { view, i, keyEvent ->
                    if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER)
                    {
                        imm.hideSoftInputFromWindow(bind.answer.windowToken, 0)
                        if(bind.answer.text.toString()=="유형")
                        {
                            val a=viewModel.pointLiveData.value?:0
                            viewModel.pointLiveData.value=a+3
                            Snackbar.make(view,"정답입니다.",Snackbar.LENGTH_SHORT).show()
                            list.add(Array("sw문제1정답!(+3)",viewModel.pointLiveData.value.toString()))
                            AlertDialog.Builder(requireContext()).apply {
                                setMessage("문제를 더 풀까요?")
                                setPositiveButton("네"){DialogInterface, Int->
                                    AlertDialog.Builder(requireContext()).apply {
                                        setCancelable(false)
                                        val binding=InputTextLayoutBinding.inflate(layoutInflater)
                                        setView(binding.root)
                                        binding.tv.text="${resources.getString(R.string.swexplanation2)} 입력후 Enter 입력해주세요.\n(한글로 입력하세요)"
                                        binding.answer.setOnKeyListener { view, i, keyEvent ->
                                            if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER)
                                            {
                                                imm.hideSoftInputFromWindow(bind.answer.windowToken, 0)
                                                if(binding.answer.text.toString()=="레벨")
                                                {
                                                    val a=viewModel.pointLiveData.value?:0
                                                    viewModel.pointLiveData.value=a+3
                                                    list.add(Array("sw문제2정답!(+3)",viewModel.pointLiveData.value.toString()))
                                                    Snackbar.make(view,"정답입니다.",Snackbar.LENGTH_SHORT).show()
                                                }
                                                else
                                                {
                                                    Snackbar.make(view,"오답입니다.",Snackbar.LENGTH_SHORT).show()
                                                }
                                            }
                                            false
                                        }
                                        setNegativeButton("닫기"){DialogInterface, Int->
                                            DialogInterface.dismiss()
                                        }
                                        show()
                                    }
                                }
                                setNegativeButton("아니요"){DialogInterface, Int->
                                    DialogInterface.dismiss()
                                }
                                show()
                            }
                        }
                        else
                        {
                            Snackbar.make(view,"오답입니다.",Snackbar.LENGTH_SHORT).show()
                        }
                    }
                    false
                }
                setNegativeButton("닫기"){DialogInterface, Int->
                    DialogInterface.dismiss()
                }
                show()
            }
        }
        binding.android.setOnClickListener {
            AlertDialog.Builder(requireContext()).apply {
                val binding=InputTextLayoutBinding.inflate(layoutInflater)
                setView(binding.root)
                binding.tv.text="${resources.getString(R.string.androidexplanation1)} 입력후 Enter 입력해주세요.\n(한글로 입력하세요)"
                binding.answer.setOnKeyListener { view, i, keyEvent ->
                    if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER)
                    {
                        imm.hideSoftInputFromWindow(binding.answer.windowToken, 0)
                        if(binding.answer.text.toString()=="인텐트")
                        {
                            val a=viewModel.pointLiveData.value?:0
                            viewModel.pointLiveData.value=a+3
                            Snackbar.make(view,"정답입니다.",Snackbar.LENGTH_SHORT).show()
                            list.add(Array("안드로이드 문제1정답!(+3)",viewModel.pointLiveData.value.toString()))
                            AlertDialog.Builder(requireContext()).apply {
                                setMessage("문제를 더 풀까요?")
                                setPositiveButton("네"){DialogInterface, Int->
                                    AlertDialog.Builder(requireContext()).apply {
                                        setCancelable(false)
                                        val binding=InputTextLayoutBinding.inflate(layoutInflater)
                                        setView(binding.root)
                                        binding.tv.text="${resources.getString(R.string.androidexplanation2)} 입력후 Enter 입력해주세요.\n(영어로 입력하세요)"
                                        binding.answer.setOnKeyListener { view, i, keyEvent ->
                                            if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER)
                                            {
                                                imm.hideSoftInputFromWindow(binding.answer.windowToken, 0)
                                                if(binding.answer.text.toString()=="dialog"||binding.answer.text.toString()=="Dialog")
                                                {
                                                    val a=viewModel.pointLiveData.value?:0
                                                    viewModel.pointLiveData.value=a+3
                                                    list.add(Array("안드로이드 문제2정답!(+3)",viewModel.pointLiveData.value.toString()))
                                                    Snackbar.make(view,"정답입니다.",Snackbar.LENGTH_SHORT).show()
                                                }
                                                else
                                                {
                                                    Snackbar.make(view,"오답입니다.",Snackbar.LENGTH_SHORT).show()
                                                }
                                            }
                                            false
                                        }
                                        setNegativeButton("닫기"){DialogInterface, Int->
                                            DialogInterface.dismiss()
                                        }
                                        show()
                                    }
                                }
                                setNegativeButton("아니요"){DialogInterface, Int->
                                    DialogInterface.dismiss()
                                }
                                show()
                            }
                        }
                        else
                        {
                            Snackbar.make(view,"오답입니다.",Snackbar.LENGTH_SHORT).show()
                        }
                    }
                    false
                }
                setNegativeButton("닫기"){DialogInterface, Int->
                    DialogInterface.dismiss()
                }
                show()
            }
        }
        binding.java.setOnClickListener {
            AlertDialog.Builder(requireContext()).apply {
                val binding=InputTextLayoutBinding.inflate(layoutInflater)
                setView(binding.root)
                binding.tv.text="${resources.getString(R.string.javaexplanation1)} 입력후 Enter 입력해주세요.\n(영어로 입력하세요)"
                binding.answer.setOnKeyListener { view, i, keyEvent ->
                    if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER)
                    {
                        imm.hideSoftInputFromWindow(binding.answer.windowToken, 0)
                        if(binding.answer.text.toString()=="extends")
                        {
                            val a=viewModel.pointLiveData.value?:0
                            viewModel.pointLiveData.value=a+3
                            Snackbar.make(view,"정답입니다.",Snackbar.LENGTH_SHORT).show()
                            list.add(Array("자바 문제1정답!(+3)",viewModel.pointLiveData.value.toString()))
                            AlertDialog.Builder(requireContext()).apply {
                                setMessage("문제를 더 풀까요?")
                                setPositiveButton("네"){DialogInterface, Int->
                                    AlertDialog.Builder(requireContext()).apply {
                                        setCancelable(false)
                                        val binding=InputTextLayoutBinding.inflate(layoutInflater)
                                        setView(binding.root)
                                        binding.tv.text="${resources.getString(R.string.javaexplanation2)} 입력후 Enter 입력해주세요.\n(한글로 입력하세요)"
                                        binding.answer.setOnKeyListener { view, i, keyEvent ->
                                            if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER)
                                            {
                                                imm.hideSoftInputFromWindow(binding.answer.windowToken, 0)
                                                if(binding.answer.text.toString()=="다형성")
                                                {
                                                    val a=viewModel.pointLiveData.value?:0
                                                    viewModel.pointLiveData.value=a+3
                                                    list.add(Array("자바 문제2정답!(+3)",viewModel.pointLiveData.value.toString()))
                                                    Snackbar.make(view,"정답입니다.",Snackbar.LENGTH_SHORT).show()
                                                }
                                                else
                                                {
                                                    Snackbar.make(view,"오답입니다.",Snackbar.LENGTH_SHORT).show()
                                                }
                                            }
                                            false
                                        }
                                        setNegativeButton("닫기"){DialogInterface, Int->
                                            DialogInterface.dismiss()
                                        }
                                        show()
                                    }
                                }
                                setNegativeButton("아니요"){DialogInterface, Int->
                                    DialogInterface.dismiss()
                                }
                                show()
                            }
                        }
                        else
                        {
                            Snackbar.make(view,"오답입니다.",Snackbar.LENGTH_SHORT).show()
                        }
                    }
                    false
                }
                setNegativeButton("닫기"){DialogInterface, Int->
                    DialogInterface.dismiss()
                }
                show()
            }
        }
    }
}
class SearchFragment:Fragment(R.layout.searchfragment){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding=SearchfragmentBinding.bind(view)
        val adapter = CustomAdapter(list)
        binding.recyclerView.adapter = adapter//recyclerview 와 adapter(customAdapter) 연결
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.setHasFixedSize(true)
    }
}
class ShopFragment:Fragment(R.layout.shopfragment){
    private val viewModel: MyViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding=ShopfragmentBinding.bind(view)
        binding.cake.setOnClickListener {
            AlertDialog.Builder(requireContext()).apply {
                setCancelable(false)
                setTitle("결제창")
                setMessage("2포인트 차감됩니다.구매하시면 하단에 OK버튼을 눌러주세요.")
                setPositiveButton("OK"){dialog,i->
                    val a=viewModel.pointLiveData.value?:0
                    if(a>=2) {
                        viewModel.pointLiveData.value = a - 2
                        list.add(Array("케이크 구매(-2)",viewModel.pointLiveData.value.toString()))
                        Snackbar.make(view,"구매완료!",Snackbar.LENGTH_SHORT).show()
                    }
                    else{
                        Snackbar.make(view,"포인트가 부족함!",Snackbar.LENGTH_SHORT).show()
                    }
                }
                setNegativeButton("CANCEL"){dialog,i->
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
                setPositiveButton("OK"){dialog,i->
                    val a=viewModel.pointLiveData.value?:0
                    if(a>=1) {
                        viewModel.pointLiveData.value = a - 1
                        list.add(Array("커피 구매(-1)",viewModel.pointLiveData.value.toString()))
                        Snackbar.make(view,"구매완료!",Snackbar.LENGTH_SHORT).show()
                    }
                    else{
                        Snackbar.make(view,"포인트가 부족함!",Snackbar.LENGTH_SHORT).show()
                    }
                }
                setNegativeButton("CANCEL"){dialog,i->
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
                setPositiveButton("OK"){dialog,i->
                    val a=viewModel.pointLiveData.value?:0
                    if(a>=3) {
                        viewModel.pointLiveData.value = a - 3
                        list.add(Array("영화티켓 구매(-3)",viewModel.pointLiveData.value.toString()))
                        Snackbar.make(view,"구매완료!",Snackbar.LENGTH_SHORT).show()
                    }
                    else{
                        Snackbar.make(view,"포인트가 부족함!",Snackbar.LENGTH_SHORT).show()
                    }
                }
                setNegativeButton("CANCEL"){dialog,i->
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
                setPositiveButton("OK"){dialog,i->
                    val a=viewModel.pointLiveData.value?:0
                    if(a>=5) {
                        viewModel.pointLiveData.value = a - 5
                        list.add(Array("스마트폰 구매(-5)",viewModel.pointLiveData.value.toString()))
                        Snackbar.make(view,"구매완료!",Snackbar.LENGTH_SHORT).show()
                    }
                    else{
                        Snackbar.make(view,"포인트가 부족함!",Snackbar.LENGTH_SHORT).show()
                    }
                }
                setNegativeButton("CANCEL"){dialog,i->
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
                setPositiveButton("OK"){dialog,i->
                    val a=viewModel.pointLiveData.value?:0
                    if(a>=3) {
                        viewModel.pointLiveData.value = a - 3
                        list.add(Array("와인 구매(-3)",viewModel.pointLiveData.value.toString()))
                        Snackbar.make(view,"구매완료!",Snackbar.LENGTH_SHORT).show()
                    }
                    else{
                        Snackbar.make(view,"포인트가 부족함!",Snackbar.LENGTH_SHORT).show()
                    }
                }
                setNegativeButton("CANCEL"){dialog,i->
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
                setPositiveButton("OK"){dialog,i->
                    val a=viewModel.pointLiveData.value?:0
                    if(a>=2) {
                        viewModel.pointLiveData.value = a - 2
                        list.add(Array("자전거 구매(-2)",viewModel.pointLiveData.value.toString()))
                        Snackbar.make(view,"구매완료!",Snackbar.LENGTH_SHORT).show()
                    }
                    else{
                        Snackbar.make(view,"포인트가 부족함!",Snackbar.LENGTH_SHORT).show()
                    }
                }
                setNegativeButton("CANCEL"){dialog,i->
                    dialog.dismiss()
                }
                show()
            }
        }
    }
}