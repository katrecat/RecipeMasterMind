package com.example.recipemastermind

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
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

import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    private var recyclerView: RecyclerView? = null
    private var recyclerViewAdapter: RecyclerViewAdapter? = null
    private var categoryRecyclerViewAdapter: CategoriesRecyclerViewAdapter? = null
    private var recipeList = mutableListOf<Recipe>()
    private var categoryList = mutableListOf<Categories>()
    private lateinit var imageView: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageView = findViewById(R.id.imageView)
        recipeList = ArrayList()
        categoryList = ArrayList()
        recyclerView = findViewById<View>(R.id.recyclerView) as RecyclerView
        recyclerViewAdapter = RecyclerViewAdapter(recipeList)
        categoryRecyclerViewAdapter = CategoriesRecyclerViewAdapter(categoryList)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerView!!.layoutManager = layoutManager

        categoryRecyclerViewAdapter!!.setOnItemClickListener(object : ClickListener<Categories> {
            override fun onItemClick(data: Categories, view: View) {
                if (view.id == R.id.image) {
                    val intent = Intent(this@MainActivity, RecipeActivity::class.java)
                    intent.putExtra("category_name", data.category_name)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@MainActivity, data.category_name, Toast.LENGTH_SHORT).show()
                }
            }
        })


        recyclerView!!.adapter = categoryRecyclerViewAdapter
        startAnimation()
        prepareCategories()
    }
    private fun startAnimation() {
        // Set the visibility of the ImageView to VISIBLE
        imageView.visibility = View.VISIBLE

        // Create an ObjectAnimator to animate the rotation property
        val animator = ObjectAnimator.ofFloat(imageView, "rotation", 0f, 728f)

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

    private fun prepareCategories() {
        val database = Firebase.database.reference
        val categoriesRef = database.child("recipe").child("-NVUd0dYe6D6Sfz1nmss")
        categoriesRef.addListenerForSingleValueEvent(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (categorySnapshot in dataSnapshot.children) {
                        val categoryName = categorySnapshot.child("category_name").value.toString()
                        val image = categorySnapshot.child("imageURL").value.toString()
                        val category = Categories(categoryName, image)
                        categoryList.add(category)
                    }
                    categoryRecyclerViewAdapter?.notifyDataSetChanged()
                } else {
                    // No data found at the specified path
                    Toast.makeText(applicationContext, "No categories found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("Firebase", "Error reading data from Firebase", databaseError.toException())
                // Display error message on screen
                Toast.makeText(applicationContext, "Error reading data from Firebase", Toast.LENGTH_SHORT).show()
            }
        })
    }


}

