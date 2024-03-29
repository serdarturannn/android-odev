package com.serdarturan.musicplayer

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class MusicViewModel : ViewModel() {
    private var mediaPlayer: MediaPlayer? = null
    private var musicFiles: List<Music> = emptyList()
    private var currentMusicIndex = 0
    val isPlaying = mutableStateOf(false)
    private var positionUpdateJob: Job? = null
    private var _filteredMusicFiles = mutableStateOf<List<Music>>(emptyList())
    val filteredMusicFiles: State<List<Music>> = _filteredMusicFiles
    val currentMusicName = mutableStateOf("")
    val currentPosition = mutableStateOf(0)
    val musicDuration = mutableStateOf(0)
    var showingFavorites = mutableStateOf(false)

    private var currentSearchQuery = ""

    init {
        startUpdatingPosition()
    }


    fun setMusicFiles(files: List<Music>, context: Context) {
        val favorites = SharedPreferencesHelper.loadFavorites(context)
        musicFiles = files.map { it.copy(isFavorite = favorites.contains(it.path)) }
        applySearchQuery()
    }

    fun searchMusic(query: String) {
        currentSearchQuery = query
        applySearchQuery()
    }

    private fun applySearchQuery() {
        _filteredMusicFiles.value = if (showingFavorites.value) {
            musicFiles.filter { it.isFavorite }
        } else {
            if (currentSearchQuery.isEmpty()) {
                musicFiles
            } else {
                musicFiles.filter { it.name.contains(currentSearchQuery, ignoreCase = true) }
            }
        }
    }



    fun toggleFavorite(music: Music, context: Context) {
        musicFiles = musicFiles.map {
            if (it == music) it.copy(isFavorite = !it.isFavorite) else it
        }
        _filteredMusicFiles.value = musicFiles.toList()
        val favorites = musicFiles.filter { it.isFavorite }.map { it.path }.toSet()
        SharedPreferencesHelper.saveFavorites(context, favorites)
    }


    private fun startUpdatingPosition() {
        positionUpdateJob?.cancel()
        positionUpdateJob = viewModelScope.launch {
            while (isActive) {
                updateCurrentPosition()
                delay(1000)
            }
        }
    }

    fun playMusic(musicFile: Music) {
        val index = _filteredMusicFiles.value.indexOf(musicFile)
        if (index != -1) {
            currentMusicIndex = index
        }

        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer().apply {
            setDataSource(musicFile.path)
            prepare()
            start()
        }
        isPlaying.value = true
        currentMusicName.value = musicFile.name
        musicDuration.value = mediaPlayer?.duration ?: 0

        startUpdatingPosition()
    }

    fun playOrPause() {
        if (mediaPlayer?.isPlaying == true) {
            mediaPlayer?.pause()
            isPlaying.value = false
        } else {
            mediaPlayer?.start()
            isPlaying.value = true
        }
    }


    fun next() {
        if (_filteredMusicFiles.value.isNotEmpty()) {
            currentMusicIndex = (currentMusicIndex + 1) % _filteredMusicFiles.value.size
            playMusic(_filteredMusicFiles.value[currentMusicIndex])
        }
    }

    fun previous() {
        if (_filteredMusicFiles.value.isNotEmpty()) {
            currentMusicIndex =
                (currentMusicIndex - 1 + _filteredMusicFiles.value.size) % _filteredMusicFiles.value.size
            playMusic(_filteredMusicFiles.value[currentMusicIndex])
        }
    }

    override fun onCleared() {
        super.onCleared()
        mediaPlayer?.release()
        positionUpdateJob?.cancel()
    }

    fun seekTo(position: Int) {
        mediaPlayer?.seekTo(position)
        currentPosition.value = position
    }

    private fun updateCurrentPosition() {
        currentPosition.value = (mediaPlayer?.currentPosition?.minus(1)) ?: 0
    }

    fun toggleShowFavorites() {
        showingFavorites.value = !showingFavorites.value
        applySearchQuery()
    }

}