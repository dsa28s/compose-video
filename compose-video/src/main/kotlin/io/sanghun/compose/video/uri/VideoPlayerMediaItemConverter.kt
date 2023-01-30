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

import android.content.Context
import android.net.Uri
import com.google.android.exoplayer2.upstream.AssetDataSource
import com.google.android.exoplayer2.upstream.DataSpec
import com.google.android.exoplayer2.upstream.FileDataSource
import com.google.android.exoplayer2.upstream.FileDataSource.FileDataSourceException
import com.google.android.exoplayer2.upstream.RawResourceDataSource

/**
 * Converts [VideoPlayerMediaItem] to [android.net.Uri].
 *
 * @param context Pass application context or activity context. Use this context to load asset file using [android.content.res.AssetManager].
 * @return [android.net.Uri]
 */
internal fun VideoPlayerMediaItem.toUri(
    context: Context,
): Uri = when (this) {
    is VideoPlayerMediaItem.RawResourceMediaItem -> {
        RawResourceDataSource.buildRawResourceUri(resourceId)
    }

    is VideoPlayerMediaItem.AssetFileMediaItem -> {
        val dataSpec = DataSpec(Uri.parse("asset:///$assetPath"))
        val assetDataSource = AssetDataSource(context)
        try {
            assetDataSource.open(dataSpec)
        } catch (e: AssetDataSource.AssetDataSourceException) {
            e.printStackTrace()
        }

        assetDataSource.uri ?: Uri.EMPTY
    }

    is VideoPlayerMediaItem.NetworkMediaItem -> {
        Uri.parse(url)
    }

    is VideoPlayerMediaItem.StorageMediaItem -> {
        val dataSpec = DataSpec(storageUri)
        val fileDataSource = FileDataSource()
        try {
            fileDataSource.open(dataSpec)
        } catch (e: FileDataSourceException) {
            e.printStackTrace()
        }

        fileDataSource.uri ?: Uri.EMPTY
    }
}
