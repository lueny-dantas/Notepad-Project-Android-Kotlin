package br.com.alura.ceep.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import br.com.alura.ceep.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert(onConflict = REPLACE)
    suspend fun salve(note: Note)

    @Insert(onConflict = REPLACE)
    suspend fun salve(note: List<Note>)

    @Query("SELECT * FROM Note WHERE disabled = 0")
    fun searchAll() : Flow<List<Note>>

    @Query("SELECT * FROM Note WHERE id = :id AND disabled = 0")
    fun searchById(id: String): Flow<Note>

    @Query("DELETE FROM Note WHERE id = :id")
    suspend fun remove(id: String)

    @Query("SELECT * FROM Note WHERE synchronize = 0 AND disabled = 0")
    fun searchNotSynchronized(): Flow<List<Note>>

    @Query("UPDATE Note SET disabled = 1 WHERE id = :id ")
    suspend fun deactivate(id: String)

    @Query("SELECT * FROM Note WHERE disabled = 1")
    fun searchDisabledNotes(): Flow<List<Note>>


}