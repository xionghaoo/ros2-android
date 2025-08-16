package xh.rabbit.ros2android

import org.ros2.rcljava.RCLJava

object Ros2Android {
    init {
        System.loadLibrary("ros2android")
    }

    const val DEFAULT_RMW = "rmw_fastrtps_cpp"
    const val DEFAULT_DOMAIN_ID = "0"

    fun initialize(domainId: String = DEFAULT_DOMAIN_ID, rwt: String = DEFAULT_RMW) {
        System.setProperty("ROS_DOMAIN_ID", domainId)
        System.setProperty("RMW_IMPLEMENTATION", rwt);

        RCLJava.rclJavaInit();
    }

    external fun hello()

}