package com.example.bitmapcrop;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.net.URI;

public class MainActivity extends AppCompatActivity {

    private final int CHOOSE_BIG_PICTURE = 1;
    private final int CHOOSE_SMALL_PICTURE = 2;
    private final int TAKE_BIG_PICTURE = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         findViewById(R.id.tv_pick_big).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 cropBigImageFromGallery();
             }
         });

        findViewById(R.id.tv_pick_small).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cropSmallImageFromGallery();
            }
        });

        findViewById(R.id.tv_take_big).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImageByCamera();
            }
        });
    }


    /**
     * 从相册截大图：图片太大会崩溃
     */
    public void cropBigImageFromGallery() {
        File cacheDir = getCacheDir();
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
        File file = new File(cacheDir, "crop.png");
        URI imageUri = file.toURI();
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 2);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 600);
        intent.putExtra("outputY", 300);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, CHOOSE_BIG_PICTURE);
    }

    /**
     * 从相册截小图
     */
    public void cropSmallImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 2);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 100);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", true);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, CHOOSE_SMALL_PICTURE);
    }


    /**
     * 拍照
     */
    public void captureImageByCamera() {
        File cacheDir = getCacheDir();
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
        File file = new File(cacheDir, "take_big.png");
        URI imageUri = file.toURI();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//action is capture
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_BIG_PICTURE);//or TAKE_SMALL_PICTURE
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

//        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(MainActivity.this, "onActivityResult", Toast.LENGTH_SHORT).show();
    }

    /**
     * 通用的剪切方法
     * @param uri
     * @param outputX
     * @param outputY
     * @param requestCode
     */
    private void cropImageUri(Uri uri, int outputX, int outputY, int requestCode){
        File cacheDir = getCacheDir();
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
        File file = new File(cacheDir, "take_big.png");
        URI imageUri = file.toURI();

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 2);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, requestCode);
    }

    /**
     * private void cropImageUri(Uri uri, int requestCode){
     Intent intent = new Intent("com.android.camera.action.CROP");
     intent.setDataAndType(uri, "image/*");
     intent.putExtra("crop", "true");
     intent.putExtra("aspectX", aspectX);
     intent.putExtra("aspectY", aspectY);
     intent.putExtra("outputX", outputX);
     intent.putExtra("outputY", outputY);
     intent.putExtra("scale", true);
     intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
     intent.putExtra("return-data", false);
     intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
     intent.putExtra("noFaceDetection", true); // no face detection
     startActivityForResult(intent, requestCode);
     }
     这个函数存在问题，裁减时打开图片的uri与保存图片的uri相同，产生冲突，导致裁减完成后图片的大小变成0Byte。
     可将相机照片保存在另外的位置，将intent.setDataAndType(uri, "image/*");中的uri换成相机照片倮存的路径即可。
     */
}
