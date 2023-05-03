package com.example.budilnik.ui.dialog

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.budilnik.R
import com.example.budilnik.databinding.DialogNoteBinding
import com.example.budilnik.databinding.FragmentAlarmBinding

class NoteDialog : DialogFragment(R.layout.dialog_note) {
    lateinit var binding: DialogNoteBinding
    private val args: NoteDialogArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DialogNoteBinding.bind(view)

        initListeners()

    }

    @SuppressLint("PrivateResource")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, com.google.android.material.R.style.Base_Theme_Material3_Dark)

    }

    private var onAcceptClickListener: ((String) -> Unit)? = null

    fun setOnAcceptClickListener(block: (String) -> Unit) {
        onAcceptClickListener = block
    }

    private fun initListeners() {
        binding.tvOk.setOnClickListener {
            onAcceptClickListener?.invoke(binding.tietComment.text.toString())
            this.dismiss()
        }
        binding.tvCancel.setOnClickListener {
            this.dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        if (dialog != null && dialog?.window != null) {
            dialog?.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            val back = ColorDrawable(Color.TRANSPARENT)
            val margin = 32
            val inset = InsetDrawable(back, margin)
            dialog!!.window!!.setBackgroundDrawable(inset)
        }
    }
}