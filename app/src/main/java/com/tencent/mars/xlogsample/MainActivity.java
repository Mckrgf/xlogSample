/*
* Tencent is pleased to support the open source community by making Mars available.
* Copyright (C) 2016 THL A29 Limited, a Tencent company. All rights reserved.
*
* Licensed under the MIT License (the "License"); you may not use this file except in 
* compliance with the License. You may obtain a copy of the License at
* http://opensource.org/licenses/MIT
*
* Unless required by applicable law or agreed to in writing, software distributed under the License is
* distributed on an "AS IS" basis, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
* either express or implied. See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.tencent.mars.xlogsample;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.tencent.mars.xlog.Xlog;
import com.tencent.mars.xlog.Log;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        String logPath = getFilesDir().getPath() + "/logsample/xlog";
        String logPath = Environment.getExternalStorageDirectory().getPath() + "/aaaaa/xlog";
        String cachePath = Environment.getExternalStorageDirectory().getPath() + "/aaaaaCache/xlog";




//        Xlog.XLogConfig xLogConfig = new Xlog.XLogConfig();
        Xlog xlog = new Xlog();

//        Xlog.open(true,Xlog.LEVEL_DEBUG,Xlog.AppednerModeAsync,cachePath,logPath,"ty","");


        Log.setLogImp(xlog);
        Log.setConsoleLogOpen(true);
        Log.appenderOpen(Xlog.LEVEL_DEBUG, Xlog.AppednerModeAsync, cachePath, logPath, "LOGSAMPLE", 0);
        xlog.setMaxFileSize(0, 1024L *1024*2);
        xlog.setMaxAliveTime(0,3600*24*1);
        //1w次 110k左右
        //10w次 1200k左右
        //10.w次 10700k左右
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 999999; i++) {
                    Log.d("zxcv","我要打印了：" + i);
//                    new Xlog().logD(0,"zxcv","tt","",0,0,System.currentTimeMillis(),0,"aaaaaa");
                }
                Log.appenderFlush();
            }
        }).start();





        Log.d("zxcv","我打印完了也flush完了");

        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText("stringFromJNI()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.appenderClose();
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
//    public native String stringFromJNI();

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("c++_shared");
        System.loadLibrary("marsxlog");
//        System.loadLibrary("native-lib");
    }
}
