package com.example.studysynchub.Login_Reg

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Patterns
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.studysynchub.R
import com.example.studysynchub.databinding.ActivityRegistrationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRegistrationBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private var UID = FirebaseAuth.getInstance().uid.toString()
    private val databaseRef = FirebaseDatabase.getInstance().getReference("StudySyncHub")
    private lateinit var name: String
    private lateinit var number : String
    private lateinit var email : String
    private lateinit var pass : String
    private lateinit var pass2 : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        initval()
        binding.btnReg.setOnClickListener {
            initval()
            val allChecked = CheckAllFields()

            if (allChecked){
                if (email.isNotEmpty() && pass.isNotEmpty() && name.isNotEmpty() && number.isNotEmpty()) {
                    firebaseAuth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(this){ task ->
                            if (task.isSuccessful) {
                                Toast.makeText(this, "SignUp Successful", Toast.LENGTH_SHORT).show()
                                //uploadImg()
                                UID = firebaseAuth.currentUser?.uid.toString()
                                Handler().postDelayed({
                                    val model = UserLoginModel(name,number,email,pass,UID)
                                    databaseRef.child("UserInfo").child(UID).setValue(model)
                                    val iLogin = Intent(this, LoginActivity::class.java)
                                    startActivity(iLogin)
                                    finish()
                                }, 2000)
                            } else {
                                Toast.makeText(this, "SignUp Unsuccessful", Toast.LENGTH_SHORT).show()
                            }
                        }
                } else {
                    Toast.makeText(this, "Please Provide Email and Password", Toast.LENGTH_SHORT).show()
                }
            }
            else
            {
                Toast.makeText(this, "Please Provide info", Toast.LENGTH_SHORT).show()
            }
        }

        binding.txtSignUp.setOnClickListener {
            val iLogin = Intent(this, LoginActivity::class.java)
            startActivity(iLogin)
            finish()
        }
    }

    private fun initval() {
        name = binding.regName.text.toString()
        email = binding.regEmail.text.toString()
        pass = binding.regPass.text.toString()
        pass2 = binding.regPass2.text.toString()
        number = binding.regNumber.text.toString()
    }
    private fun CheckAllFields(): Boolean {
        /*if ( ){
            return false
        }*/
        if (name.length == 0) {
            binding.regName.error = "Name is required"
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.regEmail.error = "Email is required"
            return false
        }
        else if (pass.length < 6) {
            binding.regPass.error = "Password must be minimum 6 characters"
            return false
        }
        else if (pass != pass2) {
            binding.regPass2.error = "Password must be matched"
            return false
        }

        // after all validation return true.
        return true
    }
}

