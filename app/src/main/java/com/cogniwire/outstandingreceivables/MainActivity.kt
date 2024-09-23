package com.cogniwire.outstandingreceivables

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cogniwire.outstandingreceivables.activities.AddEditActivity
import com.cogniwire.outstandingreceivables.ui.ReceivablesAdapter
import com.cogniwire.outstandingreceivables.db.entities.ReceivablesRecord
import com.cogniwire.outstandingreceivables.ui.RecViewModel
import com.cogniwire.outstandingreceivables.utils.Constants

import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), ReceivablesAdapter.OnClickListener {
    private lateinit var receivablesViewModel: RecViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var receivablesAdapter: ReceivablesAdapter
    private lateinit var addPromissoryNoteButton: FloatingActionButton

    private lateinit var getResult: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addPromissoryNoteButton = findViewById(R.id.add_note_button)

        recyclerView = findViewById(R.id.recycler_view)
        receivablesAdapter = ReceivablesAdapter(this)
        recyclerView.adapter = receivablesAdapter                                                   //connecting the recyclerview to the adapter
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        receivablesViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(application)
        )[RecViewModel::class.java]

        receivablesViewModel.allPromissoryNotes.observe(this) {
            /*
            here we add data to our recycler view
             */
            receivablesAdapter.submitList(it)
        }

        getResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Constants.REQUEST_CODE) {                                      //Checking the result code passed from
                    val title = it.data?.getStringExtra(Constants.INTENT_BORROWER)                      //the logic in AddEditActivity intent (via
                    val currency = it.data?.getStringExtra(Constants.INTENT_CURRENCY)
                    val amount = it.data?.getStringExtra(Constants.INTENT_AMOUNT)
                    val description = it.data?.getStringExtra(Constants.INTENT_REASON)          //addPromissoryNoteButton.setOnClickListener, below)
                    val date = it.data?.getStringExtra(Constants.INTENT_DATE)
                    val promissoryNote = ReceivablesRecord(title!!, currency!!, amount!!, description!!, date!!)

                    receivablesViewModel.addPromissoryNote(promissoryNote)

                } else if (it.resultCode == Constants.EDIT_REQUEST_CODE) {

                    val title = it.data?.getStringExtra(Constants.INTENT_BORROWER)
                    val currency = it.data?.getStringExtra(Constants.INTENT_CURRENCY)
                    val amount = it.data?.getStringExtra(Constants.INTENT_AMOUNT)
                    val description = it.data?.getStringExtra(Constants.INTENT_REASON)
                    val date = it.data?.getStringExtra(Constants.INTENT_DATE)
                    val id = it.data?.getIntExtra(Constants.EXISTS_ID, 0)
                    val promissoryNote = ReceivablesRecord(title!!, currency!!, amount!!, description!!, date!!)
                    promissoryNote.id = id!!

                    receivablesViewModel.updatePromissoryNote(promissoryNote)

                }
            }
        addPromissoryNoteButton.setOnClickListener {
            val intent = Intent(this@MainActivity, AddEditActivity::class.java)
            getResult.launch(intent)
        }

        ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val removedNote = receivablesAdapter.getPromissoryNoteAt(viewHolder.adapterPosition)
                receivablesViewModel.deletePromissoryNote(receivablesAdapter.getPromissoryNoteAt(viewHolder.adapterPosition))

                Snackbar.make(this@MainActivity, recyclerView, "Deleted Promissory Note", Snackbar.LENGTH_LONG)
                    .setAction("UNDO") {
                        receivablesViewModel.addPromissoryNote(removedNote)
                    }.show()
            }

        }).attachToRecyclerView(recyclerView)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete_all_notes -> {
                receivablesViewModel.deleteAllPromissoryNotes()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(promissoryNote: ReceivablesRecord) {
        val title = promissoryNote.borrower
        val currency = promissoryNote.currency
        val amount = promissoryNote.amount
        val description = promissoryNote.reason
        //val priority = promissoryNote.priority
        val date = promissoryNote.date
        val id = promissoryNote.id

        val record = ReceivablesRecord(title, currency, amount, description, date)
        record.id = id

        val intent = Intent(this@MainActivity, AddEditActivity::class.java)
        intent.putExtra(Constants.INTENT_BORROWER, title)
        intent.putExtra(Constants.INTENT_CURRENCY, currency)
        intent.putExtra(Constants.INTENT_AMOUNT, amount)
        intent.putExtra(Constants.INTENT_REASON, description)
        intent.putExtra(Constants.INTENT_DATE, date)
        intent.putExtra(Constants.EXISTS_ID, id)

        getResult.launch(intent)
    }
}