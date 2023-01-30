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

import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.ui.StyledPlayerView.SHOW_BUFFERING_ALWAYS
import com.google.android.exoplayer2.ui.StyledPlayerView.SHOW_BUFFERING_NEVER
import com.google.android.exoplayer2.util.RepeatModeUtil.REPEAT_TOGGLE_MODE_ALL
import com.google.android.exoplayer2.util.RepeatModeUtil.REPEAT_TOGGLE_MODE_NONE
import com.google.android.exoplayer2.util.RepeatModeUtil.REPEAT_TOGGLE_MODE_ONE
import io.sanghun.compose.video.controller.VideoPlayerControllerConfig
import io.sanghun.compose.video.uri.VideoPlayerMediaItem
import io.sanghun.compose.video.uri.toUri
import kotlinx.coroutines.delay

/**
 * [VideoPlayer] is UI component that can play video in Jetpack Compose. It works based on ExoPlayer.
 * You can play local (e.g. asset files, resource files) files and all video files in the network environment.
 * For all video formats supported by the [VideoPlayer] component, see the ExoPlayer link below.
 *
 * If you rotate the screen, the default action is to reset the player state.
 * To prevent this happening, put the following options in the `android:configChanges` option of your app's AndroidManifest.xml to keep the settings.
 * ```
 * keyboard|keyboardHidden|orientation|screenSize|screenLayout|smallestScreenSize|uiMode
 * ```
 *
 * This component is linked with Compose [androidx.compose.runtime.DisposableEffect].
 * This means that it move out of the Composable Scope, the ExoPlayer instance will automatically be destroyed as well.
 *
 * @see <a href="https://exoplayer.dev/supported-formats.html">Exoplayer Support Formats</a>
 *
 * @param modifier Modifier to apply to this layout node.
 * @param mediaItems [VideoPlayerMediaItem] to be played by the video player. The reason for receiving media items as an array is to configure multi-track. If it's a single track, provide a single list (e.g. listOf(mediaItem)).
 * @param handleLifecycle Sets whether to automatically play/stop the player according to the activity lifecycle. Default is true.
 * @param autoPlay Autoplay when media item prepared. Default is true.
 * @param usePlayerController Using player controller. Default is true.
 * @param controllerConfig Player controller config. You can customize the Video Player Controller UI.
 * @param seekBeforeMilliSeconds The seek back increment, in milliseconds. Default is 10sec (10000ms)
 * @param seekAfterMilliSeconds The seek forward increment, in milliseconds. Default is 10sec (10000ms)
 * @param repeatMode Sets the content repeat mode.
 * @param playerInstance Return exoplayer instance. This instance allows you to add [com.google.android.exoplayer2.analytics.AnalyticsListener] to receive various events from the player.
 */
@Composable
fun VideoPlayer(
    modifier: Modifier = Modifier,
    mediaItems: List<VideoPlayerMediaItem>,
    handleLifecycle: Boolean = true,
    autoPlay: Boolean = true,
    usePlayerController: Boolean = true,
    controllerConfig: VideoPlayerControllerConfig = VideoPlayerControllerConfig.Default,
    seekBeforeMilliSeconds: Long = 10000L,
    seekAfterMilliSeconds: Long = 10000L,
    repeatMode: RepeatMode = RepeatMode.NONE,
    playerInstance: ExoPlayer.() -> Unit = {},
) {
    val context = LocalContext.current
    var currentTime by remember { mutableStateOf(0L) }

    val player = remember(seekAfterMilliSeconds, seekBeforeMilliSeconds) {
        ExoPlayer.Builder(context)
            .setSeekBackIncrementMs(seekBeforeMilliSeconds)
            .setSeekForwardIncrementMs(seekAfterMilliSeconds)
            .build()
            .also(playerInstance)
    }

    val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)

    val defaultPlayerView = remember {
        StyledPlayerView(context)
    }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            currentTime = player.currentPosition
        }
    }

    LaunchedEffect(usePlayerController) {
        defaultPlayerView.useController = usePlayerController
    }

    LaunchedEffect(player) {
        defaultPlayerView.player = player
    }

    LaunchedEffect(mediaItems, player) {
        val exoPlayerMediaItems = mediaItems.map {
            MediaItem.Builder()
                .apply {
                    val uri = it.toUri(context)
                    setUri(uri)
                }.build()
        }

        player.setMediaItems(exoPlayerMediaItems)
        player.prepare()

        if (autoPlay) {
            player.play()
        }
    }

    LaunchedEffect(controllerConfig) {
        val controllerView = defaultPlayerView.rootView
        controllerView.findViewById<View>(com.google.android.exoplayer2.R.id.exo_settings).isVisible =
            controllerConfig.showSpeedAndPitchOverlay
        defaultPlayerView.setShowSubtitleButton(controllerConfig.showSubtitleButton)
        controllerView.findViewById<View>(com.google.android.exoplayer2.R.id.exo_time).isVisible =
            controllerConfig.showCurrentTimeAndTotalTime
        defaultPlayerView.setShowBuffering(
            if (!controllerConfig.showBufferingProgress) SHOW_BUFFERING_NEVER else SHOW_BUFFERING_ALWAYS,
        )
        controllerView.findViewById<View>(com.google.android.exoplayer2.R.id.exo_ffwd_with_amount).isVisible =
            controllerConfig.showForwardIncrementButton
        controllerView.findViewById<View>(com.google.android.exoplayer2.R.id.exo_rew_with_amount).isVisible =
            controllerConfig.showBackwardIncrementButton
        defaultPlayerView.setShowNextButton(controllerConfig.showNextTrackButton)
        defaultPlayerView.setShowPreviousButton(controllerConfig.showBackTrackButton)
        defaultPlayerView.setShowFastForwardButton(controllerConfig.showForwardIncrementButton)
        defaultPlayerView.setShowRewindButton(controllerConfig.showBackwardIncrementButton)
        defaultPlayerView.controllerShowTimeoutMs = controllerConfig.controllerShowTimeMilliSeconds
        defaultPlayerView.controllerAutoShow = controllerConfig.controllerAutoShow
    }

    LaunchedEffect(controllerConfig, repeatMode) {
        defaultPlayerView.setRepeatToggleModes(
            if (controllerConfig.showRepeatModeButton) {
                REPEAT_TOGGLE_MODE_ALL or REPEAT_TOGGLE_MODE_ONE
            } else {
                REPEAT_TOGGLE_MODE_NONE
            },
        )
        player.repeatMode = repeatMode.toExoPlayerRepeatMode()
    }

    DisposableEffect(
        AndroidView(
            modifier = modifier,
            factory = {
                defaultPlayerView.apply {
                    useController = usePlayerController
                }
            },
        ),
    ) {
        val observer = LifecycleEventObserver { _, event ->
            if (handleLifecycle) {
                when (event) {
                    Lifecycle.Event.ON_PAUSE -> {
                        player.pause()
                    }

                    Lifecycle.Event.ON_RESUME -> {
                        player.play()
                    }

                    else -> {}
                }
            }
        }
        val lifecycle = lifecycleOwner.value.lifecycle
        lifecycle.addObserver(observer)

        onDispose {
            player.release()
            lifecycle.removeObserver(observer)
        }
    }
}
