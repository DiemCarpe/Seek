package com.seek;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.zxing.client.android.CaptureActivity;

public class MainActivity extends AppCompatActivity {

    Button bt_scan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.capture);

//        bt_scan = findViewById(R.id.btn_scan);
//        bt_scan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //去寻找是否已经有了相机的权限
//                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
//
//                    //Toast.makeText(MainActivity.this,"您申请了动态权限",Toast.LENGTH_SHORT).show();
//                    //如果有了相机的权限有调用相机
//                    Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
////                intent.setAction(Intents.Scan.ACTION);
//                    startActivityForResult(intent, 1);
//
//                } else {
//                    //否则去请求相机权限
//                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, 100);
//
//                }
//            }
//        });
//
////        Intent intent = new Intent(this, CaptureActivity.class);
////        intent.setAction(Intents.Scan.ACTION);
////        intent.putExtra(Intents.Scan.FORMATS, "QR_CODE");
////        startActivityForResult(intent, REQUEST_CODE);
//
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
////        if (requestCode == REQUEST_CODE) {
////            if (data != null) {
////                String string=data.getStringExtra(Intents.Scan.RESULT);//条形码
////                Log.e("返回扫描结果",string);
////            }
////        }
//
//        Log.e("Main", "1");
//        if (requestCode == 1) {
//            Log.e("Main", "2");
//            if (data != null) {
//                Log.e("Main", "3");
//                String string = data.getStringExtra("displayContents");
//                Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        switch (requestCode) {
//            //如果requestCode为100 就走这里
//            case 100:
//
//                //permissions[0].equals(Manifest.permission.CAMERA)
//                //grantResults[0] == PackageManager.PERMISSION_GRANTED
//                //上面的俩个判断可有可无
//                if (permissions[0].equals(Manifest.permission.CAMERA)) {
//
//                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                        //如果用户同意了再去打开相机
//                        Toast.makeText(MainActivity.this, "允许申请权限", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
////                intent.setAction(Intents.Scan.ACTION);
//                        startActivityForResult(intent, 1);
//
//                    } else {
//                        //因为第一次的对话框是系统提供的 从这以后系统不会自动弹出对话框 我们需要自己弹出一个对话框
//                        //进行询问的工作
////                        startAlertDiaLog();
//                        Toast.makeText(this, "拒绝申请权限", Toast.LENGTH_SHORT).show();
//
//                    }
//
//                }
//
//                break;
//
//        }

    }


}