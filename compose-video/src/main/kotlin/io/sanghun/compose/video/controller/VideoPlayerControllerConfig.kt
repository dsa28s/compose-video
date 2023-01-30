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
package io.sanghun.compose.video.controller

import androidx.compose.runtime.Immutable

/**
 * Sets the detailed properties of the [io.sanghun.compose.video.VideoPlayer] Controller.
 *
 * @param showSpeedAndPitchOverlay Visible speed, audio track select button.
 * @param showSubtitleButton Visible subtitle (CC) button.
 * @param showCurrentTimeAndTotalTime Visible currentTime, totalTime text.
 * @param showBufferingProgress Visible buffering progress.
 * @param showForwardIncrementButton Show forward increment button.
 * @param showBackwardIncrementButton Show backward increment button.
 */
@Immutable
data class VideoPlayerControllerConfig(
    val showSpeedAndPitchOverlay: Boolean,
    val showSubtitleButton: Boolean,
    val showCurrentTimeAndTotalTime: Boolean,
    val showBufferingProgress: Boolean,
    val showForwardIncrementButton: Boolean,
    val showBackwardIncrementButton: Boolean,
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
            showForwardIncrementButton = false,
            showBackwardIncrementButton = false,
        )
    }
}
