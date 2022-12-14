package com.example.assignment.data


interface OperationCallback<T> {
    fun onSuccess(data: List<T>?)
    fun onError(error: String?)
}