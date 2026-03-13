package com.cardforge.app.utils

import android.content.Context
import android.net.Uri
import java.io.BufferedReader
import java.io.InputStreamReader

object TxtImporter {

    fun readText(context: Context, uri: Uri): String {

        val inputStream =
            context.contentResolver.openInputStream(uri)

        val reader =
            BufferedReader(InputStreamReader(inputStream))

        val text = reader.readText()

        reader.close()

        return text
    }
}