package com.test.chatterwave.beta.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.widget.Toast
import androidx.fragment.app.Fragment
import android.util.Base64
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

/**Function to convert long to date format to show it to user */
@RequiresApi(Build.VERSION_CODES.O)
fun Long.showFormattedDate(): CharSequence {
    val dateFormat = DateTimeFormatter.ofPattern("MMMM dd, y").withZone( ZoneId.of("UTC"))
    return dateFormat.format(Instant.ofEpochMilli(this)).toString().replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
}

/**Function to convert long to date format to send it to server as part of user model*/
@RequiresApi(Build.VERSION_CODES.O)
fun Long.sendFormattedDate(): CharSequence {
    val dateFormat = DateTimeFormatter.ofPattern("y-MM-dd").withZone( ZoneId.of("UTC"))
    return dateFormat.format(Instant.ofEpochMilli(this)).toString().replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
}

fun Context.toast(message:String){
    Toast.makeText(this, message , Toast.LENGTH_LONG).show()
}

fun Fragment.toast(message:String){
    Toast.makeText(requireContext(), message , Toast.LENGTH_LONG).show()
}

fun Long.showTime(): String{
    val min = (this / (1000 * 60));
    val sec = ((this / 1000) - min * 60);
    return String.format("%02d:%02d", min, sec)
}

fun Bitmap.encodeImage(): String {
    val baos = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.PNG, 100, baos)
    val b = baos.toByteArray()
    return "data:image/png;base64, " + Base64.encodeToString(b, Base64.DEFAULT)
}

fun String.toBitmap(): Bitmap{
    return BitmapFactory.decodeFile(this.toUri().path)
}

suspend fun String.bottomNavigationImage(context: Context) : Drawable{
    return withContext(Dispatchers.IO){
    Glide.with(context)
        .load(this@bottomNavigationImage)
        .circleCrop()
        .submit().get()}
}

fun String.parseHashtags(): List<String> {
    val matches = hashtagRegex.findAll(this)
    return matches.map { it.value }.toList()
}

suspend fun String.loadBitmap(): Bitmap? = withContext(Dispatchers.IO) {
    var inputStream: InputStream? = null
    var bitmap: Bitmap? = null
    try {
        val url = URL(this@loadBitmap)
        val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
        connection.doInput = true
        connection.connect()
        inputStream = connection.inputStream
        bitmap = BitmapFactory.decodeStream(inputStream)
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        try {
            inputStream?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    bitmap
}