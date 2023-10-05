package layout

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_app.databinding.ListItemBinding


class TaskAdapter(private val tasks: List<Task>) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(private val binding: ListItemBinding, private val adapter: TaskAdapter) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task, tasksList: List<Task>) {
            binding.cbTask.text = task.title

            //Checkbox strike through line
            binding.cbTask.setOnCheckedChangeListener { buttonView, isChecked ->
                // write here your code for example ...
                if (isChecked) {
                    binding.cbTask.paintFlags = binding.cbTask.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    adapter.notifyItemMoved(bindingAdapterPosition, tasksList.size-1)
                    
                } else {
                    binding.cbTask.paintFlags = binding.cbTask.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                }
            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding, this)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentTask = tasks[position]
        holder.bind(currentTask, tasks)
    }

    override fun getItemCount() = tasks.size
}
