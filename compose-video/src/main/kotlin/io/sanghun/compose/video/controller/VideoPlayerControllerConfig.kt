package io.sanghun.compose.video.controller

import androidx.compose.runtime.Immutable

/**
 * Sets the detailed properties of the [io.sanghun.compose.video.VideoPlayer] Controller.
 *
 * @param showSpeedAndPitchOverlay Visible speed, audio track select button.
 * @param showSubtitleButton Visible subtitle (CC) button.
 * @param showCurrentTimeAndTotalTime Visible currentTime, totalTime text.
 * @param showBufferingProgress Visible buffering progress.
 */
@Immutable
data class VideoPlayerControllerConfig(
    val showSpeedAndPitchOverlay: Boolean,
    val showSubtitleButton: Boolean,
    val showCurrentTimeAndTotalTime: Boolean,
    val showBufferingProgress: Boolean,
) {

    companion object {
        /**
         * Default config for Controller.
         */
        val Default = VideoPlayerControllerConfig(
            showSpeedAndPitchOverlay = false,
            showSubtitleButton = true,
            showCurrentTimeAndTotalTime = true,
            showBufferingProgress = false,
        )
    }
}
