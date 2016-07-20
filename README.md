# PhotoSelecter
高仿微信图片选择器
使用方法如下：


1.
GridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        
    2.    
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
