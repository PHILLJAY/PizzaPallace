package com.example.mobiledevassignment1mark2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    //Initializing buttons

    private lateinit var ingredientChips: ChipGroup
    private lateinit var sizeSpinner: Spinner
    private lateinit var cheeseBox: CheckBox
    private lateinit var deliveryBox: CheckBox
    private lateinit var finalPriceTextView: TextView
    private lateinit var checkoutButton: Button
    private lateinit var specialInstructions: EditText


    // Resources
    private lateinit var priceArray: IntArray


    //vars for toppings
    private var toppingSelected = 0
    private var totalPrice = 0
    private var sizePrice = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //Setting vars
        priceArray = resources.getIntArray(R.array.size_prices)
        ingredientChips = findViewById(R.id.chip_group_toppings)
        sizeSpinner = findViewById(R.id.sizeSpnr)
        deliveryBox = findViewById(R.id.deliveryCheckbox)
        cheeseBox = findViewById(R.id.extraCheeseCheckbox)
        finalPriceTextView = findViewById(R.id.finalPrice)
        checkoutButton = findViewById(R.id.checkOutButton)

        sizeSpinner.onItemSelectedListener = this

        val toppingArray = resources.getStringArray(R.array.array_toppings)

        //create dropdown menu based on pizza size

        ArrayAdapter.createFromResource(
            this, R.array.array_sizes, android.R.layout.simple_spinner_item).also {
                arrayAdapter ->
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            sizeSpinner.adapter = arrayAdapter
        }

        //create chip group based on toppings
        for (topping in toppingArray) {
            val newChip = Chip(this)
            newChip.text = topping
            newChip.isCheckable = true
            newChip.setOnClickListener {
                if ((it as Chip).isChecked) {
                    toppingSelected++
                } else {
                    toppingSelected--
                }
                updatePrice()
            }
            ingredientChips.addView(newChip)
        }

        deliveryBox.setOnClickListener{
            updatePrice()
        }

        cheeseBox.setOnClickListener{
            updatePrice()
        }

        //On Button Click updates price and sends it over through intents
        checkoutButton.setOnClickListener{
            updatePrice()
            val editText = findViewById<EditText>(R.id.specialInstructionsEdit)
            val instructions =  editText.text.toString()
            //bind the instructions string and the price int to next activity
            val intent = Intent(this, MainActivity2::class.java).apply {
                putExtra("INSTRUCTIONS", instructions)
                putExtra( "TOTAL_PRICE", totalPrice)
            }
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        updatePrice()
    }

    private fun updatePrice() {
        totalPrice = 0
        if (cheeseBox.isChecked){
            totalPrice += 3
        }
        if (deliveryBox.isChecked){
            totalPrice += 5
        }
        totalPrice +=(toppingSelected+sizePrice)
        finalPriceTextView.text = ("Your total is: $$totalPrice")
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
        sizePrice = priceArray[pos]
        updatePrice()
    }

    override fun onNothingSelected(p0: AdapterView<*>) {
        sizePrice = 5
        updatePrice()
    }
}