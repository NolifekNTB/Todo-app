package com.example.todo_app.VIew

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todo_app.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.todo_app.Model.Task
import java.io.File
import java.io.IOException
import android.view.View
import android.widget.AdapterView



class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var taskList = mutableListOf<Task>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Read list
        var x = FileHelper.readListFromFile(this, "daneListy")

        var uncheckedTasks: List<Task> = emptyList()
        var checkedTasks:List<Task> = emptyList()
        for (i in 0..x.size-1){
            uncheckedTasks = x.filter { !it.status } // Zadania nieodznaczone
            checkedTasks = x.filter { it.status } // Zadania zaznaczone
        }
        taskList.addAll(uncheckedTasks) // Najpierw dodaj nieodznaczone zadania
        taskList.addAll(checkedTasks) // Następnie dodaj zaznaczone zadania

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
        val sortOptions = listOf("Nazwa", "Data", "Ocena")
        binding.sortSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = sortOptions[position]

                // Tutaj obsłuż wybór użytkownika i wykonaj odpowiednie sortowanie
                when (selectedItem) {
                    "Nazwa" -> sortListByName()
                    "Data" -> Log.d("myApp", "Data")
                    "Ocena" -> Log.d("myApp", "Ocena")
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Obsłuż brak wyboru, jeśli to konieczne
            }
        }
    }

    fun sortListByName() {
        taskList.sortBy { it.title }
        //recyclerView
        val recyclerView = binding.rv
        val adapter = TaskAdapter(taskList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter.notifyDataSetChanged()
    }

    override fun onPause() {
        super.onPause()
        //Save list
        FileHelper.saveListToFile(this, taskList, "daneListy")
    }
    object FileHelper {
        private const val FILE_EXTENSION = ".json"

        fun saveListToFile(context: Context, list: List<Task>, fileName: String) {
            try {
                val file = File(context.filesDir, fileName + FILE_EXTENSION)
                val gson = Gson()
                val jsonString = gson.toJson(list)
                file.writeText(jsonString)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        fun readListFromFile(context: Context, fileName: String): List<Task> {
            try {
                val file = File(context.filesDir, fileName + FILE_EXTENSION)
                if (!file.exists()) {
                    return emptyList()
                }
                val gson = Gson()
                val jsonString = file.readText()
                val listType = object : TypeToken<List<Task>>() {}.type
                return gson.fromJson(jsonString, listType)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return emptyList()
        }
    }
}