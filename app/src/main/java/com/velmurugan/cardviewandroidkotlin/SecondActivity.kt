package com.velmurugan.cardviewandroidkotlin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipemastermind.R

class SecondActivity : AppCompatActivity() {
    private lateinit var recipeNameTextView: TextView
    private lateinit var recipeImageView: ImageView
    private lateinit var ingredientsRecyclerView: RecyclerView
    private lateinit var stepsRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recipe)

        val recipeName = intent.getStringExtra("recipeName")
        val imageURL = intent.getStringExtra("imageURL")
        val ingredientsArray = intent.getStringArrayExtra("ingredients")
        val ingredientsList = ingredientsArray?.toList() ?: emptyList<String>()
        val stepsArray = intent.getStringArrayExtra("steps")
        val stepsList = stepsArray?.toList() ?: emptyList<String>()

        recipeNameTextView = findViewById(R.id.text_recipe_name)
        recipeNameTextView.text = recipeName
        recipeImageView = findViewById(R.id.image_recipe)
        ingredientsRecyclerView = findViewById(R.id.recyclerview_ingredients)
        stepsRecyclerView = findViewById(R.id.recyclerview_steps)

        Glide.with(this)
            .load(imageURL)
            .centerCrop()
            .into(recipeImageView)

        ingredientsRecyclerView.layoutManager = LinearLayoutManager(this)
        stepsRecyclerView.layoutManager = LinearLayoutManager(this)

        val ingredientsAdapter = IngredientsAdapter(ingredientsList)
        ingredientsRecyclerView.adapter = ingredientsAdapter

        val stepsAdapter = StepsAdapter(stepsList)
        stepsRecyclerView.adapter = stepsAdapter
    }

    private inner class IngredientsAdapter(private val ingredientsList: List<String>) :
        RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(android.R.layout.simple_list_item_1, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val ingredient = ingredientsList[position]
            holder.textView.text = ingredient
        }

        override fun getItemCount(): Int {
            return ingredientsList.size
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val textView: TextView = itemView.findViewById(android.R.id.text1)
        }
    }

    private inner class StepsAdapter(private val stepsList: List<String>) :
        RecyclerView.Adapter<StepsAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(android.R.layout.simple_list_item_1, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val step = stepsList[position]
            holder.textView.text = step
        }

        override fun getItemCount(): Int {
            return stepsList.size
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val textView: TextView = itemView.findViewById(android.R.id.text1)
        }
    }
}
