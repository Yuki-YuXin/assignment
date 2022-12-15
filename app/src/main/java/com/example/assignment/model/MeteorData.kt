package com.example.assignment.model

import com.squareup.moshi.Json


data class MeteorData(
    @Json(name = "id") val id: String? = null,
    @Json(name = "mass") val mass: Double = 0.0,
    @Json(name = "name") val name: String? = null,
    @Json(name = "reclat") val reclat: String? = null,
    @Json(name = "reclong") val reclong: String? = null,
    @Json(name = "year") val year: String? = null,
)