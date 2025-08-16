package com.udi.ros2_android

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.udi.ros2_android.databinding.FragmentTalkerBinding
import org.ros2.rcljava.RCLJava
import org.ros2.rcljava.executors.Executor
import org.ros2.rcljava.executors.MultiThreadedExecutor
import org.ros2.rcljava.executors.SingleThreadedExecutor
import xh.rabbit.core.BaseFragment
import xh.rabbit.ros2android.Ros2Android
import xh.rabbit.ros2android.TalkerNode


class TalkerFragment : BaseFragment<FragmentTalkerBinding>() {

    companion object {
        const val IS_WORKING: String = "isWorking"
        val logtag: String = TalkerFragment::class.java.getName()
    }


    private var talkerNode: TalkerNode? = null
    var isWorking = false
    private var rosExecutor: Executor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.rosExecutor = MultiThreadedExecutor()

    }

    override fun onCreateBindLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentTalkerBinding {
        return FragmentTalkerBinding.inflate(inflater, container, false)
    }

    override fun onFirstViewCreated(view: View, savedInstanceState: Bundle?) {
        val buttonStart = binding.buttonStart
        buttonStart.setOnClickListener(startListener)

        val buttonStop = binding.buttonStop
        buttonStop.setOnClickListener(stopListener)

        Ros2Android.initRCLJava()

        talkerNode = TalkerNode("android_talker_node", "chatter")

        changeState(isWorking)


    }

    override fun rootView(): View {
        return binding.root
    }

    // Create an anonymous implementation of OnClickListener
    private val startListener: View.OnClickListener = View.OnClickListener {
        Log.d(logtag, "onClick() called - start button")
        Toast
            .makeText(
                requireContext(), "The Start button was clicked.",
                Toast.LENGTH_LONG
            )
            .show()
        Log.d(logtag, "onClick() ended - start button")
//        val buttonStart = findViewById(R.id.buttonStart) as Button
//        val buttonStop = findViewById(R.id.buttonStop) as Button
        changeState(true)
    }

    // Create an anonymous implementation of OnClickListener
    private val stopListener: View.OnClickListener = View.OnClickListener {
        Log.d(logtag, "onClick() called - stop button")
        Toast
            .makeText(
                requireContext(), "The Stop button was clicked.",
                Toast.LENGTH_LONG
            )
            .show()
        changeState(false)
        Log.d(logtag, "onClick() ended - stop button")
        talkerNode!!.stop()
    }

    private fun changeState(isWorking: Boolean) {
        this.isWorking = isWorking
        binding.buttonStart.isEnabled = !isWorking
        binding.buttonStop.isEnabled = isWorking
        if (isWorking) {
            rosExecutor?.addNode(talkerNode)
            talkerNode!!.start()
            rosExecutor?.spin()
        } else {
            talkerNode!!.stop()
            rosExecutor?.removeNode(talkerNode)
        }
    }

//    protected override fun onSaveInstanceState(outState: Bundle?) {
//        outState?.putBoolean(IS_WORKING, isWorking)
//        super.onSaveInstanceState(outState!!)
//    }
}