package com.example.todo_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todo_app.databinding.ActivityMainBinding
import layout.Task
import layout.TaskAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView = binding.rv
        val taskList = mutableListOf<Task>()
        taskList.add(Task("Tytuł12", "opis1", false))
        taskList.add(Task("Tytuł22", "opis2", true))
        taskList.add(Task("Tytuł32", "opis3", false))
        val adapter = TaskAdapter(taskList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}