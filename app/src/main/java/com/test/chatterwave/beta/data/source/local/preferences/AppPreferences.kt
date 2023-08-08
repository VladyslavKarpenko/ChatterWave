package com.test.chatterwave.beta.data.source.local.preferences

import android.content.Context
import com.test.chatterwave.beta.utils.ACCESS_TOKEN_KEY
import com.test.chatterwave.beta.utils.EMPTY_STRING
import com.test.chatterwave.beta.utils.PREFS_TOKEN_FILE
import com.test.chatterwave.beta.utils.REFRESH_TOKEN_KEY
import com.test.chatterwave.beta.utils.TIMER_COUNT
import com.test.chatterwave.beta.utils.TIMER_TIME_LEFT
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppPreferences @Inject constructor(@ApplicationContext context: Context) {

    private val prefs = context.getSharedPreferences(PREFS_TOKEN_FILE, Context.MODE_PRIVATE)

    fun getAccessToken(): String {
        return prefs.getString(ACCESS_TOKEN_KEY, EMPTY_STRING)!!
    }

    fun getRefreshToken(): String {
        return prefs.getString(REFRESH_TOKEN_KEY, EMPTY_STRING)!!
    }

    fun setAccessToken(token: String) {
        prefs
            .edit()
            .putString(ACCESS_TOKEN_KEY, token)
            .apply()
    }

    fun setRefreshToken(token: String) {
        prefs
            .edit()
            .putString(REFRESH_TOKEN_KEY, token)
            .apply()
    }

    fun clearTokens(){
        setAccessToken(EMPTY_STRING)
        setRefreshToken(EMPTY_STRING)
    }

    fun setTimerTime(time: Long){
        prefs.edit().putLong(TIMER_TIME_LEFT, time).apply()
    }

    fun getTimerTime(): Long{
       return prefs.getLong(TIMER_TIME_LEFT, 0)
    }

    fun setTimerCounter(counter: Int){
        prefs.edit().putInt(TIMER_COUNT, counter).apply()
    }

    fun getTimerCounter(): Int{
      return  prefs.getInt(TIMER_COUNT, 0)
    }

}