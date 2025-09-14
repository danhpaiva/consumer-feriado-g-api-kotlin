package com.example.consumerferiadog

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RetornoApiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_retorno_api)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val year = intent.getIntExtra("YEAR_EXTRA", -1)
        val retornoApi = findViewById<TextView>(R.id.textViewTitle)
        if (year != -1) {
            retornoApi.text = "Feriados em $year"
            fetchHolidays(year)
        } else {
            retornoApi.text = "Ano não especificado"
        }
    }

    private fun fetchHolidays(year: Int) {
        val tvRetorno = findViewById<TextView>(R.id.tvRetornoApi)

        val brasilApiResponse = RetrofitHelper.getInstance().create(BrasilApi::class.java)

        GlobalScope.launch(Dispatchers.IO) { // Use Dispatchers.IO para operações de rede
            try {
                // Passa o ano para a chamada da API
                val result = brasilApiResponse.getFeriados(year)
                if (result.isSuccessful) {
                    val feriados = result.body()
                    val feriadosText = feriados?.joinToString("\n") { "${it.name} - ${it.date}" }
                    withContext(Dispatchers.Main) { // Volte para a thread principal para atualizar a UI
                        tvRetorno.text = feriadosText ?: "Nenhum feriado encontrado."
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        tvRetorno.text = "Erro: ${result.code()} - ${result.message()}"
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("API_CALL", "Erro ao buscar feriados", e)
                    tvRetorno.text = "Erro na requisição: ${e.message}"
                }
            }
        }
    }
}