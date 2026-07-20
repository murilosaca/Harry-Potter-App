package com.example.harrypotterapp.ui.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.harrypotterapp.databinding.ActivityDashboardBinding
import com.example.harrypotterapp.ui.characterdetail.CharacterDetailActivity
import com.example.harrypotterapp.ui.housestudents.HouseStudentsActivity
import com.example.harrypotterapp.ui.stafflist.StaffListActivity
import com.example.harrypotterapp.ui.spells.SpellsActivity

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupButtons()
    }

    private fun setupButtons() {
        binding.btnListSpecificCharacter.setOnClickListener {
            startActivity(Intent(this, CharacterDetailActivity::class.java))
        }

        binding.btnListTeachers.setOnClickListener {
            startActivity(Intent(this, StaffListActivity::class.java))
        }

        binding.btnListHouseStudents.setOnClickListener {
            startActivity(Intent(this, HouseStudentsActivity::class.java))
        }

        binding.btnViewSpells.setOnClickListener {
            startActivity(Intent(this, SpellsActivity::class.java))
        }

        binding.btnExit.setOnClickListener {
            finishAffinity()
        }
    }
}