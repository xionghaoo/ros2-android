package com.udi.ros2_android

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.udi.ros2_android.databinding.ActivityMainBinding
import xh.rabbit.core.replaceFragment
import xh.rabbit.ros2android.Ros2Android
import xh.rabbit.ros2android.TalkerNode



class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Ros2Android.hello()

        binding.btnStartTalker.setOnClickListener {
            replaceFragment(TalkerFragment(), R.id.fragment_container1)
        }

        binding.btnStartListener.setOnClickListener {
            replaceFragment(ListenerFragment(), R.id.fragment_container2)
        }
    }
}