package net.FirebaseCRUD.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import net.FirebaseCRUD.data.Task
import java.lang.Exception

class TasksViewModel : ViewModel() {

    val NODE_TASKS = "tasks"
    private val dbTasks = Firebase.database.getReference(NODE_TASKS)


    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>>
        get() = _tasks

    private val _task = MutableLiveData<Task>()
    val task: LiveData<Task>
        get() = _task

    private val _result = MutableLiveData<Exception?>()
    val result: LiveData<Exception?>
        get() = _result

    fun addTask(task: Task) {
        task.id = dbTasks.push().key
        dbTasks.child(task.id!!).setValue(task)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    _result.value = null
                } else {
                    _result.value = it.exception
                }
            }
    }


    fun fetchTasks() {
        dbTasks.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val tasks = mutableListOf<Task>()
                    for (taskSnapshot in snapshot.children) {
                        val task = taskSnapshot.getValue(Task::class.java)
                        task?.id = taskSnapshot.key
                        task?.let { tasks.add(it) }
                    }
                    _tasks.value = tasks
                }
            }
        })
    }

    fun updateTask(task: Task) {
        dbTasks.child(task.id!!).setValue(task)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    _result.value = null
                } else {
                    _result.value = it.exception
                }
            }
    }

    fun deleteTask(task: Task) {
        dbTasks.child(task.id!!).setValue(null)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    _result.value = null
                } else {
                    _result.value = it.exception
                }
            }
    }

}