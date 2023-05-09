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
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MyBottomSheetDialog(private val mode: String) : BottomSheetDialogFragment() {
    private lateinit var binding: ItemDialogLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        isCancelable = false
        binding = ItemDialogLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ok.setOnClickListener {
            if (mode == "signin") {
                // 이메일과 비밀번호중 입력하지 않은 것이 있는 경우
                if (binding.email.text.isEmpty() || binding.pw.text.isEmpty()) {
                    AlertDialog.Builder(requireContext()).apply {
                        setMessage("이메일과 비밀번호중 입력하지 않은것이 있습니다.다시입력하세요.")
                        setCancelable(false)
                        setPositiveButton("OK") { dialog: DialogInterface, int: Int ->
                            dialog.dismiss()
                        }
                        show()
                    }
                } else {    //  이메일과 비밀번호 둘다 입력한 경우
                    Firebase.auth.signInWithEmailAndPassword(
                        binding.email.text.toString(),
                        binding.pw.text.toString()
                    ).addOnCompleteListener(requireActivity()) { // it: Task<AuthResult!>
                        if (it.isSuccessful) {  // 로그인이 성공한 경우
                            startActivity(Intent(context, SignInActivity::class.java))
                        } else {    // 로그인이 실패한 경우
                            AlertDialog.Builder(requireContext()).apply {
                                setMessage("없는 이메일과 비밀번호입니다.")
                                setCancelable(false)
                                setPositiveButton("OK") { dialog: DialogInterface, int: Int ->
                                    dialog.dismiss()
                                }
                                show()
                            }
                        }
                    }
                }
            } else if (mode == "signup") {
                val item = Item(binding.email.text.toString(), binding.pw.text.toString())
//                이메일이나 비밀번호를 입력하지 않은 것이 있는 경우
                if (binding.email.text.isEmpty() || binding.pw.text.isEmpty()) {
                    AlertDialog.Builder(requireContext()).apply {
                        setCancelable(false)
                        setMessage("이메일과 비밀번호중 입력하지 않은것이 있습니다.다시입력하세요.")
                        setPositiveButton("OK") { dialog: DialogInterface, int: Int ->
                            dialog.dismiss()
                        }
                        show()
                    }
                } else {    // 이메일과 비밀번호를 둘 다 입력한 경우
                    Firebase.auth.createUserWithEmailAndPassword(
                        binding.email.text.toString(),
                        binding.pw.text.toString()
                    ).addOnCompleteListener(requireActivity()) {
                        if (it.isSuccessful) {  // 회원가입에 성공한 경우
                            AlertDialog.Builder(requireContext()).apply {
                                setCancelable(false)
                                setMessage("회원가입되었습니다.")
                                setPositiveButton("OK") { dialog: DialogInterface, int: Int ->
                                    dialog.dismiss()
                                }
                                show()
                            }
                        } else  // 회원가입에 실패한 경우
                            AlertDialog.Builder(requireContext()).apply {
                                setCancelable(false)
                                setMessage("입력된 이메일이나 비밀번호는 이미 있습니다.다시입력해주세요.")
                                setPositiveButton("OK") { dialog: DialogInterface, int: Int ->
                                    dialog.dismiss()
                                }
                                show()
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