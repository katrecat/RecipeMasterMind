package com.example.recipemastermind

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.recipemastermind.RecyclerViewAdapter.MyViewHolder
import com.bumptech.glide.Glide


class RecyclerViewAdapter constructor(private val recipeList: List<Recipe>) :
    RecyclerView.Adapter<MyViewHolder>() {
    private var clickListener: ClickListener<Recipe>? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_adapter_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val recipe = recipeList[position]
        holder.title.text = recipe.title
        Glide.with(holder.itemView.context)
            .load(recipe.image)
            .centerCrop()
            .override(holder.itemView.width, holder.itemView.height)
            .into(holder.image)

        holder.cardView.setOnClickListener {
            clickListener?.onItemClick(recipe, holder.itemView)
        }
        holder.image.setOnClickListener {
            clickListener?.onItemClick(recipe, it)
        }
    }

    override fun getItemCount(): Int {
        return recipeList.size
    }

    fun setOnItemClickListener(recipeClickListener: ClickListener<Recipe>?) {
        clickListener = recipeClickListener
    }

    inner class MyViewHolder(itemView: View) : ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val image: ImageView = itemView.findViewById(R.id.image)
        val cardView: CardView = itemView.findViewById(R.id.cardView)

    }
}

interface ClickListener<T> {
    fun onItemClick(data: T, view: View)
}
