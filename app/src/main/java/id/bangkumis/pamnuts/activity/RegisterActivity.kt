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
import id.bangkumis.pamnuts.databinding.ActivityRegisterBinding
import id.bangkumis.pamnuts.ui.home.Data

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseDatabase = FirebaseDatabase.getInstance("https://pam-nuts-default-rtdb.asia-southeast1.firebasedatabase.app/")
        databaseReference = firebaseDatabase.reference.child("users")

        binding.registerButton.setOnClickListener {
            val registerUsername = binding.usernameEt.text.toString()
            val registerPassword = binding.passET.text.toString()
            val registerGithubUsername = binding.GitHubEt.text.toString()
            val registerNIK = binding.nimEt.text.toString()
            val registerEmail = binding.emailEt.text.toString()

            if (registerUsername.isNotEmpty() && registerPassword.isNotEmpty() && registerGithubUsername.isNotEmpty() && registerNIK.isNotEmpty() && registerEmail.isNotEmpty()) {
                registerUser(registerUsername,
                    registerPassword,
                    registerGithubUsername,
                    registerNIK,
                    registerEmail)
            }
            else {
                Toast.makeText(this@RegisterActivity, "Please Insert All Fields!", Toast.LENGTH_SHORT).show()
            }
        }
        binding.navToLogin.setOnClickListener {
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
        }
    }

    private fun registerUser(username: String, password: String, githubUsername: String, nim: String, email: String) {
        databaseReference.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(object:
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (!dataSnapshot.exists()) {
                    val id = databaseReference.push().key
                    val userData = Data(id, username, password, githubUsername, nim, email)
                    databaseReference.child(id!!).setValue(userData)
                    Toast.makeText(this@RegisterActivity, "Registrasi Berhasil", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(intent)
                }
                else {
                    Toast.makeText(this@RegisterActivity, "User sudah ada", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@RegisterActivity, "Database Error: ${databaseError.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}