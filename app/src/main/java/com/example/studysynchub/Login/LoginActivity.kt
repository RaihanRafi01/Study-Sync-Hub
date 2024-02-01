package com.example.studysynchub.Login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.studysynchub.MainActivity
import com.example.studysynchub.R
import com.example.studysynchub.databinding.ActivityLoginBinding
import com.example.studysynchub.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener(View.OnClickListener {
            val ihome = Intent(this@LoginActivity, MainActivity::class.java)

            val editor = getSharedPreferences("login", MODE_PRIVATE).edit()
            // login Condition

            val edtEmail = binding.edtEmail.text.toString()
            val edtPassword =  binding.edtPass.text.toString()
            if(edtEmail.isNotEmpty() && edtPassword.isNotEmpty()){
                firebaseAuth.signInWithEmailAndPassword(edtEmail,edtPassword).addOnCompleteListener(this){ task ->
                    if (task.isSuccessful){
                        Toast.makeText(this@LoginActivity,"Please hold for a while", Toast.LENGTH_SHORT).show()
                        editor.putBoolean("loginFlag", true).apply()
                        startActivity(ihome)
                        finish()
                    }else{
                        Toast.makeText(this@LoginActivity,"Wrong Name and Password", Toast.LENGTH_SHORT).show()
                    }

                }
            }else{
                Toast.makeText(this@LoginActivity,"Please Provide Name and Password", Toast.LENGTH_SHORT).show()
            }
            /*if (edtEmail.equals("admin")&&edtPassword.equals("123")){

            }else{
                Toast.makeText(this@LoginActivity,"Wrong Name and Password",Toast.LENGTH_LONG).show()
            }*/
            //Log.e("value","Name : "+edtName.text +" , Pass : "+edtPassword.text)

        })
    }
}