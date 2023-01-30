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
package io.sanghun.compose.video

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
import com.google.android.exoplayer2.analytics.AnalyticsListener
import io.sanghun.compose.video.controller.VideoPlayerControllerConfig
import io.sanghun.compose.video.ui.theme.ComposeVideoSampleTheme
import io.sanghun.compose.video.uri.VideoPlayerMediaItem

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                            mediaItems = listOf(
                                VideoPlayerMediaItem.NetworkMediaItem(
                                    url = "https://storage.googleapis.com/exoplayer-test-media-1/gen-3/screens/dash-vod-single-segment/video-137.mp4",
                                ),
                            ),
                            handleLifecycle = true,
                            autoPlay = true,
                            usePlayerController = true,
                            controllerConfig = VideoPlayerControllerConfig.Default.copy(
                                showSubtitleButton = false,
                                showNextTrackButton = true,
                                showBackTrackButton = true,
                                showBackwardIncrementButton = true,
                                showForwardIncrementButton = true,
                                showRepeatModeButton = true,
                            ),
                            repeatMode = repeatMode,
                            onCurrentTimeChanged = {
                                Log.e("CurrentTime", it.toString())
                            },
                            playerInstance = {
                                addAnalyticsListener(object : AnalyticsListener {
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

                                    override fun onPlayWhenReadyChanged(
                                        eventTime: AnalyticsListener.EventTime,
                                        playWhenReady: Boolean,
                                        reason: Int
                                    ) {
                                        Toast.makeText(
                                            context,
                                            "isPlaying = $playWhenReady",
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
