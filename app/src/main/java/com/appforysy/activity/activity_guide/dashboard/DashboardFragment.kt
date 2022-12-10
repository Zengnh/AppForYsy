package com.appforysy.activity.activity_guide.dashboard

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.appforysy.R

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        textView = root.findViewById(R.id.text_dashboard)
        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
            textView?.text = it
        })
        iamgeScr = root.findViewById(R.id.iamgeScr);
        return root
    }

    var iamgeScr: ImageView? = null
    var textView: TextView? = null
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        textView?.setOnClickListener {
            iamgeScr?.setImageResource(R.drawable.anim_level_master_or_operation_lv1)
            var anim: AnimationDrawable = iamgeScr?.drawable as AnimationDrawable
            anim.isOneShot = true;
            anim.start()
            Handler().postDelayed(Runnable {
                //LinearInterpolator()     其变化速率恒定
                iamgeScr?.setImageResource(R.drawable.medal14)
                var visToInvis = ObjectAnimator.ofFloat(iamgeScr, "rotationY", 0f, 19f, 0f)
                visToInvis.setDuration(8 * 60)
                visToInvis.setInterpolator(DecelerateInterpolator())
                visToInvis.start()
            }, 13 * 60)


//            //LinearInterpolator()     其变化速率恒定
//            var visToInvis = ObjectAnimator.ofFloat(iamgeScr, "rotationY", 0f, 30f,0f,-30f,0f)
//            visToInvis.setDuration(2000)
//            //DecelerateInterpolator()   其变化开始速率较快，后面减速
////            AccelerateInterpolator()
//            visToInvis.setInterpolator(DecelerateInterpolator())
////            visToInvis.addListener(object: AnimatorListenerAdapter(){
////                override fun onAnimationEnd(animation: Animator?) {
////                    super.onAnimationEnd(animation)
//////                    var visToInvisRe = ObjectAnimator.ofFloat(iamgeScr, "rotationY", 30f, 0f)
//////                    visToInvisRe.setDuration(2000)
//////                    //DecelerateInterpolator()   其变化开始速率较快，后面减速
//////                    //DecelerateInterpolator()   其变化开始速率较快，后面减速
//////                    visToInvisRe.setInterpolator(DecelerateInterpolator())
//////                    visToInvisRe.start()
////                }
////            })
//            visToInvis.start()


        }
    }


}
