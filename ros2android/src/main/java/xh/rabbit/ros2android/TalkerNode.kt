/* Copyright 2017 Esteve Fernandez <esteve@apache.org>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package xh.rabbit.ros2android

import android.util.Log
import org.ros2.rcljava.RCLJava
import org.ros2.rcljava.node.BaseComposableNode
import org.ros2.rcljava.publisher.Publisher
import org.ros2.rcljava.timer.WallTimer
import java.util.concurrent.TimeUnit

class TalkerNode(name: String?, private val topic: String) : BaseComposableNode(name) {

    private val publisher: Publisher<std_msgs.msg.String> = node.createPublisher(
        std_msgs.msg.String::class.java, this.topic
    )

    private var count = 0

    private var timer: WallTimer? = null

    fun start() {
        Log.d(logtag, "TalkerNode::start()")
        if (this.timer != null) {
            timer!!.cancel()
        }
        this.count = 0
        this.timer = node.createWallTimer(
            500, TimeUnit.MILLISECONDS
        ) { this.onTimer() }
    }

    private fun onTimer() {
        val msg = std_msgs.msg.String()
        msg.setData("Hello ROS2 from Android: " + this.count)
        Log.d(logtag, "send: Hello ROS2 from Android: + ${this.count}, ${Thread.currentThread()}")
        count++
        publisher.publish(msg)
    }

    fun stop() {
        Log.d(logtag, "TalkerNode::stop()")
        if (this.timer != null) {
            timer!!.cancel()
        }
    }

    companion object {
        private val logtag: String = TalkerNode::class.java.name
    }
}
