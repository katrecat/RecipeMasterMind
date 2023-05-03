package com.example.recipemastermind

//data class Movie(var title: String, var image: Int, var rating: Float)
data class Movie(var title: String, var image: String, var ingredients: List<Ingredient> = emptyList(), var steps: MutableList<Steps>)
data class Ingredient(
    var name: String = "",
    var quantity: String = ""
)
data class Steps(
    var step: String = ""
)