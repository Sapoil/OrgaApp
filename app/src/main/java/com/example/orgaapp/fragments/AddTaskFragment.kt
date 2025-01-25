package com.example.orgaapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.ui.AppBarConfiguration
import com.example.orgaapp.MainActivity
import com.example.orgaapp.databinding.FragmentAddTaskBinding

class AddTaskFragment : Fragment() {

    private var _binding: FragmentAddTaskBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTaskBinding.inflate(inflater, container, false)

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val mainActivity = activity as? MainActivity
        mainActivity?.sharedFloatingActionButton?.show()
        _binding=null
    }
}