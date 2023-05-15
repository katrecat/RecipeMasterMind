package com.example.recipemastermind

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.velmurugan.cardviewandroidkotlin.ClickListener
import com.example.recipemastermind.Categories
import com.example.recipemastermind.R

class CategoriesRecyclerViewAdapter(private val categoryList: List<Categories>) :
    RecyclerView.Adapter<CategoriesRecyclerViewAdapter.MyViewHolder>() {
    private var clickListener: ClickListener<Categories>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_adapter_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val category = categoryList[position]
        holder.categoryName.text = category.category_name
        Glide.with(holder.itemView.context)
            .load(category.imageURL)
            .centerCrop()
            .override(holder.itemView.width, holder.itemView.height)
            .into(holder.categoryImage)

        holder.cardView.setOnClickListener {
            clickListener?.onItemClick(category, holder.itemView)
        }
        holder.categoryImage.setOnClickListener {
            clickListener?.onItemClick(category, it)
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    fun setOnItemClickListener(categoryClickListener: ClickListener<Categories>?) {
        clickListener = categoryClickListener
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryName: TextView = itemView.findViewById(R.id.title)
        val categoryImage: ImageView = itemView.findViewById(R.id.image)
        val cardView: CardView = itemView.findViewById(R.id.cardView)
    }
}
