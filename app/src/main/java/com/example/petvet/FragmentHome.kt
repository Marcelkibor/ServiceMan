package com.example.petvet
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.time.LocalDateTime
@SuppressLint("StaticFieldLeak")
private lateinit var greetingText:TextView
@SuppressLint("StaticFieldLeak")
private lateinit var clientDisplayName: TextView
private lateinit var stringName: String
private lateinit var auth:FirebaseAuth
private lateinit var vetDb: DatabaseReference

class FragmentHome : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View{
        auth = FirebaseAuth.getInstance()
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        val cardBillings = view.cardWallet
      val cardPending = view.pendingCardView
        greetingText = view.greetingText
        clientDisplayName = view.clientDisplayName
      val cardTrackRequest = view.cardTrack
      val cardServices = view.cardService
        cardBillings.setOnClickListener {
            Toast.makeText(activity,"to be implemented",Toast.LENGTH_SHORT).show()
        }

        cardTrackRequest.setOnClickListener {
            replaceFragments(FragmentTrackRequest())
        }

        setDisplayName()
        setGreeting()
        return view
    }
    private fun setDisplayName() {
        try {
            val vetUid = auth.currentUser?.uid
            vetDb = FirebaseDatabase.getInstance().getReference("Doctor")
            vetDb.addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (snap in snapshot.children) {
                            val item = snap.getValue(VetDoctor::class.java)
                    if (item?.doctorUid == vetUid){
                        clientDisplayName.text = item?.doctorName
                        clientDisplayName.visibility = View.VISIBLE
                    }
                }} }

        override fun onCancelled(error: DatabaseError) {
            Toast.makeText(activity,error.message,Toast.LENGTH_SHORT).show()
        }
            })
        }catch (e:Exception){
            Toast.makeText(activity,e.message,Toast.LENGTH_SHORT).show()
        }
    }
    private fun replaceFragments(fragment:Fragment){
        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.vet_frame_layout,fragment)
        fragmentTransaction.commit()
    }

    @SuppressLint("NewApi")
    private fun setGreeting() {
    val current = LocalDateTime.now()
    if (current.hour<=11){
        greetingText.text = getString(R.string.morning)
    }
    else if (current.hour<=16){
        greetingText.text = getString(R.string.afternoon)
    }
    else if (current.hour <=21){
        greetingText.text = getString(R.string.evening)
    }
    else{
        greetingText.text = getString(R.string.night)
    }
    }
}