package com.example.harrypotterapp.data.repository

import com.example.harrypotterapp.data.model.Character
import com.example.harrypotterapp.data.model.Spell
import com.example.harrypotterapp.data.remote.HarryPotterApiService
import com.example.harrypotterapp.data.remote.RetrofitClient

class HarryPotterRepository(private val apiService: HarryPotterApiService = RetrofitClient.instance) {

    suspend fun getCharacterById(id: String): Result<List<Character>> {
        return try {
            val response = apiService.getCharacterById(id)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(RuntimeException("Erro ao buscar personagem: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getHogwartsStaff(): Result<List<Character>> {
        return try {
            val response = apiService.getHogwartsStaff()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(RuntimeException("Erro ao buscar professores: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getCharactersByHouse(house: String): Result<List<Character>> {
        return try {
            val response = apiService.getCharactersByHouse(house)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(RuntimeException("Erro ao buscar estudantes da casa $house: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getAllSpells(): Result<List<Spell>> {
        return try {
            val response = apiService.getAllSpells()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(RuntimeException("Erro ao buscar feitiços: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}