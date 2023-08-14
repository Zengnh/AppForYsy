package com.appforysy.activity.activity_main.information

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.appforysy.R

/**
 * 说明
 */
class FragmentInformation : Fragment() {

    private lateinit var notificationsViewModel: WrokViewModel
    private lateinit var tvFirst: TextView
    private lateinit var tvInfo02: TextView
    private lateinit var recyclerInfo: RecyclerView
    private lateinit var adapter: AdapterInformation

    //    ---------------------------------------------------------------
    private lateinit var recyclerToday: RecyclerView
    private lateinit var adapterDay: AdapterInfoDay

    private lateinit var reflushView: SwipeRefreshLayout

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel = ViewModelProviders.of(this).get(WrokViewModel::class.java)
        val root = inflater.inflate(R.layout.fragement_work, container, false)
        notificationsViewModel.initData()
//        ----------------------------------------------------------------

        recyclerToday = root.findViewById(R.id.recyclerToday)
        recyclerToday.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        adapterDay = AdapterInfoDay(notificationsViewModel.datalistDayy)
        recyclerToday.adapter = adapterDay
//        --------------------------------------------------------------------------------------------------------------------
        recyclerInfo = root.findViewById(R.id.recyclerInfo)
        recyclerInfo.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        adapter = AdapterInformation(notificationsViewModel.datalist)
        recyclerInfo.adapter = adapter
//--------------------------------------------------------------------------------------------------------------------------------------------
        reflushView = root.findViewById(R.id.reflushView)
        reflushView.setOnRefreshListener {
            reflushView.setRefreshing(false);
        }


//        ----------------------------------------------------------------

        tvFirst = root.findViewById(R.id.tvFirst)
        tvInfo02 = root.findViewById(R.id.tvInfo02)


//        android:ellipsize="start"        //省略号在开头
//        android:ellipsize="middle"       //省略号在中间
//        android:ellipsize="end"          //省略号在结尾
//        android:ellipsize="marquee"      //跑马灯显示
//        然而在使用ellipsize属性时必须要添加以下属性：
//        android:layout_width="wrap_content"

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mianShow()

    }

    fun mianShow() {
//        val content ="学习力，即学习能力，也叫做为了达成某种目标而进行自我改变的能力或者认识与掌握事物规律的能力。学习能力在学习上的作用体现在两方面：一是在学习效率方面，比如记忆力、理解力、想象力、阅读力等等；二是在学习意志力方面，比如专注力、抗干扰能力、诱惑抵御能力等等 "
        val content = "专注力,指高度集中的注意力,是信息加工活动的必要条件与能力,注意力使所选择的信息处于活动的中心,并加以维持,从而能够进行有效的加工。" +
                "专注力包含自动化专注能力及控制化专注能力。优秀的专注力=优秀的全脑能力。" +
                "对儿童来说,是指他能够注意听、注意读、注意理解、注意记忆、注意思考、注意说、写做的能力。"

        var sprint = SpannableString(content)
        sprint.setSpan(object : ClickableSpan() {
            override fun onClick(view: View) {
                Toast.makeText(context, "专注力 点击", Toast.LENGTH_SHORT).show()
            }

            override fun updateDrawState(ds: TextPaint) {
//                super.updateDrawState(ds)
                ds.setUnderlineText(false);
            }
        }, 1, 3, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)


        // 在使用SpannableString对象时要注意
        // Spanned.SPAN_EXCLUSIVE_EXCLUSIVE等的作用：
        // 用来标识在 Span 范围内的文本前后输入新的字符时是否把它们也应用这个效果。分别有
        // Spanned.SPAN_EXCLUSIVE_EXCLUSIVE(前后都不包括)、
        // Spanned.SPAN_INCLUSIVE_EXCLUSIVE(前面包括，后面不包括)、
        // Spanned.SPAN_EXCLUSIVE_INCLUSIVE(前面不包括，后面包括)、
        // Spanned.SPAN_INCLUSIVE_INCLUSIVE(前后都包括)。
        //        设置字体颜色，设置前景色
        sprint.setSpan(
            ForegroundColorSpan(resources.getColor(R.color.color_blue, null)),
            4, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        // 设置字体大小（相对值,单位：像素） 参数表示为默认字体宽度的多少倍
        sprint.setSpan(
            ScaleXSpan(1.3f), 4, 5,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        ) // 2.0f表示默认字体宽度的两倍，即X轴方向放大为默认字体的两倍，而高度不变

//        // 设置字体(default,default-bold,monospace,serif,sans-serif)
//        // 设置字体(default,default-bold,monospace,serif,sans-serif)
        sprint.setSpan(TypefaceSpan("monospace"), 5, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        sprint.setSpan(TypefaceSpan("serif"), 10, 14, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        // 设置字体大小（绝对值,单位：像素）
        // 设置字体大小（绝对值,单位：像素）
        sprint.setSpan(AbsoluteSizeSpan(20), 14, 17, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        sprint.setSpan(
            AbsoluteSizeSpan(20, true),
            17,
            19,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        ) // 第二个参数boolean dip，如果为true，表示前面的字体大小单位为dip，否则为像素，同上。

        // 设置字体大小（相对值,单位：像素） 参数表示为默认字体大小的多少倍
        sprint.setSpan(
            RelativeSizeSpan(1.3f), 19, 21,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        ) // 0.5f表示默认字体大小的一半

        sprint.setSpan(
            RelativeSizeSpan(0.7f), 21, 22,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        ) // 2.0f表示默认字体大小的两倍


        // 设置字体前景色

        // 设置字体前景色
        sprint.setSpan(
            ForegroundColorSpan(Color.MAGENTA), 22, 26,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        ) // 设置前景色为洋红色


        // 设置字体背景色

        // 设置字体背景色
        sprint.setSpan(
            BackgroundColorSpan(Color.CYAN), 27, 29,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        ) // 设置背景色为青色


        // 设置字体样式正常，粗体，斜体，粗斜体

        // 设置字体样式正常，粗体，斜体，粗斜体
        sprint.setSpan(
            StyleSpan(Typeface.NORMAL), 29, 30,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        ) // 正常

        sprint.setSpan(
            StyleSpan(Typeface.BOLD), 30, 33,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        ) // 粗体

        sprint.setSpan(
            StyleSpan(Typeface.ITALIC), 33, 34,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        ) // 斜体

        sprint.setSpan(
            StyleSpan(Typeface.BOLD_ITALIC), 34, 37,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        ) // 粗斜体

        // 设置下划线
        sprint.setSpan(UnderlineSpan(), 40, 44, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        // 设置删除线
        sprint.setSpan(StrikethroughSpan(), 45, 47, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        // 设置上下标
        sprint.setSpan(SubscriptSpan(), 51, 53, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE) // 下标


        // 设置上下标
        sprint.setSpan(SuperscriptSpan(), 55, 56, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE) // 上标


        // 超级链接（需要添加setMovementMethod方法附加响应）

        // 超级链接（需要添加setMovementMethod方法附加响应）
        sprint.setSpan(URLSpan("tel:4155551212"), 56, 59, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE) // 电话

        sprint.setSpan(
            URLSpan("mailto:webmaster@google.com"),
            62,
            66,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        ) // 邮件

        sprint.setSpan(
            URLSpan("http://www.baidu.com"),
            67,
            69,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        ) // 网络

//---------------------------------------------------------------------------

        sprint.setSpan(
            URLSpan("sms:4155551212"),
            70, 72,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        ) // 短信 使用sms:或者smsto:

        sprint.setSpan(
            URLSpan("mms:4155551212"),
            72, 74,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        ) // 彩信 使用mms:或者mmsto:

        sprint.setSpan(
            URLSpan("geo:38.899533,-77.036476"),
            74, 76,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        ) // 地图

        //下划线处理文字
//        textInfo.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG)
        tvFirst.setMovementMethod(LinkMovementMethod.getInstance())
        tvFirst.setText(sprint)
    }


}
