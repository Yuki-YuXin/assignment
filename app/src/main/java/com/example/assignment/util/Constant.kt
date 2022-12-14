package com.example.assignment.util

class Constant {
    object Intent {
        const val METEORITE_NAME = "meteorite_name"
        const val METEORITE_LAT = "meteorite_lat"
        const val METEORITE_LNG = "meteorite_long"
    }

    object Map {
        const val CAMERA_ZOOM = 4.0f
    }

    object Settings {
        const val SORT_FIELD = "sort_field"
        const val SORT_ORDER = "sort_orientation"
    }

    enum class SortOrder { ASCENDING, DESCENDING }
}
