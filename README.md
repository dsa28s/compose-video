# compose-video

## `VideoPlayer` Component for Jetpack Compose

This library is a video player component available in Android Jetpack Compose. <br />
Based on Google [ExoPlayer](https://github.com/google/ExoPlayer).


### Installation

**The library is currently developing. Therefore, you must add it directly as a module in order to use that library.** <br />
After the official release, you can download it from the Maven repository.


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
            url = "https://storage.googleapis.com/exoplayer-test-media-1/gen-3/screens/dash-vod-single-segment/video-137.mp4",
        ),
    ),
    handleLifecycle = true,
    autoPlay = true,
    usePlayerController = true,
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


### Features

- [x] Local video play (ex. assets, storage)
- [x] Network video play (ex. HLS, DASH)
- [x] Video Player Controller (Default)
- [ ] Video Player Custom Controller (using Jetpack Compose)
- [x] Full Screen (With rotate)
- [x] Repeat
- [x] Volume Control
- [ ] Chromecast
- [ ] Buffering config
- [ ] PIP (Picture In Picture)
- [ ] DRM
- [ ] Custom Headers
- [ ] Background Play
- [ ] Rate
- [ ] Resize Mode
- [ ] Select Audio Track
- [ ] Select Video Track
- [ ] SubTitle (with customize style)

### Contributing

The contribution of new features or bug fixes is always welcome. <br />
Please submit new issue or pull request anytime.

### License

Licensed under the Apache 2.0 license. See [LICENSE](LICENSE) for details.
