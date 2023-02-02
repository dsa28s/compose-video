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
