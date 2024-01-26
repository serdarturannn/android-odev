package com.serdarturan.musicplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions


class MainActivity : ComponentActivity(), EasyPermissions.PermissionCallbacks {

    private val viewModel: MusicViewModel by viewModels()
    private val helper = MusicHelper()
    private val permissionHelper = PermissionHelper(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (permissionHelper.hasStoragePermissions()) {
            setupUI()
        } else {
            permissionHelper.requestStoragePermissions(this)
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    private fun setupUI() {
        setContent {
            val musicFiles = helper.getMusicFiles(this)
            viewModel.setMusicFiles(musicFiles, this)
            val scaffoldState = rememberBottomSheetScaffoldState()
            val coroutineScope = rememberCoroutineScope()
            val isMusicPlaying = remember { mutableStateOf(false) }
            val searchQuery = remember { mutableStateOf("") }



            LaunchedEffect(viewModel.isPlaying.value) {
                isMusicPlaying.value = viewModel.isPlaying.value
                coroutineScope.launch {
                    if (isMusicPlaying.value) {
                        scaffoldState.bottomSheetState.expand()
                    }
                }
            }

            BottomSheetScaffold(
                sheetShape = RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp),
                scaffoldState = scaffoldState,
                sheetContent = {
                    MusicPlayerUI(
                        viewModel = viewModel,
                        onPreviousClick = { viewModel.previous() },
                        onPlayOrPauseClick = { viewModel.playOrPause() }
                    ) { viewModel.next() }

                },
                content = {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Button(onClick = { viewModel.toggleShowFavorites() }) {
                            Text(if (viewModel.showingFavorites.value) "Show All Music" else "Show PlayList")
                        }
                        TextField(
                            value = searchQuery.value,
                            onValueChange = { query ->
                                searchQuery.value = query
                                viewModel.searchMusic(query)
                            },
                            label = { Text("Search Music") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp)
                        )
                        MusicListUI(
                            viewModel.filteredMusicFiles.value,
                            onMusicClick = { music -> viewModel.playMusic(music) },
                            onFavoriteClick = { music -> viewModel.toggleFavorite(music, applicationContext) }
                        )


                    }
                }
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        if (requestCode == PermissionHelper.REQUEST_CODE_STORAGE_PERMS) {
            setupUI()
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }
}
