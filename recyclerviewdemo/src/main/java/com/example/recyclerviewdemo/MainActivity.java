package com.example.recyclerviewdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.rv);
//        findViewById(R.id.btn_load).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                loadImages();
//            }
//        });
        Fresco.initialize(this);
        loadImages();
    }

    private void loadImages() {
        List<String> images = new ArrayList<>();
        images.add("http://img5.imgtn.bdimg.com/it/u=1478257864,2882073929&fm=21&gp=0.jpg");
        images.add("http://img0.imgtn.bdimg.com/it/u=1231062057,3852413437&fm=21&gp=0.jpg");
        images.add("http://img5.imgtn.bdimg.com/it/u=1020667791,3260921600&fm=21&gp=0.jpg");
        images.add("http://img4.imgtn.bdimg.com/it/u=828291890,997706858&fm=21&gp=0.jpg");
images.add("http://img5.imgtn.bdimg.com/it/u=1478257864,2882073929&fm=21&gp=0.jpg");
        images.add("http://img0.imgtn.bdimg.com/it/u=1231062057,3852413437&fm=21&gp=0.jpg");
        images.add("http://img5.imgtn.bdimg.com/it/u=1020667791,3260921600&fm=21&gp=0.jpg");
        images.add("http://img4.imgtn.bdimg.com/it/u=828291890,997706858&fm=21&gp=0.jpg");
images.add("http://img5.imgtn.bdimg.com/it/u=1478257864,2882073929&fm=21&gp=0.jpg");
        images.add("http://img0.imgtn.bdimg.com/it/u=1231062057,3852413437&fm=21&gp=0.jpg");
        images.add("http://img5.imgtn.bdimg.com/it/u=1020667791,3260921600&fm=21&gp=0.jpg");
        images.add("http://img4.imgtn.bdimg.com/it/u=828291890,997706858&fm=21&gp=0.jpg");
images.add("http://img5.imgtn.bdimg.com/it/u=1478257864,2882073929&fm=21&gp=0.jpg");
        images.add("http://img0.imgtn.bdimg.com/it/u=1231062057,3852413437&fm=21&gp=0.jpg");
        images.add("http://img5.imgtn.bdimg.com/it/u=1020667791,3260921600&fm=21&gp=0.jpg");
        images.add("http://img4.imgtn.bdimg.com/it/u=828291890,997706858&fm=21&gp=0.jpg");
images.add("http://img5.imgtn.bdimg.com/it/u=1478257864,2882073929&fm=21&gp=0.jpg");
        images.add("http://img0.imgtn.bdimg.com/it/u=1231062057,3852413437&fm=21&gp=0.jpg");
        images.add("http://img5.imgtn.bdimg.com/it/u=1020667791,3260921600&fm=21&gp=0.jpg");
        images.add("http://img4.imgtn.bdimg.com/it/u=828291890,997706858&fm=21&gp=0.jpg");
images.add("http://img5.imgtn.bdimg.com/it/u=1478257864,2882073929&fm=21&gp=0.jpg");
        images.add("http://img0.imgtn.bdimg.com/it/u=1231062057,3852413437&fm=21&gp=0.jpg");
        images.add("http://img5.imgtn.bdimg.com/it/u=1020667791,3260921600&fm=21&gp=0.jpg");
        images.add("http://img4.imgtn.bdimg.com/it/u=828291890,997706858&fm=21&gp=0.jpg");

        RvAdapter adapter = new RvAdapter(images);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

    }


}
