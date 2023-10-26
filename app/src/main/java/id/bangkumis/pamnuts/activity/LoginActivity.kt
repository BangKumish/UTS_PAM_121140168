package id.bangkumis.pamnuts.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import id.bangkumis.pamnuts.databinding.ActivityLoginBinding
import id.bangkumis.pamnuts.ui.home.Data

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseDatabase = FirebaseDatabase.getInstance("https://pam-nuts-default-rtdb.asia-southeast1.firebasedatabase.app/")
        databaseReference = firebaseDatabase.reference.child("users")

        binding.loginButton.setOnClickListener {
            val loginUsername = binding.usernameEt.text.toString()
            val loginPassword = binding.passET.text.toString()

            if (loginUsername.isNotEmpty() && loginPassword.isNotEmpty()) {
                loginUser(loginUsername, loginPassword)
            }
            else {
                Toast.makeText(this@LoginActivity, "Please Insert All Fields!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.registerButton.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser (username: String, password: String) {
        databaseReference.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(object:
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (userSnapshot in dataSnapshot.children) {
                        val userData = userSnapshot.getValue(Data::class.java)

                        if (userData != null && userData.username == username && userData.password == password) {
                            Toast.makeText(this@LoginActivity, "Login Successful", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                        }
                        else {
                            Toast.makeText(this@LoginActivity, "Login Failed", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@LoginActivity, "Database Error: ${databaseError.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}