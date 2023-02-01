package io.sanghun.compose.video.cache

import android.content.Context
import com.google.android.exoplayer2.database.StandaloneDatabaseProvider
import com.google.android.exoplayer2.upstream.cache.Cache
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import java.io.File

internal object VideoPlayerCacheManager {

    private lateinit var cacheInstance: Cache

    fun initializeOrGetInstance(context: Context, maxCacheBytes: Long): Cache {
        if (::cacheInstance.isInitialized) {
            return cacheInstance
        }

        cacheInstance = SimpleCache(
            File(context.cacheDir, "video"),
            LeastRecentlyUsedCacheEvictor(maxCacheBytes),
            StandaloneDatabaseProvider(context)
        )

        return cacheInstance
    }
}
