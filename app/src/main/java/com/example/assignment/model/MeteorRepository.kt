package com.example.assignment.model

import com.example.assignment.data.OperationCallback

class MeteorRepository(private val meteorDataSource: MeteorDataSource) {

    fun fetchMeteors(callback: OperationCallback<MeteorData>) {
        meteorDataSource.retrieveMeteor(callback)
    }

    fun cancel() {
        meteorDataSource.cancel()
    }
}