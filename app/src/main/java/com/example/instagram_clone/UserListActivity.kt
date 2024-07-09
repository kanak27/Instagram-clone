package com.example.instagram_clone

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.parse.ParseException
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser
import com.parse.SaveCallback
import java.io.ByteArrayOutputStream

class UserListActivity : AppCompatActivity() {

    private fun getPhoto(){
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, 1)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == 1){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getPhoto()
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater : MenuInflater = menuInflater
        menuInflater.inflate(R.menu.share_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.share){
            if(checkSelfPermission(android.Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(arrayOf(android.Manifest.permission.READ_MEDIA_IMAGES), 1)
            } else{
                getPhoto()
            }
        } else if(item.itemId == R.id.logout){
            ParseUser.logOut()

            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)
        setSupportActionBar(findViewById(R.id.menuBar))
        title = "User List"

        val userList : ListView = findViewById(R.id.userList)
        val usernames = ArrayList<String>()
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, usernames)

        userList.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val intent = Intent(applicationContext, UserFeedActivity::class.java)
                intent.putExtra("username", usernames[position])
                startActivity(intent)
            }

        val query : ParseQuery<ParseUser> = ParseUser.getQuery()

        query.whereNotEqualTo("username", ParseUser.getCurrentUser().username)
        query.addAscendingOrder("username")
        
        query.findInBackground { objects, e ->
            if (e == null) {
                if (objects != null) {
                    if (objects.size > 0) {
                        for (user: ParseUser in objects) {
                            usernames.add(user.username)
                        }
                        userList.adapter = arrayAdapter
                    }
                }
            } else {
                e.printStackTrace()
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val selectedImage : Uri? = data?.data

        if(requestCode == 1 && resultCode == RESULT_OK && data != null){
            try {
                val bitmap : Bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, selectedImage)
                Log.i("Image selected", "Good work")

                val stream = ByteArrayOutputStream()

                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                val byteArray : ByteArray = stream.toByteArray()

                val file = ParseFile("image.png", byteArray)

                val Object = ParseObject("Image")
                Object.put("image", file)
                Object.put("username", ParseUser.getCurrentUser().username)
                Object.saveInBackground { e ->
                    if (e == null) {
                        Toast.makeText(
                            this@UserListActivity,
                            "Image has been shared!",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            this@UserListActivity,
                            "There has been an issue uploading the image!",
                            Toast.LENGTH_SHORT
                        ).show()
                        e.printStackTrace()
                    }
                }

            } catch (e : Exception){
                e.printStackTrace()
            }
        }
    }
}