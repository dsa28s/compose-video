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
package io.sanghun.compose.video.uri

import android.net.Uri
import androidx.annotation.RawRes
import com.google.android.exoplayer2.MediaItem.DrmConfiguration
import com.google.android.exoplayer2.MediaMetadata
import io.sanghun.compose.video.uri.VideoPlayerMediaItem.AssetFileMediaItem
import io.sanghun.compose.video.uri.VideoPlayerMediaItem.NetworkMediaItem
import io.sanghun.compose.video.uri.VideoPlayerMediaItem.RawResourceMediaItem
import io.sanghun.compose.video.uri.VideoPlayerMediaItem.StorageMediaItem

interface BaseVideoPlayerMediaItem {
    val mediaMetadata: MediaMetadata
    val mimeType: String
}

/**
 * Representation of a media item for [io.sanghun.compose.video.VideoPlayer].
 *
 * @see RawResourceMediaItem
 * @see AssetFileMediaItem
 * @see StorageMediaItem
 * @see NetworkMediaItem
 */
sealed interface VideoPlayerMediaItem : BaseVideoPlayerMediaItem {

    /**
     * A media item in the raw resource.
     * @param resourceId R.raw.xxxxx resource id
     * @param mediaMetadata Media Metadata. Default is empty.
     * @param mimeType Media mime type.
     */
    data class RawResourceMediaItem(
        @RawRes val resourceId: Int,
        override val mediaMetadata: MediaMetadata = MediaMetadata.EMPTY,
        override val mimeType: String = "",
    ) : VideoPlayerMediaItem

    /**
     * A media item in the assets folder.
     * @param assetPath asset media file path (e.g If there is a test.mp4 file in the assets folder, test.mp4 becomes the assetPath.)
     * @throws com.google.android.exoplayer2.upstream.AssetDataSource.AssetDataSourceException asset file is not exist or load failed.
     * @param mediaMetadata Media Metadata. Default is empty.
     * @param mimeType Media mime type.
     */
    data class AssetFileMediaItem(
        val assetPath: String,
        override val mediaMetadata: MediaMetadata = MediaMetadata.EMPTY,
        override val mimeType: String = "",
    ) : VideoPlayerMediaItem

    /**
     * A media item in the device internal / external storage.
     * @param storageUri storage file uri
     * @param mediaMetadata Media Metadata. Default is empty.
     * @param mimeType Media mime type.
     * @throws com.google.android.exoplayer2.upstream.FileDataSource.FileDataSourceException
     */
    data class StorageMediaItem(
        val storageUri: Uri,
        override val mediaMetadata: MediaMetadata = MediaMetadata.EMPTY,
        override val mimeType: String = "",
    ) : VideoPlayerMediaItem

    /**
     * A media item in the internet
     * @param url network video url'
     * @param mediaMetadata Media Metadata. Default is empty.
     * @param mimeType Media mime type.
     * @param drmConfiguration Drm configuration for media. (Default is null)
     */
    data class NetworkMediaItem(
        val url: String,
        override val mediaMetadata: MediaMetadata = MediaMetadata.EMPTY,
        override val mimeType: String = "",
        val drmConfiguration: DrmConfiguration? = null,
    ) : VideoPlayerMediaItem
}
