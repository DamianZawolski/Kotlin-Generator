package com.example.generator

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random
import android.content.Intent

class PasswordGeneratorActivity : AppCompatActivity() {

    private lateinit var passwordTextView: TextView
    private lateinit var passwordLengthEditText: EditText
    private lateinit var uppercaseSwitch: Switch
    private lateinit var lowercaseSwitch: Switch
    private lateinit var numbersSwitch: Switch
    private lateinit var specialSwitch: Switch
    private lateinit var generateButton: Button

    private var passwordLength = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_generator)

        passwordTextView = findViewById(R.id.passwordTextView)
        passwordLengthEditText = findViewById(R.id.passwordLengthEditText)
        uppercaseSwitch = findViewById(R.id.uppercaseSwitch)
        lowercaseSwitch = findViewById(R.id.lowercaseSwitch)
        numbersSwitch = findViewById(R.id.numbersSwitch)
        specialSwitch = findViewById(R.id.specialSwitch)
        generateButton = findViewById(R.id.generateButton)

        // Enable all switches by default
        uppercaseSwitch.isChecked = true
        lowercaseSwitch.isChecked = true
        numbersSwitch.isChecked = true
        specialSwitch.isChecked = true

        val generatePasswordButton: Button = findViewById(R.id.generateNumberButton)
        generatePasswordButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // Prevent all switches from being turned off
        setupSwitchListener(uppercaseSwitch)
        setupSwitchListener(lowercaseSwitch)
        setupSwitchListener(numbersSwitch)
        setupSwitchListener(specialSwitch)

        generateButton.setOnClickListener {
            val lengthText = passwordLengthEditText.text.toString()
            passwordLength = if (lengthText.isNotEmpty()) lengthText.toInt() else 10

            if (passwordLength <= 0) {
                Toast.makeText(this, "Please enter a valid length", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            generatePassword()
        }
    }

    private fun setupSwitchListener(switch: Switch) {
        switch.setOnCheckedChangeListener { _, isChecked ->
            if (!isChecked && !anySwitchChecked()) {
                switch.isChecked = true // Re-enable the switch if it was the last one checked
                Toast.makeText(this, "At least one option must be enabled", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun anySwitchChecked(): Boolean {
        return uppercaseSwitch.isChecked || lowercaseSwitch.isChecked || numbersSwitch.isChecked || specialSwitch.isChecked
    }

    private fun generatePassword() {
        val uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val lowercase = "abcdefghijklmnopqrstuvwxyz"
        val numbers = "0123456789"
        val special = "!@#\$%^&*()"

        var chars = ""
        if (uppercaseSwitch.isChecked) chars += uppercase
        if (lowercaseSwitch.isChecked) chars += lowercase
        if (numbersSwitch.isChecked) chars += numbers
        if (specialSwitch.isChecked) chars += special

        if (chars.isEmpty()) {
            Toast.makeText(this, "Please enable at least one option", Toast.LENGTH_SHORT).show()
            return
        }

        val password = (1..passwordLength)
            .map { chars.random() }
            .joinToString("")

        passwordTextView.text = password
    }
}
