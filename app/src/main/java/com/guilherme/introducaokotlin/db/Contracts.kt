package com.guilherme.introducaokotlin.db

import android.provider.BaseColumns

/**
 * Created by Guilherme on 15/01/2019.
 */

val DATABASE_NAME = "habittrainer.db"
val DATABASE_VERSION = 10

object HabitEntry : BaseColumns {
    val TABLE_NAME = "habit"
    val _ID = "id"
    val TITLE_COL = "title"
    val DESCR_COL = "description"
    val IMAGE_COL = "image"
}