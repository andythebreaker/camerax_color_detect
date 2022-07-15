package me.andythebreaker.camerax_color_detect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.camera2.interop.Camera2CameraInfo;
import androidx.camera.camera2.interop.Camera2Interop;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ExtendableBuilder;
import androidx.camera.core.FocusMeteringAction;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.MeteringPoint;
import androidx.camera.core.MeteringPointFactory;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
//import androidx.compose.ui.graphics.Paint;
import android.Manifest;
import android.graphics.RectF;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Size;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.util.concurrent.ListenableFuture;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import me.andythebreaker.camerax_color_detect.ImageUtils;
import me.andythebreaker.camerax_color_detect.RtmpClient;

public class MainActivity extends AppCompatActivity {

private Camera camera=null;
    public long lastTimeStamp;
    private final Executor executor = Executors.newSingleThreadExecutor();
    private final String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.RECORD_AUDIO", "android.permission.INTERNET", "android.permission.ACCESS_WIFI_STATE", "android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"};
    public int main_height;
    public int main_width;
    PreviewView mPreviewView;
    Button captureImage;
    TextView t3;
    TextView colorcodetext;
    TextView imgwidthheight;
    Switch switch_if_af;
    ImageView imageViewcan;
    LinearLayout linearLayout_btom;
    TextView colortext1;
    TextView colortext2;
    TextView colortext3;
    TextView colortext4;
    TextView colortext5;
    TextView colortext6;
    TextView colortext7;
    TextView colortext8;
    TextView colortext9;
    TextView colortext10;
    TextView colortext11;
    TextView colortext12;
    TextView colorrec1;
    TextView colorrec2;
    TextView colorrec3;
    TextView colorrec4;
    TextView colordec1;
    TextView colordec2;
    TextView colordec3;
    TextView colordec4;
    Button rtmpSVswitch_obj;
    private RtmpClient rtmpClient;
    EditText rtmpurl;

    @SuppressLint("SetTextI18n")
    public void rtmpSVswitch(View view) {
        CharSequence text = rtmpSVswitch_obj.getText();
        if ("start".contentEquals(text)) {
            toast_is_good_to_eat("start");
            rtmpSVswitch_obj.setText("stop");
            rtmpClient = new RtmpClient(/*this*/);
            //初始化摄像头， 同时 创建编码器
            //int heighteven = (main_height % 2 == 0) ? main_height : main_height + 1;
            rtmpClient.initVideo(800, 600, 20, 1000000);
            rtmpClient.initAudio(44100, 2);
            rtmpClient.startLive(rtmpurl.getText().toString());
        } else if ("stop".contentEquals(text)) {
            toast_is_good_to_eat("stop");
            rtmpSVswitch_obj.setText("start");
            rtmpClient.stopLive();
        } else {
            toast_is_good_to_eat("error");
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        //init
        rtmpurl = (EditText) findViewById(R.id.editTextTextPersonName2);
        rtmpSVswitch_obj = (Button) findViewById(R.id.startSV);
        colorcodetext = (TextView) findViewById(R.id.colorcodetext);
        colorcodetext.setText("null");
        t3 = (TextView) findViewById(R.id.t3);
        t3.setText("..........");
        mPreviewView = findViewById(R.id.camera_previewView);
        captureImage = findViewById(R.id.captureImg);
        switch_if_af = (Switch) findViewById(R.id.switch1);
        imgwidthheight = findViewById(R.id.imgwidthheight);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        main_height = displayMetrics.heightPixels;
        main_width = displayMetrics.widthPixels;
        imgwidthheight.setText("height" + main_height + "width" + main_width);
        imageViewcan = findViewById(R.id.imageViewcan);
        imageViewcan.setBackgroundColor(Color.TRANSPARENT);
        //toast_is_good_to_eat(mPreviewView.getScaleType().toString());
        linearLayout_btom = findViewById(R.id.linearLayout);
        mPreviewView.setScaleType(PreviewView.ScaleType.FIT_CENTER);//important!!
        colortext1 = findViewById(R.id.colortext1);
        colortext2 = findViewById(R.id.colortext2);
        colortext3 = findViewById(R.id.colortext3);
        colortext4 = findViewById(R.id.colortext4);
        colortext5 = findViewById(R.id.colortext5);
        colortext6 = findViewById(R.id.colortext6);
        colortext7 = findViewById(R.id.colortext7);
        colortext8 = findViewById(R.id.colortext8);
        colortext9 = findViewById(R.id.colortext9);
        colortext10 = findViewById(R.id.colortext10);
        colortext11 = findViewById(R.id.colortext11);
        colortext12 = findViewById(R.id.colortext12);
        colorrec1 = findViewById(R.id.colorrec1);
        colorrec2 = findViewById(R.id.colorrec2);
        colorrec3 = findViewById(R.id.colorrec3);
        colorrec4 = findViewById(R.id.colorrec4);
        colordec1 = findViewById(R.id.colordec1);
        colordec2 = findViewById(R.id.colordec2);
        colordec3 = findViewById(R.id.colordec3);
        colordec4 = findViewById(R.id.colordec4);
        colortext1.setText("init");
        colortext2.setText("init");
        colortext3.setText("init");
        colortext4.setText("init");
        colortext5.setText("init");
        colortext6.setText("init");
        colortext7.setText("init");
        colortext8.setText("init");
        colortext9.setText("init");
        colortext10.setText("init");
        colortext11.setText("init");
        colortext12.setText("init");
        colorrec1.setText("init");
        colorrec2.setText("init");
        colorrec3.setText("init");
        colorrec4.setText("init");
        colordec1.setText("init");
        colordec2.setText("init");
        colordec3.setText("init");
        colordec4.setText("init");


        if (allPermissionsGranted()) {
            startCamera(); //start camera if permission has been granted by user
        } else {
            int REQUEST_CODE_PERMISSIONS = 1001;
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        }
    }

    //function
    private void startCamera() {
        final ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                bindPreview(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                // No errors need to be handled for this Future.
                // This should never be reached.
            }
        }, ContextCompat.getMainExecutor(this));
    }

    @SuppressLint("ClickableViewAccessibility")
    void bindPreview(@NonNull ProcessCameraProvider cameraProvider) {
        Preview preview;
        preview = new Preview.Builder()
                .setTargetResolution(new Size(600, 800))
                .build();

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                .setTargetResolution(new Size(600, 800))
                .build();

        imageAnalysis.setAnalyzer(executor, imageProxy -> {
            //long currentTimeStamp = System.currentTimeMillis();
            //long intervalInMilliSeconds = TimeUnit.MILLISECONDS.toMillis(100);
            //long deltaTime = currentTimeStamp - lastTimeStamp;

            // 开启直播并且已经成功连接服务器才获取i420数据
            if ("stop".contentEquals(rtmpSVswitch_obj.getText()) && rtmpClient.isConnectd()) {
                byte[] bytes = ImageUtils.getBytes(imageProxy, 0, rtmpClient.getWidth(), rtmpClient.getHeight());
                //Log.e("andythebreaker", "byte[] bytes = ImageUtils.getBytes(imageProxy, 0, rtmpClient.getWidth(), rtmpClient.getHeight());");
                rtmpClient.sendVideo(bytes);

            }

            /*if (deltaTime >= intervalInMilliSeconds) {
                //https://stackoverflow.com/questions/59613886/android-camerax-color-detection
                @SuppressLint("UnsafeOptInUsageError") Image currentTimeImage = imageProxy.getImage();
                int this_width = currentTimeImage.getWidth();
                int this_height = currentTimeImage.getHeight();
                Log.e("andythebreaker","width:"+Integer.toString(this_width)+"height"+Integer.toString(this_height));
                //imgwidthheight.setText("width"+String.valueOf(this_width)+"height"+String.valueOf(this_height));
                //TODO: go with only buffer and not convert buffer 2 array
                Image.Plane planes[] = currentTimeImage.getPlanes();
                ByteBuffer yBuffer = planes[0].getBuffer(); // Y
                ByteBuffer uBuffer = planes[1].getBuffer(); // U
                ByteBuffer vBuffer = planes[2].getBuffer(); // V
                //byte[] data=ByteBuffer_toByteArray()
                int ySize = yBuffer.remaining();
                int uSize = uBuffer.remaining();
                int vSize = vBuffer.remaining();
                byte[] nv21 = new byte[ySize + uSize + vSize];
                yBuffer.get(nv21, 0, ySize);
                vBuffer.get(nv21, ySize, vSize);
                uBuffer.get(nv21, ySize + vSize, uSize);
                YuvImage yuvImage = new YuvImage(nv21, ImageFormat.NV21, this_width, this_height, null);
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                yuvImage.compressToJpeg(new Rect(0, 0, yuvImage.getWidth(), yuvImage.getHeight()), 50, out);
                byte[] imageBytes = out.toByteArray();
                Bitmap private_fun_Image_toBitmap_Bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                int bmp_height = private_fun_Image_toBitmap_Bitmap.getWidth();
                int bmp_width = private_fun_Image_toBitmap_Bitmap.getHeight();
                float blockcell = bmp_width / 8;
                float block_margen_top = (bmp_height - blockcell * 3) / 2;
                float leftRightMargenGuiResLine = blockcell * 6 * (1.15f / (1.15f + 0.9f + 1.15f));//copy
                float fullBodyGuiResLine = blockcell * 6 * (0.9f / (1.15f + 0.9f + 1.15f));//copy
                float margenBetweenGuiResLine = fullBodyGuiResLine / 5;//copy
                int pixel = private_fun_Image_toBitmap_Bitmap.getPixel((bmp_width / 2), (bmp_height / 2));

                colorcodetext.setText(ddb98b(rgbint(pixel)));
                //important::getpixel::width/height相反
                //TODO:整個分析都是靜巷的
                Vector rgbintVector = new Vector();
                rgbintVector.add(rgbint(private_fun_Image_toBitmap_Bitmap.getPixel(Math.round(block_margen_top + blockcell * 0.5f), Math.round(blockcell * 1.5f))));
                rgbintVector.add(rgbint(private_fun_Image_toBitmap_Bitmap.getPixel(Math.round(block_margen_top + blockcell * 0.5f), Math.round(blockcell * 2.5f))));
                rgbintVector.add(rgbint(private_fun_Image_toBitmap_Bitmap.getPixel(Math.round(block_margen_top + blockcell * 0.5f), Math.round(blockcell * 3.5f))));
                rgbintVector.add(rgbint(private_fun_Image_toBitmap_Bitmap.getPixel(Math.round(block_margen_top + blockcell * 0.5f), Math.round(blockcell * 4.5f))));
                rgbintVector.add(rgbint(private_fun_Image_toBitmap_Bitmap.getPixel(Math.round(block_margen_top + blockcell * 0.5f), Math.round(blockcell * 5.5f))));
                rgbintVector.add(rgbint(private_fun_Image_toBitmap_Bitmap.getPixel(Math.round(block_margen_top + blockcell * 0.5f), Math.round(blockcell * 6.5f))));
                rgbintVector.add(rgbint(private_fun_Image_toBitmap_Bitmap.getPixel(Math.round(block_margen_top + blockcell * 2.5f), Math.round(blockcell * 1.5f))));
                rgbintVector.add(rgbint(private_fun_Image_toBitmap_Bitmap.getPixel(Math.round(block_margen_top + blockcell * 2.5f), Math.round(blockcell * 2.5f))));
                rgbintVector.add(rgbint(private_fun_Image_toBitmap_Bitmap.getPixel(Math.round(block_margen_top + blockcell * 2.5f), Math.round(blockcell * 3.5f))));
                rgbintVector.add(rgbint(private_fun_Image_toBitmap_Bitmap.getPixel(Math.round(block_margen_top + blockcell * 2.5f), Math.round(blockcell * 4.5f))));
                rgbintVector.add(rgbint(private_fun_Image_toBitmap_Bitmap.getPixel(Math.round(block_margen_top + blockcell * 2.5f), Math.round(blockcell * 5.5f))));
                rgbintVector.add(rgbint(private_fun_Image_toBitmap_Bitmap.getPixel(Math.round(block_margen_top + blockcell * 2.5f), Math.round(blockcell * 6.5f))));
                colortext1.setText(ddb98b((int[]) rgbintVector.get(0)));
                colortext2.setText(ddb98b((int[]) rgbintVector.get(1)));
                colortext3.setText(ddb98b((int[]) rgbintVector.get(2)));
                colortext4.setText(ddb98b((int[]) rgbintVector.get(3)));
                colortext5.setText(ddb98b((int[]) rgbintVector.get(4)));
                colortext6.setText(ddb98b((int[]) rgbintVector.get(5)));
                colortext7.setText(ddb98b((int[]) rgbintVector.get(6)));
                colortext8.setText(ddb98b((int[]) rgbintVector.get(7)));
                colortext9.setText(ddb98b((int[]) rgbintVector.get(8)));
                colortext10.setText(ddb98b((int[]) rgbintVector.get(9)));
                colortext11.setText(ddb98b((int[]) rgbintVector.get(10)));
                colortext12.setText(ddb98b((int[]) rgbintVector.get(11)));

                textViewHexTextSetBackground(colortext1);
                textViewHexTextSetBackground(colortext2);
                textViewHexTextSetBackground(colortext3);
                textViewHexTextSetBackground(colortext4);
                textViewHexTextSetBackground(colortext5);
                textViewHexTextSetBackground(colortext6);
                textViewHexTextSetBackground(colortext7);
                textViewHexTextSetBackground(colortext8);
                textViewHexTextSetBackground(colortext9);
                textViewHexTextSetBackground(colortext10);
                textViewHexTextSetBackground(colortext11);
                textViewHexTextSetBackground(colortext12);
                rgbintVector.add(rgbint(private_fun_Image_toBitmap_Bitmap.getPixel(Math.round(block_margen_top + blockcell * 1.5f), Math.round(blockcell + leftRightMargenGuiResLine + margenBetweenGuiResLine * 1))));
                rgbintVector.add(rgbint(private_fun_Image_toBitmap_Bitmap.getPixel(Math.round(block_margen_top + blockcell * 1.5f), Math.round(blockcell + leftRightMargenGuiResLine + margenBetweenGuiResLine * 2))));
                rgbintVector.add(rgbint(private_fun_Image_toBitmap_Bitmap.getPixel(Math.round(block_margen_top + blockcell * 1.5f), Math.round(blockcell + leftRightMargenGuiResLine + margenBetweenGuiResLine * 3))));
                rgbintVector.add(rgbint(private_fun_Image_toBitmap_Bitmap.getPixel(Math.round(block_margen_top + blockcell * 1.5f), Math.round(blockcell + leftRightMargenGuiResLine + margenBetweenGuiResLine * 4))));
                colorrec1.setText(ddb98b((int[]) rgbintVector.get(12)));
                colorrec2.setText(ddb98b((int[]) rgbintVector.get(13)));
                colorrec3.setText(ddb98b((int[]) rgbintVector.get(14)));
                colorrec4.setText(ddb98b((int[]) rgbintVector.get(15)));

                textViewHexTextSetBackground(colorrec1);
                textViewHexTextSetBackground(colorrec2);
                textViewHexTextSetBackground(colorrec3);
                textViewHexTextSetBackground(colorrec4);

                colordec1.setText(enum12color(findColorSim(rgbintVector, 12 + 0)));
                colordec2.setText(enum12color(findColorSim(rgbintVector, 12 + 1)));
                colordec3.setText(enum12color(findColorSim(rgbintVector, 12 + 2)));
                colordec4.setText(enum12color(findColorSim(rgbintVector, 12 + 3)));
                currentTimeImage.close();
                lastTimeStamp = currentTimeStamp;
            }*/

            System.gc();
            /*TODO GC
             * bitmap : use : https://stackoverflow.com/questions/3117429/garbage-collector-in-android
             * not to use : https://stackoverflow.com/questions/8177802/garbage-collection-in-android-done-manually
             * so, use or not use ????
             * not use full
             */

            imageProxy.close();
        });

        ImageCapture.Builder builder = new ImageCapture.Builder();

        //TODO:72dpi->96dpi
        final ImageCapture imageCapture = builder
                .setTargetRotation(this.getWindowManager().getDefaultDisplay().getRotation())
                .build();

        preview.setSurfaceProvider(mPreviewView.getSurfaceProvider());

        /*Camera*/ camera = cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector, preview, imageAnalysis, imageCapture);
        camera.getCameraControl().enableTorch(true);//important phone NEED torch !! else will error ； https://stackoverflow.com/questions/67472332/flash-mode-torch-in-camerax
        int camphy_bottom = Objects.requireNonNull(preview.getResolutionInfo()).getCropRect().bottom;
        int camphy_top = preview.getResolutionInfo().getCropRect().top;
        int camphy_left = preview.getResolutionInfo().getCropRect().left;
        int camphy_right = preview.getResolutionInfo().getCropRect().right;
        String cam_phy = "camphy_bottom\n" + camphy_bottom + "camphy_top\n" + camphy_top + "camphy_left\n" + camphy_left + "camphy_right\n" + camphy_right;
        //Log.e("dev", cam_phy);
        int cam_phy_width = main_width;
        int cam_phy_height = (main_width * camphy_right) / camphy_bottom;
        Bitmap draw_a_rect_bitmap = Bitmap.createBitmap(cam_phy_width, cam_phy_height/*720,720*/, Bitmap.Config.ARGB_8888);
        Paint p = new Paint();
        p.setAntiAlias(true);                                    // 設置畫筆的鋸齒效果。 true是去除。
        p.setColor(Color.RED);                                // 設置紅色
        p.setTextSize(16);                                        // 設置文字的大小為 16。
        p.setStyle(Paint.Style.STROKE);
        Canvas canvas = new Canvas(draw_a_rect_bitmap);
        canvas.drawText(cam_phy, 10, 10, p);        // 寫一段文字
        /*canvas.drawCircle(80,20,20,p);
        RectF rect = new RectF(0f, 0f, 720f, 600f);
        canvas.drawRect(rect, p);*/
        float blockcell = (float) (cam_phy_width / 8.0);
        float block_margen_top = (cam_phy_height - blockcell * 3) / 2;
        float leftRightMargenGuiResLine = blockcell * 6 * (1.15f / (1.15f + 0.9f + 1.15f));
        float fullBodyGuiResLine = blockcell * 6 * (0.9f / (1.15f + 0.9f + 1.15f));
        float margenBetweenGuiResLine = fullBodyGuiResLine / 5;
        RectF color_cell1 = new RectF(blockcell * 1, block_margen_top + blockcell * 0, blockcell * 2, block_margen_top + blockcell * 1);
        RectF color_cell2 = new RectF(blockcell * 2, block_margen_top + blockcell * 0, blockcell * 3, block_margen_top + blockcell * 1);
        RectF color_cell3 = new RectF(blockcell * 3, block_margen_top + blockcell * 0, blockcell * 4, block_margen_top + blockcell * 1);
        RectF color_cell4 = new RectF(blockcell * 4, block_margen_top + blockcell * 0, blockcell * 5, block_margen_top + blockcell * 1);
        RectF color_cell5 = new RectF(blockcell * 5, block_margen_top + blockcell * 0, blockcell * 6, block_margen_top + blockcell * 1);
        RectF color_cell6 = new RectF(blockcell * 6, block_margen_top + blockcell * 0, blockcell * 7, block_margen_top + blockcell * 1);
        RectF color_cell7 = new RectF(blockcell * 1, block_margen_top + blockcell * 2, blockcell * 2, block_margen_top + blockcell * 3);
        RectF color_cell8 = new RectF(blockcell * 2, block_margen_top + blockcell * 2, blockcell * 3, block_margen_top + blockcell * 3);
        RectF color_cell9 = new RectF(blockcell * 3, block_margen_top + blockcell * 2, blockcell * 4, block_margen_top + blockcell * 3);
        RectF color_cell10 = new RectF(blockcell * 4, block_margen_top + blockcell * 2, blockcell * 5, block_margen_top + blockcell * 3);
        RectF color_cell11 = new RectF(blockcell * 5, block_margen_top + blockcell * 2, blockcell * 6, block_margen_top + blockcell * 3);
        RectF color_cell12 = new RectF(blockcell * 6, block_margen_top + blockcell * 2, blockcell * 7, block_margen_top + blockcell * 3);
        RectF guiResLine1 = new RectF(blockcell + leftRightMargenGuiResLine + margenBetweenGuiResLine * 1 - 1, block_margen_top + blockcell * 1, blockcell + leftRightMargenGuiResLine + margenBetweenGuiResLine * 1 + 1, block_margen_top + blockcell * 2);
        RectF guiResLine2 = new RectF(blockcell + leftRightMargenGuiResLine + margenBetweenGuiResLine * 2 - 1, block_margen_top + blockcell * 1, blockcell + leftRightMargenGuiResLine + margenBetweenGuiResLine * 2 + 1, block_margen_top + blockcell * 2);
        RectF guiResLine3 = new RectF(blockcell + leftRightMargenGuiResLine + margenBetweenGuiResLine * 3 - 1, block_margen_top + blockcell * 1, blockcell + leftRightMargenGuiResLine + margenBetweenGuiResLine * 3 + 1, block_margen_top + blockcell * 2);
        RectF guiResLine4 = new RectF(blockcell + leftRightMargenGuiResLine + margenBetweenGuiResLine * 4 - 1, block_margen_top + blockcell * 1, blockcell + leftRightMargenGuiResLine + margenBetweenGuiResLine * 4 + 1, block_margen_top + blockcell * 2);
        canvas.drawRect(color_cell1, p);
        canvas.drawRect(color_cell2, p);
        canvas.drawRect(color_cell3, p);
        canvas.drawRect(color_cell4, p);
        canvas.drawRect(color_cell5, p);
        canvas.drawRect(color_cell6, p);
        canvas.drawRect(color_cell7, p);
        canvas.drawRect(color_cell8, p);
        canvas.drawRect(color_cell9, p);
        canvas.drawRect(color_cell10, p);
        canvas.drawRect(color_cell11, p);
        canvas.drawRect(color_cell12, p);
        canvas.drawRect(guiResLine1, p);
        canvas.drawRect(guiResLine2, p);
        canvas.drawRect(guiResLine3, p);
        canvas.drawRect(guiResLine4, p);
        imageViewcan.setImageBitmap(draw_a_rect_bitmap);

        @SuppressLint({"RestrictedApi", "UnsafeOptInUsageError"}) CameraCharacteristics camChars = Camera2CameraInfo
                .extractCameraCharacteristics(camera.getCameraInfo());
        float discoveredMinFocusDistance = camChars
                .get(CameraCharacteristics.LENS_INFO_MINIMUM_FOCUS_DISTANCE);
        //Log.i("dev", "\t\t\t----------------------------\n\n\n----------------------------------------\t\t\tfound it! " + discoveredMinFocusDistance);
        t3.setText(String.valueOf(discoveredMinFocusDistance));
        captureImage.setOnClickListener(v -> {
            if (switch_if_af.isChecked()) {
                focus(mPreviewView, null, camera);
            }
            SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
            File file = new File(getBatchDirectoryName(), mDateFormat.format(new Date()) + ".jpg");
            ImageCapture.OutputFileOptions outputFileOptions = new ImageCapture.OutputFileOptions.Builder(file).build();
            imageCapture.takePicture(outputFileOptions, executor, new ImageCapture.OnImageSavedCallback() {
                @Override
                public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                    new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(MainActivity.this, "Image Saved successfully", Toast.LENGTH_SHORT).show());
                }

                @Override
                public void onError(@NonNull ImageCaptureException error) {
                    //TODO: https://blog.csdn.net/djzhao627/article/details/111696044
                    error.printStackTrace();
                    new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(MainActivity.this, "Image Saved ERROR" + error.toString(), Toast.LENGTH_SHORT).show());
                }
            });
        });

        mPreviewView.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_UP:
                    return focus(v, event, camera);
                //break;
                default:
                    // Unhandled event.
                    return false;
            }
            return true;
        });
    }

    private void setFocusDistance(ExtendableBuilder<?> builder, float distance) {
        Camera2Interop.Extender extender = new Camera2Interop.Extender(builder);
        extender.setCaptureRequestOption(CaptureRequest.CONTROL_AF_MODE, CameraMetadata.CONTROL_AF_MODE_OFF);
        extender.setCaptureRequestOption(CaptureRequest.LENS_FOCUS_DISTANCE, distance);
    }

    private boolean focus(View v, MotionEvent event, Camera camcrtl) {
        final float x = (event != null) ? event.getX() : v.getX() + v.getWidth() / 2f;
        final float y = (event != null) ? event.getY() : v.getY() + v.getHeight() / 2f;
        toast_is_good_to_eat("x=" + x + ";y=" + y);
        MeteringPointFactory pointFactory = mPreviewView.getMeteringPointFactory();
        float afPointWidth = 1.0f / 6.0f;  // 1/6 total area
        float aePointWidth = afPointWidth * 1.5f;
        MeteringPoint afPoint = pointFactory.createPoint(x, y);
        MeteringPoint aePoint = pointFactory.createPoint(x, y);
        FocusMeteringAction wtfijustwanttofuckingfocusashit;
        wtfijustwanttofuckingfocusashit = new FocusMeteringAction.Builder(afPoint).build();
        camcrtl.getCameraControl().startFocusAndMetering(wtfijustwanttofuckingfocusashit);
        return true;
    }

    private boolean allPermissionsGranted() {
        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public void toast_is_good_to_eat(String msg_to_toast_out) {
        Toast toast3 = Toast.makeText(this, msg_to_toast_out, Toast.LENGTH_SHORT);
        toast3.show();
    }

    public static String getBatchDirectoryName() {
        String app_folder_path;
        app_folder_path = Environment.getExternalStorageDirectory().toString() + "/images";
        File dir = new File(app_folder_path);
        if (!dir.exists() && !dir.mkdirs()) {

        }
        //Log.d("app_folder_path", app_folder_path);
        return app_folder_path;
    }

    public static int[] rgbint(int pix) {
        int red = Color.red(pix);
        int blue = Color.blue(pix);
        int green = Color.green(pix);
        return new int[]{red, green, blue};
    }

    public static String ddb98b(int[] intary3) {
        return String.format("#%02x%02x%02x", intary3[0], intary3[1], intary3[2]);
    }

    /**
     * @param colorStr e.g. "#FFFFFF"
     * @return
     */
    public static Color hex2Rgb(String colorStr) {
        Color stufftoreturn = new Color();
        Color.valueOf(
                Integer.valueOf(colorStr.substring(1, 3), 16) / 255f,
                Integer.valueOf(colorStr.substring(3, 5), 16) / 255f,
                Integer.valueOf(colorStr.substring(5, 7), 16) / 255f);
        return stufftoreturn;
    }

    public static void textViewHexTextSetBackground(TextView tv_parm) {
        tv_parm.setBackgroundColor(Color.parseColor(tv_parm.getText().toString()));
    }

    public static double diff_rms(int[] targ, int[] ref) {
        return Math.sqrt(Math.pow(((double) (targ[0] - ref[0])), 2) + Math.pow(((double) (targ[1] - ref[1])), 2) + Math.pow(((double) (targ[2] - ref[2])), 2));
    }

    public static int doubleArrayFindMinIndex(double[] parm_double_ary) {
        OptionalDouble min_num = Arrays.stream(parm_double_ary).min();
        if (min_num.isPresent()) {
            for (int i = 0; i < parm_double_ary.length; i++) {
                if (parm_double_ary[i] == min_num.getAsDouble()) {
                    return i + 1;
                }
            }
            return 0;
        } else {
            return -1;
        }
    }

    public static int findColorSim(Vector colorRefAndTargetIntArrayVector, int indexTarget) {
        //Log.d("math","findColorSim");
        int[] targetColorRGB = ((int[]) colorRefAndTargetIntArrayVector.get(indexTarget));
        //Log.d("math","int[] targetColorRGB=((int[])colorRefAndTargetIntArrayVector.get(indexTarget));");
        double[] rmsResult12 = new double[]{
                diff_rms(targetColorRGB, ((int[]) colorRefAndTargetIntArrayVector.get(0))),
                diff_rms(targetColorRGB, ((int[]) colorRefAndTargetIntArrayVector.get(1))),
                diff_rms(targetColorRGB, ((int[]) colorRefAndTargetIntArrayVector.get(2))),
                diff_rms(targetColorRGB, ((int[]) colorRefAndTargetIntArrayVector.get(3))),
                diff_rms(targetColorRGB, ((int[]) colorRefAndTargetIntArrayVector.get(4))),
                diff_rms(targetColorRGB, ((int[]) colorRefAndTargetIntArrayVector.get(5))),
                diff_rms(targetColorRGB, ((int[]) colorRefAndTargetIntArrayVector.get(6))),
                diff_rms(targetColorRGB, ((int[]) colorRefAndTargetIntArrayVector.get(7))),
                diff_rms(targetColorRGB, ((int[]) colorRefAndTargetIntArrayVector.get(8))),
                diff_rms(targetColorRGB, ((int[]) colorRefAndTargetIntArrayVector.get(9))),
                diff_rms(targetColorRGB, ((int[]) colorRefAndTargetIntArrayVector.get(10))),
                diff_rms(targetColorRGB, ((int[]) colorRefAndTargetIntArrayVector.get(11)))
        };
        //Log.d("math","        double[] rmsResult12 = new double[]{\n");
        int answer = doubleArrayFindMinIndex(rmsResult12);
        //Log.d("answer", String.valueOf(answer));
        return doubleArrayFindMinIndex(rmsResult12);
    }

    public static String enum12color(int color12) {
        switch (color12) {
            case -1:
                return "math error (@ find min.)";
            case 0:
                return "math error (@ min. find index)";
            case 1:
                return "black";
            case 2:
                return "brown";
            case 3:
                return "red";
            case 4:
                return "orange";
            case 5:
                return "yellow";
            case 6:
                return "green";
            case 7:
                return "blue";
            case 8:
                return "violet";
            case 9:
                return "gray";
            case 10:
                return "white";
            case 11:
                return "gold";
            case 12:
                return "silver";
            default:
                return "phrase color text error (@ switch)";
        }
    }

    public void torchSW(View view) {
        Button buttontmp = (Button) findViewById(R.id.torchSW);

        if(buttontmp.getText()== "lightON"){
            buttontmp.setText("lightOFF");
            camera.getCameraControl().enableTorch(true);
        }else{
            buttontmp.setText("lightON");
            camera.getCameraControl().enableTorch(false);
        }
    }private Bitmap mBitmap;
    private Canvas mCanvas;
    private Rect mBounds;

    public void initIfNeeded() {
        if(mBitmap == null) {
            mBitmap = Bitmap.createBitmap(1,1, Bitmap.Config.ARGB_8888);
            mCanvas = new Canvas(mBitmap);
            mBounds = new Rect();
        }
    }

    public int getBackgroundColor_(View view) {
        // The actual color, not the id.
        int color = Color.BLACK;

        if(view.getBackground() instanceof ColorDrawable) {
            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                initIfNeeded();

                // If the ColorDrawable makes use of its bounds in the draw method,
                // we may not be able to get the color we want. This is not the usual
                // case before Ice Cream Sandwich (4.0.1 r1).
                // Yet, we change the bounds temporarily, just to be sure that we are
                // successful.
                ColorDrawable colorDrawable = (ColorDrawable)view.getBackground();

                mBounds.set(colorDrawable.getBounds()); // Save the original bounds.
                colorDrawable.setBounds(0, 0, 1, 1); // Change the bounds.

                colorDrawable.draw(mCanvas);
                color = mBitmap.getPixel(0, 0);

                colorDrawable.setBounds(mBounds); // Restore the original bounds.
            }
            else {
                color = ((ColorDrawable)view.getBackground()).getColor();
            }
        }

        return color;
    }
    public static int getBackgroundColor(View view) {
        Drawable drawable = view.getBackground();
        if (drawable instanceof ColorDrawable) {
            ColorDrawable colorDrawable = (ColorDrawable) drawable;
            if (Build.VERSION.SDK_INT >= 11) {
                return colorDrawable.getColor();
            }
            try {
                Field field = colorDrawable.getClass().getDeclaredField("mState");
                field.setAccessible(true);
                Object object = field.get(colorDrawable);
                field = object.getClass().getDeclaredField("mUseColor");
                field.setAccessible(true);
                return field.getInt(object);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }
}

//TODO:bottom linear layout don't set die pixel