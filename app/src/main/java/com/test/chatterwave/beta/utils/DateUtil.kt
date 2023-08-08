package com.test.chatterwave.beta.utils

import java.time.ZonedDateTime

fun getCurrentTimeInMillis() : Long{
    return ZonedDateTime.now().toInstant().toEpochMilli()
}