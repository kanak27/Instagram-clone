package com.example.instagram_clone

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.marginLeft
import androidx.core.view.marginTop
import com.parse.FindCallback
import com.parse.GetDataCallback
import com.parse.ParseException
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ParseQuery

class UserFeedActivity : AppCompatActivity() {

    private lateinit var linLayout : LinearLayout

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_feed)
        setSupportActionBar(findViewById(R.id.titleBar))
        linLayout = findViewById(R.id.linLayout)

        val intent = intent
        val username = intent.getStringExtra("username")

        title = "$username's Photos"

        val query = ParseQuery<ParseObject>("Image")

        query.whereEqualTo("username", username)
        query.orderByDescending("createdAt")

        query.findInBackground { objects, e ->
            if (e == null) {
                if (objects != null) {
                    for (obj: ParseObject in objects) {
                        val file: ParseFile = obj.get("image") as ParseFile

                        file.getDataInBackground(object : GetDataCallback {
                            override fun done(data: ByteArray?, e: ParseException?) {
                                if (e == null && data != null) run {
                                    val bitmap: Bitmap =
                                        BitmapFactory.decodeByteArray(data, 0, data.size)

                                    val imageView = ImageView(applicationContext)

                                    imageView.layoutParams= MarginLayoutParams(
                                        ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ).apply {
                                        setMargins(5, 10, 5, 10)
                                    }

                                    imageView.setImageBitmap(bitmap)
                                    linLayout.addView(imageView)
                                }
                            }

                        })
                    }

                }
            }
        }


    }
}