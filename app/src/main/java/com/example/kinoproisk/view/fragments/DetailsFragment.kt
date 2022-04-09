package com.example.kinoproisk.view.fragments


import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.kinoproisk.R
import com.example.kinoproisk.databinding.FragmentDetailsBinding
import com.example.kinoproisk.data.Entity.Film
import com.example.kinoproisk.view.notification.NotificationHelper
import com.example.kinoproisk.viewmodel.DetailsFragmentViewModel
import com.example.remote_module.entity.ApiConstants
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*


class DetailsFragment : Fragment() {
    private lateinit var film: Film
    private lateinit var binding: FragmentDetailsBinding
    private val viewModel: DetailsFragmentViewModel by viewModels()
    private val scope = CoroutineScope(Dispatchers.IO)






    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFilmsDetails()

        binding.detailsFabFavorites.setOnClickListener {
            if (!film.isInFavorites) {
                binding.detailsFabFavorites.setImageResource(R.drawable.ic_baseline_favorite_24)
                film.isInFavorites = true
            } else {
                binding.detailsFabFavorites.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                film.isInFavorites = false
            }
        }

        binding.detailsFabShare.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(
                Intent.EXTRA_TEXT,
                "Check out this film: ${film.title} \n\n ${film.description}"
            )
            intent.type = "text/plain"
            startActivity(Intent.createChooser(intent, "Share To:"))
        }

        binding.detailsFabWatchLater.setOnClickListener {
            NotificationHelper.notificationSet(requireContext(), film)
        }

        binding.detailsFabDownloadWp.setOnClickListener {
            performAsyncLoadOfPoster()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

    private fun setFilmsDetails() {
        film = arguments?.get("film") as Film

        binding.detailsToolbar.title = film.title
        Glide.with(this)
            .load(ApiConstants.IMAGES_URL + "w780" + film.poster)
            .centerCrop()
            .into(binding.detailsPoster)
        binding.detailsDescription.text = film.description

        binding.detailsFabFavorites.setImageResource(
            if (film.isInFavorites) R.drawable.ic_baseline_favorite_24
            else R.drawable.ic_baseline_favorite_border_24
        )
    }

    private fun performAsyncLoadOfPoster() {

        if (!checkPermission()) {
            requestPermission()
            return
        }

        MainScope().launch {
            binding.progressBar.isVisible = true
            val job = scope.async {
                viewModel.loadWallpaper(ApiConstants.IMAGES_URL + "original" + film.poster)
            }
            saveToGallery(job.await())
            Snackbar.make(
                binding.root,
                R.string.downloaded_to_gallery,
                Snackbar.LENGTH_LONG
            )
                .setAction(R.string.open) {
                    val intent = Intent()
                    intent.action = Intent.ACTION_VIEW
                    intent.type = "image/*"
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }
                .show()

            binding.progressBar.isVisible = false
        }
    }

    private fun checkPermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            1
        )
    }

    private fun saveToGallery(bitmap: Bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val contentValues = ContentValues().apply {
                put(MediaStore.Images.Media.TITLE, film.title.handleSingleQuote())
                put(
                    MediaStore.Images.Media.DISPLAY_NAME,
                    film.title.handleSingleQuote()
                )
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                put(
                    MediaStore.Images.Media.DATE_ADDED,
                    System.currentTimeMillis() / 1000
                )
                put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/FilmsSearchApp")
            }

            val contentResolver = requireActivity().contentResolver
            val uri = contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )
            val outputStream = contentResolver.openOutputStream(uri!!)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream?.close()
        } else {
            @Suppress("DEPRECATION")
            MediaStore.Images.Media.insertImage(
                requireActivity().contentResolver,
                bitmap,
                film.title.handleSingleQuote(),
                film.description.handleSingleQuote()
            )
        }
    }

    private fun String.handleSingleQuote(): String {
        return this.replace("'", "")
    }

    private fun addFilmToWatchLater(film: Film) {

    }
}