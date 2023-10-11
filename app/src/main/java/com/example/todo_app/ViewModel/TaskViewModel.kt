package com.example.todo_app.ViewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.todo_app.Model.Task
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.IOException

class TaskViewModel: ViewModel() {
    fun saveListToFile(context: Context, list: List<Task>, fileName: String) {
        val FILE_EXTENSION = ".json"
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
        val FILE_EXTENSION = ".json"
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