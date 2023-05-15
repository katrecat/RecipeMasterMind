package com.example.recipemastermind

data class Recipe(var title: String, var image: String, var ingredients: List<Ingredient> = emptyList(), var steps: MutableList<Steps>)
data class Ingredient(
    var name: String = "",
    var quantity: String = ""
)
data class Steps(
    var step: String = ""
)

data class Categories(
    var category_name: String,
    var imageURL: String,
)