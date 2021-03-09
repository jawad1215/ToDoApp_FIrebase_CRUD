package net.FirebaseCRUD.data

import com.google.firebase.database.Exclude


data class Task(
    @get:Exclude
    var id: String? = null,
    var name: String? = null,
    @get:Exclude
    var isDeleted: Boolean = false
)