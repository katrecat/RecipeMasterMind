package com.example.recipemastermind

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.velmurugan.cardviewandroidkotlin.ClickListener
import com.velmurugan.cardviewandroidkotlin.RecyclerViewAdapter
import com.velmurugan.cardviewandroidkotlin.SecondActivity


class MainActivity : AppCompatActivity() {
    private var recyclerView: RecyclerView? = null
    private var recyclerViewAdapter: RecyclerViewAdapter? = null
    private var movieList = mutableListOf<Movie>()
    private lateinit var imageView: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageView = findViewById(R.id.imageView)
        movieList = ArrayList()
        recyclerView = findViewById<View>(R.id.recyclerView) as RecyclerView
        recyclerViewAdapter = RecyclerViewAdapter(movieList)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerView!!.layoutManager = layoutManager
        recyclerViewAdapter!!.setOnItemClickListener(object : ClickListener<Movie> {

            override fun onItemClick(data: Movie, view: View) {
                if (view.id == R.id.image) {
                    val intent = Intent(this@MainActivity, SecondActivity::class.java)
                    intent.putExtra("recipeName", data.title)
                    intent.putExtra("imageURL", data.image)
                    intent.putExtra("ingredients", data.ingredients.map { it.name}.toTypedArray())
                    intent.putExtra("steps", data.steps.map { it.step }.toTypedArray())
                    startActivity(intent)
                } else {
                    Toast.makeText(this@MainActivity, data.title, Toast.LENGTH_SHORT).show()
                }
            }

        })

        val firebase : DatabaseReference = FirebaseDatabase.getInstance().getReference()

        recyclerView!!.adapter = recyclerViewAdapter
        startAnimation()
        prepareMovie()
    }
    private fun startAnimation() {
        // Set the visibility of the ImageView to VISIBLE
        imageView.visibility = View.VISIBLE

        // Create an ObjectAnimator to animate the rotation property
        val animator = ObjectAnimator.ofFloat(imageView, "rotation", 0f, 720f)

        // Set the duration of the animation (in milliseconds)
        animator.duration = 2000

        // Set the interpolator for linear animation
        animator.interpolator = LinearInterpolator()

        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                // Set the visibility of the ImageView to INVISIBLE
                imageView.visibility = View.GONE
            }
        })
        // Start the animation
        animator.start()
    }
    private fun prepareMovie() {
        val database = Firebase.database.reference
        val recipeRef = database.child("recipe").child("-NTxXR-kLLPC_Blr6iD-")
        recipeRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (recipeIdSnapshot in dataSnapshot.children) {
                    var number_ingredients = 0
                    var number_steps = 0
                    val recipeName = recipeIdSnapshot.child("name").value.toString()
                    val imageURL = recipeIdSnapshot.child("imageURL").value.toString()
                    val ingredientsList = mutableListOf<Ingredient>()
                    val stepsList = mutableListOf<Steps>()
                    for (ingredientSnapshot in recipeIdSnapshot.child("ingredients").children) {
                        var ingredientName = ingredientSnapshot.child("name").value.toString()
                        number_ingredients++
                        val ingredientQuantity = ingredientSnapshot.child("quantity").value.toString()
                        ingredientName = "$number_ingredients) " + ingredientName + " [" + ingredientQuantity +"]"
                        val ingredient = Ingredient(ingredientName, ingredientQuantity)
                        ingredientsList.add(ingredient)
                    }

                    for (stepSnapshot in recipeIdSnapshot.child("steps").children) {
                        val stepName = stepSnapshot.value.toString()
                        number_steps++
                        val step = Steps("$number_steps) $stepName")
                        stepsList.add(step)
                    }

                    val movie = Movie(recipeName, imageURL, ingredientsList, stepsList)
                    movieList.add(movie)
                }
                recyclerViewAdapter?.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("Firebase", "Error reading data from Firebase", databaseError.toException())
            }
        })
    }

}

