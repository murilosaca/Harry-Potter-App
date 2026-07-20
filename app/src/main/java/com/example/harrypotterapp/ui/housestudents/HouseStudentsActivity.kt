package com.example.harrypotterapp.ui.housestudents

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.harrypotterapp.data.repository.HarryPotterRepository
import com.example.harrypotterapp.databinding.ActivityHouseStudentsBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HouseStudentsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHouseStudentsBinding
    private val repository = HarryPotterRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHouseStudentsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
    }

    private fun setupListeners() {
        binding.btnSearchHouseStudents.setOnClickListener {
            val checkedRadioButtonId = binding.radioGroupHouses.checkedRadioButtonId
            if (checkedRadioButtonId != -1) {
                val house = when (checkedRadioButtonId) {
                    binding.radioGryffindor.id -> "gryffindor"
                    binding.radioSlytherin.id -> "slytherin"
                    binding.radioHufflepuff.id -> "hufflepuff"
                    binding.radioRavenclaw.id -> "ravenclaw"
                    else -> null
                }
                if (house != null) {
                    fetchHouseStudents(house)
                } else {
                    Toast.makeText(this, "Selecione uma casa válida.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor, selecione uma casa.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fetchHouseStudents(house: String) {
        binding.progressBarHouseStudents.visibility = View.VISIBLE
        binding.tvHouseStudentsContent.text = ""

        lifecycleScope.launch(Dispatchers.IO) {
            val result = repository.getCharactersByHouse(house)

            withContext(Dispatchers.Main) {
                binding.progressBarHouseStudents.visibility = View.GONE

                result.onSuccess { students ->
                    if (students.isNotEmpty()) {
                        val stringBuilder = StringBuilder()
                        students.forEachIndexed { index, character ->
                            if (character.hogwartsStudent) { // Filtra apenas estudantes
                                stringBuilder.append("${index + 1}. Nome: ${character.name}\n")
                                stringBuilder.append("   Espécie: ${character.species}\n")
                                stringBuilder.append("\n")
                            }
                        }
                        if (stringBuilder.isNotEmpty()) {
                            binding.tvHouseStudentsContent.text = "Estudantes de ${house.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }}:\n\n${stringBuilder.toString()}"
                        } else {
                            binding.tvHouseStudentsContent.text = "Nenhum estudante encontrado para ${house.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }}."
                            Toast.makeText(this@HouseStudentsActivity, "Nenhum estudante encontrado para ${house.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }}.", Toast.LENGTH_SHORT).show()
                        }

                    } else {
                        binding.tvHouseStudentsContent.text = "Nenhum estudante encontrado para ${house.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }}."
                        Toast.makeText(this@HouseStudentsActivity, "Nenhum estudante encontrado para ${house.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }}.", Toast.LENGTH_SHORT).show()
                    }
                }.onFailure { throwable ->
                    binding.tvHouseStudentsContent.text = "Erro ao carregar estudantes."
                    Toast.makeText(this@HouseStudentsActivity, "Erro ao carregar estudantes: ${throwable.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}