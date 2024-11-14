package com.example.todoapp.models

import com.google.gson.annotations.SerializedName
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_items")
data class TodoItem(
    @PrimaryKey
    @SerializedName("id")
    val id: String,

    @SerializedName("text")
    var text: String,

    @SerializedName("importance")
    var importance: String?,

    @SerializedName("color")
    var color: String? = null,

    @SerializedName("changed_at")
    var changedAt: Long? = null,

    @SerializedName("deadline")
    var deadline: Long? = null,

    @SerializedName("done")
    var isCompleted: Boolean = false,

    @SerializedName("created_at")
    var createdAt: Long? = null,

    @SerializedName("last_updated_by")
    var lastUpdatedBy: String? = null,

    @SerializedName("files")
    val files: String? = null
)


