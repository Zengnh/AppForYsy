package com.mykotlin.activity.activity_bluetouch

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.mykotlin.activity.BaseActivity
import com.mykotlin.databinding.ActivityBluetouchBinding
import androidx.core.app.ActivityCompat.startActivityForResult


/**
 * 用来处理蓝牙模块吧。明天写测试用例
 *
 * <!--蓝牙权限-->
<uses-permission android:name="android.permission.BLUETOOTH"/>
<!--让应用启动设备发现或操纵蓝牙设置-->
<uses-permission android:name="android.permission.BLUETOOTH_ADMIN"/>
<!-- LE Beacons位置相关权限-->
<!-- 如果设配Android9及更低版本，可以申请 ACCESS_COARSE_LOCATION -->
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<!--ble模块 设置为true表示只有支持ble的手机才能安装-->
<uses-feature
android:name="android.hardware.bluetooth_le"
android:required="true" />
 *
 */
class BluetouchActivity : BaseActivity() {
    lateinit var binderBluetouchActivity: ActivityBluetouchBinding;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binderBluetouchActivity = ActivityBluetouchBinding.inflate(layoutInflater)
        setContentView(binderBluetouchActivity.root)

//        PackageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)
        openBlue();
        regitBro();
        initEvent();
    }

    private fun initEvent() {
        binderBluetouchActivity.startScreen.setOnClickListener {
            mBluetoothScreen.startScan(srcreen)
            Handler().postDelayed(object : Runnable {
                override fun run() {
                    stopScreen()
                }
            }, 8000);
//            mBluetoothScreen.startScan(null,mScanSettings,srcreen);
        }
    }

    lateinit var mBluetoothAdapter: BluetoothAdapter
    lateinit var mBluetoothScreen: BluetoothLeScanner
    fun openBlue() {
        //初始化ble设配器
        //初始化ble设配器
        val manager: BluetoothManager =getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
//        mBluetoothAdapter = manager.getAdapter()
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
//判断蓝牙是否开启，如果关闭则请求打开蓝牙
//判断蓝牙是否开启，如果关闭则请求打开蓝牙
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            //方式一：请求打开蓝牙
            val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(intent, 1)
            //方式二：半静默打开蓝牙
            //低版本android会静默打开蓝牙，高版本android会请求打开蓝牙
            //mBluetoothAdapter.enable();
        } else {
            mBluetoothScreen = mBluetoothAdapter.bluetoothLeScanner
            getCutBlue()
        }
    }

    fun getCutBlue(){
        val pairedDevices = mBluetoothAdapter.bondedDevices
// If there are paired devices
// If there are paired devices
        if (pairedDevices.size > 0) {
            // Loop through paired devices
            for (device in pairedDevices) {
                // Add the name and address to an array adapter to show in a ListView
                Log.i("znh","@@defList=${device.name}${device.address}" )
            }
        }
    }


    fun stopScreen() {
        mBluetoothScreen.stopScan(srcreen);
    }

    var strDevice = ""
    val srcreen: ScanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult?) {
            super.onScanResult(callbackType, result)
            var device: BluetoothDevice = result!!.getDevice();
            // 更新数据
            strDevice += device.getAddress() + "" + device.getName()
            binderBluetouchActivity.textView.setText("onScanResult：" + strDevice)
            Log.i("znh", "onScanResult：" + strDevice);
        }

        override fun onBatchScanResults(results: MutableList<ScanResult>?) {
            super.onBatchScanResults(results)
            Log.i("znh", "扫描到多少个设备：" + results?.size);
            if (results != null) {
                for (sc: ScanResult in results) {
                    Log.i("znh", "设备列表：" + sc.device.name);
                    Log.i("znh", "设备列表：" + sc.device.address);
                }
            }
//            binderBluetouchActivity.textView.setText("扫描到多少个设备："+results?.size)
//            //            //停止扫描
//            stopScreen()
        }

        override fun onScanFailed(errorCode: Int) {
            super.onScanFailed(errorCode)
            Log.i("znh", "onScanFailed：" + errorCode);
//            errorCode=1;Fails to start scan as BLE scan with the same settings is already started by the app.
//            errorCode=2;Fails to start scan as app cannot be registered.
//            errorCode=3;Fails to start scan due an internal error
//                    errorCode=4;Fails to start power optimized scan as this feature is not supported
            binderBluetouchActivity.textView.setText("扫描设备err：" + errorCode)
        }
    }

    // Create a BroadcastReceiver for ACTION_FOUND
    // Create a BroadcastReceiver for ACTION_FOUND
    val mReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action

            Log.i("znh", "#############"+action)
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND == action) {
                // Get the BluetoothDevice object from the Intent
                val device =intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                // Add the name and address to an array adapter to show in a ListView
                Log.i("znh", """${device.name} ${device.address}""".trimIndent()
                )
            }else if (action == BluetoothAdapter.ACTION_STATE_CHANGED) {

                //获取蓝牙广播中的蓝牙新状态
                val blueNewState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0)
                //获取蓝牙广播中的蓝牙旧状态
                val blueOldState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0)
                when (blueNewState) {
                    BluetoothAdapter.STATE_TURNING_ON -> {
                    }
                    BluetoothAdapter.STATE_ON -> {
                    }
                    BluetoothAdapter.STATE_TURNING_OFF -> {
                    }
                    BluetoothAdapter.STATE_OFF -> {
                    }
                }
            }


        }
    }


    fun regitBro() {
// Register the BroadcastReceiver
        // Register the BroadcastReceiver
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED) //搜索结束
        registerReceiver(mReceiver, filter)
    }

}