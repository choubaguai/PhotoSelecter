# PhotoSelecter
高仿微信图片选择器：

![image](https://github.com/choubaguai/PhotoSelecter/blob/master/GIF.gif ) 

大家好，第一次使用，请见谅。


鄙人手拙，收录的第一个自己的使用过还算不错的东西，类似微信选择照片。


使用方法很简单：


1.在你的module的build.gradle中添加下面这句代码：
```
compile 'com.superbro:photoselecter:1.0.1'
```
2.在你的manifests文件中添加下面两个activity：

```
 <activity
            android:name="utils.PhotoPickerActivity"
            android:configChanges="orientation|screenSize"
            android:theme="@style/PhotoPickerTheme"/>
 <activity
            android:name="utils.PhotoPreviewActivity"
            android:theme="@style/PhotoPickerTheme"/>
```            
3.然后添加相机和访问文件的权限：
```
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
 <uses-permission android:name="android.permission.CAMERA"/>
``` 
4.在你的activity或者fragment里面调用：
```
mRcyGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        if (imagePaths.size() < IMAGE_SIZE && position == imagePaths.size()) {

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
                                    intent.setCurrentItem(position);
                                    intent.setPhotoPaths(imagePaths);
                                    startActivityForResult(intent, REQUEST_PREVIEW_CODE);

                        }

            }
        });
        ```
5.重写onActivityResult方法：
```java
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
```

6.具体的请看源码： 

https://github.com/choubaguai/PhotoSelecter/blob/master/photoselecter/src/main/java/com/superbro/myapplication/MainActivity.java
