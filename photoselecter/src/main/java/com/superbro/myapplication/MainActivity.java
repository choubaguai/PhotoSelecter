package com.superbro.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

import utils.PhotoPickerActivity;
import utils.PhotoPickerIntent;
import utils.PhotoPreviewActivity;
import utils.PhotoPreviewIntent;
import utils.SelectModel;

public class MainActivity extends AppCompatActivity {


    GridView mGridView;
    private static final int REQUEST_CAMERA_CODE = 10;
    private static final int REQUEST_PREVIEW_CODE = 20;
    private ArrayList<String> imagePaths = new ArrayList<>();
    private static final int IMAGE_SIZE = 16;
    GrideAdapter mGrideAdapter;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        mGridView = (GridView) findViewById(R.id.gridView);
        mGrideAdapter = new GrideAdapter();
        mGridView.setAdapter(mGrideAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (imagePaths.size() < IMAGE_SIZE && i == imagePaths.size()) {
                    //照片选择
                    PhotoPickerIntent intent = new PhotoPickerIntent(mContext);
                    intent.setSelectModel(SelectModel.MULTI);
                    intent.setShowCarema(true); // 是否显示拍照
                    intent.setMaxTotal(IMAGE_SIZE); // 最多选择照片数量，默认为9
                    intent.setSelectedPaths(imagePaths); // 已选中的照片地址， 用于回显选中状态
                    startActivityForResult(intent, REQUEST_CAMERA_CODE);
                } else {
                    //查看大图
                    PhotoPreviewIntent intent = new PhotoPreviewIntent(mContext);
                    intent.setCurrentItem(i);
                    intent.setPhotoPaths(imagePaths);
                    startActivityForResult(intent, REQUEST_PREVIEW_CODE);
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                // 选择照片
                case REQUEST_CAMERA_CODE:
                    loadAdpater(data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT));
                    break;
                // 预览
                case REQUEST_PREVIEW_CODE:
                    loadAdpater(data.getStringArrayListExtra(PhotoPreviewActivity.EXTRA_RESULT));
                    break;
            }
        }
    }

    private void loadAdpater(ArrayList<String> paths) {
        if (imagePaths == null) {
            imagePaths = new ArrayList<>();
        }
        imagePaths.clear();
        imagePaths.addAll(paths);
        mGrideAdapter.notifyDataSetChanged();
    }

    class GrideAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            int total = imagePaths.size();
            if (total < IMAGE_SIZE)
                total++;
            return total;
        }

        @Override
        public String getItem(int i) {
            return imagePaths.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_gridview, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.image);
            if (i == imagePaths.size() && imagePaths.size() < IMAGE_SIZE) {
                Glide.with(mContext)
                        .load(R.drawable.icon_add)
                        .override(186,186)
                        .placeholder(R.mipmap.default_error)
                        .error(R.mipmap.default_error)
                        .centerCrop()
                        .crossFade()
                        .into(imageView);
            } else {
                Glide.with(mContext)
                        .load(new File(getItem(i)))
                        .override(186,186)
                        .placeholder(R.mipmap.default_error)
                        .error(R.mipmap.default_error)
                        .centerCrop()
                        .crossFade()
                        .into(imageView);
            }
            return view;
        }
    }
}
