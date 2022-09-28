package com.example.mobiledevassignment1mark2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.text.NumberFormat


class MainActivity2 : AppCompatActivity() {

    private lateinit var totalPriceTextView: TextView
    private lateinit var deliveryInstructionsTextView: TextView
    private lateinit var returnButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val deliveryInstructions = intent.getStringExtra("INSTRUCTIONS")
        val totalPrice = intent.getIntExtra("TOTAL_PRICE", 0)

        totalPriceTextView = findViewById(R.id.totalPriceText)
        deliveryInstructionsTextView = findViewById(R.id.specialInstructionsText)
        returnButton = findViewById(R.id.returnButton)

        totalPriceTextView.text = NumberFormat.getCurrencyInstance().format(totalPrice)

        deliveryInstructionsTextView. text = deliveryInstructions

        returnButton.setOnClickListener{
            val intent2 = Intent(this, MainActivity::class.java)
            startActivity(intent2)
        }


    }
}