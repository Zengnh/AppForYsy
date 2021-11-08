package com.workysy.activity.activity_main.notifications

import android.graphics.Color
import android.graphics.Paint
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
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.appforysy.R

class WorkFragment : Fragment() {

    private lateinit var notificationsViewModel: WrokViewModel
    private lateinit var textview:TextView
    private lateinit var textviewline:TextView

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
                ViewModelProviders.of(this).get(WrokViewModel::class.java)
        val root = inflater.inflate(R.layout.fragement_work, container, false)

        textview=root.findViewById(R.id.tvline)
        textviewline=root.findViewById(R.id.tv)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TextView进一步深化：
        // Textview 可以对其文字进行格式化。
        // 通过查询资料，了解到格式化文字的方式主要分为两大类：
        // 第一类：HTML标签格式化文字
        // 代码比较简单，如下：

        // TextView htmlFormateTextView = (TextView)getActivity().findViewById(R.id.textviewa);
        // String source = "这只是一个测试，测试<u>下划线</u>、<i>斜体字</i>、<font color='red'>红色字</font>的格式";
        // htmlFormateTextView.setText(Html.fromHtml(source));
        // 第二类通过SpannableString进行格式化操作


        // TextView进一步深化：
        // Textview 可以对其文字进行格式化。
        // 通过查询资料，了解到格式化文字的方式主要分为两大类：
        // 第一类：HTML标签格式化文字
        // 代码比较简单，如下：

        // TextView htmlFormateTextView = (TextView)getActivity().findViewById(R.id.textviewa);
        // String source = "这只是一个测试，测试<u>下划线</u>、<i>斜体字</i>、<font color='red'>红色字</font>的格式";
        // htmlFormateTextView.setText(Html.fromHtml(source));
        // 第二类通过SpannableString进行格式化操作
        var msp: SpannableString? = null
        // 创建一个 SpannableString对象
        // 创建一个 SpannableString对象
        msp = SpannableString("字体测试字体大小一半两倍前景色背景色正常粗体斜体粗斜体下划线删除线x1x2电话邮件网站短信彩信地图X轴综合")

        // 设置字体(default,default-bold,monospace,serif,sans-serif)

        // 设置字体(default,default-bold,monospace,serif,sans-serif)
        msp.setSpan(TypefaceSpan("monospace"), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        msp.setSpan(TypefaceSpan("serif"), 2, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        // 设置字体大小（绝对值,单位：像素）

        // 设置字体大小（绝对值,单位：像素）
        msp.setSpan(AbsoluteSizeSpan(20), 4, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        msp.setSpan(
            AbsoluteSizeSpan(20, true),
            6,
            8,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        ) // 第二个参数boolean dip，如果为true，表示前面的字体大小单位为dip，否则为像素，同上。


        // 设置字体大小（相对值,单位：像素） 参数表示为默认字体大小的多少倍

        // 设置字体大小（相对值,单位：像素） 参数表示为默认字体大小的多少倍
        msp.setSpan(
            RelativeSizeSpan(0.5f),
            8,
            10,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        ) // 0.5f表示默认字体大小的一半

        msp.setSpan(
            RelativeSizeSpan(2.0f),
            10,
            12,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        ) // 2.0f表示默认字体大小的两倍


        // 设置字体前景色

        // 设置字体前景色
        msp.setSpan(
            ForegroundColorSpan(Color.MAGENTA),
            12,
            15,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        ) // 设置前景色为洋红色


        // 设置字体背景色

        // 设置字体背景色
        msp.setSpan(
            BackgroundColorSpan(Color.CYAN),
            15,
            18,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        ) // 设置背景色为青色


        // 设置字体样式正常，粗体，斜体，粗斜体

        // 设置字体样式正常，粗体，斜体，粗斜体
        msp.setSpan(
            StyleSpan(Typeface.NORMAL),
            18,
            20,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        ) // 正常

        msp.setSpan(
            StyleSpan(Typeface.BOLD),
            20,
            22,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        ) // 粗体

        msp.setSpan(
            StyleSpan(Typeface.ITALIC),
            22,
            24,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        ) // 斜体

        msp.setSpan(
            StyleSpan(Typeface.BOLD_ITALIC),
            24,
            27,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        ) // 粗斜体


        // 设置下划线

        // 设置下划线
        msp.setSpan(UnderlineSpan(), 27, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        // 设置删除线

        // 设置删除线
        msp.setSpan(StrikethroughSpan(), 30, 33, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        // 设置上下标

        // 设置上下标
        msp.setSpan(SubscriptSpan(), 34, 35, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE) // 下标

        msp.setSpan(SuperscriptSpan(), 36, 37, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE) // 上标


        // 超级链接（需要添加setMovementMethod方法附加响应）

        // 超级链接（需要添加setMovementMethod方法附加响应）
        msp.setSpan(URLSpan("tel:4155551212"), 37, 39, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE) // 电话

        msp.setSpan(
            URLSpan("mailto:webmaster@google.com"),
            39,
            41,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        ) // 邮件

        msp.setSpan(URLSpan("http://www.baidu.com"), 41, 43, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE) // 网络

        msp.setSpan(
            URLSpan("sms:4155551212"),
            43,
            45,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        ) // 短信 使用sms:或者smsto:

        msp.setSpan(
            URLSpan("mms:4155551212"),
            45,
            47,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        ) // 彩信 使用mms:或者mmsto:

        msp.setSpan(
            URLSpan("geo:38.899533,-77.036476"),
            47,
            49,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        ) // 地图


        // 设置字体大小（相对值,单位：像素） 参数表示为默认字体宽度的多少倍

        // 设置字体大小（相对值,单位：像素） 参数表示为默认字体宽度的多少倍
        msp.setSpan(
            ScaleXSpan(2.0f),
            49,
            51,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        ) // 2.0f表示默认字体宽度的两倍，即X轴方向放大为默认字体的两倍，而高度不变

        // SpannableString对象设置给TextView
        // SpannableString对象设置给TextView
        textview.setText(msp)
        // 设置TextView可点击
        // 设置TextView可点击
        textview.setMovementMethod(LinkMovementMethod.getInstance())

        // 在使用SpannableString对象时要注意
        // Spanned.SPAN_EXCLUSIVE_EXCLUSIVE等的作用：
        // 用来标识在 Span 范围内的文本前后输入新的字符时是否把它们也应用这个效果。分别有
        // Spanned.SPAN_EXCLUSIVE_EXCLUSIVE(前后都不包括)、Spanned.SPAN_INCLUSIVE_EXCLUSIVE(前面包括，后面不包括)、Spanned.SPAN_EXCLUSIVE_INCLUSIVE(前面不包括，后面包括)、Spanned.SPAN_INCLUSIVE_INCLUSIVE(前后都包括)。

//下划线处理文字

        // 在使用SpannableString对象时要注意
        // Spanned.SPAN_EXCLUSIVE_EXCLUSIVE等的作用：
        // 用来标识在 Span 范围内的文本前后输入新的字符时是否把它们也应用这个效果。分别有
        // Spanned.SPAN_EXCLUSIVE_EXCLUSIVE(前后都不包括)、Spanned.SPAN_INCLUSIVE_EXCLUSIVE(前面包括，后面不包括)、Spanned.SPAN_EXCLUSIVE_INCLUSIVE(前面不包括，后面包括)、Spanned.SPAN_INCLUSIVE_INCLUSIVE(前后都包括)。

//下划线处理文字
        textviewline.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG)
        textviewline.setText("shshhshhshs")

    }
    fun mianShow(){
        val content = "getString(R.string.protocolAgree_info)getString(R.string.protocolAgree_info)getString(R.string.protocolAgree_info)"

        var sprint = SpannableString(content)

        sprint.setSpan(object : ClickableSpan() {
            override fun onClick(view: View) {

            }
            override fun updateDrawState(ds: TextPaint) {
//                super.updateDrawState(ds)
                ds.setUnderlineText(false);
            }
        }, 18, 24, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)

        sprint.setSpan(
            ForegroundColorSpan(resources.getColor(R.color.color_1e)),
            18,
            24,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        sprint.setSpan(object : ClickableSpan() {
            override fun onClick(view: View) {
            }
            override fun updateDrawState(ds: TextPaint) {
//                super.updateDrawState(ds)
                ds.setUnderlineText(false);
            }
        }, 25, 33, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)

        sprint.setSpan(
            ForegroundColorSpan(resources.getColor(R.color.color_1e)),
            25,
            33,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        ) // 设置前景色为洋红色


        // 在使用SpannableString对象时要注意
        // Spanned.SPAN_EXCLUSIVE_EXCLUSIVE等的作用：
        // 用来标识在 Span 范围内的文本前后输入新的字符时是否把它们也应用这个效果。分别有
        // Spanned.SPAN_EXCLUSIVE_EXCLUSIVE(前后都不包括)、Spanned.SPAN_INCLUSIVE_EXCLUSIVE(前面包括，后面不包括)、Spanned.SPAN_EXCLUSIVE_INCLUSIVE(前面不包括，后面包括)、Spanned.SPAN_INCLUSIVE_INCLUSIVE(前后都包括)。

        //下划线处理文字
//        textInfo.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG)
        textview.setMovementMethod(LinkMovementMethod.getInstance())
        textview.setText(sprint)
    }
}
