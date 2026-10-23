package com.example.data.model

import com.squareup.moshi.JsonClass

/**
 * Coordinate representation in WGS84 (Latitude and Longitude in degrees).
 */
@JsonClass(generateAdapter = true)
data class GeoPoint(
    val latitude: Double,
    val longitude: Double,
    val altitudeMeters: Double = 0.0
)

/**
 * Point of Interest (POI) in Khorasan Razavi for offline search.
 */
@JsonClass(generateAdapter = true)
data class MapPlacePOI(
    val id: String,
    val namePersian: String,
    val nameEnglish: String = "",
    val category: POICategory,
    val countyName: String,
    val latitude: Double,
    val longitude: Double,
    val elevationMeters: Int = 0,
    val description: String = "",
    val utmCoordinate: String = "",
    val isMilitaryOrStrategic: Boolean = false
)

enum class POICategory(val titlePersian: String, val icon: String) {
    CITY("شهر / مرکز شهرستان", "🏙️"),
    PEAK("قله و ارتفاعات", "🏔️"),
    VALLEY("دره و تنگه طبیعی", "🏞️"),
    MILITARY("نظامی و پادگان", "🎖️"),
    RELIGIOUS("مذهبی و زیارتی", "🕌"),
    HISTORICAL("تاریخی و میراث فرهنگی", "🏛️"),
    WATERFALL("آبشار و چشمه", "🌊"),
    SHELTER("جانپناه و کمپ", "⛺"),
    HIKING_START("آغاز مسیر کوهپیمایی", "🥾")
}

/**
 * Walking / Hiking trail representation.
 */
@JsonClass(generateAdapter = true)
data class HikingTrail(
    val id: String,
    val namePersian: String,
    val region: String,
    val points: List<GeoPoint>,
    val lengthKm: Double,
    val elevationGainMeters: Int,
    val difficulty: TrailDifficulty,
    val estimatedHours: Double,
    val description: String,
    val colorHex: String = "#10B981",
    val waypoints: List<TrailWaypoint> = emptyList()
)

@JsonClass(generateAdapter = true)
data class TrailWaypoint(
    val title: String,
    val latitude: Double,
    val longitude: Double,
    val elevationMeters: Int,
    val description: String = ""
)

enum class TrailDifficulty(val titlePersian: String, val colorHex: String) {
    EASY("آسان (طبیعت‌گردی عمومی)", "#10B981"),
    MODERATE("متوسط (کوهپیمایی سبک)", "#F59E0B"),
    HARD("سخت (کوهنوردی سنگین)", "#EF4444"),
    TECHNICAL("فنی و نیازمند تجهیزات", "#7C3AED")
}

enum class MapTileSource(
    val titlePersian: String,
    val primaryUrl: String,
    val fallbackUrls: List<String> = emptyList(),
    val serverNamePersian: String = ""
) {
    GOOGLE_TERRAIN(
        "توپوگرافی و ارتفاعات کوهستان (Google Terrain)",
        "https://mt1.google.com/vt/lyrs=p&x={x}&y={y}&z={z}",
        listOf(
            "https://mt2.google.com/vt/lyrs=p&x={x}&y={y}&z={z}",
            "https://server.arcgisonline.com/ArcGIS/rest/services/World_Topo_Map/MapServer/tile/{z}/{y}/{x}"
        ),
        "سرور توپوگرافی گوگل"
    ),
    GOOGLE_SATELLITE(
        "تصاویر زنده ماهواره‌ای (Google Satellite)",
        "https://mt1.google.com/vt/lyrs=y&x={x}&y={y}&z={z}",
        listOf(
            "https://mt2.google.com/vt/lyrs=y&x={x}&y={y}&z={z}",
            "https://server.arcgisonline.com/ArcGIS/rest/services/World_Imagery/MapServer/tile/{z}/{y}/{x}"
        ),
        "سرور ماهواره‌ای گوگل و Esri"
    ),
    CARTO_VOYAGER(
        "نقشه خیابانی و طبیعی (Carto Voyager)",
        "https://basemaps.cartocdn.com/rastertiles/voyager/{z}/{x}/{y}.png",
        listOf(
            "https://mt1.google.com/vt/lyrs=m&x={x}&y={y}&z={z}",
            "https://tile.openstreetmap.org/{z}/{x}/{y}.png"
        ),
        "سرور کارتو و خیابان‌های شهری"
    ),
    CARTO_DARK(
        "تاکتیکی و دید در شب (Carto Dark)",
        "https://a.basemaps.cartocdn.com/dark_all/{z}/{x}/{y}.png",
        listOf(
            "https://b.basemaps.cartocdn.com/dark_all/{z}/{x}/{y}.png"
        ),
        "سرور دید در شب تاکتیکی"
    ),
    OPEN_TOPO_MAP(
        "منحنی‌های میزان اوپن‌توپو (OpenTopo)",
        "https://a.tile.opentopomap.org/{z}/{x}/{y}.png",
        listOf(
            "https://b.tile.opentopomap.org/{z}/{x}/{y}.png",
            "https://mt1.google.com/vt/lyrs=p&x={x}&y={y}&z={z}"
        ),
        "سرور بین‌المللی OpenTopo"
    ),
    KHORASAN_OFFLINE_VECTOR(
        "نقشه آفلاین وکتور خراسان رضوی (1:25000)",
        "",
        emptyList(),
        "پایگاه‌داده داخلی آفلاین"
    )
}

enum class MapOnlineMode(val titlePersian: String) {
    ONLINE("آنلاین 🌐"),
    OFFLINE("آفلاین 📴")
}
