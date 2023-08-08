package com.test.chatterwave.beta.utils

sealed class TimerEvent<T>(
    val data: T? = null,
){
    class TimerStart<T>(data: T?) : TimerEvent<T>(data = data)
    class TimerStop<T> : TimerEvent<T>()
}
