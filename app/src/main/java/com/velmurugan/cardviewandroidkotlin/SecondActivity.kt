package com.velmurugan.cardviewandroidkotlin

import CountdownTimer
import TimePickerFragment
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipemastermind.R
import com.google.android.material.floatingactionbutton.FloatingActionButton


class SecondActivity : AppCompatActivity() {
    private lateinit var recipeNameTextView: TextView
    private lateinit var recipeImageView: ImageView
    private lateinit var ingredientsRecyclerView: RecyclerView
    private lateinit var stepsRecyclerView: RecyclerView
    private lateinit var countdownTimer: CountdownTimer
    private lateinit var textViewCountdown: TextView
    private lateinit var buttonStartPause: Button
    private lateinit var buttonStop: Button
    private lateinit var customTime: String // Add this variable to store the custom time
    private lateinit var context: Context // Add this variable to store the context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recipe)

        val recipeName = intent.getStringExtra("recipeName")
        val imageURL = intent.getStringExtra("imageURL")
        val ingredientsArray = intent.getStringArrayExtra("ingredients")
        val ingredientsList = ingredientsArray?.toList() ?: emptyList<String>()
        val stepsArray = intent.getStringArrayExtra("steps")
        val stepsList = stepsArray?.toList() ?: emptyList<String>()

        context = this // Store the context of the activity

        recipeNameTextView = findViewById(R.id.text_recipe_name)
        recipeNameTextView.text = recipeName
        recipeImageView = findViewById(R.id.image_recipe)
        ingredientsRecyclerView = findViewById(R.id.recyclerview_ingredients)
        stepsRecyclerView = findViewById(R.id.recyclerview_steps)
        textViewCountdown = findViewById(R.id.text_countdown)
        buttonStartPause = findViewById(R.id.button_start_pause)
        buttonStop = findViewById(R.id.button_stop)

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

        //Countdown logic
        countdownTimer = CountdownTimer(0, 1000, context) // Pass the context parameter

        buttonStartPause.setOnClickListener {
            if (countdownTimer.isRunning()) {
                countdownTimer.pause()
                buttonStartPause.text = "Start"
            } else {
                val selectedTime = textViewCountdown.text.toString()
                val durationMillis = parseTimeToMillis(selectedTime)

                // Set the time remaining to the selected duration
                countdownTimer.timeRemainingMillis = durationMillis

                countdownTimer.start(textViewCountdown, buttonStartPause, buttonStop)
            }
        }

        //FAB button logic
        val fabShare: FloatingActionButton = findViewById(R.id.fab_send)
        fabShare.setOnClickListener {
            val ingredientsText = ingredientsList.joinToString(separator = "\n")
            val sendIntent = Intent(Intent.ACTION_SEND)
            sendIntent.type = "text/plain"
            sendIntent.putExtra(Intent.EXTRA_TEXT, ingredientsText)

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

        buttonStop.setOnClickListener {
            countdownTimer.stop(textViewCountdown, buttonStartPause, buttonStop)
        }
    }

    private fun parseTimeToMillis(time: String): Long {
        val parts = time.split(":")
        val hours = parts[0].toLong()
        val minutes = parts[1].toLong()
        val seconds = parts[2].toLong()
        return  (hours * 3600 + minutes * 60 + seconds) * 1000
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
    fun showTimePickerDialog(view: View) {
        val timePicker = TimePickerFragment()
        timePicker.show(supportFragmentManager, "timePicker")
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
