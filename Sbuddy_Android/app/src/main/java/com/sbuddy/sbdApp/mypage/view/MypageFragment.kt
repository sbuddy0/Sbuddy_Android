package com.sbuddy.sbdApp.mypage.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.sbuddy.sbdApp.R
import com.sbuddy.sbdApp.mypage.viewmodel.MypageViewModel

class MypageFragment : Fragment() {
    private lateinit var mypageViewModel: MypageViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mypage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mypageViewModel = ViewModelProvider(this)[MypageViewModel::class.java]

    }

    override fun onResume() {
        super.onResume()

        mypageViewModel.myDetail()
    }
}