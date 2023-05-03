package com.velmurugan.cardviewandroidkotlin

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.recipemastermind.R

class SecondActivity : AppCompatActivity() {
    private lateinit var recipeNameTextView: TextView
    private lateinit var recipeImageView: ImageView
    private lateinit var ingredientsListView: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recipe)

        val recipeName = intent.getStringExtra("recipeName")
        val imageURL = intent.getStringExtra("imageURL")
        val ingredientsArray = intent.getStringArrayExtra("ingredients")
        val ingredientsList = ingredientsArray?.toList() ?: emptyList<String>()

        recipeNameTextView = findViewById(R.id.text_recipe_name)
        recipeNameTextView.text = recipeName
        recipeImageView = findViewById(R.id.image_recipe)
        ingredientsListView = findViewById(R.id.listview_ingredients)

        Glide.with(this)
            .load(imageURL)
            .centerCrop()
            .into(recipeImageView)

        val ingredientsAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, ingredientsList)
        ingredientsListView.adapter = ingredientsAdapter
    }
}