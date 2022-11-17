package com.example.assignment.model

import com.squareup.moshi.Json


data class MeteorData(
    @Json(name = "id") var id: String? = null,
    @Json(name = "mass") var mass: Double = 0.0,
    @Json(name = "name") var name: String? = null,
    @Json(name = "reclat") var reclat: String? = null,
    @Json(name = "reclong") var reclong: String? = null,
    @Json(name = "year") var year: String? = null,
)