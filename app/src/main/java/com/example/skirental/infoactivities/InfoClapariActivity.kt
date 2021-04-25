package com.example.skirental.infoactivities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.skirental.activities.BeteActivity
import com.example.skirental.databinding.ActivityInfoClapariBinding

class InfoClapariActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInfoClapariBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoClapariBinding.inflate(layoutInflater)
        setContentView(binding.root)

        butoane()
    }

    private fun butoane(){
        binding.buttonAlegeClapari.setOnClickListener(){
            Toast.makeText(this, "click", Toast.LENGTH_SHORT).show()
            val intent1 = Intent(this, BeteActivity::class.java)
            intent1.putExtra("inaltime",intent.getStringExtra("inaltime"))
            intent1.putExtra("marimepicior",intent.getStringExtra("marimepicior"))
            intent1.putExtra("sex",intent.getStringExtra("sex"))
            intent1.putExtra("varsta",intent.getStringExtra("varsta"))
            startActivity(intent1)
        }
    }
}