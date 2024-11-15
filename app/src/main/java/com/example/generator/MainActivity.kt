package com.example.generator

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random
import android.content.Intent

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val randomNumberText: TextView = findViewById(R.id.randomNumberText)
        val minNumberInput: EditText = findViewById(R.id.minNumberInput)
        val maxNumberInput: EditText = findViewById(R.id.maxNumberInput)
        val oddNumberSwitch: Switch = findViewById(R.id.oddNumberSwitch)
        val evenNumberSwitch: Switch = findViewById(R.id.evenNumberSwitch)
        val generateButton: Button = findViewById(R.id.generateButton)

        val generatePasswordButton: Button = findViewById(R.id.generatePasswordButton)
        generatePasswordButton.setOnClickListener {
            val intent = Intent(this, PasswordGeneratorActivity::class.java)
            startActivity(intent)
        }

        oddNumberSwitch.setOnClickListener{
            if (evenNumberSwitch.isChecked){
                evenNumberSwitch.isChecked=false
            }
        }

        evenNumberSwitch.setOnClickListener{
            if (oddNumberSwitch.isChecked){
                oddNumberSwitch.isChecked=false
            }
        }

        generateButton.setOnClickListener {
            val min = minNumberInput.text.toString().toIntOrNull() ?: 0
            val max = maxNumberInput.text.toString().toIntOrNull() ?: 0

            if (min >= max) {
                randomNumberText.text = "Error"
                return@setOnClickListener
            }

            var randomNumber: Int

            do {
                randomNumber = Random.nextInt(min, max + 1)
            } while (
                (oddNumberSwitch.isChecked && randomNumber % 2 == 0) ||
                (evenNumberSwitch.isChecked && randomNumber % 2 != 0)
            )
            randomNumberText.text = randomNumber.toString()
        }
    }
}
