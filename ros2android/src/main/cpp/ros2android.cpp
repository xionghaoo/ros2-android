#include <jni.h>
#include "log.h"

extern "C"
JNIEXPORT void JNICALL
Java_xh_rabbit_ros2android_Ros2Android_hello(JNIEnv *env, jobject thiz) {
    LOGCATD("hello world");
}