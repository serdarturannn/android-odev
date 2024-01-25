package com.serdarturan.musicplayer

import android.content.Context
import android.provider.MediaStore
import java.util.Locale
import java.util.concurrent.TimeUnit

class MusicHelper {
    fun getMusicFiles(context: Context): List<Music> {

        val musicList = mutableListOf<Music>()
        val projection = arrayOf(MediaStore.Audio.Media.DISPLAY_NAME, MediaStore.Audio.Media.DATA)
        val cursor = context.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            null
        )
        cursor?.use {
            while (it.moveToNext()) {
                val name = cursor.getString(0)
                val path = cursor.getString(1)
                musicList.add(Music(name, path))
            }
        }
        return musicList
    }

    companion object {
        fun formatTime(millis: Int): String = String.format(
            Locale.getDefault(), "%02d:%02d",
            TimeUnit.MILLISECONDS.toMinutes(millis.toLong()),
            TimeUnit.MILLISECONDS.toSeconds(millis.toLong()) % 60
        )
    }
}
