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

import android.widget.TextView
import org.ros2.rcljava.node.BaseComposableNode
import org.ros2.rcljava.subscription.Subscription

class ListenerNode(
    name: String?, private val topic: String,
    private val listenerView: TextView
) : BaseComposableNode(name) {
    private val subscriber: Subscription<std_msgs.msg.String> = node.createSubscription(
        std_msgs.msg.String::class.java, this.topic
    ) { msg: std_msgs.msg.String ->
        listenerView.append(
            "Hello ROS2 from Android: " + msg.data +
                    "\r\n"
        )
    }
}
