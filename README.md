# compose-video <img src="https://img.shields.io/github/v/release/dsa28s/compose-video.svg?label=latest"/>

## `VideoPlayer` Component for Jetpack Compose

This library is a video player component available in Android Jetpack Compose. <br />
Based on [androidx.media3]((https://github.com/google/ExoPlayer)) Package.

In addition to the basic functions of Exoplayer, it supports full screen, PIP, ChromeCast, and more.
All formats supported by ExoPlayer are playable, and DRM content is also supported.

### Installation

The easiest way to get started using VideoPlayer component is to add it as a gradle dependency in the build.gradle file
of your app module.

```gradle
implementation 'io.sanghun:compose-video:1.2.0'
implementation 'androidx.media3:media3-exoplayer:1.1.0' // [Required] androidx.media3 ExoPlayer dependency
implementation 'androidx.media3:media3-session:1.1.0' // [Required] MediaSession Extension dependency
implementation 'androidx.media3:media3-ui:1.1.0' // [Required] Base Player UI

implementation 'androidx.media3:media3-exoplayer-dash:1.1.0' // [Optional] If your media item is DASH
implementation 'androidx.media3:media3-exoplayer-hls:1.1.0' // [Optional] If your media item is HLS (m3u8..)
```

or if your project using `build.gradle.kts`

```kotlin
implementation("io.sanghun:compose-video:1.2.0")
implementation("androidx.media3:media3-exoplayer:1.1.0") // [Required] androidx.media3 ExoPlayer dependency
implementation("androidx.media3:media3-session:1.1.0") // [Required] MediaSession Extension dependency
implementation("androidx.media3:media3-ui:1.1.0") // [Required] Base Player UI

implementation("androidx.media3:media3-exoplayer-dash:1.1.0") // [Optional] If your media item is DASH
implementation("androidx.media3:media3-exoplayer-hls:1.1.0") // [Optional] If your media item is HLS (m3u8..)
```

### Preview 📸

| <img src="https://github.com/dsa28s/compose-video/raw/main/artwork/compose-video-player.png" width="200px"> | <img src="https://github.com/dsa28s/compose-video/raw/main/artwork/fullscreen.gif" width="200px"> | <img src="https://github.com/dsa28s/compose-video/raw/main/artwork/pip.gif" width="200px"> |
|:--:|:--:|:--:|
| **VideoPlayer** | **Full Screen** | **PIP** |

### Usage

```kotlin
VideoPlayer(
    mediaItems = listOf(
        VideoPlayerMediaItem.RawResourceMediaItem(
            resourceId = R.raw.movie1,
        ),
        VideoPlayerMediaItem.AssetFileMediaItem(
            assetPath = "videos/test.mp4"
        ),
        VideoPlayerMediaItem.StorageMediaItem(
            storageUri = "content://xxxxx"
        ),
        VideoPlayerMediaItem.NetworkMediaItem(
            url = "https://storage.googleapis.com/wvmedia/cbcs/h264/tears/tears_aes_cbcs.mpd",
            mediaMetadata = MediaMetadata.Builder().setTitle("Widevine DASH cbcs: Tears").build(),
            mimeType = MimeTypes.APPLICATION_MPD,
            drmConfiguration = MediaItem.DrmConfiguration.Builder(C.WIDEVINE_UUID)
                .setLicenseUri("https://proxy.uat.widevine.com/proxy?provider=widevine_test")
                .build(),
        )
    ),
    handleLifecycle = true,
    autoPlay = true,
    usePlayerController = true,
    enablePip = true,
    handleAudioFocus = true,
    controllerConfig = VideoPlayerControllerConfig(
        showSpeedAndPitchOverlay = false,
        showSubtitleButton = false,
        showCurrentTimeAndTotalTime = true,
        showBufferingProgress = false,
        showForwardIncrementButton = true,
        showBackwardIncrementButton = true,
        showBackTrackButton = true,
        showNextTrackButton = true,
        showRepeatModeButton = true,
        controllerShowTimeMilliSeconds = 5_000,
        controllerAutoShow = true,
    ),
    volume = 0.5f,  // volume 0.0f to 1.0f
    repeatMode = RepeatMode.NONE,       // or RepeatMode.ALL, RepeatMode.ONE
    onCurrentTimeChanged = { // long type, current player time (millisec)
        Log.e("CurrentTime", it.toString())
    },
    playerInstance = { // ExoPlayer instance (Experimental)
        addAnalyticsListener(
            object : AnalyticsListener {
                // player logger
            }
        )
    },
    modifier = Modifier
        .fillMaxSize()
        .align(Alignment.Center),
)
```

### Enable cache

By default, the LRU cache is used. To set up a cache for video player common use, call the method below only once when
the app starts.

```kotlin
VideoPlayerCacheManager.initialize(context, 1024 * 1024 * 1024)    // 1GB
```

### Features

- [x] Migrate Google ExoPlayer -> androidx.media3 Package
  - Now Google ExoPlayer included in androidx.media3 package
- [x] Local video play (ex. assets, storage)
- [x] Network video play (ex. HLS, DASH)
- [x] Video Player Controller (Default)
- [ ] Video Player Custom Controller (using Jetpack Compose)
- [x] Audio Focus
- [x] Full Screen (With rotate)
- [x] Repeat
- [x] Volume Control
- [x] Select Audio Track
- [x] Select Video Track
- [x] SubTitle
- [ ] Chromecast
- [ ] Buffering config
- [x] PIP (Picture In Picture)
- [x] DRM
- [ ] Custom Headers
- [x] Background Play
- [ ] Rate
- [ ] Resize Mode
- [x] Video Caching

### Contributing

The contribution of new features or bug fixes is always welcome. <br />
Please submit new issue or pull request anytime.

### License

Licensed under the Apache 2.0 license. See [LICENSE](LICENSE) for details.
