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
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private final int CHOOSE_BIG_PICTURE = 1;
    private final int CHOOSE_SMALL_PICTURE = 2;
    private final int TAKE_BIG_PICTURE = 3;
    private final int REQUEST_PICK = 4;
    private final int REQUEST_PICK_BIG = 5;
    private final int REQUEST_CAMERA = 6;
    private String mCurrentPhotoPath;

    private static final String JPEG_FILE_PREFIX = "IMG_";
    private static final String JPEG_FILE_SUFFIX = ".jpg";
    private AlbumStorageDirFactory mAlbumStorageDirFactory = null;


    @Bind(R.id.image)
    ImageView mImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mAlbumStorageDirFactory = new FroyoAlbumDirFactory();
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

            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            File f;

            try {
                f = setUpPhotoFile();
                mCurrentPhotoPath = f.getAbsolutePath();
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
            } catch (IOException e) {
                e.printStackTrace();
                f = null;
                mCurrentPhotoPath = null;
            }

        startActivityForResult(takePictureIntent, REQUEST_CAMERA);
    }

    private File setUpPhotoFile() throws IOException {

        File f = createImageFile();
        mCurrentPhotoPath = f.getAbsolutePath();

        return f;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
        File albumF = getAlbumDir();
        File imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX, albumF);
        return imageF;
    }


    private File getAlbumDir() {
        File storageDir = null;

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {

            storageDir = mAlbumStorageDirFactory.getAlbumStorageDir(getAlbumName());

            if (storageDir != null) {
                if (! storageDir.mkdirs()) {
                    if (! storageDir.exists()){
                        Log.d("CameraSample", "failed to create directory");
                        return null;
                    }
                }
            }

        } else {
            Log.v(getString(R.string.app_name), "External storage is not mounted READ/WRITE.");
        }

        return storageDir;
    }

    private String getAlbumName() {
        return "crop";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CAMERA ) { //拍照返回
            if (resultCode == RESULT_OK) {
                handleBigCameraPhoto();
            }

        }else if (requestCode == REQUEST_PICK) {
            Uri uri = data.getData();
            Log.d("way", "uri: " + uri);

            try {
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
//                mImageView.setImageURI(uri);
                String path = GetPathFromUri4kitkat.getPath(this, uri);
                Bitmap bitmap = getBitmap(path, true);

                mImageView.setImageBitmap(bitmap);

                mImageView.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }

//            goCropActivity(uri);
        }

        Toast.makeText(MainActivity.this, "onActivityResult", Toast.LENGTH_SHORT).show();
    }

    public void goCropActivity(Uri imageUri) {

        Intent intent = new Intent(this, CropImageActivity.class);
        intent.putExtra("Uri", imageUri);
        startActivity(intent);

    }

    private void handleBigCameraPhoto() {

        if (mCurrentPhotoPath != null) {
            setPic();
            galleryAddPic();
            mCurrentPhotoPath = null;
        }

    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
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

        Intent intent = new Intent(this, CropImageActivity.class);
//        intent.putExtra("bitmap", bitmap);
        MyApplication application = (MyApplication) getApplication();
        application.mBitmap = bitmap;
        startActivity(intent);

    }

    private Bitmap getBitmap(String path ,boolean isLargeFlg){

//先解析图片边框的大小
        BitmapFactory.Options ops = new BitmapFactory.Options();
        ops.inJustDecodeBounds = true;
        Bitmap bm = BitmapFactory.decodeFile(path, ops);
        ops.inSampleSize = 1;
        int oHeight = ops.outHeight;
        int oWidth = ops.outWidth;

//控制压缩比
        int contentHeight = 0;
        int contentWidth = 0;
        contentWidth = 400;
        contentHeight = 800;
//        if(isLargeFlg){
//        }else{
//            contentHeight = ITEM_HEIGHT;
//            contentWidth = ITEM_WIDTH;
//        }
        if(((float)oHeight/contentHeight) < ((float)oWidth/contentWidth)){
            ops.inSampleSize = (int) Math.ceil((float)oWidth/contentWidth);
        }else{
            ops.inSampleSize = (int) Math.ceil((float)oHeight/contentHeight);
        }
        ops.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(path, ops);
        return bm;
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
