package net.FirebaseCRUD.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_tasks.*
import net.FirebaseCRUD.R
import net.FirebaseCRUD.data.Task


class TasksFragment : Fragment(), RecyclerViewClickListener {

    private lateinit var viewModel: TasksViewModel
    private val adapter = TasksAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(TasksViewModel::class.java)
        return inflater.inflate(R.layout.fragment_tasks, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.fetchTasks()


        adapter.listener = this
        recycler_view_authors.adapter = adapter

       // viewModel.fetchFilteredAuthors(6)
//        viewModel.getRealtimeUpdates()

        viewModel.tasks.observe(viewLifecycleOwner, Observer {
            adapter.setTasks(it)
        })

        viewModel.task.observe(viewLifecycleOwner, Observer {
            adapter.addTask(it)
        })

        button_add.setOnClickListener {
            AddTaskDialogFragment()
                .show(childFragmentManager, "")
        }
    }

    override fun onRecyclerViewItemClicked(view: View, task: Task) {
        when (view.id) {
            R.id.button_edit -> {
                EditTaskDialogFragment(task).show(childFragmentManager, "")
            }
            R.id.button_delete -> {
                AlertDialog.Builder(requireContext()).also {
                    it.setTitle(getString(R.string.delete_confirmation))
                    it.setPositiveButton(getString(R.string.yes)) { dialog, which ->
                        viewModel.deleteTask(task)
                    }
                }.create().show()
            }
        }
    }
}
