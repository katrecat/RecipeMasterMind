package com.example.recipemastermind

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.velmurugan.cardviewandroidkotlin.ClickListener
import com.velmurugan.cardviewandroidkotlin.RecyclerViewAdapter


class MainActivity : AppCompatActivity() {
    private var recyclerView: RecyclerView? = null
    private var recyclerViewAdapter: RecyclerViewAdapter? = null
    private var movieList = mutableListOf<Movie>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        movieList = ArrayList()
        recyclerView = findViewById<View>(R.id.recyclerView) as RecyclerView
        recyclerViewAdapter = RecyclerViewAdapter(movieList)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerView!!.layoutManager = layoutManager
        recyclerViewAdapter!!.setOnItemClickListener(object : ClickListener<Movie> {
            override fun onItemClick(data: Movie) {
                Toast.makeText(this@MainActivity, data.title, Toast.LENGTH_SHORT).show()
            }
        })

        val firebase : DatabaseReference = FirebaseDatabase.getInstance().getReference()

        recyclerView!!.adapter = recyclerViewAdapter
        prepareMovie()
    }
    private fun prepareMovie() {
        val database = Firebase.database.reference
        val recipeRef = database.child("recipe").child("-NTxXR-kLLPC_Blr6iD-")

        recipeRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (recipeIdSnapshot in dataSnapshot.children) {
                    val recipeName = recipeIdSnapshot.child("name").value.toString()
                    val imageURL = recipeIdSnapshot.child("imageURL").value.toString()
                    val movie = Movie(recipeName, imageURL)
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

