package id.bangkumis.pamnuts.ui.home

import android.os.Bundle
import android.service.autofill.UserData
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import id.bangkumis.pamnuts.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private lateinit var database : DatabaseReference

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object {
        var EXTRA_NAME = "extra_name"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val name = arguments?.getString(EXTRA_NAME).toString()
        if (arguments != null) {
            binding.textName.text = name

            readData(name)
        }
    }

    private fun readData(username: String) {
        database = FirebaseDatabase.getInstance().getReference("users")
        database.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        val user = userSnapshot.getValue(Data::class.java)

                        if (user != null && username == user.username) {
                            val githubUsername = snapshot.child("githubUsername")
                            val nik = snapshot.child("nik")
                            val email = snapshot.child("email")

                            binding.textGithubUsername.text = githubUsername.toString()
                            binding.textNik.text = nik.toString()
                            binding.textEmail.text = email.toString()
                        }
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }
}