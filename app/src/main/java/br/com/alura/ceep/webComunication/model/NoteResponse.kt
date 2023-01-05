package br.com.alura.ceep.webComunication.model

import br.com.alura.ceep.model.Note
import com.squareup.moshi.Json
import java.util.*

class NoteResponse(
    val id: String?,
    val titulo: String?,
    val descricao: String?,
    val imagem: String?,
) {
    val note: Note
        get() = Note(
            id = id ?: UUID.randomUUID().toString(),
            title = titulo ?: "",
            description = descricao ?: "",
            image = imagem ?: ""
        )
}