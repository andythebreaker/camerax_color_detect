#include <jni.h>
#include <string>
#include <pthread.h>
#include <rtmp.h>
#include "JavaCallHelper.h"
#include "VideoChannel.h"
#include "AudioChannel.h"

#include <android/log.h>

#define LOGI(...) ((void)__android_log_print(ANDROID_LOG_INFO, "native-lib", __VA_ARGS__))
#define LOG_ANDYTHEBREAKER_IMAGE_DONT_GO_OUT 0

VideoChannel *videoChannel = nullptr;
AudioChannel *audioChannel = nullptr;
JavaVM *javaVM = nullptr;
JavaCallHelper *helper = nullptr;
pthread_t pid;
char *path = nullptr;
RTMP *rtmp = nullptr;
uint64_t startTime;

pthread_mutex_t mutex;

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {
    javaVM = vm;
    return JNI_VERSION_1_4;
}

void callback(RTMPPacket *packet) {
    if (rtmp) {
        packet->m_nInfoField2 = rtmp->m_stream_id;
        packet->m_nTimeStamp = RTMP_GetTime() - startTime;
        // 1: 放到队列中
        RTMP_SendPacket(rtmp, packet, 1);
    }
    RTMPPacket_Free(packet);
    delete (packet);
}


void *connect(void *args) {
    int ret;
    rtmp = RTMP_Alloc();
    RTMP_Init(rtmp);
    do {
        // 解析url地址，可能失败(地址不合法)
        ret = RTMP_SetupURL(rtmp, path);
        if (!ret) {
            //todo 通知Java 地址传的有问题。
            break;
        }
        //开启输出模式， 播放拉流不需要推流，就可以不开
        RTMP_EnableWrite(rtmp);
        ret = RTMP_Connect(rtmp, nullptr);
        if (!ret) {
            //todo 通知Java 服务器连接失败。
            break;
        }
        ret = RTMP_ConnectStream(rtmp, 0);
        if (!ret) {
            //todo 通知Java 未连接到流。
            break;
        }
        // 发送audio specific config(告诉播放器怎么解码我推流的音频)
        RTMPPacket *packet = audioChannel->getAudioConfig();
        callback(packet);
    } while (false);

    if (!ret) {
        RTMP_Close(rtmp);
        RTMP_Free(rtmp);
        rtmp = nullptr;
    }

    delete (path);
    path = nullptr;

    // 通知Java层可以开始推流了
    helper->onParpare(ret);
    startTime = RTMP_GetTime();
    return nullptr;
}


extern "C"
JNIEXPORT void JNICALL
Java_me_andythebreaker_camerax_1color_1detect_RtmpClient_connect(JNIEnv *env, jobject thiz, jstring url_) {

    const char *url = env->GetStringUTFChars(url_, nullptr);
    path = new char[strlen(url) + 1];
    strcpy(path, url);
    //启动子线程
    pthread_create(&pid, nullptr, connect, nullptr);

    env->ReleaseStringUTFChars(url_, url);


}

extern "C"
JNIEXPORT void JNICALL
Java_me_andythebreaker_camerax_1color_1detect_RtmpClient_nativeInit(JNIEnv *env, jobject thiz) {
    helper = new JavaCallHelper(javaVM, env, thiz);
    pthread_mutex_init(&mutex, nullptr);
}

extern "C"
JNIEXPORT void JNICALL
Java_me_andythebreaker_camerax_1color_1detect_RtmpClient_disConnect(JNIEnv *env, jobject thiz) {
    pthread_mutex_lock(&mutex);
    if (rtmp) {
        RTMP_Close(rtmp);
        RTMP_Free(rtmp);
        rtmp = nullptr;
    }

    if (videoChannel) {
        videoChannel->resetPts();
    }
    pthread_mutex_unlock(&mutex);
}
extern "C"
JNIEXPORT void JNICALL
Java_me_andythebreaker_camerax_1color_1detect_RtmpClient_initVideoEnc(JNIEnv *env, jobject thiz, jint width,
                                                              jint height, jint fps,
                                                              jint bit_rate) {
    // 准备好视频编码器
    videoChannel = new VideoChannel;
    videoChannel->openCodec(width, height, fps, bit_rate);
    videoChannel->setCallback(callback);
}

extern "C"
JNIEXPORT void JNICALL
Java_me_andythebreaker_camerax_1color_1detect_RtmpClient_releaseVideoEnc(JNIEnv *env, jobject thiz) {
    if (videoChannel) {
        delete (videoChannel);
        videoChannel = nullptr;
    }
}

extern "C"
JNIEXPORT void JNICALL
Java_me_andythebreaker_camerax_1color_1detect_RtmpClient_nativeDeInit(JNIEnv *env, jobject thiz) {
    if (helper) {
        delete (helper);
        helper = nullptr;
    }
    pthread_mutex_destroy(&mutex);
}

extern "C"
JNIEXPORT void JNICALL
Java_me_andythebreaker_camerax_1color_1detect_RtmpClient_nativeSendVideo(JNIEnv *env, jobject thiz,
                                                                 jbyteArray buffer) {

    if(LOG_ANDYTHEBREAKER_IMAGE_DONT_GO_OUT)LOGI("Java_me_andythebreaker_camerax_1color_1detect_RtmpClient_nativeSendVideo");

    jbyte *data = env->GetByteArrayElements(buffer, nullptr);
    if(LOG_ANDYTHEBREAKER_IMAGE_DONT_GO_OUT)LOGI("jbyte *data = env->GetByteArrayElements(buffer, 0);");
    pthread_mutex_lock(&mutex);
    if(LOG_ANDYTHEBREAKER_IMAGE_DONT_GO_OUT)LOGI("pthread_mutex_lock(&mutex);");
    //编码与推流
    videoChannel->encode(reinterpret_cast<uint8_t *>(data));
    if(LOG_ANDYTHEBREAKER_IMAGE_DONT_GO_OUT)LOGI("videoChannel->encode(reinterpret_cast<uint8_t *>(data));");
    pthread_mutex_unlock(&mutex);
    if(LOG_ANDYTHEBREAKER_IMAGE_DONT_GO_OUT)LOGI("pthread_mutex_unlock(&mutex);");
    env->ReleaseByteArrayElements(buffer, data, JNI_COMMIT);
    if(LOG_ANDYTHEBREAKER_IMAGE_DONT_GO_OUT)LOGI("env->ReleaseByteArrayElements(buffer, data, 0);");
}

extern "C"
JNIEXPORT jint JNICALL
Java_me_andythebreaker_camerax_1color_1detect_RtmpClient_initAudioEnc(JNIEnv *env, jobject thiz,
                                                              jint sample_rate, jint channels) {
    audioChannel = new AudioChannel();
    audioChannel->setCallback(callback);
    audioChannel->openCodec(sample_rate, channels);
    return audioChannel->getInputByteNum();
}

extern "C"
JNIEXPORT void JNICALL
Java_me_andythebreaker_camerax_1color_1detect_RtmpClient_releaseAudioEnc(JNIEnv *env, jobject thiz) {
    if (audioChannel) {
        delete (audioChannel);
        audioChannel = nullptr;
    }
}

extern "C"
JNIEXPORT void JNICALL
Java_me_andythebreaker_camerax_1color_1detect_RtmpClient_nativeSendAudio(JNIEnv *env, jobject thiz,
                                                                 jbyteArray buffer, jint len) {
    jbyte *data = env->GetByteArrayElements(buffer, nullptr);
    pthread_mutex_lock(&mutex);
    audioChannel->encode(reinterpret_cast<int32_t *>(data), len);
    pthread_mutex_unlock(&mutex);
    env->ReleaseByteArrayElements(buffer, data, 0);
}

