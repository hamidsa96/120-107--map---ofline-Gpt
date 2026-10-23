package com.example.data.model

import com.squareup.moshi.JsonClass

/**
 * Represents an individual shot or correction record in an artillery/mortar mission.
 */
@JsonClass(generateAdapter = true)
data class ShotRecord(
    val shotNumber: Int,
    val time: String,
    val type: String,
    val typeClass: String,
    val targetX: Double,
    val targetY: Double,
    val otDist: Double,
    val otAzEast: Long,
    val otAzNato: Long,
    val opDist: Double,
    val opAzEast: Long,
    val opAzNato: Long,
    val corrLat: String,
    val corrRng: String,
    val ptDist: Long,
    val elev: Double?,
    val ptAzEast: Long,
    val ptAzNato: Long,
    val dial: String,
    val dialVal: Long,
    val desc: String,
    val charge: Int? = null
)

@JsonClass(generateAdapter = true)
data class TacticalPoint(
    val id: String,
    val label: String,
    val x: Double,
    val y: Double,
    val shotNumber: Int,
    val time: String,
    val dial: String,
    val distPT: Long,
    val elev: Double?,
    val azPTEast: Long,
    val azPTNato: Long,
    val corrLat: String,
    val corrRng: String
)
