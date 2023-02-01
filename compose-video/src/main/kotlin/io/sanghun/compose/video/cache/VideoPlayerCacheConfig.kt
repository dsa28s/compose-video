package io.sanghun.compose.video.cache

import androidx.compose.runtime.Immutable

/**
 * Settings for video cache.
 * The cache method is the Last Recently Used (LRU) method.
 *
 * [VideoPlayerCacheConfig] lets you set whether to enable the cache and the maximum cache size in bytes.
 *
 * @param enableCache Sets whether cache is enabled. Default is true.
 * @param maxCacheSize Sets the maximum cache capacity in bytes. If the cache builds up as much as the set capacity, it is deleted from the oldest cache. Default is 104857600 bytes (100MB).
 */
@Immutable
data class VideoPlayerCacheConfig(
    val enableCache: Boolean,
    val maxCacheSize: Long,
) {

    companion object {

        /**
         * Default config for cache.
         */
        val Default = VideoPlayerCacheConfig(
            enableCache = true,
            maxCacheSize = 100 * 1024 * 1024,
        )
    }
}
