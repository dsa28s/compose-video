package io.sanghun.compose.video.uri

import androidx.annotation.RawRes
import androidx.compose.runtime.Immutable

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
     */
    @Immutable
    data class AssetFileMediaItem(
        val assetPath: String,
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
