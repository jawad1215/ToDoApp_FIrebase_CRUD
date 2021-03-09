package net.FirebaseCRUD.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_view_task.view.*
import net.FirebaseCRUD.R
import net.FirebaseCRUD.data.Task

class TasksAdapter : RecyclerView.Adapter<TasksAdapter.TasksViewModel>() {

    private var tasks = mutableListOf<Task>()
    var listener: RecyclerViewClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TasksViewModel(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_task, parent, false)
    )

    override fun getItemCount() = tasks.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TasksViewModel, position: Int) {
        holder.view.text_view_name.text = tasks[position].name
        holder.view.button_edit.setOnClickListener {
            listener?.onRecyclerViewItemClicked(it, tasks[position])
        }
        holder.view.button_delete.setOnClickListener {
            listener?.onRecyclerViewItemClicked(it, tasks[position])
        }
    }

    fun setTasks(tasks: List<Task>) {
        this.tasks = tasks as MutableList<Task>
        notifyDataSetChanged()
    }

    fun addTask(task: Task) {
        if (!tasks.contains(task)) {
            tasks.add(task)
        } else {
            val index = tasks.indexOf(task)
            if (task.isDeleted) {
                tasks.removeAt(index)
            } else {
                tasks[index] = task
            }
        }
        notifyDataSetChanged()
    }

    class TasksViewModel(val view: View) : RecyclerView.ViewHolder(view)
}