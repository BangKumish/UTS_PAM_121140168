package id.bangkumis.pamnuts.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import id.bangkumis.pamnuts.activity.LoginActivity
import id.bangkumis.pamnuts.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding?= null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.logoutButton.setOnClickListener {
            activity?.let {
                val intent = Intent(it, LoginActivity::class.java)
                it.startActivity(intent)
            }
        }
        val root: View = binding.root
        val textUser: TextView = binding.textUser
        val textGit: TextView = binding.textGit
        val textNik: TextView = binding.textNik
        val textEmail: TextView = binding.textEmail

        profileViewModel.text.observe(viewLifecycleOwner) {
            textUser.text = "Andreas"
            textGit.text = "BangKumish"
            textNik.text = "121140168"
            textEmail.text = "andreas.121140168@student.itera.ac.id"
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
