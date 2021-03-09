package net.FirebaseCRUD.ui

import android.view.View
import net.FirebaseCRUD.data.Task

interface RecyclerViewClickListener {
    fun onRecyclerViewItemClicked(view: View, task: Task)
}