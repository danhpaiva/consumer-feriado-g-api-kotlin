package com.example.consumerferiadog

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val editTextYear = findViewById<EditText>(R.id.editTextYear)
        val buttonSearch = findViewById<Button>(R.id.buttonFetchHolidays)

        buttonSearch.setOnClickListener {
            val ano = editTextYear.text.toString()

            if (ano.isNotEmpty()) {
                val year = ano.toIntOrNull()
                if (year != null && year in 1900..2100) {
                    // Crie um Intent para a próxima tela e passe o ano
                    val intent = Intent(this, RetornoApiActivity::class.java).apply {
                        putExtra("YEAR_EXTRA", year)
                    }
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Por favor, insira um ano válido (ex: 2025)", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor, insira o ano.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}