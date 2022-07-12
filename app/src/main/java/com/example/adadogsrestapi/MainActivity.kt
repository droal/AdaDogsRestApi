package com.example.adadogsrestapi

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import com.example.adadogsrestapi.network.DogsImageService
import com.example.adadogsrestapi.network.RetrofitGenerator
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val image = findViewById<ImageView>(R.id.imageView)
        val imageService = RetrofitGenerator.getrandomImageService()
        loadNewImage(image, imageService)

        findViewById<Button>(R.id.button).setOnClickListener {
            loadNewImage(image, imageService)
        }
    }

    private fun loadNewImage(image: ImageView, imageService: DogsImageService){
        GlobalScope.launch(Dispatchers.IO) {
            val response = imageService.getRandomDogImage()
            if (response.isSuccessful) {
                val randomDogImageDto = response.body()
                runOnUiThread {
                    Picasso.get().load(randomDogImageDto?.message).into(image)
                }

            }
        }
    }
}