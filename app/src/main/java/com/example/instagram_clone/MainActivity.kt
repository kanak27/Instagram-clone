package com.example.instagram_clone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.parse.ParseAnalytics
import com.parse.ParseUser

class MainActivity : AppCompatActivity(), View.OnKeyListener, View.OnClickListener {
    private var user : ParseUser = ParseUser()
    private lateinit var username : EditText
    private lateinit var password : EditText

    private fun showUserList(){
        val intent = Intent(applicationContext, UserListActivity::class.java)
        startActivity(intent)
    }

    fun signUp(view : View){
        if(username.text.toString() == "" || password.text.toString() == ""){
            Toast.makeText(
                applicationContext,
                "A username and Password is required!",
                Toast.LENGTH_SHORT
            ).show()
        }
        else{
            user.username = username.text.toString()
            user.setPassword(password.text.toString())

            user.signUpInBackground { e ->
                if (e == null) {
                    Toast.makeText(applicationContext, "Signed Up", Toast.LENGTH_SHORT).show()
                    showUserList()
                } else {
                    Toast.makeText(
                        applicationContext,
                        e.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    }

    fun login(view: View){
        user.username = username.text.toString()
        user.setPassword(password.text.toString())

        ParseUser.logInInBackground(username.text.toString(), password.text.toString()
        ) { user, e ->
            if (user != null) {
                Toast.makeText(applicationContext, "Logged In", Toast.LENGTH_SHORT).show()
                showUserList()
            } else {
                Toast.makeText(
                    applicationContext,
                    e.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        username = findViewById(R.id.UsernameEditText)
        password = findViewById(R.id.PasswordEditText)

        val logo : ImageView = findViewById(R.id.imageView)
        val backGroundLayout : ConstraintLayout = findViewById(R.id.backGroundLayout)

        logo.setOnClickListener(this)
        backGroundLayout.setOnClickListener(this)

        password.setOnKeyListener(this)

        if(ParseUser.getCurrentUser() != null){
            showUserList()
        }

        ParseAnalytics.trackAppOpenedInBackground(intent)
    }

    override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
        if (event != null) {
            if(keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN){
                if (v != null ) {
                    login(v)
                }
            }
        }
        return false
    }

    override fun onClick(v: View?) {
        if (v != null) {
            if((v.id == R.id.imageView) || (v.id == R.id.backGroundLayout)){
                val inputMethodManage : InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManage.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
            }
        }
    }
}