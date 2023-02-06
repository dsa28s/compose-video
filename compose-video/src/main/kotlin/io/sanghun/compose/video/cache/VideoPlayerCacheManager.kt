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

import android.content.Context
import com.google.android.exoplayer2.database.StandaloneDatabaseProvider
import com.google.android.exoplayer2.upstream.cache.Cache
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import java.io.File

/**
 * Manage video player cache.
 */
object VideoPlayerCacheManager {

    private lateinit var cacheInstance: Cache

    /**
     * Set the cache for video player.
     * It can only be set once in the app, and it is shared and used by multiple video players.
     *
     * @param context Current activity context.
     * @param maxCacheBytes Sets the maximum cache capacity in bytes. If the cache builds up as much as the set capacity, it is deleted from the oldest cache.
     *
     * @throws IllegalStateException Occurs when method is called multiple times.
     */
    fun initialize(context: Context, maxCacheBytes: Long) {
        if (::cacheInstance.isInitialized) {
            throw IllegalStateException("You can only set it up once in the app.")
        }

        cacheInstance = SimpleCache(
            File(context.cacheDir, "video"),
            LeastRecentlyUsedCacheEvictor(maxCacheBytes),
            StandaloneDatabaseProvider(context),
        )
    }

    /**
     * Gets the ExoPlayer cache instance. If null, the cache to be disabled.
     */
    internal fun getCache(): Cache? =
        if (::cacheInstance.isInitialized) {
            cacheInstance
        } else {
            null
        }
}
