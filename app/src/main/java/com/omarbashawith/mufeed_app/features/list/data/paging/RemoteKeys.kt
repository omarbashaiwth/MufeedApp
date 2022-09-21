package com.omarbashawith.mufeed_app.features.list.data.paging

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys_table")
data class RemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val prePage: Int?,
    val nextPage: Int?
)
