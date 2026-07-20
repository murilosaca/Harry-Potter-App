package com.example.harrypotterapp.ui.spells

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.harrypotterapp.databinding.ActivitySpellDetailBinding

class SpellDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySpellDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpellDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val spellName = intent.getStringExtra("spell_name")
        val spellDescription = intent.getStringExtra("spell_description")

        binding.tvSpellDetailName.text = "Nome: ${spellName ?: "N/A"}"
        binding.tvSpellDetailDescription.text = "Descrição: ${spellDescription ?: "N/A"}"
    }
}