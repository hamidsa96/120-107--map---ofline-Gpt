package com.example.data.model

/**
 * Domain model representing a complete firing mission.
 */
data class FireMission(
    val id: String,
    val name: String,
    val description: String,
    val system: String, // "107" for Rocket 107mm, "120" for Mortar 120mm
    val startedAt: String,
    val endedAt: String,
    val timestamp: Long,
    val shotCount: Int,
    val finalDial: String,
    val finalDist: Long,
    val finalElev: Double?,
    val finalAz: String,
    val shots: List<ShotRecord>,
    val commanderName: String = "",
    val crewMembers: String = "",
    val operationalArea: String = ""
)
