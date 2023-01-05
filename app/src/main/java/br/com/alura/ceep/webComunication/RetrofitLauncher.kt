package br.com.alura.ceep.webComunication

import br.com.alura.ceep.webComunication.services.NoteService
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RetrofitLauncher {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.100.118:8080/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
    val noteService = retrofit.create(NoteService::class.java)
}