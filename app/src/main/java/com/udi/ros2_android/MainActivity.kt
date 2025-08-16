package com.udi.ros2_android

import android.R
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.udi.ros2_android.databinding.ActivityMainBinding
import geometry_msgs.msg.Twist
import geometry_msgs.msg.Vector3
import org.ros2.rcljava.RCLJava
import org.ros2.rcljava.node.Node
import org.ros2.rcljava.publisher.Publisher
import xh.rabbit.ros2android.Ros2Android


class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding
    private var node: Node? = null
    private var publisher: Publisher<Twist>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Ros2Android.hello()


        // 初始化 ROS 2
        Ros2Android.initialize()
        node = RCLJava.createNode("android_teleop")
        publisher = node?.createPublisher<Twist>(Twist::class.java, "/turtle1/cmd_vel")


        // 前进
        binding.btnUp.setOnClickListener { publishTwist(2.0, 0.0) }

        // 后退
        binding.btnDown.setOnClickListener { publishTwist(-2.0, 0.0) }

        // 左转
        binding.btnLeft.setOnClickListener { publishTwist(0.0, 2.0) }

        // 右转
        binding.btnRight.setOnClickListener { publishTwist(0.0, -2.0) }
    }

    private fun publishTwist(linear: Double, angular: Double) {
        val msg = Twist()

        val linearVel = Vector3()
        linearVel.setX(linear)
        linearVel.setY(0.0)
        linearVel.setZ(0.0)

        val angularVel = Vector3()
        angularVel.setX(0.0)
        angularVel.setY(0.0)
        angularVel.setZ(angular)

        msg.setLinear(linearVel)
        msg.setAngular(angularVel)

        publisher!!.publish(msg)
    }
}