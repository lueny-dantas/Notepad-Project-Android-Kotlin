package br.com.alura.ceep.repository

import br.com.alura.ceep.database.dao.NoteDao
import br.com.alura.ceep.model.Note
import br.com.alura.ceep.webComunication.services.NoteWebClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class NoteRepository(
    private val dao: NoteDao,
    private val webClient: NoteWebClient
) {

    fun searchAll(): Flow<List<Note>> {
        return dao.searchAll()
    }

    private suspend fun updateAll() {
        webClient.searchAll()?.let { notes ->
            val synchronizedNotes = notes.map {
                it.copy(synchronize = true)
            }
            dao.salve(synchronizedNotes)
        }

    }

    fun searchById(id: String): Flow<Note> {
        return dao.searchById(id)

    }

    suspend fun remove(id: String) {
        dao.deactivate(id)
        if (webClient.remove(id)) {
            dao.remove(id)
        }

    }

    suspend fun salve(note: Note) {
        dao.salve(note)
        if(webClient.salve(note)){
            val syncedNote = note.copy(synchronize = true)
            dao.salve(syncedNote)
        }

    }

    suspend fun synchronize() {
        val disabledNotes = dao.searchDisabledNotes().first()
        disabledNotes. forEach { disabledNote ->
            remove(disabledNote.id)
        }
        val  unsynchronizedNotes = dao.searchNotSynchronized().first()
        unsynchronizedNotes.forEach{unsynchronizedNote ->
            salve(unsynchronizedNote)
        }
        updateAll()
    }
}