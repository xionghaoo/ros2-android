package xh.rabbit.ros2android

import org.ros2.rcljava.RCLJava

object Ros2Android {
    init {
        System.loadLibrary("ros2android")
    }

    external fun hello()

    fun initRCLJava() {
        RCLJava.rclJavaInit();
    }
}