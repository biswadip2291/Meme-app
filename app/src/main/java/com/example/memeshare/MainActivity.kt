package com.example.memeshare

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    var current: String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadMeme()
    }
private fun loadMeme(){
    progress.visibility=View.VISIBLE
    // Instantiate the RequestQueue.

    val url = "https://meme-api.herokuapp.com/gimme"

// Request a string response from the provided URL.
    val jsonObjectRequest = JsonObjectRequest(
        Request.Method.GET, url,null,
        Response.Listener { response ->
            current=response.getString("url")
            Glide.with(this).load(current).listener(object:RequestListener<Drawable>{



                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    progress.visibility=View.GONE
                    return false
                }

                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                    progress.visibility=View.GONE
                    return false
                }
            }).into(MemeImage)

        },
        Response.ErrorListener {
Toast.makeText(this,"something went wrong",Toast.LENGTH_LONG).show()
           progress.visibility=View.GONE
        })

// Add the request to the RequestQueue.
MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
}
    fun shareMeme(view: View) {
val y=Intent(Intent.ACTION_SEND)
        y.type="text/plain"
        y.putExtra(Intent.EXTRA_TEXT,"hey checkout this meme!!$current")
        val choose=Intent.createChooser(y,"share this meme using..")
       startActivity(choose)
    }
    fun nextMeme(view: View) {
loadMeme()
    }
}