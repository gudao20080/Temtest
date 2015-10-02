package com.example.bitmapcrop;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.example.bitmapcrop.view.CropImageView;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * User: WangKai(123940232@qq.com)
 * 2015-10-02 08:43
 */
public class CropImageActivity extends AppCompatActivity {
    @Bind(R.id.btn_cancel)
    Button cancelBtn;
    @Bind(R.id.btn_ok)
    Button okBtn;
    @Bind(R.id.cropImageView)
    CropImageView mImageView;
    private String filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);
        ButterKnife.bind(this);

        Uri uri = getIntent().getParcelableExtra("Uri");
//        mImageView.setImageBitmap(bitmap);
        mImageView.setImageURI(uri);
//        mImageView.setImageResource(R.mipmap.ic_launcher);
//        Bitmap bitmap = BitmapFactory.decodeFile(uri2File(uri).getAbsolutePath());
//        mImageView.setImageBitmap(bitmap);
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

    public File uri2File(Uri uri) {
        String[] proj = { MediaStore.Images.Media.DATA };

        Cursor actualimagecursor = managedQuery(uri,proj,null,null,null);

        int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        actualimagecursor.moveToFirst();

        String img_path = actualimagecursor.getString(actual_image_column_index);

        File file = new File(img_path);
        return file;
    }
}
