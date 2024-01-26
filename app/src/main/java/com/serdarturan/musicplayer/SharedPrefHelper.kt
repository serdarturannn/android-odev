package com.serdarturan.musicplayer

import android.content.Context
import android.content.SharedPreferences


// to save favorite musics we used shared preferences
object SharedPreferencesHelper {
    
    private const val PREFS_NAME = "MusicPlayerPrefs"
    private const val FAVORITES_KEY = "Favorites"

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun saveFavorites(context: Context, favorites: Set<String>) {
        getSharedPreferences(context).edit()
            .putStringSet(FAVORITES_KEY, favorites)
            .apply()
    }

    fun loadFavorites(context: Context): Set<String> {
        return getSharedPreferences(context).getStringSet(FAVORITES_KEY, emptySet()) ?: emptySet()
    }
}
