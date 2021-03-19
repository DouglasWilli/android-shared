package com.cesarschool.android_shared.ui.photo

import android.app.Activity
import android.app.RecoverableSecurityException
import android.content.ContentUris
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import com.cesarschool.android_shared.databinding.ActivityPhotoDetailsBinding
import com.cesarschool.android_shared.model.Photo

class PhotoDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPhotoDetailsBinding
    private lateinit var photo: Photo

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotoDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.getParcelableExtra<Photo>("PHOTO")?.let { photo = it }

        photo.let { photoItem ->
            binding.apply {
                imgViewPhoto.setImageURI(photoItem.uri)

                btnRemove.setOnClickListener {
                    deleteImage(photoItem.uri)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun deleteImage(uri: Uri) {
        try {
            contentResolver.delete( uri,
                "${MediaStore.Images.Media._ID} = ?",
                arrayOf(ContentUris.parseId(uri).toString())
            )
        } catch (securityException: SecurityException) {
            val recoverSecException = securityException as? RecoverableSecurityException ?: throw securityException
            val intentSender = recoverSecException.userAction.actionIntent.intentSender
            startIntentSenderForResult(intentSender, REQUEST_DELETE_CODE, null, 0, 0, 0, null) }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == REQUEST_DELETE_CODE) {
            deleteImage(photo.uri)
            val returnIntent = Intent()
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }
    }

    companion object {
        const val REQUEST_DELETE_CODE = 2
    }
}