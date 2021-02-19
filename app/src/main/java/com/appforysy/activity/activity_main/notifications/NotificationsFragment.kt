package com.appforysy.activity.activity_main.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.appforysy.R
import com.toolmvplibrary.view.WaveFunctionView
import com.workysy.activity.activity_main.notifications.WrokViewModel

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: WrokViewModel
    private lateinit var functionView: WaveFunctionView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel = ViewModelProviders.of(this).get(WrokViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)
        functionView = root.findViewById(R.id.waveFunctionView);
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        functionView.postDelayed(Runnable { functionView.ChangeWaveLevel(1) }, 3100)
        functionView.postDelayed(Runnable { functionView.ChangeWaveLevel(8) }, 6200)
        functionView.postDelayed(Runnable { functionView.ChangeWaveLevel(3) }, 9200)
    }

}
