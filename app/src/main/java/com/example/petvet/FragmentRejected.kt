package com.example.petvet
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_rejected.view.*

private lateinit var requestList:ArrayList<ClientRequest>
private lateinit var requestRecyclerView: RecyclerView
private lateinit var dbRef: DatabaseReference
class FragmentRejected : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_rejected, container, false)
        fetchRecyclerDetails()
        requestRecyclerView = view.rvRejected
        requestRecyclerView.layoutManager = LinearLayoutManager(activity)
        requestRecyclerView.setHasFixedSize(true)
        requestList = arrayListOf<ClientRequest>()
        return view
    }
    private fun fetchRecyclerDetails() {
        dbRef = FirebaseDatabase.getInstance().getReference("Reject")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
        try {
            if (snapshot.exists()){
                for (snap in snapshot.children){
                    val item = snap.getValue(ClientRequest::class.java)
                    requestList.add(item!!)
                }
                Toast.makeText(activity,"${requestList.size}!",Toast.LENGTH_SHORT).show()

                val adapter =  ClientRequestAdapter(requestList)
                view?.rvRejected!!.adapter = adapter
            }
        }
        catch(e:Exception){
            Toast.makeText(activity,e.message, Toast.LENGTH_SHORT).show()
        }
    }
    override fun onCancelled(error: DatabaseError) {
        Toast.makeText(activity,error.message, Toast.LENGTH_SHORT).show()
    }
})
}
}