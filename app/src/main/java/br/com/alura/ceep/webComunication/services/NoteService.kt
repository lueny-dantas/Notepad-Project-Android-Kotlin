package br.com.alura.ceep.webComunication.services

import br.com.alura.ceep.webComunication.model.NoteRequest
import br.com.alura.ceep.webComunication.model.NoteResponse
import retrofit2.Response
import retrofit2.http.*

interface NoteService {
    @GET("notas")
    suspend fun searchAll(): List<NoteResponse>

    @PUT("notas/{id}")
    suspend fun salve(
        @Path("id") id: String,
        @Body note: NoteRequest
    ): Response<NoteResponse>

    @DELETE("notas/{id}")
    suspend fun remove(@Path("id") id: String): Response<Unit>
}