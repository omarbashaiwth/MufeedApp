package com.omarbashawith.mufeed_app.features.list.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.omarbashawith.mufeed_app.features.list.data.paging.RemoteKeys

@Dao
interface RemoteKeysDao {

    @Query("SELECT * FROM remote_keys_table WHERE id= :id")
    suspend fun getRemoteKeysById(id: String): RemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRemoteKeys(remoteKeys: List<RemoteKeys>)

    @Query("DELETE FROM remote_keys_table")
    suspend fun deleteAllRemoteKeys()
}