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

import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.MediaMetadata
import com.google.android.exoplayer2.util.MimeTypes
import io.sanghun.compose.video.uri.VideoPlayerMediaItem

const val MIME_TYPE_DASH = MimeTypes.APPLICATION_MPD
const val MIME_TYPE_HLS = MimeTypes.APPLICATION_M3U8
const val MIME_TYPE_VIDEO_MP4 = MimeTypes.VIDEO_MP4

/**
 * Sample video from https://github.com/google/ExoPlayer/blob/release-v2/demos/cast/src/main/java/com/google/android/exoplayer2/castdemo/DemoUtil.java
 */
val samplePlayList = listOf(
    VideoPlayerMediaItem.NetworkMediaItem(
        url = "https://storage.googleapis.com/wvmedia/clear/h264/tears/tears.mpd",
        mediaMetadata = MediaMetadata.Builder().setTitle("Clear DASH: Tears").build(),
        mimeType = MIME_TYPE_DASH,
    ),
    VideoPlayerMediaItem.NetworkMediaItem(
        url = "https://storage.googleapis.com/shaka-demo-assets/angel-one-hls/hls.m3u8",
        mediaMetadata = MediaMetadata.Builder().setTitle("Clear HLS: Angel one").build(),
        mimeType = MIME_TYPE_HLS,
    ),
    VideoPlayerMediaItem.NetworkMediaItem(
        url = "https://html5demos.com/assets/dizzy.mp4",
        mediaMetadata = MediaMetadata.Builder().setTitle("Clear MP4: Dizzy").build(),
        mimeType = MIME_TYPE_VIDEO_MP4,
    ),
    // DRM Content
    VideoPlayerMediaItem.NetworkMediaItem(
        url = "https://storage.googleapis.com/wvmedia/cenc/h264/tears/tears.mpd",
        mediaMetadata = MediaMetadata.Builder().setTitle("Widevine DASH cenc: Tears").build(),
        mimeType = MIME_TYPE_DASH,
        drmConfiguration = MediaItem.DrmConfiguration.Builder(C.WIDEVINE_UUID)
            .setLicenseUri("https://proxy.uat.widevine.com/proxy?provider=widevine_test")
            .build(),
    ),
    VideoPlayerMediaItem.NetworkMediaItem(
        url = "https://storage.googleapis.com/wvmedia/cbc1/h264/tears/tears_aes_cbc1.mpd",
        mediaMetadata = MediaMetadata.Builder().setTitle("Widevine DASH cbc1: Tears").build(),
        mimeType = MIME_TYPE_DASH,
        drmConfiguration = MediaItem.DrmConfiguration.Builder(C.WIDEVINE_UUID)
            .setLicenseUri("https://proxy.uat.widevine.com/proxy?provider=widevine_test")
            .build(),
    ),
    VideoPlayerMediaItem.NetworkMediaItem(
        url = "https://storage.googleapis.com/wvmedia/cbcs/h264/tears/tears_aes_cbcs.mpd",
        mediaMetadata = MediaMetadata.Builder().setTitle("Widevine DASH cbcs: Tears").build(),
        mimeType = MIME_TYPE_DASH,
        drmConfiguration = MediaItem.DrmConfiguration.Builder(C.WIDEVINE_UUID)
            .setLicenseUri("https://proxy.uat.widevine.com/proxy?provider=widevine_test")
            .build(),
    ),
)
