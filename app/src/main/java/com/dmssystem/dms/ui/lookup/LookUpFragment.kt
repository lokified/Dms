package com.dmssystem.dms.ui.lookup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dmssystem.dms.R
import com.dmssystem.dms.databinding.FragmentLookUpBinding

class LookUpFragment : Fragment() {
    private lateinit var binding: FragmentLookUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLookUpBinding.inflate(inflater, container, false)
        return binding.root
    }


}