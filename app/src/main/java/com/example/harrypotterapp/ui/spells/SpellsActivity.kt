package com.example.harrypotterapp.ui.spells

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.harrypotterapp.data.model.Spell
import com.example.harrypotterapp.data.repository.HarryPotterRepository
import com.example.harrypotterapp.databinding.ActivitySpellsBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SpellsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySpellsBinding
    private lateinit var spellAdapter: SpellAdapter
    private val repository = HarryPotterRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpellsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        fetchAllSpells()
    }

    private fun setupRecyclerView() {
        spellAdapter = SpellAdapter { spell ->
            val intent = Intent(this, SpellDetailActivity::class.java).apply {
                putExtra("spell_name", spell.name)
                putExtra("spell_description", spell.description)
            }
            startActivity(intent)
        }
        binding.recyclerViewSpells.adapter = spellAdapter
    }

    private fun fetchAllSpells() {
        binding.progressBarSpells.visibility = View.VISIBLE

        lifecycleScope.launch(Dispatchers.IO) {
            val result = repository.getAllSpells()

            withContext(Dispatchers.Main) {
                binding.progressBarSpells.visibility = View.GONE

                result.onSuccess { spells ->
                    if (spells.isNotEmpty()) {
                        spellAdapter.updateSpells(spells)
                    } else {
                        Toast.makeText(this@SpellsActivity, "Nenhum feitiço encontrado.", Toast.LENGTH_SHORT).show()
                    }
                }.onFailure { throwable ->
                    Toast.makeText(this@SpellsActivity, "Erro ao carregar feitiços: ${throwable.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}