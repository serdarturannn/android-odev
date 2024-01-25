package com.serdarturan.musicplayer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun MusicPlayerUI(
    viewModel: MusicViewModel,
    onPreviousClick: () -> Unit,
    onPlayOrPauseClick: () -> Unit,
    onNextClick: () -> Unit
) {



    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(70, 70, 70, 255)),
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            color = Color.White,
            text = viewModel.currentMusicName.value,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp),
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            )
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                color = Color.White,
                text = MusicHelper.formatTime(viewModel.currentPosition.value)
            )
            Text(
                color = Color.White,
                text = MusicHelper.formatTime(viewModel.musicDuration.value)
            )
        }

        Slider(
            value = viewModel.currentPosition.value.toFloat(),
            onValueChange = { newPosition ->
                viewModel.seekTo(newPosition.toInt())
            },
            valueRange = 0f..viewModel.musicDuration.value.toFloat(),
            colors = SliderDefaults.colors( // Customized slider color
                thumbColor = Color(115, 105, 255, 255),
                activeTrackColor = Color(115, 105, 255, 255),
                inactiveTrackColor = Color.White
            )
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { onPreviousClick() }) {
                Icon(
                    painterResource(id = R.drawable.baseline_navigate_before_24),
                    contentDescription = "Previous",
                    tint = Color(
                        115,
                        105,
                        255,
                        255
                    )
                )
            }
            IconButton(onClick = { onPlayOrPauseClick() }) {
                Icon(
                    if (viewModel.isPlaying.value) painterResource(id = R.drawable.baseline_pause_24) else painterResource(
                        id = R.drawable.baseline_play_arrow_24
                    ),
                    contentDescription = "Play/Pause", tint = Color(115, 105, 255, 255)
                )
            }
            IconButton(onClick = { onNextClick() }) {
                Icon(
                    painterResource(id = R.drawable.baseline_navigate_next_24),
                    contentDescription = "Next", tint = Color(115, 105, 255, 255)
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewMusicPlayerUI() {
    val viewModel = MusicViewModel()
    viewModel.currentMusicName.value = "song1"
    MusicPlayerUI(viewModel, {}, {}, {})
}