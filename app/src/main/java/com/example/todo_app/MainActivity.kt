package com.example.todo_app

import android.R.attr.data
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todo_app.databinding.ActivityMainBinding
import layout.Task
import layout.TaskAdapter


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var taskList = mutableListOf<Task>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //recyclerView
        val recyclerView = binding.rv
        val adapter = TaskAdapter(taskList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        binding.btnAdd.setOnClickListener{
            //walidacja daych
            if (binding.etAdd.text.isEmpty()) {
                binding.etAdd.hint = "To pole nie może być puste"
            } else {
                //add task
                var textToAdd = binding.etAdd.text.toString()
                taskList.add(Task(textToAdd, "opis", false))
                adapter.notifyItemInserted(taskList.size - 1)
                binding.etAdd.setText("")
            }
        }
    }

    override fun onResume() {
        super.onResume()
    }
}