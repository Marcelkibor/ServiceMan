package com.example.petvet
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlin.properties.Delegates

private var earthRadius by Delegates.notNull<Double>()

class LocationTest : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        earthRadius = 6371.0088
    }



}