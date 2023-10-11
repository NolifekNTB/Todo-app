package com.example.todo_app.VIew

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todo_app.databinding.ActivityMainBinding
import com.example.todo_app.Model.Task
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_app.ViewModel.TaskViewModel
class MainActivity : AppCompatActivity() {
    private val mainVM = TaskViewModel()
    private lateinit var binding: ActivityMainBinding
    private var taskList = mutableListOf<Task>()
    private lateinit var adapter: TaskAdapter
    private lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Read list
        taskList = mainVM.readListFromFile(this, "daneListy").sortedWith(compareByDescending {!it.status }).toMutableList()

        //RecyclerView setup
        recyclerView = binding.rv
        adapter = TaskAdapter(taskList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        binding.btnAdd.setOnClickListener{
            //Validation
            if (binding.etAdd.text.isEmpty()) {
                binding.etAdd.hint = "To pole nie może być puste"
            } else {
                // Add task
                val textToAdd = binding.etAdd.text.toString()
                taskList.add(Task(textToAdd, "opis", false))
                adapter.notifyItemInserted(taskList.size - 1)
                binding.etAdd.setText("")
            }
        }
    }
    override fun onPause() {
        super.onPause()
        //Save list
        mainVM.saveListToFile(this, taskList, "daneListy")
    }
}