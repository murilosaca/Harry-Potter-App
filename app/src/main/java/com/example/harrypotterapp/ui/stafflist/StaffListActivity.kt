package com.example.harrypotterapp.ui.stafflist

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.harrypotterapp.data.repository.HarryPotterRepository
import com.example.harrypotterapp.databinding.ActivityStaffListBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StaffListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStaffListBinding
    private val repository = HarryPotterRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStaffListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fetchHogwartsStaff()
    }

    private fun fetchHogwartsStaff() {
        binding.progressBarStaffList.visibility = View.VISIBLE
        binding.tvStaffListContent.text = ""

        lifecycleScope.launch(Dispatchers.IO) {
            val result = repository.getHogwartsStaff()

            withContext(Dispatchers.Main) {
                binding.progressBarStaffList.visibility = View.GONE

                result.onSuccess { staffList ->
                    if (staffList.isNotEmpty()) {
                        val stringBuilder = StringBuilder()
                        staffList.forEachIndexed { index, character ->
                            stringBuilder.append("${index + 1}. Nome: ${character.name}\n")
                            stringBuilder.append("   Nomes Alternativos: ${character.alternateNames?.joinToString(", ") ?: "Nenhum"}\n")
                            stringBuilder.append("   Espécie: ${character.species}\n")
                            stringBuilder.append("   Casa: ${character.house ?: "N/A"}\n")
                            stringBuilder.append("\n")
                        }
                        binding.tvStaffListContent.text = stringBuilder.toString()
                    } else {
                        binding.tvStaffListContent.text = "Nenhum professor encontrado."
                        Toast.makeText(this@StaffListActivity, "Nenhum professor encontrado.", Toast.LENGTH_SHORT).show()
                    }
                }.onFailure { throwable ->
                    binding.tvStaffListContent.text = "Erro ao carregar professores."
                    Toast.makeText(this@StaffListActivity, "Erro ao carregar professores: ${throwable.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}