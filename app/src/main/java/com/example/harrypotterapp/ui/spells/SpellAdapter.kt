package com.example.harrypotterapp.ui.spells

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.harrypotterapp.data.model.Spell
import com.example.harrypotterapp.databinding.ItemSpellBinding

class SpellAdapter(private val onSpellClick: (Spell) -> Unit) :
    RecyclerView.Adapter<SpellAdapter.SpellViewHolder>() {

    private val spells = mutableListOf<Spell>()

    fun updateSpells(newSpells: List<Spell>) {
        spells.clear()
        spells.addAll(newSpells)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpellViewHolder {
        val binding = ItemSpellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SpellViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SpellViewHolder, position: Int) {
        val spell = spells[position]
        holder.bind(spell)
    }

    override fun getItemCount(): Int = spells.size

    inner class SpellViewHolder(private val binding: ItemSpellBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(spell: Spell) {
            binding.tvSpellNameItem.text = spell.name
            binding.root.setOnClickListener {
                onSpellClick(spell)
            }
        }
    }
}