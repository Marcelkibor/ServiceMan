package com.example.petvet
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_request.view.*

private lateinit var requestList:ArrayList<ClientConfirmRequest>
private lateinit var ARecyclerView: RecyclerView
private lateinit var dbRef:DatabaseReference
class FragmentAccepted : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_accepted, container, false)
        ARecyclerView = view.requestRv
        ARecyclerView.layoutManager = LinearLayoutManager(activity)
        ARecyclerView.setHasFixedSize(true)
        requestList = arrayListOf<ClientConfirmRequest>()
        setRequests()
        return view
    }
    private fun setRequests() {
    dbRef = FirebaseDatabase.getInstance().getReference("Accept")
    dbRef.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            if (snapshot.exists()){
                for (snap in snapshot.children){
                    val item = snap.getValue(ClientConfirmRequest::class.java)
                    requestList.add(item!!)
                }
                val adapter = AcceptedRequestAdapter(requestList)
                view?.requestRv!!.adapter = adapter
                Toast.makeText(activity, "items are${requestList}",Toast.LENGTH_SHORT).show()
            }
        }
        override fun onCancelled(error: DatabaseError) {
            Toast.makeText(activity,error.message, Toast.LENGTH_SHORT).show()
        }
    })
    }
}