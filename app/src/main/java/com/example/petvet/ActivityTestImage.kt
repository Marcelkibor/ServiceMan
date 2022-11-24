package com.example.petvet

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.ColorSpace.Model
import android.net.Uri
import android.os.Bundle
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import com.example.petvet.databinding.TryImageUploadBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage


@SuppressLint("StaticFieldLeak")
private lateinit var binding: TryImageUploadBinding
private lateinit var imageUri:Uri
class ActivityTestImage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TryImageUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.testImage.setOnClickListener {
            val galleryInt = Intent()
            galleryInt.action = Intent.ACTION_GET_CONTENT
            galleryInt.type = "image/*"
            startActivityForResult(galleryInt, 2)
        }
        binding.btConfirm.setOnClickListener {
   postContent(imageUri)
   }
    }
    private fun postContent(uri: Uri) {
        val fileRef = FirebaseStorage.getInstance().getReference("gallery/" + getFileExtension(uri))
        fileRef.putFile(uri).addOnSuccessListener {
            fileRef.downloadUrl.addOnSuccessListener { uri ->
              Toast.makeText(this,"Success, Image uri is ${uri.toString()}",Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun getFileExtension(mUri: Uri): String? {
        val cr = contentResolver
        val mime = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(cr.getType(mUri))
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            imageUri = data.data!!
            binding.testImage.setImageURI(imageUri)
        }
    }
}