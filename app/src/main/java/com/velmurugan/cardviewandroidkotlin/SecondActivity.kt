package com.velmurugan.cardviewandroidkotlin

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.recipemastermind.R

class SecondActivity : AppCompatActivity() {
    private lateinit var recipeNameTextView: TextView
    private lateinit var recipeImageView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recipe)

        val recipeName = intent.getStringExtra("recipeName")
        val imageURL = intent.getStringExtra("imageURL")



        recipeNameTextView = findViewById(R.id.text_recipe_name)
        recipeNameTextView.text = recipeName

        recipeImageView = findViewById(R.id.image_recipe)

        Glide.with(this)
            .load(imageURL)
            .centerCrop()
            .into(recipeImageView)
    }
}