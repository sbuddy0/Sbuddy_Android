package com.sbuddy.sbdApp.util

import android.content.Context
import android.net.Uri
import com.google.gson.Gson
import com.sbuddy.sbdApp.http.Post
import com.sbuddy.sbdApp.http.Update
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream

object UploadUtil {

    fun uriToFile(context: Context?, uri: Uri): File {
        val file = File(context?.cacheDir, "temp_image.jpg") // 임시 파일 생성

        context!!.contentResolver.openInputStream(uri)?.use { inputStream ->
            FileOutputStream(file).use { outputStream ->
                val buffer = ByteArray(1024)
                var bytesRead: Int
                while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                    outputStream.write(buffer, 0, bytesRead)
                }
            }
        }

        return file
    }
    fun prepareFilePart(partName: String, file: File): MultipartBody.Part {
        val requestFile = RequestBody.create(MediaType.parse("image/*"), file)
        return MultipartBody.Part.createFormData(partName, file.name, requestFile)
    }

    fun createJsonRequestBody(post: Post): RequestBody {
        val gson = Gson()
        val jsonString = gson.toJson(post)
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString)
    }

    fun createJsonRequestBody(post: Update): RequestBody {
        val gson = Gson()
        val jsonString = gson.toJson(post)
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString)
    }
}