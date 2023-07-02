package com.example.oh_sheet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class dataCategory(val name: String, val totalTimeSpent: Long)

//Create a ViewHolder class
class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val categoryName: TextView = itemView.findViewById(R.id.category_name)
    val totalTimeSpent: TextView = itemView.findViewById(R.id.total_time)
}

//Create an adapter class
class CategoryAdapter(private val categories: List<dataCategory>) : RecyclerView.Adapter<CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_custom_category_list_view, parent, false)
        return CategoryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.categoryName.text = category.name
        holder.totalTimeSpent.text = "Total Time Spent: ${category.totalTimeSpent} hours"

    }

    override fun getItemCount(): Int {
        return categories.size
    }
}