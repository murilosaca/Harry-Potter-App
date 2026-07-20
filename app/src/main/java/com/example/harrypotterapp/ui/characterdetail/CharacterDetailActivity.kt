package com.example.harrypotterapp.ui.characterdetail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.harrypotterapp.data.repository.HarryPotterRepository
import com.example.harrypotterapp.databinding.ActivityCharacterDetailBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharacterDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCharacterDetailBinding
    private val repository = HarryPotterRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
    }

    private fun setupListeners() {
        binding.btnSearchCharacter.setOnClickListener {
            val characterId = binding.etCharacterId.text.toString().trim()
            if (characterId.isNotEmpty()) {
                fetchCharacterDetails(characterId)
            } else {
                Toast.makeText(this, "Por favor, digite um ID de personagem.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fetchCharacterDetails(id: String) {
        binding.progressBarCharacterDetail.visibility = View.VISIBLE
        clearCharacterDetails()

        lifecycleScope.launch(Dispatchers.IO) {
            val result = repository.getCharacterById(id)

            withContext(Dispatchers.Main) {
                binding.progressBarCharacterDetail.visibility = View.GONE

                result.onSuccess { characters ->
                    if (characters.isNotEmpty()) {
                        val character = characters.first()
                        displayCharacterDetails(character)
                    } else {
                        Toast.makeText(this@CharacterDetailActivity, "Personagem não encontrado ou ID inválido.", Toast.LENGTH_LONG).show()
                        clearCharacterDetails()
                    }
                }.onFailure { throwable ->
                    Toast.makeText(this@CharacterDetailActivity, "Erro ao buscar: ${throwable.message}", Toast.LENGTH_LONG).show()
                    clearCharacterDetails()
                }
            }
        }
    }

    private fun displayCharacterDetails(character: com.example.harrypotterapp.data.model.Character) {
        binding.tvCharacterName.text = "Nome: ${character.name}"
        binding.tvCharacterSpecies.text = "Espécie: ${character.species}"
        binding.tvCharacterHouse.text = "Casa: ${character.house ?: "N/A"}"

        val alternateNamesText = if (!character.alternateNames.isNullOrEmpty()) {
            "Nomes Alternativos: ${character.alternateNames.joinToString(", ")}"
        } else {
            "Nomes Alternativos: Nenhum"
        }
        binding.tvCharacterAlternateNames.text = alternateNamesText

        if (!character.imageUrl.isNullOrEmpty()) {
            binding.ivCharacterImage.load(character.imageUrl) {
                placeholder(android.R.drawable.ic_menu_gallery)
                error(android.R.drawable.ic_delete)
            }
            binding.ivCharacterImage.visibility = View.VISIBLE
        } else {
            binding.ivCharacterImage.visibility = View.GONE
        }
    }

    private fun clearCharacterDetails() {
        binding.tvCharacterName.text = "Nome:"
        binding.tvCharacterSpecies.text = "Espécie:"
        binding.tvCharacterHouse.text = "Casa:"
        binding.tvCharacterAlternateNames.text = "Nomes Alternativos:"
        binding.ivCharacterImage.visibility = View.GONE
        binding.ivCharacterImage.setImageDrawable(null)
    }
}