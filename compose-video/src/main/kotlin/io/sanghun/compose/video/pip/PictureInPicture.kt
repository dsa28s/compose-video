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
package io.sanghun.compose.video.pip

import android.app.PictureInPictureParams
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import com.google.android.exoplayer2.ui.StyledPlayerView
import io.sanghun.compose.video.util.findActivity

/**
 * Enables PIP mode for the current activity.
 *
 * @param context Activity context.
 * @param defaultPlayerView Current video player controller.
 */
@Suppress("DEPRECATION")
internal fun enterPIPMode(context: Context, defaultPlayerView: StyledPlayerView) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N &&
        context.packageManager.hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE)
    ) {
        defaultPlayerView.useController = false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val params = PictureInPictureParams.Builder()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                params
                    .setTitle("Video Player")
            }

            context.findActivity().enterPictureInPictureMode(params.build())
        } else {
            context.findActivity().enterPictureInPictureMode()
        }
    }
}
