package com.example.harrypotterapp.data.model

import com.google.gson.annotations.SerializedName

data class Character(
    val id: String,
    val name: String,
    @SerializedName("alternate_names")
    val alternateNames: List<String>?,
    val species: String,
    val gender: String,
    val house: String?,
    val dateOfBirth: String?,
    val yearOfBirth: Int?,
    val wizard: Boolean,
    val ancestry: String?,
    val eyeColour: String?,
    val hairColour: String?,
    val wand: Wand?,
    val patronus: String?,
    val hogwartsStudent: Boolean,
    val hogwartsStaff: Boolean,
    val actor: String,
    val alive: Boolean,
    @SerializedName("image")
    val imageUrl: String?
)

data class Wand(
    val wood: String?,
    val core: String?,
    val length: Double?
)