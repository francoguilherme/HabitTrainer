package com.guilherme.introducaokotlin

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.EditText
import com.guilherme.introducaokotlin.db.HabitDbTable
import kotlinx.android.synthetic.main.activity_create_habit.*
import java.io.IOException

class CreateHabitActivity : AppCompatActivity() {

    private val TAG = CreateHabitActivity::class.java.simpleName

    private val CHOOSE_IMAGE_REQUEST = 1

    private var imageBitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_habit)
    }

    private fun EditText.isBlank() = this.text.toString().isBlank() // Extension function!!!

    fun storeHabit(v: View){
        if (et_title.isBlank() || et_description.isBlank()){
            Log.d(TAG, "Title or description missing")
            displayErrorMessage("Your habit needs a title and description")
            return
        } else if (imageBitmap == null){
            Log.d(TAG, "Image missing")
            displayErrorMessage("Add a picture to your habit")
            return
        }

        error_text.visibility = View.INVISIBLE

        // Store the habit...
        val title = et_title.text.toString()
        val description = et_description.text.toString()
        val habit = Habit(title, description, imageBitmap!!)

        val id = HabitDbTable(this).store(habit)

        if (id == -1L){
            displayErrorMessage("Habit could not be store")
        } else{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun displayErrorMessage(s: String) {
        error_text.text = s
        error_text.visibility = View.VISIBLE
    }

    fun chooseImage(v: View){
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        val chooser = Intent.createChooser(intent, "Choose Image")
        startActivityForResult(chooser, CHOOSE_IMAGE_REQUEST)

        Log.d(TAG,"Intent to choose image sent...")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CHOOSE_IMAGE_REQUEST && resultCode == Activity.RESULT_OK
                && data != null && data.data != null){
            Log.d(TAG, "An image was choosen by the suer")
            
            val bitmap = tryReadBitmap(data.data)

            bitmap?.let{
                this.imageBitmap = bitmap
                iv_image.setImageBitmap(bitmap)
                Log.d(TAG, "Read image bitmap and updated image view")
            }
        }
    }

    private fun tryReadBitmap(data: Uri): Bitmap? {
        return try {
            MediaStore.Images.Media.getBitmap(contentResolver, data)
        } catch (e: IOException){
            e.printStackTrace()
            null
        }
    }
}
