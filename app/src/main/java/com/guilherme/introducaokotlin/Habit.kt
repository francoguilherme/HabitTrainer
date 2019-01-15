package com.guilherme.introducaokotlin

import android.graphics.Bitmap


data class Habit (val title: String, val description: String, val image: Bitmap)

//fun getSampleHabits(): List<Habit>{
//    return listOf(
//            Habit("Go for a walk",
//                    "Walking on sunshine",
//                    R.drawable.walk),
//
//            Habit("Drink water",
//            "Hydrate pls",
//            R.drawable.water)
//    )
//}