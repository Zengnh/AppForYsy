# AppForYsy
完整项目DEMO，其中lib为项目通用


	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

	dependencies {
		//别人的lib体验
	       //implementation 'com.github.ekaoe.AppForYsy:rootlibs:1.0.1'
	
               implementation 'com.github.ekaoe.AppForYsy:toolmvplibrary:1.01'
	}
