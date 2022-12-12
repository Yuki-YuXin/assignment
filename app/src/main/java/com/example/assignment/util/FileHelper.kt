package com.example.assignment.util

import android.content.Context
import android.util.Log
import com.example.assignment.model.MeteorData
import com.google.gson.Gson
import java.io.*
import kotlin.reflect.typeOf


internal object FileHelper {

    private val TAG = FileHelper::class.java.name

    fun writeToFile(data: List<MeteorData>, context: Context) {
        try {
            val outputStreamWriter =
                OutputStreamWriter(
                    context.openFileOutput(
                        "data.json",
                        Context.MODE_PRIVATE
                    )
                )
            outputStreamWriter.write(data.toString())
            outputStreamWriter.close()
        } catch (e: IOException) {
            Log.e(TAG, "File write failed :", e)
        }
    }

    fun readFromFile(context: Context): List<MeteorData> {
        var jsonString : String? = null
        var listMeteor : List<MeteorData> = emptyList()
        try {
            val inputStream = context.openFileInput("data.json")

            if (inputStream != null) {
                val receiveString = inputStream.bufferedReader().use(BufferedReader::readText)
                inputStream.close()
                jsonString = receiveString
                listMeteor = Gson().fromJson(jsonString, Array<MeteorData>::class.java).toList()
            }
        } catch (e: FileNotFoundException) {
            Log.e(TAG, "File not found :", e)
        } catch (e: IOException) {
            Log.e(TAG, "Can not read file :", e)
        }
        return listMeteor
    }
}