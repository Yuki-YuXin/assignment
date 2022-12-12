package com.example.assignment.data

import com.example.assignment.model.MeteorData
import com.example.assignment.model.MeteorDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MeteorRemoteDataSource(apiClient: ApiClient) : MeteorDataSource {

    private var call: Call<List<MeteorData>>? = null
    private val service = apiClient.build()

    override fun retrieveMeteor(callback: OperationCallback<MeteorData>) {
        call = service.getMeteorsJsonData()
        call?.enqueue(object : Callback<List<MeteorData>> {
            override fun onFailure(call: Call<List<MeteorData>>, t: Throwable) {
                callback.onError(t.message)
            }

            override fun onResponse(
                call: Call<List<MeteorData>>,
                response: Response<List<MeteorData>>
            ) {
                response.body()?.let {
                    if (response.isSuccessful) {
                        callback.onSuccess(it)
                    } else {
                        callback.onError("Error")
                    }
                }
            }
        })
    }

    override fun cancel() {
        call?.let {
            it.cancel()
        }
    }
}