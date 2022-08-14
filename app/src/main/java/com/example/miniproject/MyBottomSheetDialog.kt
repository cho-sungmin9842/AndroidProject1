package com.example.miniproject

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import com.example.miniproject.databinding.ItemDialogLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MyBottomSheetDialog(val mode:String) : BottomSheetDialogFragment() {
    private lateinit var binding: ItemDialogLayoutBinding
    private val viewModel:MyViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        isCancelable=false
        binding= ItemDialogLayoutBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ok.setOnClickListener {
            if(mode=="signin")
            {
                if(binding.id.text.isEmpty() || binding.pw.text.isEmpty())
                {
                    AlertDialog.Builder(requireContext()).apply {
                        setMessage("아이디와 비밀번호중 입력하지 않은것이 있습니다.다시입력하세요.")
                        setCancelable(false)
                        setPositiveButton("OK"){ dialog: DialogInterface, int:Int->
                            dialog.dismiss()
                        }
                        show()
                    }
                }
                else
                {
                    if(viewModel.itemSize==0)
                    {
                        AlertDialog.Builder(requireContext()).apply {
                            setMessage("없는 아이디와 비밀번호입니다.회원가입을 해주세요.")
                            setCancelable(false)
                            setPositiveButton("OK"){ dialog: DialogInterface, int:Int->
                                dialog.dismiss()
                            }
                            show()
                        }
                    }
                    else
                    {
                        val item = viewModel.getallitem()
                        var k = 0
                        for (i in 0 until item.size) {
                            if (item.get(i).id == binding.id.text.toString() && item.get(i).pw == binding.pw.text.toString()) {
                                startActivity(Intent(context, SignInActivity::class.java))
                            } else {
                                k = k + 1
                            }
                        }
                        if (k == item.size) {
                            AlertDialog.Builder(requireContext()).apply {
                                setMessage("없는 아이디와 비밀번호입니다.다시로그인하거나 회원가입을 해주세요.")
                                setCancelable(false)
                                setPositiveButton("OK") { dialog: DialogInterface, int: Int ->
                                    dialog.dismiss()
                                }
                                show()
                            }
                        }
                    }
                }
            }
            else if(mode=="signup")
            {
                val item=Item(binding.id.text.toString(),binding.pw.text.toString())
                if(binding.id.text.isEmpty() || binding.pw.text.isEmpty())
                {
                    AlertDialog.Builder(requireContext()).apply {
                        setCancelable(false)
                        setMessage("아이디와 비밀번호중 입력하지 않은것이 있습니다.다시입력하세요.")
                        setPositiveButton("OK"){ dialog: DialogInterface, int:Int->
                            dialog.dismiss()
                        }
                        show()
                    }
                }
                else
                {
                    val viewmodelitem=viewModel.getallitem()
                    if(viewmodelitem.isEmpty())
                    {
                        viewModel.addItem(item)
                        AlertDialog.Builder(requireContext()).apply {
                            setCancelable(false)
                            setMessage("회원가입되었습니다")
                            setPositiveButton("OK"){ dialog: DialogInterface, int:Int->
                                dialog.dismiss()
                            }
                            show()
                        }
                    }
                    else
                    {
                        var a = 0
                        for (i in 0 until viewmodelitem.size)
                        {
                            a = a + 1
                            if (viewmodelitem.get(i).id == item.id)
                            {
                                AlertDialog.Builder(requireContext()).apply {
                                    setCancelable(false)
                                    setMessage("입력된 아이디는 이미있는 아이디입니다.다시입력해주세요.")
                                    setPositiveButton("OK"){ dialog: DialogInterface, int:Int->
                                        dialog.dismiss()
                                    }
                                    show()
                                }
                                a = 0
                            }
                        }
                        if (a == viewmodelitem.size)
                        {
                            viewModel.addItem(item)
                            AlertDialog.Builder(requireContext()).apply {
                                setCancelable(false)
                                setMessage("회원가입되었습니다.")
                                setPositiveButton("OK"){ dialog: DialogInterface, int:Int->
                                    dialog.dismiss()
                                }
                                show()
                            }
                        }
                    }
                }
                dismiss()
            }
        }
        binding.cancel.setOnClickListener {
            dismiss()
        }
    }
}