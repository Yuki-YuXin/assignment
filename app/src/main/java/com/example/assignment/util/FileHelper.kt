package com.example.assignment.util

import android.content.Context
import android.util.Log
import com.example.assignment.model.MeteorData
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
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
            val gsonPretty = GsonBuilder().setPrettyPrinting().create()
            val jsonTutPretty: String = gsonPretty.toJson(data)
            outputStreamWriter.write(jsonTutPretty)
            outputStreamWriter.close()
        } catch (e: IOException) {
            Log.e(TAG, "File write failed :", e)
        }
    }

    fun readFromFile(context: Context): List<MeteorData> {
        var listMeteor : List<MeteorData> = emptyList()
        try {
            val inputStream = context.openFileInput("data.json")

            if (inputStream != null) {
                val receiveString = inputStream.bufferedReader().use(BufferedReader::readText)
                inputStream.close()
                val gson = Gson()
                val listType = object : TypeToken<List<MeteorData>>() {}.type
                listMeteor = gson.fromJson(receiveString, listType)
            }
        } catch (e: FileNotFoundException) {
            Log.e(TAG, "File not found :", e)
        } catch (e: IOException) {
            Log.e(TAG, "Can not read file :", e)
        }
        return listMeteor
    }
}