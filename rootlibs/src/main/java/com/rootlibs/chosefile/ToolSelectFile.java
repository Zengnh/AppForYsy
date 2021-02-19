package com.rootlibs.chosefile;

import android.app.Activity;
import android.content.Intent;

import com.kbeanie.multipicker.api.FilePicker;
import com.kbeanie.multipicker.api.ImagePicker;
import com.kbeanie.multipicker.api.Picker;
import com.kbeanie.multipicker.api.VideoPicker;
import com.kbeanie.multipicker.api.callbacks.FilePickerCallback;
import com.kbeanie.multipicker.api.callbacks.ImagePickerCallback;
import com.kbeanie.multipicker.api.callbacks.VideoPickerCallback;
import com.kbeanie.multipicker.api.entity.ChosenFile;
import com.kbeanie.multipicker.api.entity.ChosenImage;
import com.kbeanie.multipicker.api.entity.ChosenVideo;
import com.rootlibs.downloader.InterListener;

import java.util.List;

//https://github.com/coomar2841/android-multipicker-library
//compile 'com.kbeanie:multipicker:1.6@aar'
public class ToolSelectFile {
    //    选择图片
    private ImagePicker imagePicker;
    private FilePicker filePicker;
    private VideoPicker viewp;

    public ToolSelectFile(Activity act) {
        imagePicker = new ImagePicker(act);
        filePicker = new FilePicker(act);
        viewp = new VideoPicker(act);
    }

    public void setResult(Intent data) {
        imagePicker.submit(data);
        filePicker.submit(data);
        viewp.submit(data);
    }

    private InterFaceResult listener;
    public void selectImage(InterFaceResult<List<ChosenImage>> ls) {
        this.listener = ls;
//        imagePicker.submit(new Intent());
        imagePicker.setImagePickerCallback(new ImagePickerCallback() {
                                               @Override
                                               public void onImagesChosen(List<ChosenImage> images) {
                                                   // Display images
                                                   if (listener != null) {
                                                       listener.result(images);
                                                   }
                                               }

                                               @Override
                                               public void onError(String message) {
                                                   // Do error handling
                                                   if (listener != null) {
                                                       listener.err(message);
                                                   }
                                               }
                                           }
        );
// imagePicker.allowMultiple(); // Default is false
// imagePicker.shouldGenerateMetadata(false); // Default is true
// imagePicker.shouldGenerateThumbnails(false); // Default is true
        imagePicker.pickImage();

    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if(resultCode == RESULT.OK) {
//            if(requestCode == Picker.PICK_IMAGE_DEVICE) {
//                imagePicker.submit(data);
//            }
//        }
//    }


    public void getFile(Activity act, InterFaceResult<List<ChosenFile>> ls) {
        this.listener = ls;

// filePicker.allowMultiple();
// filePicker.
        filePicker.setFilePickerCallback(new FilePickerCallback() {
            @Override
            public void onFilesChosen(List<ChosenFile> files) {
                // Display Files
                if (listener != null) {
                    listener.result(files);
                }
            }

            @Override
            public void onError(String message) {
                // Handle errors
                if (listener != null) {
                    listener.err(message);
                }
            }
        });

        filePicker.pickFile();
    }

    public void getVideo(Activity act, InterFaceResult<List<ChosenVideo>> ls) {
        this.listener = ls;
        viewp.setVideoPickerCallback(new VideoPickerCallback() {
            @Override
            public void onVideosChosen(List<ChosenVideo> list) {
                if (listener != null) {
                    listener.result(list);
                }
            }

            @Override
            public void onError(String message) {
                // Handle errors
                if (listener != null) {
                    listener.err(message);
                }
            }
        });
    }


}
