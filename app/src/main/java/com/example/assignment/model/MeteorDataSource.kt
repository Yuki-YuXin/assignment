package com.example.assignment.model

import com.example.assignment.data.OperationCallback

interface MeteorDataSource {
    fun retrieveMeteor(callback: OperationCallback<MeteorData>)
    fun cancel()
}