---
title: camerax_color_detect
lang: zh-tw
tags: github
---

[![hackmd-github-sync-badge](https://hackmd.io/ECZWdPoMQI2U9fR0du-ckA/badge)](https://hackmd.io/ECZWdPoMQI2U9fR0du-ckA)

---

# Code branch announcement

- goodCVbasic
> Mobile device computing
- main
> Streaming and remote image analysis

---

# camerax_color_detect

[demo video](https://youtu.be/2Amm6SG2TCs)

## introduction

Use the new version of the Android camera api to read the camera image, and use its image analysis function to obtain the data of each frame

When the user provides a 12-color color card, align the 12-color border in the UI, and provide a four-line sampling target in the center of the UI, and compare the four-line sampling target's desired color with the reference color

Give the color matching information of the target object

### Branch relevance

This branch is based on the following branch:

[camPdfHttpAndriod](https://github.com/andythebreaker/camPdfHttpAndriod)

## mechanism

### Pixel sampling

Only sample the central single point of the frame line visualization

### Color comparison

![](https://i.imgur.com/fQn4WZJ.png)


![](https://i.imgur.com/lEy6VNo.png)


euclidean distance base clustering strategy is employed to regcognize color bars 
(REF: 
[Real-time resistor color code recognition using image processing in mobile devce](https://ieeexplore.ieee.org/document/8710533) 
)

### UI alignment border

Between the following lines of the [main program](https://github.com/andythebreaker/camerax_color_detect/blob/master/app/src/main/java/me/andythebreaker/camerax_color_detect/MainActivity.java)

`Bitmap draw_a_rect_bitmap = Bitmap.createBitmap(cam_phy_width, cam_phy_height/*720,720*/, Bitmap.Config.ARGB_8888);`

`imageViewcan.setImageBitmap(draw_a_rect_bitmap);`

## In development

### Abandon the use of java language and replace it with PWA to increase compatibility

It is expected to use RTSP to transmit real-time video signals, and create a client to analyze the video signals.

The following packages are currently found:

|instruction|link|Test Results|
|--|--|--
|Real-time streaming library based on Camera 2||Could not be successfully created|
|Real-time streaming server that has been packaged into an app, which can read camera images||The test is successful, it is expected to learn more about its code to know how to implement it|
|Open source real-time streaming server, such use can avoid floating IP problems, it should be a standard solution||Part of the test was successful, but there was a problem of not being able to receive the signal when crossing the local area network, and further debugging is required|

There are still some solutions to be tested in the starred area of my github, and it is expected to continue to try

All in all, the new version of the camera api does not have very complete streaming support. I expect to use the current image analysis method to package the images and throw them out in a streaming way; but need to understand the streaming protocol better

## Results and discussion

### a. defect

    1. ink color hex/print diff. (data also diff.)
    2. res. 4 color bar not in same place
    3. object too small (phy.), NON-usr-friendly
    4. paper (color ref.) reflect, more when ink increase

to fix a. ref. to peaper

    - use 'any' way to recog. color "bar"
    - edge dect. (?)
    - or peak vlaue method (?)

### b. realtime prob.

    1. no need realrime
    2. cap. 1 frfam and send back to srv. , use py. to analysis
    3. do avg. instead of one pixel

to fix b. 

    - use stream tech. (?)
    - use parl. instead of for loop (?)

## Known defects

- Many UI components are no longer needed, but only use hidden methods
- The data column in the bottom row of UI layout should respond dynamically

Please refer to the TODO part of the code

## environment

use 

    compile sdk version = 30

	min sdk v. = 26
	
	target sdk v. = 29

to avoid problem @ saving img.

##### error s.t.:

`EPERM`

![](https://i.imgur.com/bC7rhxW.jpg)


## More brilliant parts that may be practiced

Monochrome-based color correction

# TODO

```=
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
I/ax_color_detec: NativeAlloc concurrent copying GC freed 29831(1130KB) AllocSpace objects, 0(0B) LOS objects, 80% free, 4561KB/22MB, paused 182us total 106.037ms
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: calloc(1, 31392) failed: returning null pointer
E/Camera2-Metadata: resizeIfNeeded: Can't allocate larger metadata buffer
    updateImpl: Unable to update metadata entry android.lens.info.shadingMapSize (90006): Out of memory (-12)
W/Binder: Caught a RuntimeException from the binder stub implementation.
    java.lang.IllegalStateException: Unknown error (-12) while trying to update metadata
        at android.hardware.camera2.impl.CameraMetadataNative.nativeWriteValues(Native Method)
        at android.hardware.camera2.impl.CameraMetadataNative.writeValues(CameraMetadataNative.java:1876)
        at android.hardware.camera2.impl.CameraMetadataNative.setBase(CameraMetadataNative.java:1558)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:491)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:503)
        at android.hardware.camera2.impl.CameraDeviceImpl$CameraDeviceCallbacks.onResultReceived(CameraDeviceImpl.java:1937)
        at android.hardware.camera2.ICameraDeviceCallbacks$Stub.onTransact(ICameraDeviceCallbacks.java:182)
        at android.os.Binder.execTransactInternal(Binder.java:1195)
        at android.os.Binder.execTransact(Binder.java:1159)
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: calloc(1, 31392) failed: returning null pointer
E/Camera2-Metadata: resizeIfNeeded: Can't allocate larger metadata buffer
    updateImpl: Unable to update metadata entry android.lens.info.shadingMapSize (90006): Out of memory (-12)
W/Binder: Caught a RuntimeException from the binder stub implementation.
    java.lang.IllegalStateException: Unknown error (-12) while trying to update metadata
        at android.hardware.camera2.impl.CameraMetadataNative.nativeWriteValues(Native Method)
        at android.hardware.camera2.impl.CameraMetadataNative.writeValues(CameraMetadataNative.java:1876)
        at android.hardware.camera2.impl.CameraMetadataNative.setBase(CameraMetadataNative.java:1558)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:491)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:503)
        at android.hardware.camera2.impl.CameraDeviceImpl$CameraDeviceCallbacks.onResultReceived(CameraDeviceImpl.java:1937)
        at android.hardware.camera2.ICameraDeviceCallbacks$Stub.onTransact(ICameraDeviceCallbacks.java:182)
        at android.os.Binder.execTransactInternal(Binder.java:1195)
        at android.os.Binder.execTransact(Binder.java:1159)
W/libc: calloc(1, 31392) failed: returning null pointer
E/Camera2-Metadata: resizeIfNeeded: Can't allocate larger metadata buffer
    updateImpl: Unable to update metadata entry android.lens.info.shadingMapSize (90006): Out of memory (-12)
W/Binder: Caught a RuntimeException from the binder stub implementation.
    java.lang.IllegalStateException: Unknown error (-12) while trying to update metadata
        at android.hardware.camera2.impl.CameraMetadataNative.nativeWriteValues(Native Method)
        at android.hardware.camera2.impl.CameraMetadataNative.writeValues(CameraMetadataNative.java:1876)
        at android.hardware.camera2.impl.CameraMetadataNative.setBase(CameraMetadataNative.java:1558)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:491)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:503)
        at android.hardware.camera2.impl.CameraDeviceImpl$CameraDeviceCallbacks.onResultReceived(CameraDeviceImpl.java:1937)
        at android.hardware.camera2.ICameraDeviceCallbacks$Stub.onTransact(ICameraDeviceCallbacks.java:182)
        at android.os.Binder.execTransactInternal(Binder.java:1195)
        at android.os.Binder.execTransact(Binder.java:1159)
W/libc: calloc(1, 31392) failed: returning null pointer
E/Camera2-Metadata: resizeIfNeeded: Can't allocate larger metadata buffer
    updateImpl: Unable to update metadata entry android.lens.info.shadingMapSize (90006): Out of memory (-12)
W/Binder: Caught a RuntimeException from the binder stub implementation.
    java.lang.IllegalStateException: Unknown error (-12) while trying to update metadata
        at android.hardware.camera2.impl.CameraMetadataNative.nativeWriteValues(Native Method)
        at android.hardware.camera2.impl.CameraMetadataNative.writeValues(CameraMetadataNative.java:1876)
        at android.hardware.camera2.impl.CameraMetadataNative.setBase(CameraMetadataNative.java:1558)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:491)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:503)
        at android.hardware.camera2.impl.CameraDeviceImpl$CameraDeviceCallbacks.onResultReceived(CameraDeviceImpl.java:1937)
        at android.hardware.camera2.ICameraDeviceCallbacks$Stub.onTransact(ICameraDeviceCallbacks.java:182)
        at android.os.Binder.execTransactInternal(Binder.java:1195)
        at android.os.Binder.execTransact(Binder.java:1159)
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: calloc(1, 31392) failed: returning null pointer
E/Camera2-Metadata: resizeIfNeeded: Can't allocate larger metadata buffer
    updateImpl: Unable to update metadata entry android.lens.info.shadingMapSize (90006): Out of memory (-12)
W/Binder: Caught a RuntimeException from the binder stub implementation.
    java.lang.IllegalStateException: Unknown error (-12) while trying to update metadata
        at android.hardware.camera2.impl.CameraMetadataNative.nativeWriteValues(Native Method)
        at android.hardware.camera2.impl.CameraMetadataNative.writeValues(CameraMetadataNative.java:1876)
        at android.hardware.camera2.impl.CameraMetadataNative.setBase(CameraMetadataNative.java:1558)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:491)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:503)
        at android.hardware.camera2.impl.CameraDeviceImpl$CameraDeviceCallbacks.onResultReceived(CameraDeviceImpl.java:1937)
        at android.hardware.camera2.ICameraDeviceCallbacks$Stub.onTransact(ICameraDeviceCallbacks.java:182)
        at android.os.Binder.execTransactInternal(Binder.java:1195)
        at android.os.Binder.execTransact(Binder.java:1159)
W/libc: calloc(1, 31392) failed: returning null pointer
E/Camera2-Metadata: resizeIfNeeded: Can't allocate larger metadata buffer
    updateImpl: Unable to update metadata entry android.lens.info.shadingMapSize (90006): Out of memory (-12)
W/Binder: Caught a RuntimeException from the binder stub implementation.
    java.lang.IllegalStateException: Unknown error (-12) while trying to update metadata
        at android.hardware.camera2.impl.CameraMetadataNative.nativeWriteValues(Native Method)
        at android.hardware.camera2.impl.CameraMetadataNative.writeValues(CameraMetadataNative.java:1876)
        at android.hardware.camera2.impl.CameraMetadataNative.setBase(CameraMetadataNative.java:1558)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:491)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:503)
        at android.hardware.camera2.impl.CameraDeviceImpl$CameraDeviceCallbacks.onResultReceived(CameraDeviceImpl.java:1937)
        at android.hardware.camera2.ICameraDeviceCallbacks$Stub.onTransact(ICameraDeviceCallbacks.java:182)
        at android.os.Binder.execTransactInternal(Binder.java:1195)
        at android.os.Binder.execTransact(Binder.java:1159)
W/libc: calloc(1, 31392) failed: returning null pointer
E/Camera2-Metadata: resizeIfNeeded: Can't allocate larger metadata buffer
    updateImpl: Unable to update metadata entry android.lens.info.shadingMapSize (90006): Out of memory (-12)
W/Binder: Caught a RuntimeException from the binder stub implementation.
    java.lang.IllegalStateException: Unknown error (-12) while trying to update metadata
        at android.hardware.camera2.impl.CameraMetadataNative.nativeWriteValues(Native Method)
        at android.hardware.camera2.impl.CameraMetadataNative.writeValues(CameraMetadataNative.java:1876)
        at android.hardware.camera2.impl.CameraMetadataNative.setBase(CameraMetadataNative.java:1558)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:491)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:503)
        at android.hardware.camera2.impl.CameraDeviceImpl$CameraDeviceCallbacks.onResultReceived(CameraDeviceImpl.java:1937)
        at android.hardware.camera2.ICameraDeviceCallbacks$Stub.onTransact(ICameraDeviceCallbacks.java:182)
        at android.os.Binder.execTransactInternal(Binder.java:1195)
        at android.os.Binder.execTransact(Binder.java:1159)
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: calloc(1, 31392) failed: returning null pointer
E/Camera2-Metadata: resizeIfNeeded: Can't allocate larger metadata buffer
    updateImpl: Unable to update metadata entry android.lens.info.shadingMapSize (90006): Out of memory (-12)
W/Binder: Caught a RuntimeException from the binder stub implementation.
    java.lang.IllegalStateException: Unknown error (-12) while trying to update metadata
        at android.hardware.camera2.impl.CameraMetadataNative.nativeWriteValues(Native Method)
        at android.hardware.camera2.impl.CameraMetadataNative.writeValues(CameraMetadataNative.java:1876)
        at android.hardware.camera2.impl.CameraMetadataNative.setBase(CameraMetadataNative.java:1558)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:491)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:503)
        at android.hardware.camera2.impl.CameraDeviceImpl$CameraDeviceCallbacks.onResultReceived(CameraDeviceImpl.java:1937)
        at android.hardware.camera2.ICameraDeviceCallbacks$Stub.onTransact(ICameraDeviceCallbacks.java:182)
        at android.os.Binder.execTransactInternal(Binder.java:1195)
        at android.os.Binder.execTransact(Binder.java:1159)
W/libc: calloc(1, 31392) failed: returning null pointer
E/Camera2-Metadata: resizeIfNeeded: Can't allocate larger metadata buffer
    updateImpl: Unable to update metadata entry android.lens.info.shadingMapSize (90006): Out of memory (-12)
W/Binder: Caught a RuntimeException from the binder stub implementation.
    java.lang.IllegalStateException: Unknown error (-12) while trying to update metadata
        at android.hardware.camera2.impl.CameraMetadataNative.nativeWriteValues(Native Method)
        at android.hardware.camera2.impl.CameraMetadataNative.writeValues(CameraMetadataNative.java:1876)
        at android.hardware.camera2.impl.CameraMetadataNative.setBase(CameraMetadataNative.java:1558)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:491)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:503)
        at android.hardware.camera2.impl.CameraDeviceImpl$CameraDeviceCallbacks.onResultReceived(CameraDeviceImpl.java:1937)
        at android.hardware.camera2.ICameraDeviceCallbacks$Stub.onTransact(ICameraDeviceCallbacks.java:182)
        at android.os.Binder.execTransactInternal(Binder.java:1195)
        at android.os.Binder.execTransact(Binder.java:1159)
W/libc: calloc(1, 31392) failed: returning null pointer
E/Camera2-Metadata: resizeIfNeeded: Can't allocate larger metadata buffer
    updateImpl: Unable to update metadata entry android.lens.info.shadingMapSize (90006): Out of memory (-12)
W/Binder: Caught a RuntimeException from the binder stub implementation.
    java.lang.IllegalStateException: Unknown error (-12) while trying to update metadata
        at android.hardware.camera2.impl.CameraMetadataNative.nativeWriteValues(Native Method)
        at android.hardware.camera2.impl.CameraMetadataNative.writeValues(CameraMetadataNative.java:1876)
        at android.hardware.camera2.impl.CameraMetadataNative.setBase(CameraMetadataNative.java:1558)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:491)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:503)
        at android.hardware.camera2.impl.CameraDeviceImpl$CameraDeviceCallbacks.onResultReceived(CameraDeviceImpl.java:1937)
        at android.hardware.camera2.ICameraDeviceCallbacks$Stub.onTransact(ICameraDeviceCallbacks.java:182)
        at android.os.Binder.execTransactInternal(Binder.java:1195)
        at android.os.Binder.execTransact(Binder.java:1159)
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: calloc(1, 31392) failed: returning null pointer
E/Camera2-Metadata: resizeIfNeeded: Can't allocate larger metadata buffer
    updateImpl: Unable to update metadata entry android.lens.info.shadingMapSize (90006): Out of memory (-12)
W/Binder: Caught a RuntimeException from the binder stub implementation.
    java.lang.IllegalStateException: Unknown error (-12) while trying to update metadata
        at android.hardware.camera2.impl.CameraMetadataNative.nativeWriteValues(Native Method)
        at android.hardware.camera2.impl.CameraMetadataNative.writeValues(CameraMetadataNative.java:1876)
        at android.hardware.camera2.impl.CameraMetadataNative.setBase(CameraMetadataNative.java:1558)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:491)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:503)
        at android.hardware.camera2.impl.CameraDeviceImpl$CameraDeviceCallbacks.onResultReceived(CameraDeviceImpl.java:1937)
        at android.hardware.camera2.ICameraDeviceCallbacks$Stub.onTransact(ICameraDeviceCallbacks.java:182)
        at android.os.Binder.execTransactInternal(Binder.java:1195)
        at android.os.Binder.execTransact(Binder.java:1159)
W/libc: calloc(1, 31392) failed: returning null pointer
E/Camera2-Metadata: resizeIfNeeded: Can't allocate larger metadata buffer
    updateImpl: Unable to update metadata entry android.lens.info.shadingMapSize (90006): Out of memory (-12)
W/Binder: Caught a RuntimeException from the binder stub implementation.
    java.lang.IllegalStateException: Unknown error (-12) while trying to update metadata
        at android.hardware.camera2.impl.CameraMetadataNative.nativeWriteValues(Native Method)
        at android.hardware.camera2.impl.CameraMetadataNative.writeValues(CameraMetadataNative.java:1876)
        at android.hardware.camera2.impl.CameraMetadataNative.setBase(CameraMetadataNative.java:1558)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:491)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:503)
        at android.hardware.camera2.impl.CameraDeviceImpl$CameraDeviceCallbacks.onResultReceived(CameraDeviceImpl.java:1937)
        at android.hardware.camera2.ICameraDeviceCallbacks$Stub.onTransact(ICameraDeviceCallbacks.java:182)
        at android.os.Binder.execTransactInternal(Binder.java:1195)
        at android.os.Binder.execTransact(Binder.java:1159)
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: calloc(1, 31392) failed: returning null pointer
E/Camera2-Metadata: resizeIfNeeded: Can't allocate larger metadata buffer
    updateImpl: Unable to update metadata entry android.lens.info.shadingMapSize (90006): Out of memory (-12)
W/Binder: Caught a RuntimeException from the binder stub implementation.
    java.lang.IllegalStateException: Unknown error (-12) while trying to update metadata
        at android.hardware.camera2.impl.CameraMetadataNative.nativeWriteValues(Native Method)
        at android.hardware.camera2.impl.CameraMetadataNative.writeValues(CameraMetadataNative.java:1876)
        at android.hardware.camera2.impl.CameraMetadataNative.setBase(CameraMetadataNative.java:1558)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:491)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:503)
        at android.hardware.camera2.impl.CameraDeviceImpl$CameraDeviceCallbacks.onResultReceived(CameraDeviceImpl.java:1937)
        at android.hardware.camera2.ICameraDeviceCallbacks$Stub.onTransact(ICameraDeviceCallbacks.java:182)
        at android.os.Binder.execTransactInternal(Binder.java:1195)
        at android.os.Binder.execTransact(Binder.java:1159)
W/libc: calloc(1, 31392) failed: returning null pointer
E/Camera2-Metadata: resizeIfNeeded: Can't allocate larger metadata buffer
    updateImpl: Unable to update metadata entry android.lens.info.shadingMapSize (90006): Out of memory (-12)
W/Binder: Caught a RuntimeException from the binder stub implementation.
    java.lang.IllegalStateException: Unknown error (-12) while trying to update metadata
        at android.hardware.camera2.impl.CameraMetadataNative.nativeWriteValues(Native Method)
        at android.hardware.camera2.impl.CameraMetadataNative.writeValues(CameraMetadataNative.java:1876)
        at android.hardware.camera2.impl.CameraMetadataNative.setBase(CameraMetadataNative.java:1558)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:491)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:503)
        at android.hardware.camera2.impl.CameraDeviceImpl$CameraDeviceCallbacks.onResultReceived(CameraDeviceImpl.java:1937)
        at android.hardware.camera2.ICameraDeviceCallbacks$Stub.onTransact(ICameraDeviceCallbacks.java:182)
        at android.os.Binder.execTransactInternal(Binder.java:1195)
        at android.os.Binder.execTransact(Binder.java:1159)
W/libc: calloc(1, 31392) failed: returning null pointer
E/Camera2-Metadata: resizeIfNeeded: Can't allocate larger metadata buffer
    updateImpl: Unable to update metadata entry android.lens.info.shadingMapSize (90006): Out of memory (-12)
W/libc: memalign(16, 720000) failed: returning null pointer
W/Binder: Caught a RuntimeException from the binder stub implementation.
    java.lang.IllegalStateException: Unknown error (-12) while trying to update metadata
        at android.hardware.camera2.impl.CameraMetadataNative.nativeWriteValues(Native Method)
        at android.hardware.camera2.impl.CameraMetadataNative.writeValues(CameraMetadataNative.java:1876)
        at android.hardware.camera2.impl.CameraMetadataNative.setBase(CameraMetadataNative.java:1558)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:491)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:503)
        at android.hardware.camera2.impl.CameraDeviceImpl$CameraDeviceCallbacks.onResultReceived(CameraDeviceImpl.java:1937)
        at android.hardware.camera2.ICameraDeviceCallbacks$Stub.onTransact(ICameraDeviceCallbacks.java:182)
        at android.os.Binder.execTransactInternal(Binder.java:1195)
        at android.os.Binder.execTransact(Binder.java:1159)
W/libc: calloc(1, 31392) failed: returning null pointer
E/Camera2-Metadata: resizeIfNeeded: Can't allocate larger metadata buffer
    updateImpl: Unable to update metadata entry android.lens.info.shadingMapSize (90006): Out of memory (-12)
W/Binder: Caught a RuntimeException from the binder stub implementation.
    java.lang.IllegalStateException: Unknown error (-12) while trying to update metadata
        at android.hardware.camera2.impl.CameraMetadataNative.nativeWriteValues(Native Method)
        at android.hardware.camera2.impl.CameraMetadataNative.writeValues(CameraMetadataNative.java:1876)
        at android.hardware.camera2.impl.CameraMetadataNative.setBase(CameraMetadataNative.java:1558)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:491)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:503)
        at android.hardware.camera2.impl.CameraDeviceImpl$CameraDeviceCallbacks.onResultReceived(CameraDeviceImpl.java:1937)
        at android.hardware.camera2.ICameraDeviceCallbacks$Stub.onTransact(ICameraDeviceCallbacks.java:182)
        at android.os.Binder.execTransactInternal(Binder.java:1195)
        at android.os.Binder.execTransact(Binder.java:1159)
W/libc: calloc(1, 31392) failed: returning null pointer
E/Camera2-Metadata: resizeIfNeeded: Can't allocate larger metadata buffer
    updateImpl: Unable to update metadata entry android.lens.info.shadingMapSize (90006): Out of memory (-12)
W/Binder: Caught a RuntimeException from the binder stub implementation.
    java.lang.IllegalStateException: Unknown error (-12) while trying to update metadata
        at android.hardware.camera2.impl.CameraMetadataNative.nativeWriteValues(Native Method)
        at android.hardware.camera2.impl.CameraMetadataNative.writeValues(CameraMetadataNative.java:1876)
        at android.hardware.camera2.impl.CameraMetadataNative.setBase(CameraMetadataNative.java:1558)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:491)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:503)
        at android.hardware.camera2.impl.CameraDeviceImpl$CameraDeviceCallbacks.onResultReceived(CameraDeviceImpl.java:1937)
        at android.hardware.camera2.ICameraDeviceCallbacks$Stub.onTransact(ICameraDeviceCallbacks.java:182)
        at android.os.Binder.execTransactInternal(Binder.java:1195)
        at android.os.Binder.execTransact(Binder.java:1159)
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: calloc(1, 31392) failed: returning null pointer
E/Camera2-Metadata: resizeIfNeeded: Can't allocate larger metadata buffer
    updateImpl: Unable to update metadata entry android.lens.info.shadingMapSize (90006): Out of memory (-12)
W/Binder: Caught a RuntimeException from the binder stub implementation.
    java.lang.IllegalStateException: Unknown error (-12) while trying to update metadata
        at android.hardware.camera2.impl.CameraMetadataNative.nativeWriteValues(Native Method)
        at android.hardware.camera2.impl.CameraMetadataNative.writeValues(CameraMetadataNative.java:1876)
        at android.hardware.camera2.impl.CameraMetadataNative.setBase(CameraMetadataNative.java:1558)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:491)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:503)
        at android.hardware.camera2.impl.CameraDeviceImpl$CameraDeviceCallbacks.onResultReceived(CameraDeviceImpl.java:1937)
        at android.hardware.camera2.ICameraDeviceCallbacks$Stub.onTransact(ICameraDeviceCallbacks.java:182)
        at android.os.Binder.execTransactInternal(Binder.java:1195)
        at android.os.Binder.execTransact(Binder.java:1159)
W/libc: calloc(1, 31392) failed: returning null pointer
E/Camera2-Metadata: resizeIfNeeded: Can't allocate larger metadata buffer
    updateImpl: Unable to update metadata entry android.lens.info.shadingMapSize (90006): Out of memory (-12)
W/Binder: Caught a RuntimeException from the binder stub implementation.
    java.lang.IllegalStateException: Unknown error (-12) while trying to update metadata
        at android.hardware.camera2.impl.CameraMetadataNative.nativeWriteValues(Native Method)
        at android.hardware.camera2.impl.CameraMetadataNative.writeValues(CameraMetadataNative.java:1876)
        at android.hardware.camera2.impl.CameraMetadataNative.setBase(CameraMetadataNative.java:1558)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:491)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:503)
        at android.hardware.camera2.impl.CameraDeviceImpl$CameraDeviceCallbacks.onResultReceived(CameraDeviceImpl.java:1937)
        at android.hardware.camera2.ICameraDeviceCallbacks$Stub.onTransact(ICameraDeviceCallbacks.java:182)
        at android.os.Binder.execTransactInternal(Binder.java:1195)
        at android.os.Binder.execTransact(Binder.java:1159)
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: calloc(1, 31392) failed: returning null pointer
E/Camera2-Metadata: resizeIfNeeded: Can't allocate larger metadata buffer
    updateImpl: Unable to update metadata entry android.lens.info.shadingMapSize (90006): Out of memory (-12)
W/Binder: Caught a RuntimeException from the binder stub implementation.
    java.lang.IllegalStateException: Unknown error (-12) while trying to update metadata
        at android.hardware.camera2.impl.CameraMetadataNative.nativeWriteValues(Native Method)
        at android.hardware.camera2.impl.CameraMetadataNative.writeValues(CameraMetadataNative.java:1876)
        at android.hardware.camera2.impl.CameraMetadataNative.setBase(CameraMetadataNative.java:1558)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:491)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:503)
        at android.hardware.camera2.impl.CameraDeviceImpl$CameraDeviceCallbacks.onResultReceived(CameraDeviceImpl.java:1937)
        at android.hardware.camera2.ICameraDeviceCallbacks$Stub.onTransact(ICameraDeviceCallbacks.java:182)
        at android.os.Binder.execTransactInternal(Binder.java:1195)
        at android.os.Binder.execTransact(Binder.java:1159)
W/libc: calloc(1, 31392) failed: returning null pointer
E/Camera2-Metadata: resizeIfNeeded: Can't allocate larger metadata buffer
E/Camera2-Metadata: updateImpl: Unable to update metadata entry android.lens.info.shadingMapSize (90006): Out of memory (-12)
W/Binder: Caught a RuntimeException from the binder stub implementation.
    java.lang.IllegalStateException: Unknown error (-12) while trying to update metadata
        at android.hardware.camera2.impl.CameraMetadataNative.nativeWriteValues(Native Method)
        at android.hardware.camera2.impl.CameraMetadataNative.writeValues(CameraMetadataNative.java:1876)
        at android.hardware.camera2.impl.CameraMetadataNative.setBase(CameraMetadataNative.java:1558)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:491)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:503)
        at android.hardware.camera2.impl.CameraDeviceImpl$CameraDeviceCallbacks.onResultReceived(CameraDeviceImpl.java:1937)
        at android.hardware.camera2.ICameraDeviceCallbacks$Stub.onTransact(ICameraDeviceCallbacks.java:182)
        at android.os.Binder.execTransactInternal(Binder.java:1195)
        at android.os.Binder.execTransact(Binder.java:1159)
W/libc: calloc(1, 31392) failed: returning null pointer
E/Camera2-Metadata: resizeIfNeeded: Can't allocate larger metadata buffer
    updateImpl: Unable to update metadata entry android.lens.info.shadingMapSize (90006): Out of memory (-12)
W/Binder: Caught a RuntimeException from the binder stub implementation.
    java.lang.IllegalStateException: Unknown error (-12) while trying to update metadata
        at android.hardware.camera2.impl.CameraMetadataNative.nativeWriteValues(Native Method)
        at android.hardware.camera2.impl.CameraMetadataNative.writeValues(CameraMetadataNative.java:1876)
        at android.hardware.camera2.impl.CameraMetadataNative.setBase(CameraMetadataNative.java:1558)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:491)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:503)
        at android.hardware.camera2.impl.CameraDeviceImpl$CameraDeviceCallbacks.onResultReceived(CameraDeviceImpl.java:1937)
        at android.hardware.camera2.ICameraDeviceCallbacks$Stub.onTransact(ICameraDeviceCallbacks.java:182)
        at android.os.Binder.execTransactInternal(Binder.java:1195)
        at android.os.Binder.execTransact(Binder.java:1159)
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: calloc(1, 31392) failed: returning null pointer
E/Camera2-Metadata: resizeIfNeeded: Can't allocate larger metadata buffer
    updateImpl: Unable to update metadata entry android.lens.info.shadingMapSize (90006): Out of memory (-12)
W/Binder: Caught a RuntimeException from the binder stub implementation.
    java.lang.IllegalStateException: Unknown error (-12) while trying to update metadata
        at android.hardware.camera2.impl.CameraMetadataNative.nativeWriteValues(Native Method)
        at android.hardware.camera2.impl.CameraMetadataNative.writeValues(CameraMetadataNative.java:1876)
        at android.hardware.camera2.impl.CameraMetadataNative.setBase(CameraMetadataNative.java:1558)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:491)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:503)
        at android.hardware.camera2.impl.CameraDeviceImpl$CameraDeviceCallbacks.onResultReceived(CameraDeviceImpl.java:1937)
        at android.hardware.camera2.ICameraDeviceCallbacks$Stub.onTransact(ICameraDeviceCallbacks.java:182)
        at android.os.Binder.execTransactInternal(Binder.java:1195)
        at android.os.Binder.execTransact(Binder.java:1159)
W/libc: calloc(1, 31392) failed: returning null pointer
E/Camera2-Metadata: resizeIfNeeded: Can't allocate larger metadata buffer
    updateImpl: Unable to update metadata entry android.lens.info.shadingMapSize (90006): Out of memory (-12)
W/Binder: Caught a RuntimeException from the binder stub implementation.
    java.lang.IllegalStateException: Unknown error (-12) while trying to update metadata
        at android.hardware.camera2.impl.CameraMetadataNative.nativeWriteValues(Native Method)
        at android.hardware.camera2.impl.CameraMetadataNative.writeValues(CameraMetadataNative.java:1876)
        at android.hardware.camera2.impl.CameraMetadataNative.setBase(CameraMetadataNative.java:1558)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:491)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:503)
        at android.hardware.camera2.impl.CameraDeviceImpl$CameraDeviceCallbacks.onResultReceived(CameraDeviceImpl.java:1937)
        at android.hardware.camera2.ICameraDeviceCallbacks$Stub.onTransact(ICameraDeviceCallbacks.java:182)
        at android.os.Binder.execTransactInternal(Binder.java:1195)
        at android.os.Binder.execTransact(Binder.java:1159)
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: calloc(1, 31392) failed: returning null pointer
E/Camera2-Metadata: resizeIfNeeded: Can't allocate larger metadata buffer
    updateImpl: Unable to update metadata entry android.lens.info.shadingMapSize (90006): Out of memory (-12)
W/Binder: Caught a RuntimeException from the binder stub implementation.
    java.lang.IllegalStateException: Unknown error (-12) while trying to update metadata
        at android.hardware.camera2.impl.CameraMetadataNative.nativeWriteValues(Native Method)
        at android.hardware.camera2.impl.CameraMetadataNative.writeValues(CameraMetadataNative.java:1876)
        at android.hardware.camera2.impl.CameraMetadataNative.setBase(CameraMetadataNative.java:1558)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:491)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:503)
        at android.hardware.camera2.impl.CameraDeviceImpl$CameraDeviceCallbacks.onResultReceived(CameraDeviceImpl.java:1937)
        at android.hardware.camera2.ICameraDeviceCallbacks$Stub.onTransact(ICameraDeviceCallbacks.java:182)
        at android.os.Binder.execTransactInternal(Binder.java:1195)
        at android.os.Binder.execTransact(Binder.java:1159)
W/libc: calloc(1, 31392) failed: returning null pointer
E/Camera2-Metadata: resizeIfNeeded: Can't allocate larger metadata buffer
    updateImpl: Unable to update metadata entry android.lens.info.shadingMapSize (90006): Out of memory (-12)
W/Binder: Caught a RuntimeException from the binder stub implementation.
    java.lang.IllegalStateException: Unknown error (-12) while trying to update metadata
        at android.hardware.camera2.impl.CameraMetadataNative.nativeWriteValues(Native Method)
        at android.hardware.camera2.impl.CameraMetadataNative.writeValues(CameraMetadataNative.java:1876)
        at android.hardware.camera2.impl.CameraMetadataNative.setBase(CameraMetadataNative.java:1558)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:491)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:503)
        at android.hardware.camera2.impl.CameraDeviceImpl$CameraDeviceCallbacks.onResultReceived(CameraDeviceImpl.java:1937)
        at android.hardware.camera2.ICameraDeviceCallbacks$Stub.onTransact(ICameraDeviceCallbacks.java:182)
        at android.os.Binder.execTransactInternal(Binder.java:1195)
        at android.os.Binder.execTransact(Binder.java:1159)
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: calloc(1, 31392) failed: returning null pointer
E/Camera2-Metadata: resizeIfNeeded: Can't allocate larger metadata buffer
    updateImpl: Unable to update metadata entry android.lens.info.shadingMapSize (90006): Out of memory (-12)
W/Binder: Caught a RuntimeException from the binder stub implementation.
    java.lang.IllegalStateException: Unknown error (-12) while trying to update metadata
        at android.hardware.camera2.impl.CameraMetadataNative.nativeWriteValues(Native Method)
        at android.hardware.camera2.impl.CameraMetadataNative.writeValues(CameraMetadataNative.java:1876)
        at android.hardware.camera2.impl.CameraMetadataNative.setBase(CameraMetadataNative.java:1558)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:491)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:503)
        at android.hardware.camera2.impl.CameraDeviceImpl$CameraDeviceCallbacks.onResultReceived(CameraDeviceImpl.java:1937)
        at android.hardware.camera2.ICameraDeviceCallbacks$Stub.onTransact(ICameraDeviceCallbacks.java:182)
        at android.os.Binder.execTransactInternal(Binder.java:1195)
        at android.os.Binder.execTransact(Binder.java:1159)
W/libc: calloc(1, 31392) failed: returning null pointer
E/Camera2-Metadata: resizeIfNeeded: Can't allocate larger metadata buffer
    updateImpl: Unable to update metadata entry android.lens.info.shadingMapSize (90006): Out of memory (-12)
W/Binder: Caught a RuntimeException from the binder stub implementation.
    java.lang.IllegalStateException: Unknown error (-12) while trying to update metadata
        at android.hardware.camera2.impl.CameraMetadataNative.nativeWriteValues(Native Method)
        at android.hardware.camera2.impl.CameraMetadataNative.writeValues(CameraMetadataNative.java:1876)
        at android.hardware.camera2.impl.CameraMetadataNative.setBase(CameraMetadataNative.java:1558)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:491)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:503)
        at android.hardware.camera2.impl.CameraDeviceImpl$CameraDeviceCallbacks.onResultReceived(CameraDeviceImpl.java:1937)
        at android.hardware.camera2.ICameraDeviceCallbacks$Stub.onTransact(ICameraDeviceCallbacks.java:182)
        at android.os.Binder.execTransactInternal(Binder.java:1195)
        at android.os.Binder.execTransact(Binder.java:1159)
W/libc: calloc(1, 31392) failed: returning null pointer
E/Camera2-Metadata: resizeIfNeeded: Can't allocate larger metadata buffer
    updateImpl: Unable to update metadata entry android.lens.info.shadingMapSize (90006): Out of memory (-12)
W/Binder: Caught a RuntimeException from the binder stub implementation.
    java.lang.IllegalStateException: Unknown error (-12) while trying to update metadata
        at android.hardware.camera2.impl.CameraMetadataNative.nativeWriteValues(Native Method)
        at android.hardware.camera2.impl.CameraMetadataNative.writeValues(CameraMetadataNative.java:1876)
        at android.hardware.camera2.impl.CameraMetadataNative.setBase(CameraMetadataNative.java:1558)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:491)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:503)
        at android.hardware.camera2.impl.CameraDeviceImpl$CameraDeviceCallbacks.onResultReceived(CameraDeviceImpl.java:1937)
        at android.hardware.camera2.ICameraDeviceCallbacks$Stub.onTransact(ICameraDeviceCallbacks.java:182)
        at android.os.Binder.execTransactInternal(Binder.java:1195)
        at android.os.Binder.execTransact(Binder.java:1159)
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: calloc(1, 31392) failed: returning null pointer
E/Camera2-Metadata: resizeIfNeeded: Can't allocate larger metadata buffer
    updateImpl: Unable to update metadata entry android.lens.info.shadingMapSize (90006): Out of memory (-12)
W/Binder: Caught a RuntimeException from the binder stub implementation.
    java.lang.IllegalStateException: Unknown error (-12) while trying to update metadata
        at android.hardware.camera2.impl.CameraMetadataNative.nativeWriteValues(Native Method)
        at android.hardware.camera2.impl.CameraMetadataNative.writeValues(CameraMetadataNative.java:1876)
        at android.hardware.camera2.impl.CameraMetadataNative.setBase(CameraMetadataNative.java:1558)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:491)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:503)
        at android.hardware.camera2.impl.CameraDeviceImpl$CameraDeviceCallbacks.onResultReceived(CameraDeviceImpl.java:1937)
        at android.hardware.camera2.ICameraDeviceCallbacks$Stub.onTransact(ICameraDeviceCallbacks.java:182)
        at android.os.Binder.execTransactInternal(Binder.java:1195)
        at android.os.Binder.execTransact(Binder.java:1159)
W/libc: calloc(1, 31392) failed: returning null pointer
E/Camera2-Metadata: resizeIfNeeded: Can't allocate larger metadata buffer
    updateImpl: Unable to update metadata entry android.lens.info.shadingMapSize (90006): Out of memory (-12)
W/Binder: Caught a RuntimeException from the binder stub implementation.
    java.lang.IllegalStateException: Unknown error (-12) while trying to update metadata
        at android.hardware.camera2.impl.CameraMetadataNative.nativeWriteValues(Native Method)
        at android.hardware.camera2.impl.CameraMetadataNative.writeValues(CameraMetadataNative.java:1876)
        at android.hardware.camera2.impl.CameraMetadataNative.setBase(CameraMetadataNative.java:1558)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:491)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:503)
        at android.hardware.camera2.impl.CameraDeviceImpl$CameraDeviceCallbacks.onResultReceived(CameraDeviceImpl.java:1937)
        at android.hardware.camera2.ICameraDeviceCallbacks$Stub.onTransact(ICameraDeviceCallbacks.java:182)
        at android.os.Binder.execTransactInternal(Binder.java:1195)
        at android.os.Binder.execTransact(Binder.java:1159)
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: calloc(1, 31392) failed: returning null pointer
E/Camera2-Metadata: resizeIfNeeded: Can't allocate larger metadata buffer
    updateImpl: Unable to update metadata entry android.lens.info.shadingMapSize (90006): Out of memory (-12)
W/Binder: Caught a RuntimeException from the binder stub implementation.
    java.lang.IllegalStateException: Unknown error (-12) while trying to update metadata
        at android.hardware.camera2.impl.CameraMetadataNative.nativeWriteValues(Native Method)
        at android.hardware.camera2.impl.CameraMetadataNative.writeValues(CameraMetadataNative.java:1876)
        at android.hardware.camera2.impl.CameraMetadataNative.setBase(CameraMetadataNative.java:1558)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:491)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:503)
        at android.hardware.camera2.impl.CameraDeviceImpl$CameraDeviceCallbacks.onResultReceived(CameraDeviceImpl.java:1937)
        at android.hardware.camera2.ICameraDeviceCallbacks$Stub.onTransact(ICameraDeviceCallbacks.java:182)
        at android.os.Binder.execTransactInternal(Binder.java:1195)
        at android.os.Binder.execTransact(Binder.java:1159)
W/libc: calloc(1, 31392) failed: returning null pointer
E/Camera2-Metadata: resizeIfNeeded: Can't allocate larger metadata buffer
    updateImpl: Unable to update metadata entry android.lens.info.shadingMapSize (90006): Out of memory (-12)
W/Binder: Caught a RuntimeException from the binder stub implementation.
    java.lang.IllegalStateException: Unknown error (-12) while trying to update metadata
        at android.hardware.camera2.impl.CameraMetadataNative.nativeWriteValues(Native Method)
        at android.hardware.camera2.impl.CameraMetadataNative.writeValues(CameraMetadataNative.java:1876)
        at android.hardware.camera2.impl.CameraMetadataNative.setBase(CameraMetadataNative.java:1558)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:491)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:503)
        at android.hardware.camera2.impl.CameraDeviceImpl$CameraDeviceCallbacks.onResultReceived(CameraDeviceImpl.java:1937)
        at android.hardware.camera2.ICameraDeviceCallbacks$Stub.onTransact(ICameraDeviceCallbacks.java:182)
        at android.os.Binder.execTransactInternal(Binder.java:1195)
        at android.os.Binder.execTransact(Binder.java:1159)
W/libc: calloc(1, 31392) failed: returning null pointer
E/Camera2-Metadata: resizeIfNeeded: Can't allocate larger metadata buffer
    updateImpl: Unable to update metadata entry android.lens.info.shadingMapSize (90006): Out of memory (-12)
W/Binder: Caught a RuntimeException from the binder stub implementation.
    java.lang.IllegalStateException: Unknown error (-12) while trying to update metadata
        at android.hardware.camera2.impl.CameraMetadataNative.nativeWriteValues(Native Method)
        at android.hardware.camera2.impl.CameraMetadataNative.writeValues(CameraMetadataNative.java:1876)
        at android.hardware.camera2.impl.CameraMetadataNative.setBase(CameraMetadataNative.java:1558)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:491)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:503)
        at android.hardware.camera2.impl.CameraDeviceImpl$CameraDeviceCallbacks.onResultReceived(CameraDeviceImpl.java:1937)
        at android.hardware.camera2.ICameraDeviceCallbacks$Stub.onTransact(ICameraDeviceCallbacks.java:182)
        at android.os.Binder.execTransactInternal(Binder.java:1195)
        at android.os.Binder.execTransact(Binder.java:1159)
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: calloc(1, 31392) failed: returning null pointer
E/Camera2-Metadata: resizeIfNeeded: Can't allocate larger metadata buffer
    updateImpl: Unable to update metadata entry android.lens.info.shadingMapSize (90006): Out of memory (-12)
W/Binder: Caught a RuntimeException from the binder stub implementation.
    java.lang.IllegalStateException: Unknown error (-12) while trying to update metadata
        at android.hardware.camera2.impl.CameraMetadataNative.nativeWriteValues(Native Method)
        at android.hardware.camera2.impl.CameraMetadataNative.writeValues(CameraMetadataNative.java:1876)
        at android.hardware.camera2.impl.CameraMetadataNative.setBase(CameraMetadataNative.java:1558)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:491)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:503)
        at android.hardware.camera2.impl.CameraDeviceImpl$CameraDeviceCallbacks.onResultReceived(CameraDeviceImpl.java:1937)
        at android.hardware.camera2.ICameraDeviceCallbacks$Stub.onTransact(ICameraDeviceCallbacks.java:182)
        at android.os.Binder.execTransactInternal(Binder.java:1195)
        at android.os.Binder.execTransact(Binder.java:1159)
W/libc: calloc(1, 31392) failed: returning null pointer
E/Camera2-Metadata: resizeIfNeeded: Can't allocate larger metadata buffer
    updateImpl: Unable to update metadata entry android.lens.info.shadingMapSize (90006): Out of memory (-12)
W/Binder: Caught a RuntimeException from the binder stub implementation.
    java.lang.IllegalStateException: Unknown error (-12) while trying to update metadata
        at android.hardware.camera2.impl.CameraMetadataNative.nativeWriteValues(Native Method)
        at android.hardware.camera2.impl.CameraMetadataNative.writeValues(CameraMetadataNative.java:1876)
        at android.hardware.camera2.impl.CameraMetadataNative.setBase(CameraMetadataNative.java:1558)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:491)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:503)
        at android.hardware.camera2.impl.CameraDeviceImpl$CameraDeviceCallbacks.onResultReceived(CameraDeviceImpl.java:1937)
        at android.hardware.camera2.ICameraDeviceCallbacks$Stub.onTransact(ICameraDeviceCallbacks.java:182)
        at android.os.Binder.execTransactInternal(Binder.java:1195)
        at android.os.Binder.execTransact(Binder.java:1159)
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: calloc(1, 31392) failed: returning null pointer
E/Camera2-Metadata: resizeIfNeeded: Can't allocate larger metadata buffer
    updateImpl: Unable to update metadata entry android.lens.info.shadingMapSize (90006): Out of memory (-12)
W/Binder: Caught a RuntimeException from the binder stub implementation.
    java.lang.IllegalStateException: Unknown error (-12) while trying to update metadata
        at android.hardware.camera2.impl.CameraMetadataNative.nativeWriteValues(Native Method)
        at android.hardware.camera2.impl.CameraMetadataNative.writeValues(CameraMetadataNative.java:1876)
        at android.hardware.camera2.impl.CameraMetadataNative.setBase(CameraMetadataNative.java:1558)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:491)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:503)
        at android.hardware.camera2.impl.CameraDeviceImpl$CameraDeviceCallbacks.onResultReceived(CameraDeviceImpl.java:1937)
        at android.hardware.camera2.ICameraDeviceCallbacks$Stub.onTransact(ICameraDeviceCallbacks.java:182)
        at android.os.Binder.execTransactInternal(Binder.java:1195)
        at android.os.Binder.execTransact(Binder.java:1159)
W/libc: calloc(1, 31392) failed: returning null pointer
E/Camera2-Metadata: resizeIfNeeded: Can't allocate larger metadata buffer
    updateImpl: Unable to update metadata entry android.lens.info.shadingMapSize (90006): Out of memory (-12)
W/Binder: Caught a RuntimeException from the binder stub implementation.
    java.lang.IllegalStateException: Unknown error (-12) while trying to update metadata
        at android.hardware.camera2.impl.CameraMetadataNative.nativeWriteValues(Native Method)
        at android.hardware.camera2.impl.CameraMetadataNative.writeValues(CameraMetadataNative.java:1876)
        at android.hardware.camera2.impl.CameraMetadataNative.setBase(CameraMetadataNative.java:1558)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:491)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:503)
        at android.hardware.camera2.impl.CameraDeviceImpl$CameraDeviceCallbacks.onResultReceived(CameraDeviceImpl.java:1937)
        at android.hardware.camera2.ICameraDeviceCallbacks$Stub.onTransact(ICameraDeviceCallbacks.java:182)
        at android.os.Binder.execTransactInternal(Binder.java:1195)
        at android.os.Binder.execTransact(Binder.java:1159)
W/libc: calloc(1, 31392) failed: returning null pointer
E/Camera2-Metadata: resizeIfNeeded: Can't allocate larger metadata buffer
    updateImpl: Unable to update metadata entry android.lens.info.shadingMapSize (90006): Out of memory (-12)
W/Binder: Caught a RuntimeException from the binder stub implementation.
    java.lang.IllegalStateException: Unknown error (-12) while trying to update metadata
        at android.hardware.camera2.impl.CameraMetadataNative.nativeWriteValues(Native Method)
        at android.hardware.camera2.impl.CameraMetadataNative.writeValues(CameraMetadataNative.java:1876)
        at android.hardware.camera2.impl.CameraMetadataNative.setBase(CameraMetadataNative.java:1558)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:491)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:503)
        at android.hardware.camera2.impl.CameraDeviceImpl$CameraDeviceCallbacks.onResultReceived(CameraDeviceImpl.java:1937)
        at android.hardware.camera2.ICameraDeviceCallbacks$Stub.onTransact(ICameraDeviceCallbacks.java:182)
        at android.os.Binder.execTransactInternal(Binder.java:1195)
        at android.os.Binder.execTransact(Binder.java:1159)
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: calloc(1, 31392) failed: returning null pointer
E/Camera2-Metadata: resizeIfNeeded: Can't allocate larger metadata buffer
    updateImpl: Unable to update metadata entry android.lens.info.shadingMapSize (90006): Out of memory (-12)
W/Binder: Caught a RuntimeException from the binder stub implementation.
    java.lang.IllegalStateException: Unknown error (-12) while trying to update metadata
        at android.hardware.camera2.impl.CameraMetadataNative.nativeWriteValues(Native Method)
        at android.hardware.camera2.impl.CameraMetadataNative.writeValues(CameraMetadataNative.java:1876)
        at android.hardware.camera2.impl.CameraMetadataNative.setBase(CameraMetadataNative.java:1558)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:491)
        at android.hardware.camera2.impl.CameraMetadataNative.set(CameraMetadataNative.java:503)
        at android.hardware.camera2.impl.CameraDeviceImpl$CameraDeviceCallbacks.onResultReceived(CameraDeviceImpl.java:1937)
        at android.hardware.camera2.ICameraDeviceCallbacks$Stub.onTransact(ICameraDeviceCallbacks.java:182)
        at android.os.Binder.execTransactInternal(Binder.java:1195)
        at android.os.Binder.execTransact(Binder.java:1159)
W/libc: calloc(1, 15696) failed: returning null pointer
A/libc: Fatal signal 11 (SIGSEGV), code 1 (SEGV_MAPERR), fault addr 0x0 in tid 2929 (Binder:2747_5), pid 2747 (ax_color_detect)
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: pthread_create failed: couldn't allocate 1052672-bytes mapped space: Out of memory
E/libutils.threads: androidCreateRawThreadEtc failed (entry=0xf1e535c1, res=11, Success)
    (android threadPriority=0)
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
W/libc: memalign(16, 720000) failed: returning null pointer
```