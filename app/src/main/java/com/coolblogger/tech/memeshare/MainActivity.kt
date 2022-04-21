package com.coolblogger.tech.memeshare

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MainActivity : AppCompatActivity() {

var currentImageUrl: String ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadMeme()
    }
    private fun loadMeme(){
        val progressBar: ProgressBar = findViewById(R.id.progressBar)
        progressBar.setVisibility(View.VISIBLE)


        val url = "https://meme-api.herokuapp.com/gimme"

// Request a string response from the provided URL.
        val JsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url,null,
            { response ->
                // Display the first 500 characters of the response string.
                currentImageUrl= response.getString("url")
                val imageView: ImageView = findViewById(R.id.MemeImageView)
                Glide.with(this).load(currentImageUrl).listener(object:RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.setVisibility(View.GONE)
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.setVisibility(View.GONE)
                        return false
                    }
                }).into(imageView)
            }, { error ->
                // TODO: Handle error
            })
      MySingleton.getInstance(this).addToRequestQueue(JsonObjectRequest )
    }
    fun memeshare(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
       intent.type = "Text/plain"
        
        intent.putExtra(Intent.EXTRA_TEXT,"Check out these coll memes  $currentImageUrl ")
        val chosser = Intent(Intent.createChooser(intent, "Share it using ..."))
        startActivity(chosser)
    }
    fun nextmeme(view: View) {
        loadMeme()
    }
    fun prevmeme(view: View) {
        loadMeme()
    }
}
