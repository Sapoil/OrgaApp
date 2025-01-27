package com.example.orgaapp.fragments

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.orgaapp.MainActivity
import com.example.orgaapp.R
import com.example.orgaapp.database.AppDatabase
import com.example.orgaapp.database.Task
import com.example.orgaapp.databinding.FragmentAddTaskBinding
import com.example.orgaapp.utils.MyAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date
import java.util.Locale

class AddTaskFragment : Fragment() {

    private var _binding: FragmentAddTaskBinding? = null
    private val binding get() = _binding!!
    private lateinit var db: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        db = (activity as MainActivity).db
        _binding = FragmentAddTaskBinding.inflate(inflater, container, false)

        val impoArray = resources.getStringArray(R.array.importance_array)
        binding.editImportance.adapter = MyAdapter<String>(requireActivity(),
            android.R.layout.simple_spinner_dropdown_item,
            impoArray)

        val orgaArray = mutableListOf<String>()
        orgaArray.add("Sélection de l'organisation")

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val organizations = db.orgaDao().getAll()
                organizations.forEach { orga ->
                    orgaArray.add(orga.name)
                }
            }
            orgaArray.add("+ Créer une nouvelle organisation")
            binding.editOrga.setAdapter(MyAdapter<String>(requireActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                orgaArray.toTypedArray()))

        }



        binding.editDeadline.minDate = Date().time

        binding.confirmButton.setOnClickListener{
            val title = binding.editTitle.text.toString()
            var detail = binding.editDetail.text.toString()
            val orga = binding.editOrga.selectedItem.toString()
            val impo = binding.editImportance.selectedItem.toString()
            val deadline = formatDateForSQLite(binding.editDeadline.dayOfMonth,binding.editDeadline.month,binding.editDeadline.year)
            var error: String = ""
            var isValid: Boolean = true
            if(title.isBlank()) {
                error += "Il faut mettre un titre\n"
                isValid = false
            }
            if((orga == orgaArray[0]) or (orga == orgaArray[orgaArray.size - 1])){
                error += "Il faut sélectionner une organisation\n"
                isValid = false
            }
            if(impo == impoArray[0]){
                error += "Il faut sélectionner une importance\n"
                isValid = false
            }
            if(isValid){
                lifecycleScope.launch {
                    withContext(Dispatchers.IO){
                        db.taskDao().insert(Task(title=title,detail=detail, importance = impo.toInt(), deadline = deadline,orgId=db.orgaDao().getId(orga)))
                    }
                }
                requireActivity().supportFragmentManager.popBackStack()
            }
            else{
                AlertDialog.Builder(requireActivity())
                    .setTitle("Tâche incomplète")
                    .setMessage(error)
                    .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                    .show()
            }
        }

        binding.editOrga.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position == orgaArray.size-1){
                    //TODO: implémenter fragment de création d'organisation
                    AlertDialog.Builder(requireActivity())
                        .setTitle("WIP")
                        .setMessage("WIP")
                        .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                        .show()
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun formatDateForSQLite(day: Int, month: Int, year: Int): Date{
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.parse(String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, day))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val mainActivity = activity as? MainActivity
        mainActivity?.sharedFloatingActionButton?.show()
        mainActivity?.sharedFloatingActionButton?.isEnabled = true
        _binding=null
    }

}