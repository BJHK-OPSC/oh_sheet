package com.example.oh_sheet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TableRowC(val Description: String, val Start: String, val End: String, val Categories: String) {
    // Add properties for other columns as needed
}

class TableAdapter(private var data: ArrayList<TableRowC>) : RecyclerView.Adapter<TableAdapter.ViewHolder>() {

    //------------------------------------------------------------------------------------------------\\
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val column1TextView: TextView = itemView.findViewById(R.id.textDescripiton)
        val column2TextView: TextView = itemView.findViewById(R.id.textStartDate)
        val column3TextView: TextView = itemView.findViewById(R.id.textEndDate)
        val column4TextView: TextView = itemView.findViewById(R.id.textCategories)
        // Add references to other TextViews for additional columns
    }

    //------------------------------------------------------------------------------------------------\\
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.table_row_item, parent, false)
        return ViewHolder(itemView)
    }

    //------------------------------------------------------------------------------------------------\\
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentRow = data[position]
        holder.column1TextView.text = currentRow.Description
        holder.column2TextView.text = currentRow.Start
        holder.column3TextView.text = currentRow.End
        holder.column4TextView.text = currentRow.Categories
    }

    //------------------------------------------------------------------------------------------------\\
    override fun getItemCount(): Int {
        return data.size
    }

}
//------------------------------------------End of File------------------------------------------------------\\