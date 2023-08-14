package com.libraryassets

object Constants {

    //小米推送
    const val MI_APP_ID = "2882303761518328603"
    const val MI_APP_KEY = "5361832890603"

    //百度语音合成
    const val BAI_DU_APP_ID = "11060787"
    const val BAI_DU_APP_KEY = "9GBNOL0KxGBFj88tTjSxioA9"
    const val BAI_DU_SECRET_KEY = "216f41a897fdc87ee7dca5cc3a2be7be"

    //bugly
    const val BUGLY_ID = "da71f8fca8"

    //白板
    const val WHITE_BOARD_ID="1188/PUQoefKd-62kNw"

    //声网
    const val AGORA_APP_ID="1144b589a6384d629b770521d51ee098"

    //课程类型 其实1是认知力 3是omo
    const val COURSE_TYPE_CONNECT=2//幼小衔接
    const val COURSE_TYPE_READ=4//阅读理解



    //Rxbus code
    const val RX_BUS_COURSE_FINISH="courseFinish"//课程结束或者被移出教室
    const val RX_BUS_UP_LOG="upLog"//上传日志
    const val RX_BUS_LEARN_REFRESH="learnRefresh"//刷新课表数据
    const val RX_BUS_REFRESH_AVATAR="refreshAvatar"//刷新头像
    const val RX_BUS_REFRESH_PENDANT="refreshPendant"//刷新挂件
    const val RX_BUS_REMIND_OUT_COURSE="remindOutCourse"//被踢出教室或者信令掉了
    const val RX_BUS_MAKE_UP_ID_REMIND="makeUpIdRemind"//补课素材更新提示
    const val RX_BUS_STATE_CHANGE="stateChange"//app切后台or切前台
    const val RX_BUS_COURSE_IMAGE="courseImage"//上课拍照提交图片or跳过or返回
    const val RX_BUS_HOMEWORK_REFRESH="homeworkRefresh"//每日任务刷新
    const val RX_BUS_BLUETOOTH_CHANGE="bluetoothChange"//蓝牙切换
    const val RX_BUS_PK_END="PKEnd"//pk结束
    const val RX_BUS_PK_CLOSE="PKClose"//关闭pk
    const val RX_BUS_PK_ADD_GOLD="PKAddGold"//pk添加金币
    const val RX_BUS_GOLD_NUM_CHANGE="goldNumChange"//金币数量改变
}