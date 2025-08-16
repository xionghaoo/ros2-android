
package com.udi.ros2_android

import android.R
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.udi.ros2_android.databinding.FragmentListenerBinding
import org.ros2.rcljava.RCLJava
import org.ros2.rcljava.executors.Executor
import org.ros2.rcljava.executors.MultiThreadedExecutor
import org.ros2.rcljava.executors.SingleThreadedExecutor
import xh.rabbit.core.BaseFragment
import xh.rabbit.ros2android.ListenerNode


class ListenerFragment : BaseFragment<FragmentListenerBinding>() {

    companion object {

        const val IS_WORKING: String = "isWorking"

        private var listenerNode: ListenerNode? = null


        val logtag: String = ListenerFragment::class.java.getName()

    }

    private var isWorking = false
    private var rosExecutor: Executor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.rosExecutor = MultiThreadedExecutor()

    }

    override fun onCreateBindLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentListenerBinding {
        return FragmentListenerBinding.inflate(inflater, container, false)
    }

    override fun onFirstViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.buttonStart.setOnClickListener(startListener)

        binding.buttonStop.setOnClickListener(stopListener)

        binding.listenerView.movementMethod = ScrollingMovementMethod()

        RCLJava.rclJavaInit()

        listenerNode =
            ListenerNode("android_listener_node", "chatter", binding.listenerView)

        changeState(isWorking)


    }

    override fun rootView(): View {
        return binding.root
    }

    // Create an anonymous implementation of OnClickListener
    private val startListener: View.OnClickListener = View.OnClickListener {
        Log.d(logtag, "onClick() called - start button")
        Toast.makeText(
            requireContext(), "The Start button was clicked.",
            Toast.LENGTH_LONG
        ).show()
        Log.d(logtag, "onClick() ended - start button")
        changeState(true)
    }

    // Create an anonymous implementation of OnClickListener
    private val stopListener: View.OnClickListener = View.OnClickListener {
        Log.d(logtag, "onClick() called - stop button")
        Toast.makeText(
            requireContext(), "The Stop button was clicked.",
            Toast.LENGTH_LONG
        ).show()
        changeState(false)
        Log.d(logtag, "onClick() ended - stop button")
    }

    private fun changeState(isWorking: Boolean) {
        this.isWorking = isWorking
        binding.buttonStart.isEnabled = !isWorking
        binding.buttonStop.isEnabled = isWorking
        if (isWorking) {
            rosExecutor?.addNode(listenerNode)
            rosExecutor?.spin()
        } else {
            rosExecutor?.removeNode(listenerNode)
        }
    }
}