package com.appforysy.activity.activity_main.my

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Path
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.animation.addListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.appforysy.R
import com.rootlibs.loadimg.ToolGlide
import com.toolmvplibrary.tool_app.ToolScreenDensity
import com.toolmvplibrary.tool_premission.ToolAppPremission
import com.appforysy.activity.activity_main.information.WrokViewModel
import com.zxinglib.ZxingActivity

class FragmentMy : Fragment() {
    private lateinit var notificationsViewModel: WrokViewModel
    private lateinit var img_star_anim: ImageView
    private lateinit var imageViewOpen: ImageView

//    private lateinit var imageBgWather: ImageView
    private lateinit var iamgeGifStar: ImageView
    private lateinit var iamgeGifStar2: ImageView


    private lateinit var fraMyContent: LinearLayout
    private lateinit var lockLayout: View
    private lateinit var toScreen: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var instance=ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        notificationsViewModel = ViewModelProvider(this,instance).get(WrokViewModel::class.java)

//        notificationsViewModel = ViewModelProviders.of(this).get(WrokViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)
        lockLayout = root.findViewById(R.id.lockLayout)
        img_star_anim = root.findViewById(R.id.img_star_anim)
        iamgeGifStar = root.findViewById(R.id.iamgeGifStar)
        iamgeGifStar2 = root.findViewById(R.id.iamgeGifStar2)

        imageViewOpen = root.findViewById(R.id.imageViewOpen)
        fraMyContent = root.findViewById(R.id.fraMyContent)
        toScreen = root.findViewById(R.id.toScreen)
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageViewOpen.setOnClickListener {
            startAnim(img_star_anim)
        }

//        ToolGlide.loadImge(context, R.drawable.gif_blue_info, imageBgWather)

        ToolGlide.loadImge(context, R.mipmap.axt, iamgeGifStar2)
        ToolGlide.loadImge(context, R.drawable.image_line_while, iamgeGifStar)
        toScreen.setOnClickListener {
            toScreenGoods()
        }
    }

    fun startAnim(view: View) {
        lockLayout.visibility = View.GONE
//        var fraPar: FrameLayout.LayoutParams = view.layoutParams as FrameLayout.LayoutParams;
//        fraPar.topMargin = 100
//        fraPar.leftMargin = 0

        view.visibility = View.VISIBLE
        var screenW = ToolScreenDensity.getInstance().getRootViewWidth(activity)
        var screenH = ToolScreenDensity.getInstance().getRootViewHight(activity)
        var baseMTop = (screenH - screenW) / 2


        var path = Path()
        path.moveTo((screenW / 2).toFloat(), baseMTop.toFloat())
        path.lineTo(screenW.toFloat(), (baseMTop + screenW).toFloat())

        path.lineTo(0f, (baseMTop + screenW / 3).toFloat())

        path.lineTo(screenW.toFloat(), (baseMTop + screenW / 3).toFloat())

        path.lineTo(0f, (baseMTop + screenW).toFloat())
        path.lineTo((screenW / 2).toFloat(), baseMTop.toFloat())

        var objAnim = ObjectAnimator.ofFloat(view, "translationX", "translationY", path)
        objAnim.setDuration(3000)
        objAnim.addListener(onEnd = {
            view.visibility = View.INVISIBLE

            fraMyContent.visibility = View.VISIBLE
        })
        objAnim.start()
    }


    fun toScreenGoods() {
        if (ToolAppPremission.checkCamreaPermission(activity)) {
            screenGoodsResult.launch(Intent(activity, ZxingActivity::class.java))
        } else {
            ToolAppPremission.requestCamera(activity, 100)
        }
    }

    var screenGoodsResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                if (it.data != null) {
                    var result = it.data!!.getStringExtra("result")
                    Toast.makeText(context, " 扫描结果：" + result, Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, " 商品扫描失败：", Toast.LENGTH_LONG).show()
                }
            }
        }
}
