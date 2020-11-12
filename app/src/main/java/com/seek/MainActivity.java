package com.seek;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.client.android.CaptureActivity;
import com.google.zxing.client.android.camera.CameraManager;

public class MainActivity extends AppCompatActivity {

    private Button urlbutton, Settingbutton, calbutton;
    private CameraManager cameraManager;
    private Intent intent, intent1;
    private Menu menu;
    private static final String TAG = "第一个页面";

    CameraManager getCameraManager() {
        return cameraManager;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);
        urlbutton = (Button) findViewById(R.id.urlbutton);
        urlbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //显式intent
//                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                //隐式intent
//                Intent intent = new Intent("android.intent.action.MAIN");
//                intent.addCategory("android.intent.category.Settings");
                //其他
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.baidu.com"));
                startActivity(intent);
            }
        });
        Settingbutton = (Button) findViewById(R.id.Settingbutton);
        Settingbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = "MainActivity";
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                //传递参数，以extra_data为信号
//                intent.putExtra("extra_data",data);
//                startActivity(intent);
                startActivityForResult(intent, 1);
            }
        });

        //电话
        calbutton = (Button) findViewById(R.id.calbutton);
        calbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent calintent = new Intent(Intent.ACTION_DIAL);
                calintent.setData(Uri.parse("tel:10086"));
                startActivity(calintent);
            }
        });

    }

    //intent回调处理
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    String returneData = data.getStringExtra("data_return");
                    Toast.makeText(this, returneData, Toast.LENGTH_SHORT).show();
                } else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "屁都没有给", Toast.LENGTH_SHORT).show();

                }
                break;
            default:
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            //如果requestCode为100 就走这里
            case 100:

                //permissions[0].equals(Manifest.permission.CAMERA)
                //grantResults[0] == PackageManager.PERMISSION_GRANTED
                //上面的俩个判断可有可无
                if (permissions[0].equals(Manifest.permission.CAMERA)) {

                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        //如果用户同意了再去打开相机
                        Toast.makeText(MainActivity.this, "允许申请权限", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
//                intent.setAction(Intents.Scan.ACTION);
                        startActivityForResult(intent, 1);

                    } else {
                        //因为第一次的对话框是系统提供的 从这以后系统不会自动弹出对话框 我们需要自己弹出一个对话框
                        //进行询问的工作
//                        startAlertDiaLog();
                        Toast.makeText(this, "拒绝申请权限", Toast.LENGTH_SHORT).show();

                    }

                }

                break;

        }

    }
    //重写onCreateOptionsMenu事件
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
        //return super.onCreateOptionsMenu(menu);
    }

    //menu点击事件
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting_item:
                Intent hdintent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivityForResult(hdintent, 1);
                Toast.makeText(this, "回调设置", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ccsetting_item:
                String data = "老子从第一个首页来的！";
                Intent ccintent = new Intent(MainActivity.this, SettingsActivity.class);
                ccintent.putExtra("extra_data", data);
                startActivity(ccintent);
                Toast.makeText(this, "传参设置", Toast.LENGTH_SHORT).show();
                break;
            case R.id.url_item:
                Intent urlintent = new Intent(Intent.ACTION_VIEW);
                urlintent.setData(Uri.parse("http://www.baidu.com"));
                startActivity(urlintent);
                Toast.makeText(this, "链接", Toast.LENGTH_SHORT).show();
                break;
            case R.id.cal_item:
                Intent calintent = new Intent(Intent.ACTION_DIAL);
                calintent.setData(Uri.parse("tel:10086"));
                startActivity(calintent);
                Toast.makeText(this, "电话", Toast.LENGTH_SHORT).show();
                break;
            case R.id.about_item:
                Toast.makeText(this, "关于", Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }
}

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Log.e(TAG, "requestCode:" + requestCode + "," + resultCode + "," + "data:" + data);
//        if (requestCode == 1) {
//            Log.e(TAG, "requestCode:" + requestCode);
//            if (data != null) {
//                Log.e(TAG, "data:" + data);
//                String string = data.getStringExtra("displayContents");
//                Log.e(TAG, "onActivityResult: " + string);
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse(string));
//                startActivity(intent);
//                Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

//        //去寻找是否已经有了相机的权限
//        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
//
//            //Toast.makeText(MainActivity.this,"您申请了动态权限",Toast.LENGTH_SHORT).show();
//            //如果有了相机的权限有调用相机
//            Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
////                intent.setAction(Intents.Scan.ACTION);
//            startActivityForResult(intent, 1);
//
//        } else {
//            //否则去请求相机权限
//            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, 100);
//
//        }