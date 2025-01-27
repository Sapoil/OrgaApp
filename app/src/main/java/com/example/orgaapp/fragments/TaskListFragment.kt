package com.example.orgaapp.fragments

import TaskAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.orgaapp.MainActivity
import com.example.orgaapp.R
import com.example.orgaapp.database.AppDatabase
import com.example.orgaapp.database.Task
import com.example.orgaapp.databinding.FragmentTaskListBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class TaskListFragment : Fragment() {

    private lateinit var db: AppDatabase
    private var _binding: FragmentTaskListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        db = (activity as MainActivity).db
        _binding = FragmentTaskListBinding.inflate(inflater, container, false)

        return inflater.inflate(R.layout.fragment_task_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val taskRecyclerView = binding.taskRecyclerView
        val emptyTextView = binding.emptyTextView
        taskRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Charger les tâches avec une coroutine
        lifecycleScope.launch {
            val tasks = loadTasksFromDatabase()

            if (tasks.isEmpty()) {
                taskRecyclerView.visibility = View.GONE
                emptyTextView.visibility = View.VISIBLE
            } else {
                taskRecyclerView.visibility = View.VISIBLE
                emptyTextView.visibility = View.GONE
                taskRecyclerView.adapter = TaskAdapter(tasks)
            }
        }
    }

    private suspend fun loadTasksFromDatabase(): List<Task> {
        return withContext(Dispatchers.IO) {
            db.taskDao().getAll() // Méthode DAO pour récupérer toutes les tâches
        }
    }
}