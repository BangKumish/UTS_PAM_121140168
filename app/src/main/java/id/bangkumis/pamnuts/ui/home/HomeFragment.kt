package id.bangkumis.pamnuts.ui.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import id.bangkumis.pamnuts.R
import java.util.Locale

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var adapter: MyAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var userArrayList: ArrayList<Data>
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)

        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        userArrayList = arrayListOf<Data>()
        getUserData()
        adapter = MyAdapter(userArrayList)
        recyclerView.adapter = adapter
        searchView = view.findViewById(R.id.search_action)
        adapter.onItemClick = {
            it.username?.let { it1 -> navigateToDetail(it1) }
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Handler(Looper.getMainLooper()).removeCallbacksAndMessages(null)
                Handler(Looper.getMainLooper()).postDelayed({
                    filterList(newText)
                }, 1250)

                return false
            }

        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        param1 = null
    }

    private fun navigateToDetail(extraName: String){
        findNavController().navigate(HomeFragmentDirections.actionNavHomeToNavUserDetail(extraName))
    }

    private fun filterList(query: String?) {

        if (query != null) {
            val filteredList = ArrayList<Data>()
            for (i in userArrayList) {
                if (i.username?.lowercase(Locale.ROOT)!!.contains(query)) {
                    filteredList.add(i)
                }
            }

            if (filteredList.isEmpty()) {
                Toast.makeText(context, "No Data Found", Toast.LENGTH_SHORT).show()
            }
            else {
                adapter.selfFIlteredList(filteredList)
            }
        }
    }

    private fun getUserData() {

        database = FirebaseDatabase.getInstance("https://pam-nuts-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("users")

        database.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        val user = userSnapshot.getValue(Data::class.java)
                        userArrayList.add(user!!)
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

}