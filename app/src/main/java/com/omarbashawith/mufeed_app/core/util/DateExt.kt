package com.omarbashawith.mufeed_app.core.util

import android.content.Context
import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.*

fun String.timeAgo(): String {
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    val ago = format.parse(this)!!.time
    val now = System.currentTimeMillis()

    return DateUtils.getRelativeTimeSpanString(
        ago,
        now,
        DateUtils.MINUTE_IN_MILLIS
    ).toString()
}