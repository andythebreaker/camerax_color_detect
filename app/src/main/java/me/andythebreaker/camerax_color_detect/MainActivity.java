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
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
    PreviewView mPreviewView;
    Button captureImage;
    TextView t3;
    TextView colorcodetext;
    SeekBar sk;
    TextView aaaaaaaaaaaaaaaa;
    RecyclerView mRecyclerView;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    Preview.Builder previewBuilder_wf = new Preview.Builder();
    Switch switch_if_af;

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
                    YuvImage yuvImage = new YuvImage(nv21, ImageFormat.NV21, currentTimeImage.getWidth(), currentTimeImage.getHeight(), null);
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    yuvImage.compressToJpeg(new Rect(0, 0, yuvImage.getWidth(), yuvImage.getHeight()), 50, out);
                    byte[] imageBytes = out.toByteArray();
                    Bitmap private_fun_Image_toBitmap_Bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    int pixel = private_fun_Image_toBitmap_Bitmap.getPixel((private_fun_Image_toBitmap_Bitmap.getWidth() / 2), (private_fun_Image_toBitmap_Bitmap.getHeight() / 2));
                    int red = Color.red(pixel);
                    int blue = Color.blue(pixel);
                    int green = Color.green(pixel);
                    colorcodetext.setText(String.format("#%02x%02x%02x", red, green, blue));
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
                    error.printStackTrace();
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
        return app_folder_path;
    }
}