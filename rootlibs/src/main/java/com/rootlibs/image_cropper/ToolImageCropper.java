package com.rootlibs.image_cropper;
//https://github.com/ArthurHub/Android-Image-Cropper
public class ToolImageCropper {
//    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
//<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
//            -keep class androidx.appcompat.widget.** { *; }
//    api 'com.theartofdev.edmodo:android-image-cropper:2.8.+'

//         <activity android:name="com.theartofdev.edmodo.cropper.CropImageActivity"
//    android:theme="@style/Base.Theme.AppCompat"/>


//    // start picker to get image for cropping and then use the image in cropping activity
//CropImage.activity()
//        .setGuidelines(CropImageView.Guidelines.ON)
//  .start(this);
//
//// start cropping activity for pre-acquired image saved on the device
//CropImage.activity(imageUri)
//            .start(this);
//
//// for fragment (DO NOT use `getActivity()`)
//CropImage.activity()
//        .start(getContext(), this);

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            if (resultCode == RESULT_OK) {
//                Uri resultUri = result.getUri();
//            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                Exception error = result.getError();
//            }
//        }
//    }

//  使用方法 #########################################################################

//    <!-- Image Cropper fill the remaining available height -->
//<com.theartofdev.edmodo.cropper.CropImageView
//    xmlns:custom="http://schemas.android.com/apk/res-auto"
//    android:id="@+id/cropImageView"
//    android:layout_width="match_parent"
//    android:layout_height="0dp"
//    android:layout_weight="1"/>
//    Set image to crop
//cropImageView.setImageUriAsync(uri);
//// or (prefer using uri for performance and better user experience)
//cropImageView.setImageBitmap(bitmap);
//    Get cropped image
//// subscribe to async event using cropImageView.setOnCropImageCompleteListener(listener)
//cropImageView.getCroppedImageAsync();
//    // or
//    Bitmap cropped = cropImageView.getCroppedImage();


}
