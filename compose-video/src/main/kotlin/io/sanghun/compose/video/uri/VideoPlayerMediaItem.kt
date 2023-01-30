package io.sanghun.compose.video.uri

import android.net.Uri
import androidx.annotation.RawRes
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import io.sanghun.compose.video.uri.VideoPlayerMediaItem.AssetFileMediaItem
import io.sanghun.compose.video.uri.VideoPlayerMediaItem.NetworkMediaItem
import io.sanghun.compose.video.uri.VideoPlayerMediaItem.RawResourceMediaItem

/**
 * Representation of a media item for [io.sanghun.compose.video.VideoPlayer].
 *
 * @see RawResourceMediaItem
 * @see AssetFileMediaItem
 * @see NetworkMediaItem
 */
@Immutable
sealed interface VideoPlayerMediaItem {

    /**
     * A media item in the raw resource.
     * @param resourceId R.raw.xxxxx resource id
     */
    @Immutable
    data class RawResourceMediaItem(
        @RawRes val resourceId: Int,
    ) : VideoPlayerMediaItem

    /**
     * A media item in the assets folder.
     * @param assetPath asset media file path (e.g If there is a test.mp4 file in the assets folder, test.mp4 becomes the assetPath.)
     * @throws com.google.android.exoplayer2.upstream.AssetDataSource.AssetDataSourceException asset file is not exist or load failed
     */
    @Immutable
    data class AssetFileMediaItem(
        val assetPath: String,
    ) : VideoPlayerMediaItem

    /**
     * A media item in the device internal / external storage.
     * @param storageUri storage file uri
     * @throws com.google.android.exoplayer2.upstream.FileDataSource.FileDataSourceException
     */
    @Stable
    data class StorageMediaItem(
        val storageUri: Uri,
    ) : VideoPlayerMediaItem

    /**
     * A media item in the internet
     * @param url network video url
     */
    @Immutable
    data class NetworkMediaItem(
        val url: String,
    ) : VideoPlayerMediaItem
}
