package layout

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_app.databinding.ListItemBinding

class TaskAdapter(private val tasks: List<Task>) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(private val binding: ListItemBinding, private val adapter: TaskAdapter) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task) {
            binding.cbTask.text = task.title

            // Zastosuj styl przekreślenia tekstu, jeśli checkbox jest zaznaczony
            if (binding.cbTask.isChecked) {
                binding.cbTask.setPaintFlags(binding.cbTask.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
            } else {
                binding.cbTask.paintFlags = binding.cbTask.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()

            }

            // Obsługa kliknięcia checkboxa
            binding.cbTask.setOnCheckedChangeListener { _, isChecked ->
                // Zaktualizuj model danych na podstawie stanu checkboxa
                binding.cbTask.isChecked = isChecked
                // Powiadom adapter o zmianie danych
                adapter.notifyItemChanged(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding, this)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentTask = tasks[position]
        holder.bind(currentTask)
    }

    override fun getItemCount() = tasks.size
}
