package com.serdarturan.musicplayer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MusicListUI(musicFiles: List<Music>, onMusicClick: (Music) -> Unit) {
    LazyColumn(modifier = Modifier.padding(bottom = 32.dp)) {

        items(musicFiles) { music ->
            Card(
                backgroundColor = Color(115, 105, 255, 255),
                shape = RoundedCornerShape(4.dp),
                elevation = 4.dp,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable {
                        onMusicClick(music)
                    }
            ) {
                Text(
                    text = music.name,
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 16.sp,
//                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMusicListUI() {
    val sampleMusicFiles = listOf(
        Music("Song 1 Song 1 Song 1 Song 1 Song 1", "path1"),
        Music("Song 2 Song 2 Song 2 Song 2 Song 2", "path2"),
        Music("Song 3 Song 3 Song 3 Song 3 Song 3", "path3")
    )
    MusicListUI(sampleMusicFiles) {}
}