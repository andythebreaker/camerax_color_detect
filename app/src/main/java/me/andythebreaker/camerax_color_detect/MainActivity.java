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
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.media.Image;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.util.concurrent.ListenableFuture;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {


    public long lastTimeStamp;
    private Executor executor = Executors.newSingleThreadExecutor();
    private int REQUEST_CODE_PERMISSIONS = 1001;
    private final String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE"};
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init
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
        imgwidthheight.setText("height" + String.valueOf(main_height) + "width" + String.valueOf(main_width));
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


        if (allPermissionsGranted()) {
            startCamera(); //start camera if permission has been granted by user
        } else {
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

    void bindPreview(@NonNull ProcessCameraProvider cameraProvider) {
        Preview preview;
        preview = new Preview.Builder()
                .build();

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                .build();

        imageAnalysis.setAnalyzer(executor, new ImageAnalysis.Analyzer() {
            @Override
            public void analyze(@NonNull ImageProxy imageProxy) {
                long currentTimeStamp = System.currentTimeMillis();
                long intervalInMilliSeconds = TimeUnit.MILLISECONDS.toMillis(100);
                long deltaTime = currentTimeStamp - lastTimeStamp;
                if (deltaTime >= intervalInMilliSeconds) {
                    //https://stackoverflow.com/questions/59613886/android-camerax-color-detection
                    @SuppressLint("UnsafeOptInUsageError") Image currentTimeImage = imageProxy.getImage();
                    int this_width = currentTimeImage.getWidth();
                    int this_height = currentTimeImage.getHeight();
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
                    int pixel = private_fun_Image_toBitmap_Bitmap.getPixel((bmp_width / 2), (bmp_height / 2));
                    /*int red = Color.red(pixel);
                    int blue = Color.blue(pixel);
                    int green = Color.green(pixel);*/
                    colorcodetext.setText(/*String.format("#%02x%02x%02x", red, green, blue)*/ddb98b(rgbint(pixel)));
                    //important::getpixel::width/height相反
                    //TODO:整個分析都是靜巷的
                    colortext1.setText(ddb98b(rgbint(private_fun_Image_toBitmap_Bitmap.getPixel(Math.round(block_margen_top + blockcell * 0.5f), Math.round(blockcell * 1.5f)))));
                    colortext2.setText(ddb98b(rgbint(private_fun_Image_toBitmap_Bitmap.getPixel(Math.round(block_margen_top + blockcell * 0.5f), Math.round(blockcell * 2.5f)))));
                    colortext3.setText(ddb98b(rgbint(private_fun_Image_toBitmap_Bitmap.getPixel(Math.round(block_margen_top + blockcell * 0.5f), Math.round(blockcell * 3.5f)))));
                    colortext4.setText(ddb98b(rgbint(private_fun_Image_toBitmap_Bitmap.getPixel(Math.round(block_margen_top + blockcell * 0.5f), Math.round(blockcell * 4.5f)))));
                    colortext5.setText(ddb98b(rgbint(private_fun_Image_toBitmap_Bitmap.getPixel(Math.round(block_margen_top + blockcell * 0.5f), Math.round(blockcell * 5.5f)))));
                    colortext6.setText(ddb98b(rgbint(private_fun_Image_toBitmap_Bitmap.getPixel(Math.round(block_margen_top + blockcell * 0.5f), Math.round(blockcell * 6.5f)))));
                    colortext7.setText(ddb98b(rgbint(private_fun_Image_toBitmap_Bitmap.getPixel(Math.round(block_margen_top + blockcell * 2.5f), Math.round(blockcell * 1.5f)))));
                    colortext8.setText(ddb98b(rgbint(private_fun_Image_toBitmap_Bitmap.getPixel(Math.round(block_margen_top + blockcell * 2.5f), Math.round(blockcell * 2.5f)))));
                    colortext9.setText(ddb98b(rgbint(private_fun_Image_toBitmap_Bitmap.getPixel(Math.round(block_margen_top + blockcell * 2.5f), Math.round(blockcell * 3.5f)))));
                    colortext10.setText(ddb98b(rgbint(private_fun_Image_toBitmap_Bitmap.getPixel(Math.round(block_margen_top + blockcell * 2.5f), Math.round(blockcell * 4.5f)))));
                    colortext11.setText(ddb98b(rgbint(private_fun_Image_toBitmap_Bitmap.getPixel(Math.round(block_margen_top + blockcell * 2.5f), Math.round(blockcell * 5.5f)))));
                    colortext12.setText(ddb98b(rgbint(private_fun_Image_toBitmap_Bitmap.getPixel(Math.round(block_margen_top + blockcell * 2.5f), Math.round(blockcell * 6.5f)))));
                    colortext1.setBackgroundColor(Color.parseColor(colortext1.getText().toString()));
                    colortext2.setBackgroundColor(Color.parseColor(colortext2.getText().toString()));
                    colortext3.setBackgroundColor(Color.parseColor(colortext3.getText().toString()));
                    colortext4.setBackgroundColor(Color.parseColor(colortext4.getText().toString()));
                    colortext5.setBackgroundColor(Color.parseColor(colortext5.getText().toString()));
                    colortext6.setBackgroundColor(Color.parseColor(colortext6.getText().toString()));
                    colortext7.setBackgroundColor(Color.parseColor(colortext7.getText().toString()));
                    colortext8.setBackgroundColor(Color.parseColor(colortext8.getText().toString()));
                    colortext9.setBackgroundColor(Color.parseColor(colortext9.getText().toString()));
                    colortext10.setBackgroundColor(Color.parseColor(colortext10.getText().toString()));
                    colortext11.setBackgroundColor(Color.parseColor(colortext11.getText().toString()));
                    colortext12.setBackgroundColor(Color.parseColor(colortext12.getText().toString()));
                    colorrec1.setText("init");
                    colorrec2.setText("init");
                    colorrec3.setText("init");
                    colorrec4.setText("init");
                    currentTimeImage.close();
                    lastTimeStamp = currentTimeStamp;
                }
                imageProxy.close();
            }
        });

        ImageCapture.Builder builder = new ImageCapture.Builder();

        //TODO:72dpi->96dpi
        final ImageCapture imageCapture = builder
                .setTargetRotation(this.getWindowManager().getDefaultDisplay().getRotation())
                .build();

        preview.setSurfaceProvider(mPreviewView.getSurfaceProvider());

        Camera camera = cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector, preview, imageAnalysis, imageCapture);

        int camphy_bottom = preview.getResolutionInfo().getCropRect().bottom;
        int camphy_top = preview.getResolutionInfo().getCropRect().top;
        int camphy_left = preview.getResolutionInfo().getCropRect().left;
        int camphy_right = preview.getResolutionInfo().getCropRect().right;
        String cam_phy = "camphy_bottom\n" + camphy_bottom + "camphy_top\n" + camphy_top + "camphy_left\n" + camphy_left + "camphy_right\n" + camphy_right;
        Log.i("dev", cam_phy);
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
        float blockcell = cam_phy_width / 8;
        float block_margen_top = (cam_phy_height - blockcell * 3) / 2;
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
        imageViewcan.setImageBitmap(draw_a_rect_bitmap);

        @SuppressLint({"RestrictedApi", "UnsafeOptInUsageError"}) CameraCharacteristics camChars = Camera2CameraInfo
                .extractCameraCharacteristics(camera.getCameraInfo());
        float discoveredMinFocusDistance = camChars
                .get(CameraCharacteristics.LENS_INFO_MINIMUM_FOCUS_DISTANCE);
        Log.i("dev", "\t\t\t----------------------------\n\n\n----------------------------------------\t\t\tfound it! " + discoveredMinFocusDistance);
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
                    new Handler(Looper.getMainLooper()).post(() -> {
                        Toast.makeText(MainActivity.this, "Image Saved successfully", Toast.LENGTH_SHORT).show();
                    });
                }

                @Override
                public void onError(@NonNull ImageCaptureException error) {
                    //TODO: https://blog.csdn.net/djzhao627/article/details/111696044
                    error.printStackTrace();
                    new Handler(Looper.getMainLooper()).post(() -> {
                        Toast.makeText(MainActivity.this, "Image Saved ERROR" + error.toString(), Toast.LENGTH_SHORT).show();
                    });
                }
            });
        });

        mPreviewView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
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
            }
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
        toast_is_good_to_eat("x=" + String.valueOf(x) + ";y=" + String.valueOf(y));
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
        String app_folder_path = "";
        app_folder_path = Environment.getExternalStorageDirectory().toString() + "/images";
        File dir = new File(app_folder_path);
        if (!dir.exists() && !dir.mkdirs()) {

        }
        Log.d("app_folder_path", app_folder_path);
        return app_folder_path;
    }

    public static int[] rgbint(int pix) {
        int red = Color.red(pix);
        int blue = Color.blue(pix);
        int green = Color.green(pix);
        int[] stufftoreturn = new int[]{red, green, blue};
        return stufftoreturn;
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
        stufftoreturn.valueOf(
                Integer.valueOf(colorStr.substring(1, 3), 16) / 255f,
                Integer.valueOf(colorStr.substring(3, 5), 16) / 255f,
                Integer.valueOf(colorStr.substring(5, 7), 16) / 255f);
        return stufftoreturn;
    }
}

//TODO:bottom linear layout don't set die pixel