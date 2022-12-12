package com.example.assignment.model

import java.io.Serializable

data class MeteorData(
    val id: String? = null,
    val mass: Double = 0.0,
    var name: String? = null,
    val reclat: String? = null,
    val reclong: String? = null,
    val year: String ?= null
) : Serializable