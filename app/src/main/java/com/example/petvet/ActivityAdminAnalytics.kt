package com.example.petvet

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.petvet.databinding.AdminAnalyticsBinding
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate

@SuppressLint("StaticFieldLeak")
private lateinit var binding: AdminAnalyticsBinding
private lateinit var totalServices: String
private lateinit var totalPayments: String
private lateinit var totalClients: String
private lateinit var totalRequests: String
private lateinit var totalVets: String

class ActivityAdminAnalytics : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AdminAnalyticsBinding.inflate(layoutInflater)
        val bundle: Bundle? = intent.extras
        totalVets = bundle?.getString("totalVets").toString()
        totalClients = bundle?.getString("totalClients").toString()
        totalPayments = bundle?.getString("totalPayments").toString()
        totalServices = bundle?.getString("totalServices").toString()
        totalRequests = bundle?.getString("totalRequests").toString()
        binding.addClients.text = totalClients
//        binding.addDoctors.text = totalVets
        binding.addPayment.text = totalPayments
        binding.addService.text = totalServices
        binding.addDoctors.text = totalVets
        binding.addRequest.text = totalRequests
        binding.addClients.text = totalClients
        setUsersPie()
        setContentView(binding.root)
        binding.backArrow.setOnClickListener {
            val intent = Intent(this@ActivityAdminAnalytics, AdminPanel::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun setUsersPie() {
        try {
            val userList: ArrayList<PieEntry> = ArrayList()
            val listAnalytics: ArrayList<PieEntry> = ArrayList()
            listAnalytics.add(PieEntry(totalRequests.toFloat(), "Requests"))
            listAnalytics.add(PieEntry(totalServices.toFloat(), "Services"))
            listAnalytics.add(PieEntry(totalPayments.toFloat(), "Payments"))
            val pieChartAnalytics = binding.pieChartUsers
            val pieAnalyticsData = PieDataSet(listAnalytics, "")
            pieAnalyticsData.setColors(ColorTemplate.MATERIAL_COLORS, 255)
            pieAnalyticsData.valueTextSize = 14f
            pieAnalyticsData.valueTextColor = Color.BLACK
            val analyticsData = PieData(pieAnalyticsData)
            pieChartAnalytics.data = analyticsData
            pieChartAnalytics.description.text = "Service Analytics"
            pieChartAnalytics.centerText = "Analytics"
            pieChartAnalytics.animateX(1000)
            //users pie chart
            userList.add(PieEntry(totalClients.toFloat(), "Clients"))
            userList.add(PieEntry(totalVets.toFloat(), "Vets"))
            val pieChartUsers = binding.pieChartServices
            val pieDataset = PieDataSet(userList, "")
            pieDataset.setColors(ColorTemplate.MATERIAL_COLORS, 255)
            pieDataset.valueTextSize = 14f
            pieDataset.valueTextColor = Color.BLACK
            val pieUsersData = PieData(pieDataset)
            pieChartUsers.data = pieUsersData
            pieChartUsers.description.text = "System Users"
            pieChartUsers.centerText = "Users"
            pieChartUsers.animateY(1200)
        } catch (e: Exception) {
            Toast.makeText(this, "total vets ${e.message}", Toast.LENGTH_SHORT).show()

        }

    }
}