package com.example.petvet
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petvet.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_admin_billings.view.*

@SuppressLint("StaticFieldLeak")
private lateinit var dbRef: DatabaseReference
private lateinit var paymentRecyclerView: RecyclerView
private lateinit var paymentList: ArrayList<Payment>
class FragmentAdminBillings : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_admin_billings,container,false)
        paymentList = arrayListOf<Payment>()
        paymentRecyclerView = view.rvCompletedPayments
        paymentRecyclerView.layoutManager = LinearLayoutManager(activity)
        paymentRecyclerView.setHasFixedSize(true)
        fetchPaymentList()
        return view

    }

    private fun fetchPaymentList() {
        try {
            dbRef = FirebaseDatabase.getInstance().getReference("Payment")
            dbRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        for (snap in snapshot.children){
                            val item = snap.getValue(Payment::class.java)
                            paymentList.add(item!!)
                        }
                        val adapter = PaymentListAdapter(paymentList)
                        paymentRecyclerView.adapter = adapter
                        adapter.setOnItemClickListener(object : PaymentListAdapter.OnItemClickListener{
                            override fun onItemClick(position: Int) {
                                Toast.makeText(activity,"I can be Clicked", Toast.LENGTH_SHORT).show()
//                                val intent = Intent(activity,ClientSelectServiceActivity::class.java)
//                                val currentDoctor = doctorList[position]
//                                val docUID = currentDoctor.doctorUid.toString()
//                                intent.putExtra("VetUID",docUID)
//                                startActivity(intent)
                            }
                        })
                    }
                    else{
                        Toast.makeText(activity,"No Data!!", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(activity,error.message, Toast.LENGTH_SHORT).show()
                }
            })
        }catch (e:Exception){
            Toast.makeText(activity,e.message, Toast.LENGTH_SHORT).show()

        }

    }
}