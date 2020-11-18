package com.seek;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.zxing.Result;
import com.google.zxing.client.android.CaptureActivity;
import com.google.zxing.client.android.CaptureActivityHandler;
import com.google.zxing.client.android.FinishListener;
import com.google.zxing.client.android.camera.CameraManager;
import com.seek.camera.open.CameraFacing;

import java.io.IOException;

//public class MainActivity extends  AppCompatActivity {
public final class MainActivity extends Activity implements SurfaceHolder.Callback {

    private Button urlbutton, Settingbutton, calbutton,scanbutton,button;
    private CameraManager cameraManager;
    private Intent intent, intent1;
    private Menu menu;
    private static final String TAG = "Main页面";
    private int clickNumber;
    private InactivityTimer inactivityTimer;
    private CameraFacing direction=CameraFacing.BACK; //默认调用后置摄像头
    private ImageView switchCamera;
    private CaptureActivityHandler handler;

    public Handler getHandler() {
        return handler;
    }
    public MainActivity() {
    }

    CameraManager getCameraManager() {
        return cameraManager;
    }

    @Override
    protected void onResume() {
        super.onResume();
        switchCamera=(ImageView)findViewById(R.id.switch_camera);


        switchCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "onClick: 切换相机" );
                //不是前置摄像头就切为后置
                if(direction.equals(CameraFacing.FRONT)){
                    direction = CameraFacing.BACK;
                }else{
                    //切换为前置摄像头
                    direction = CameraFacing.FRONT;
                }
                initCamera(direction);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.capture);
        scanbutton=(Button)findViewById(R.id.scanbutton);
        scanbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MainActivity.this,CaptureActivity.class);
                startActivity(intent);
            }
        });
        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(clickNumber%2 == 0 ){
                    Toast.makeText(MainActivity.this, "你真好奇", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(MainActivity.this, "你真闲", Toast.LENGTH_SHORT).show();
                }
                clickNumber++;
            }
        });
        //去寻找是否已经有了相机的权限
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

            //Toast.makeText(MainActivity.this,"您申请了动态权限",Toast.LENGTH_SHORT).show();
            Log.e(TAG, "onCreate: 已经申请了动态权限" );
            //如果有了相机的权限有调用相机
            Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
            startActivityForResult(intent, 1);

        } else {
            //否则去请求相机权限
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, 100);

        }
        //取出临时保存的数据
        if (savedInstanceState != null){
            String tempData=savedInstanceState.getString("data_key");
            Toast.makeText(this, tempData, Toast.LENGTH_SHORT).show();
        }

    }

    //intent回调处理
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    String returneData = data.getStringExtra("displayContents");
                    Intent gpurl = new Intent(Intent.ACTION_VIEW);
                    gpurl.setData(Uri.parse(returneData));
                    startActivity(gpurl);
                    Log.e(TAG, "onActivityResult: "+returneData );
                } else if (resultCode == RESULT_CANCELED) {
                    //Toast.makeText(this, "扫描的二维码有误！", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onActivityResult: 扫描的二维码有误或者退出扫面页面" );
                }
                break;
            case 2:
                if (resultCode == RESULT_OK){
                    String returneData = data.getStringExtra("data_return");
                    Log.e(TAG, "onActivityResult: "+returneData );
                    Toast.makeText(this, returneData, Toast.LENGTH_SHORT).show();

                } else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "屁都没有给", Toast.LENGTH_SHORT).show();

                }
                break;
            default:
        }
    }
    //页面被销毁后保存临时数据
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        String tempData="保存的临时数据";
        outState.putString("data_key",tempData);
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
                startActivityForResult(hdintent, 2);
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
    //处理扫描后的结果,把扫描后的结果通过浏览器打开
    public void handleDecode(Result rawResult, Bitmap barcode, float scaleFactor){
        inactivityTimer.onActivity();
        String result = rawResult.getText();
        if (result.equals("")){
            Toast.makeText(MainActivity.this, "Scan Failed!", Toast.LENGTH_SHORT).show();
        }else{
            Log.e(TAG, "handleDecode: "+result);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(result));
            startActivity(intent);
        }
    }
    private void displayFrameworkBugMessageAndExit () {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(com.google.zxing.client.android.R.string.app_name));
        builder.setMessage(getString(com.google.zxing.client.android.R.string.msg_camera_framework_bug));
        builder.setPositiveButton(com.google.zxing.client.android.R.string.button_ok, new FinishListener(this));
        builder.setOnCancelListener(new FinishListener(this));
        builder.show();
    }
    private void initCamera (SurfaceHolder surfaceHolder){
        if (surfaceHolder == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
        }
        if (cameraManager.isOpen()) {
            Log.w(TAG, "initCamera() while already open -- late SurfaceView callback?");


            //如果已经打开则关闭当前摄像头然后再打开一个其他的
            handler = null ;
            cameraManager.closeDriver();
//            return;
        }
        try {
            cameraManager.openDriver(surfaceHolder);
            // Creating the handler starts the preview, which can also throw a RuntimeException.
            if (handler == null) {
                handler = new CaptureActivityHandler(this, decodeFormats, decodeHints, characterSet, cameraManager);
            }
//            decodeOrStoreSavedBitmap(null, null);
        } catch (IOException ioe) {
            Log.w(TAG, ioe);
            displayFrameworkBugMessageAndExit();
        } catch (RuntimeException e) {
            // Barcode Scanner has seen crashes in the wild of this variety:
            // java.?lang.?RuntimeException: Fail to connect to camera service
            Log.w(TAG, "Unexpected error initializing camera", e);
            displayFrameworkBugMessageAndExit();
        }
    }


    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

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


//        urlbutton = (Button) findViewById(R.id.urlbutton);
//        urlbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //显式intent
////                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
//                //隐式intent
////                Intent intent = new Intent("android.intent.action.MAIN");
////                intent.addCategory("android.intent.category.Settings");
//                //其他
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse("http://www.baidu.com"));
//                startActivity(intent);
//            }
//        });
//        Settingbutton = (Button) findViewById(R.id.Settingbutton);
//        Settingbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String data = "MainActivity";
//                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
//                //传递参数，以extra_data为信号
////                intent.putExtra("extra_data",data);
////                startActivity(intent);
//                startActivityForResult(intent, 1);
//            }
//        });
//
//        //电话
//        calbutton = (Button) findViewById(R.id.calbutton);
//        calbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent calintent = new Intent(Intent.ACTION_DIAL);
//                calintent.setData(Uri.parse("tel:10086"));
//                startActivity(calintent);
//            }
//        });