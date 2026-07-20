package com.example.harrypotterapp.data.remote

import com.example.harrypotterapp.data.model.Character
import com.example.harrypotterapp.data.model.Spell
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface HarryPotterApiService {

    @GET("character/{id}")
    suspend fun getCharacterById(@Path("id") id: String): Response<List<Character>>

    @GET("characters/staff")
    suspend fun getHogwartsStaff(): Response<List<Character>>

    @GET("characters/house/{house}")
    suspend fun getCharactersByHouse(@Path("house") house: String): Response<List<Character>>

    @GET("spells")
    suspend fun getAllSpells(): Response<List<Spell>>
}