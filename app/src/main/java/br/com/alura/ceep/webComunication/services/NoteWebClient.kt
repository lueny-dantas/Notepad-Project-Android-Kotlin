package br.com.alura.ceep.webComunication.services

import android.util.Log
import br.com.alura.ceep.model.Note
import br.com.alura.ceep.webComunication.RetrofitLauncher
import br.com.alura.ceep.webComunication.model.NoteRequest

private const val TAG = "noteWebClient"

class NoteWebClient {

    private val noteService: NoteService = RetrofitLauncher().noteService

    suspend fun searchAll(): List<Note>? {
        return try {
            val notesResponse = noteService
                .searchAll()
            notesResponse.map { noteResponse ->
                noteResponse.note
            }
        } catch (e: Exception) {
            null
        }
    }

    suspend fun salve(note: Note): Boolean {
        try {
            val response = noteService.salve(
                note.id, NoteRequest(
                    titulo = note.title,
                    descricao = note.description,
                    imagem = note.image
                )
            )
            return response.isSuccessful
        } catch (error: Exception) {
            Log.e(TAG, "SALVA: FALHA AO TENTAR SALVAR", error)
        }
        return false
    }

    suspend fun remove(id: String): Boolean {
        try {
            noteService.remove(id)
            return true
        } catch (error: Exception) {
            Log.e(TAG, "REMOVE: FALHA AO TENTAR REMOVER NOTA", error)
        }
        return false

    }
}