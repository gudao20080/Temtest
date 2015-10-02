package com.example.bitmapcrop;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.net.URI;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private final int CHOOSE_BIG_PICTURE = 1;
    private final int CHOOSE_SMALL_PICTURE = 2;
    private final int TAKE_BIG_PICTURE = 3;
    private final int REQUEST_PICK = 4;
    private final int REQUEST_PICK_BIG = 5;
    private final int REQUEST_CAMERA = 6;
    Uri imageUri;
    String filePath;
    private File imageFile;


    @Bind(R.id.image)
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
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
                selectPhoto();
            }
        });
        findViewById(R.id.tv_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImageByCamera();
            }
        });
    }

    public void selectPhoto() {
        // 选择图片
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_PICK);
    }


    /**
     * 拍照
     */
    public void captureImageByCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(Environment.getExternalStorageDirectory() + "/Images");
        if(!file.exists()){
            file.mkdirs();
        }
        imageFile = new File(Environment.getExternalStorageDirectory() + "/Images/",
            "cameraImg" + String.valueOf(System.currentTimeMillis()) + ".png");

        Uri mUri = Uri.fromFile(imageFile);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
        cameraIntent.putExtra("return-data", true);
        startActivityForResult(cameraIntent, REQUEST_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CAMERA ) { //拍照返回
            Uri uri;
            if (null == data) {
                uri = Uri.fromFile(imageFile);
            }else {
                uri = data.getData();
            }
            Log.d("way", "uri: " + uri);
            imageView.setImageURI(uri);
//            goCropActivity(uri);

        }else if (requestCode == REQUEST_PICK) {
            Uri uri = data.getData();
            Log.d("way", "uri: " + uri);
            imageView.setImageURI(uri);
//            goCropActivity(uri);
        }


        Toast.makeText(MainActivity.this, "onActivityResult", Toast.LENGTH_SHORT).show();
    }

    public void goCropActivity(Uri imageUri) {

        Intent intent = new Intent(this, CropImageActivity.class);
        intent.putExtra("Uri", imageUri);
        startActivity(intent);

    }

    private void setPic() {
        // Get the dimensions of the View
        int targetW = mImageView.getWidth();
        int targetH = mImageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        mImageView.setImageBitmap(bitmap);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
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
        Uri imageUri = Uri.parse("file:///" + file.getAbsolutePath());
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
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 400);
        intent.putExtra("outputY", 400);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
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
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", true);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, CHOOSE_SMALL_PICTURE);
    }

}
