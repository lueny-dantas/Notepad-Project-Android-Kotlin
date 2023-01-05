package br.com.alura.ceep.webComunication.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
class NoteRequest(
    val titulo: String,
    val descricao: String,
    val imagem: String? = null
) {
}