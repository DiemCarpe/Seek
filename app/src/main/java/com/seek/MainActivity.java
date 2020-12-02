package com.seek;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
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
//import com.seek.CaptureActivity;
import com.seek.decode.MainActivityHandler;
//import com.google.zxing.client.android.FinishListener;
import com.seek.camera.CameraManager;
import com.seek.camera.open.CameraFacing;
import com.seek.view.ViewfinderView;
import com.seek.BaseActivity;

import java.io.IOException;

public class MainActivity extends BaseActivity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
//public final class MainActivity extends Activity implements SurfaceHolder.Callback {

    private Button urlbutton, Settingbutton, calbutton, scanbutton, button, submit;
    private CameraManager cameraManager;
    private Intent intent, intent1;
    private Menu menu;
    private static final String TAG = "Main页面";
    private int clickNumber;
    private InactivityTimer inactivityTimer;
    private CameraFacing direction = CameraFacing.BACK; //默认调用后置摄像头
    private ImageView switchCamera, icon,mainMenu;
    private MainActivityHandler handler;
    private SurfaceView surfaceView;
    private ViewfinderView viewfinderView;
    private String data = "第一个从第一个首页来的！";
    private String data1 = "第二个从第一个首页来的！";
    private EditText editText;
    private ProgressBar progressBar;
    public Handler getHandler() {
        return handler;
    }

//    public MainActivity() {
//    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onCreate(savedInstanceState);
        Log.e(TAG, "MainActivity onCreate: Task is: " + getTaskId());
        setContentView(R.layout.activity_main);
        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.hide();
        }
        //扫一扫
        scanbutton = (Button) findViewById(R.id.scanbutton);
        scanbutton.setOnClickListener(this);
        //无聊
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
        //输入内容的获取
        editText = (EditText) findViewById(R.id.editTextDate);
        //输入内容的显示
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(this);
        icon = (ImageView) findViewById(R.id.icon);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //PopupMenu点击与应用  因为PopupMenu没有在activity_main 布局内所以单独写了一个点击事件
        mainMenu = (ImageView) findViewById(R.id.main_menu);
        mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //通过PopupMenu的构造函数实例化一个PopupMenu对象，传递一个当前上下文对象以及绑定的View
                PopupMenu popupMenu = new PopupMenu(MainActivity.this,view);
                //获取菜单填充器
                MenuInflater inflater = popupMenu.getMenuInflater();
                //使用MenuInflater.inflate()方法加载menu的XML文件到PopupMenu.getMenu()中
                inflater.inflate(R.menu.main,popupMenu.getMenu());
                //绑定菜单的点击事件
                popupMenu.setOnMenuItemClickListener(MainActivity.this);
                //显示
                popupMenu.show();
                Log.e(TAG, "onClick: popupMenu");
            }
        });
        //取出临时保存的数据 2
        if (savedInstanceState != null) {
            String tempData = savedInstanceState.getString("data_key");
            Toast.makeText(this, tempData, Toast.LENGTH_SHORT).show();
            Log.e(TAG, "onCreate: " + tempData);
        }

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.scanbutton:
//                Intent intent = new Intent(MainActivity.this, DialogAcitvity.class);
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.button:
                if (clickNumber % 2 == 0) {
                    Toast.makeText(MainActivity.this, "你真好奇", Toast.LENGTH_SHORT).show();
                    //重新设置照片
                    icon.setImageResource(R.drawable.switch_camera);

                } else {
                    Toast.makeText(MainActivity.this, "你真闲", Toast.LENGTH_SHORT).show();
                    icon.setImageResource(R.drawable.ic_seek);
                }
                //显示进度
                if (progressBar.getVisibility() == View.GONE) {
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    progressBar.setVisibility(View.GONE);
                }
                int progress = progressBar.getProgress();
                progress = progress + 10;
                if (progress > 100) {
                    progress = 0;
                }
                progressBar.setProgress(progress);

                clickNumber++;
                Intent intent1 = new Intent(MainActivity.this, ThirdActivity.class);
                startActivity(intent1);

                break;
            case R.id.submit:
                String inputText = editText.getText().toString();
                Toast.makeText(MainActivity.this, inputText, Toast.LENGTH_SHORT).show();
                break;
            default:
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart: 不可见变为可见");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: 准备好与用户进行交互，位于返回栈的栈顶");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause: 去启动或恢复另一个活动调用");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop: 完全不可见时调用");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: 被销毁前调用");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAG, "onRestart: 由停止变为运行前调用");
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
                    Log.e(TAG, "onActivityResult: " + returneData);
                } else if (resultCode == RESULT_CANCELED) {
                    //Toast.makeText(this, "扫描的二维码有误！", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onActivityResult: 扫描的二维码有误或者退出扫面页面");
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    String returneData = data.getStringExtra("data_return");
                    Log.e(TAG, "onActivityResult: " + returneData);
                    Toast.makeText(this, returneData, Toast.LENGTH_SHORT).show();

                } else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "屁都没有给", Toast.LENGTH_SHORT).show();

                }
                break;
            default:
        }
    }

    //页面被销毁后保存临时数据 1
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        String tempData = "保存的临时数据";
        outState.putString("data_key", tempData);
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
                        Intent intent = new Intent(Intent.ACTION_VIEW);
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


    //完成Intent的构建 并且所有SettingsActivity的数据都由actionStart传递
    public static void actionStart(Context context, String data, String data1) {
        Intent intent = new Intent(context, SettingsActivity.class);
        intent.putExtra("extra_data", data);
        intent.putExtra("extra_data1", data1);
        context.startActivity(intent);
    }



    //PopupMenu点击事件
    @Override
    public boolean onMenuItemClick(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            //回调1
            case R.id.setting_item:
                Intent hdintent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivityForResult(hdintent, 2);
                Log.e(TAG, "onMenuItemClick: 回调设置");
                break;
            //传参1
            case R.id.ccsetting_item:
                //调用方法传递
                actionStart(MainActivity.this, data, data1);
                Log.e(TAG, "onMenuItemClick: 传参设置");

                break;
            case R.id.url_item:
                Intent urlintent = new Intent(Intent.ACTION_VIEW);
                urlintent.setData(Uri.parse("http://www.baidu.com"));
                startActivity(urlintent);
                Log.e(TAG, "onMenuItemClick: 链接");

                break;
            case R.id.cal_item:
                Intent calintent = new Intent(Intent.ACTION_DIAL);
                calintent.setData(Uri.parse("tel:10086"));
                startActivity(calintent);
                Log.e(TAG, "onMenuItemClick: 电话");

                break;
                //AlertDialog对话框
            case R.id.AlertDialog_item:
                //创建一个AlertDialog的实例
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setTitle("This is Dialog");
                alertDialog.setMessage("Something important");
                //点击其他位置对话框是否消失，以及back键是否消失
                alertDialog.setCancelable(false);
                //调用setPositiveButton为对话框设置ok按钮的点击
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "Dialog OK", Toast.LENGTH_SHORT).show();
                    }
                });
                //调用setNegativeButton为对话框设置Cancel按钮的点击
                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "Dialog Cancel", Toast.LENGTH_SHORT).show();
                    }
                });
                //调用show显示对话框
                alertDialog.show();
                break;
                //弹出的对话框中还显示进度条
            case  R.id.ProgressDialog_item:
                //创建一个ProgressDialog的实例
                ProgressDialog progreesDialog= new ProgressDialog(MainActivity.this);
                progreesDialog.setTitle("This is ProgressDialog");
                progreesDialog.setMessage("Loading. . . . .");
                //数据加载完成后必须调用progreesDialog.dismiss()方法来关闭对话框
                progreesDialog.setCancelable(true);
                //调用show显示对话框
                progreesDialog.show();
            default:
        }
        return true;
    }

    //处理扫描后的结果,把扫描后的结果通过浏览器打开
    public void handleDecode(Result rawResult, Bitmap barcode, float scaleFactor) {
        inactivityTimer.onActivity();
        String result = rawResult.getText();
        if (result.equals("")) {
            Toast.makeText(MainActivity.this, "Scan Failed!", Toast.LENGTH_SHORT).show();
        } else {
            Log.e(TAG, "handleDecode: " + result);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(result));
            startActivity(intent);
        }
    }

    public void drawViewfinder() {

        viewfinderView.drawViewfinder();


    }

}


//menu
//    //重写onCreateOptionsMenu事件
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//        //return super.onCreateOptionsMenu(menu);
//    }
//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        return super.onPrepareOptionsMenu(menu);
//    }


//    private void displayFrameworkBugMessageAndExit () {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle(getString(com.google.zxing.client.android.R.string.app_name));
//        builder.setMessage(getString(com.google.zxing.client.android.R.string.msg_camera_framework_bug));
//        builder.setPositiveButton(com.google.zxing.client.android.R.string.button_ok, new FinishListener(this));
//        builder.setOnCancelListener(new FinishListener(this));
//        builder.show();
//    }
//    private void initCamera (SurfaceHolder surfaceHolder){
//        if (surfaceHolder == null) {
//            throw new IllegalStateException("No SurfaceHolder provided");
//        }
//        if (cameraManager.isOpen()) {
//            Log.w(TAG, "initCamera() while already open -- late SurfaceView callback?");
//
//
//            //如果已经打开则关闭当前摄像头然后再打开一个其他的
//            handler = null ;
//            cameraManager.closeDriver();
////            return;
//        }
//        try {
//            cameraManager.openDriver(surfaceHolder);
//            // Creating the handler starts the preview, which can also throw a RuntimeException.
//            if (handler == null) {
//                handler = new CaptureActivityHandler(this, decodeFormats, decodeHints, characterSet, cameraManager);
//            }
////            decodeOrStoreSavedBitmap(null, null);
//        } catch (IOException ioe) {
//            Log.w(TAG, ioe);
//            displayFrameworkBugMessageAndExit();
//        } catch (RuntimeException e) {
//            // Barcode Scanner has seen crashes in the wild of this variety:
//            // java.?lang.?RuntimeException: Fail to connect to camera service
//            Log.w(TAG, "Unexpected error initializing camera", e);
//            displayFrameworkBugMessageAndExit();
//        }
//    }

//
//    @Override
//    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
//
//    }
//
//    @Override
//    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {
//
//    }
//
//    @Override
//    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
//
//    }


//    @Override
//    protected void onResume() {
//        super.onResume();
//        switchCamera=(ImageView)findViewById(R.id.switch_camera);
//
//        SurfaceView surfaceView = findViewById(R.id.preview_view);
//        final SurfaceHolder surfaceHolder=surfaceView.getHolder();
//        if (surfaceHolder!=null){
//            initCamera(surfaceHolder);
//        }else {
//            surfaceHolder.addCallback(this);
//        }
//
//        switchCamera.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.e(TAG, "onClick: 切换相机" );
//                //不是前置摄像头就切为后置
//                if(direction.equals(CameraFacing.FRONT)){
//                    direction = CameraFacing.BACK;
//                }else{
//                    //切换为前置摄像头
//                    direction = CameraFacing.FRONT;
//                }
//                initCamera(surfaceHolder);
//            }
//        });
//    }

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