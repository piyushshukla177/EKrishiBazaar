package com.ekrishibazaar.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.ekrishibazaar.interfaces.ImageCompressTaskListener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ImageCompressTask implements Runnable {

    private Context mContext;
    private List<String> originalPaths = new ArrayList<>();
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private List<File> result = new ArrayList<>();
    private ImageCompressTaskListener mIImageCompressTaskListener;
    String imagename;

    public ImageCompressTask(Context context, String path,
                             ImageCompressTaskListener compressTaskListener, String imagename) {
        originalPaths.add(path);
        mContext = context;
        mIImageCompressTaskListener = compressTaskListener;
        this.imagename = imagename;
    }

    public ImageCompressTask(Context context, List<String> paths,
                             ImageCompressTaskListener compressTaskListener) {
        originalPaths = paths;
        mContext = context;
        mIImageCompressTaskListener = compressTaskListener;
    }

    @Override
    public void run() {
        try {
            for (String path : originalPaths) {
                File file = FileCompressUtils.getCompressed(mContext, path);
                result.add(file);
            }
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (mIImageCompressTaskListener != null)
                        mIImageCompressTaskListener.onComplete(result, imagename);
                }
            });
        } catch (final IOException ex) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (mIImageCompressTaskListener != null)
                        mIImageCompressTaskListener.onError(ex);
                }
            });
        }
    }
}

