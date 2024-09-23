package com.cogniwire.outstandingreceivables.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.cogniwire.outstandingreceivables.R
import com.cogniwire.outstandingreceivables.utils.Constants

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date


class AddEditActivity : AppCompatActivity() {
    private lateinit var editTextBorrower: EditText
    private lateinit var currencySpinner: Spinner
    private lateinit var editTextAmount: EditText
    private lateinit var editTextReasons: EditText
    private lateinit var autoTextDate: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit)

        editTextBorrower = findViewById(R.id.borrower)
        currencySpinner = findViewById(R.id.currency_spinner)
        editTextAmount = findViewById(R.id.amount)
        editTextReasons = findViewById(R.id.reason)
        autoTextDate = findViewById(R.id.date)

        val spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.currencies_array, R.layout.spinner) // Set up the adapter for the Spinner
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        currencySpinner.adapter = spinnerAdapter


        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
        if (intent.hasExtra(Constants.EXISTS_ID)) {                                                 //This is where the logic decides whether the intent operation is running
                                                                                                    //on a new promissory note or an existing one, also setting the field vars
            editTextBorrower.setText(intent.getStringExtra(Constants.INTENT_BORROWER))              //The EXISTS_ID is used for this decision point.
            currencySpinner.setSelection(spinnerAdapter.getPosition(intent.getStringExtra(Constants.INTENT_CURRENCY)))
            editTextAmount.setText(intent.getStringExtra(Constants.INTENT_AMOUNT))
            editTextReasons.setText(intent.getStringExtra(Constants.INTENT_REASON))                 //See last if/else statement block in this class for more details oin this
            autoTextDate.setText(intent.getStringExtra(Constants.INTENT_DATE))                      //assignment

            title = "Edit Promissory Note"
        } else {
            autoTextDate.text = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())

            title = "Add Promissory Note"
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.save_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save_menu_item -> {
                savePromissoryNote()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun savePromissoryNote() {
        val borrower = editTextBorrower.text.toString()
        val currency = currencySpinner.selectedItem.toString()
        val amount = editTextAmount.text.toString()
        val reason = editTextReasons.text.toString()
        val date = autoTextDate.text.toString()

        if (borrower.trim().isEmpty() || reason.trim().isEmpty()) {
            Toast.makeText(this@AddEditActivity, "Please insert borrower and reason(s)", Toast.LENGTH_SHORT).show()
            return
        }

        val id = intent.getIntExtra(Constants.EXISTS_ID, -1)                             //Simple check to see if EXISTS_ID already exists in the intent,
        if (id != -1) {                                                                             //since this intent is used both add and edit.
            setResult(Constants.EDIT_REQUEST_CODE, Intent().apply {                       //Applying the Constant EDIT_REQUEST_CODE to the intent
                putExtra(Constants.INTENT_BORROWER, borrower)
                putExtra(Constants.INTENT_CURRENCY, currency)                                       //to be retrieved via getResult() in MainActivity
                putExtra(Constants.INTENT_AMOUNT, amount)
                putExtra(Constants.INTENT_REASON, reason)
                putExtra(Constants.INTENT_DATE, date)

                putExtra(Constants.EXISTS_ID, id)                                                   //Retain EXIST_ID, unlike below, which is a new promissory note intent
            })
        } else {
            setResult(Constants.REQUEST_CODE, Intent().apply {                            //Similarly, applying the Constant EDIT_REQUEST_CODE to the intent
                putExtra(Constants.INTENT_BORROWER, borrower)
                putExtra(Constants.INTENT_CURRENCY, currency)                                       //to be retrieved via getResult() in MainActivity
                putExtra(Constants.INTENT_AMOUNT, amount)
                putExtra(Constants.INTENT_REASON, reason)
                putExtra(Constants.INTENT_DATE, date)

            })
        }
        finish()

    }
}