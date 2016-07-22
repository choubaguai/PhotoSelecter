# PhotoSelecter
高仿微信图片选择器<\br>
使用方法如下：<\br>

1.compile 'com.superbro:photoselecter:1.0.1'
 
2.https://github.com/choubaguai/PhotoSelecter/blob/master/photoselecter/src/main/java/com/superbro/myapplication/MainActivity.java

3.
```
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
