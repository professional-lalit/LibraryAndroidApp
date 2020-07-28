package com.library.app.utils

import com.library.app.common.CustomApplication
import java.io.IOException
import java.io.InputStreamReader

/**
 * Created by Lalit N. Hajare, Software Engineer on 28/07/2020
 */
object FileReader {
    fun readStringFromFile(fileName: String): String {
        try {
            val inputStream = CustomApplication.appInstance!!.assets.open(fileName)
            val builder = StringBuilder()
            val reader = InputStreamReader(inputStream, "UTF-8")
            reader.readLines().forEach {
                builder.append(it)
            }
            return builder.toString()
        } catch (e: IOException) {
            throw e
        }
    }
}