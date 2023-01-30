package io.sanghun.compose.video.controller

import androidx.compose.runtime.Immutable

/**
 * Sets the detailed properties of the [io.sanghun.compose.video.VideoPlayer] Controller.
 *
 * @param showSpeedAndPitchOverlay Visible speed, audio track select button.
 */
@Immutable
data class VideoPlayerControllerConfig(
    val showSpeedAndPitchOverlay: Boolean,
) {

    companion object {
        /**
         * Default config for Controller.
         */
        val Default = VideoPlayerControllerConfig(
            showSpeedAndPitchOverlay = true,
        )
    }
}
