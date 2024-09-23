package com.cogniwire.outstandingreceivables.ui


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cogniwire.outstandingreceivables.db.entities.ReceivablesRecord
import com.cogniwire.outstandingreceivables.R
import androidx.recyclerview.widget.*

class ReceivablesAdapter(val listener: OnClickListener) :
    ListAdapter<ReceivablesRecord, ReceivablesAdapter.PromissoryNoteViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ReceivablesRecord>() {                           //DIFF_CALLBACK and DiffUtil.ItemCallback are
            override fun areItemsTheSame(oldItem: ReceivablesRecord, newItem: ReceivablesRecord): Boolean {         //used for item comparison of lists
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ReceivablesRecord, newItem: ReceivablesRecord): Boolean {      //compare the promissory notes records
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PromissoryNoteViewHolder {
        return PromissoryNoteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.promissory_note_item, parent, false)   //Create the view holder holding the individual records
        )
    }

    override fun onBindViewHolder(holder: PromissoryNoteViewHolder, position: Int) {
        val promissoryNote = getItem(position)                                                                      //link to DB records
        holder.textViewTitle.text = promissoryNote.borrower
        holder.textViewCCY.text = promissoryNote.currency
        holder.textViewAmt.text = promissoryNote.amount
        holder.textViewDescription.text = promissoryNote.reason
        holder.textViewDate.text = promissoryNote.date
    }

    fun getPromissoryNoteAt(position: Int): ReceivablesRecord {
        return getItem(position)
    }

    inner class PromissoryNoteViewHolder(view: View) : RecyclerView.ViewHolder(view) {              //ViewHolder reference setup
        val textViewTitle = view.findViewById(R.id.text_view_borrower) as TextView
        val textViewCCY = view.findViewById(R.id.text_view_currency) as TextView
        val textViewAmt = view.findViewById(R.id.text_view_amount) as TextView
        val textViewDescription = view.findViewById(R.id.text_view_reason) as TextView
        val textViewDate = view.findViewById(R.id.text_view_date) as TextView

        init {
            view.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    listener.onClick(getItem(adapterPosition))
                }
            }
        }
    }

    interface OnClickListener {
        fun onClick(promissoryNote: ReceivablesRecord)
    }
}