/*
 * Copyright 2023 Dora Lee
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.sanghun.compose.video.sample

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.media3.exoplayer.analytics.AnalyticsListener
import io.sanghun.compose.video.RepeatMode
import io.sanghun.compose.video.ResizeMode
import io.sanghun.compose.video.VideoPlayer
import io.sanghun.compose.video.cache.VideoPlayerCacheManager
import io.sanghun.compose.video.controller.VideoPlayerControllerConfig
import io.sanghun.compose.video.sample.ui.theme.ComposeVideoSampleTheme
import io.sanghun.compose.video.toRepeatMode

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        VideoPlayerCacheManager.initialize(this, 1024 * 1024 * 1024) // 1GB

        setContent {
            ComposeVideoSampleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                    ) {
                        val context = LocalContext.current

                        var repeatMode by remember { mutableStateOf(RepeatMode.NONE) }

                        VideoPlayer(
                            mediaItems = samplePlayList,
                            handleLifecycle = false,
                            autoPlay = true,
                            usePlayerController = true,
                            enablePipWhenBackPressed = true,
                            enablePip = true,
                            controllerConfig = VideoPlayerControllerConfig.Default.copy(
                                showSubtitleButton = true,
                                showNextTrackButton = true,
                                showBackTrackButton = true,
                                showBackwardIncrementButton = true,
                                showForwardIncrementButton = true,
                                showRepeatModeButton = true,
                                showFullScreenButton = true,
                            ),
                            repeatMode = repeatMode,
                            resizeMode = ResizeMode.FIT,
                            onCurrentTimeChanged = {
                                Log.e("CurrentTime", it.toString())
                            },
                            playerInstance = {
                                Log.e("VOLUME", volume.toString())
                                addAnalyticsListener(object : AnalyticsListener {
                                    @SuppressLint("UnsafeOptInUsageError")
                                    override fun onRepeatModeChanged(
                                        eventTime: AnalyticsListener.EventTime,
                                        rMode: Int,
                                    ) {
                                        repeatMode = rMode.toRepeatMode()
                                        Toast.makeText(
                                            context,
                                            "RepeatMode changed = ${rMode.toRepeatMode()}",
                                            Toast.LENGTH_LONG,
                                        )
                                            .show()
                                    }

                                    @SuppressLint("UnsafeOptInUsageError")
                                    override fun onPlayWhenReadyChanged(
                                        eventTime: AnalyticsListener.EventTime,
                                        playWhenReady: Boolean,
                                        reason: Int,
                                    ) {
                                        Toast.makeText(
                                            context,
                                            "isPlaying = $playWhenReady",
                                            Toast.LENGTH_LONG,
                                        )
                                            .show()
                                    }

                                    @SuppressLint("UnsafeOptInUsageError")
                                    override fun onVolumeChanged(
                                        eventTime: AnalyticsListener.EventTime,
                                        volume: Float,
                                    ) {
                                        Toast.makeText(
                                            context,
                                            "Player volume changed = $volume",
                                            Toast.LENGTH_LONG,
                                        )
                                            .show()
                                    }
                                })
                            },
                            modifier = Modifier
                                .fillMaxSize()
                                .align(Alignment.Center),
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeVideoSampleTheme {
        Greeting("Android")
    }
}
