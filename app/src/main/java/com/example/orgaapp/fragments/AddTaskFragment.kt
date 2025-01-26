package com.example.orgaapp.fragments

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.orgaapp.MainActivity
import com.example.orgaapp.R
import com.example.orgaapp.database.AppDatabase
import com.example.orgaapp.databinding.FragmentAddTaskBinding
import com.example.orgaapp.utils.MyAdapter
import java.util.Date
import java.util.Locale

class AddTaskFragment : Fragment() {

    private var _binding: FragmentAddTaskBinding? = null
    private val binding get() = _binding!!
    //private lateinit var db: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //db = (activity as MainActivity).db
        _binding = FragmentAddTaskBinding.inflate(inflater, container, false)

        binding.editImportance.adapter = MyAdapter<String>(requireActivity(),
            android.R.layout.simple_spinner_dropdown_item,
            resources.getStringArray(R.array.importance_array))

        binding.editOrga.adapter = MyAdapter<String>(requireActivity(),
            android.R.layout.simple_spinner_dropdown_item,
            resources.getStringArray(R.array.importance_array))

        binding.editDeadline.minDate = Date().time

        binding.confirmButton.setOnClickListener{
            val title = binding.editTitle.text.toString()
            var detail = binding.editDetail.text.toString()
            val orga = binding.editOrga.selectedItem
            val impo = binding.editImportance.selectedItem
            val deadline = formatDateForSQLite(binding.editDeadline.dayOfMonth,binding.editDeadline.month,binding.editDeadline.year)

            if(title.isBlank()) {
                AlertDialog.Builder(requireActivity())
                    .setTitle("Erreur")
                    .setMessage(orga.toString())
                    .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                    .show()
            }
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun formatDateForSQLite(day: Int, month: Int, year: Int): Date{
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return dateFormat.parse(String.format(Locale.getDefault(), "%02d-%02d-%04d", day, month + 1, year))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val mainActivity = activity as? MainActivity
        mainActivity?.sharedFloatingActionButton?.show()
        mainActivity?.sharedFloatingActionButton?.isEnabled = true
        _binding=null
    }

}