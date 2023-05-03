package com.example.budilnik.ui.fragments.search_fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.budilnik.R
import com.example.budilnik.databinding.FragmentSearchBinding

class SearchFragment: Fragment(R.layout.fragment_search) {
    lateinit var binding: FragmentSearchBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)

    }
}