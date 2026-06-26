package com.example.tastebud.utils

import android.content.Context
import android.net.Uri
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object CloudinaryUploader {

    suspend fun uploadImage(
        context: Context,
        imageUri: Uri,
        preset: String = "tastebud_preset",
        onProgress: (Float) -> Unit = {}
    ): String = suspendCoroutine { continuation ->

        MediaManager.get()
            .upload(imageUri)
            .option("resource_type", "auto")
            .unsigned(preset)
            .callback(object : UploadCallback {

                override fun onStart(requestId: String?) {
                    // Upload started
                }

                override fun onProgress(
                    requestId: String?,
                    bytes: Long,
                    totalBytes: Long
                ) {

                    if (totalBytes > 0) {
                        onProgress(bytes.toFloat() / totalBytes.toFloat())
                    }

                }

                override fun onSuccess(
                    requestId: String?,
                    resultData: Map<*, *>?
                ) {

                    val imageUrl =
                        resultData?.get("secure_url") as? String

                    if (!imageUrl.isNullOrEmpty()) {

                        continuation.resume(imageUrl)

                    } else {

                        continuation.resumeWithException(
                            Exception("No image URL returned from Cloudinary.")
                        )

                    }

                }

                override fun onError(
                    requestId: String?,
                    error: ErrorInfo?
                ) {

                    continuation.resumeWithException(
                        Exception(
                            error?.description
                                ?: "Image upload failed."
                        )
                    )

                }

                override fun onReschedule(
                    requestId: String?,
                    error: ErrorInfo?
                ) {

                    continuation.resumeWithException(
                        Exception(
                            error?.description
                                ?: "Upload was rescheduled."
                        )
                    )

                }

            })
            .dispatch(context)

    }

}