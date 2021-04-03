package com.ekrishibazaar.interfaces;

import java.io.File;
import java.util.List;


public interface ImageCompressTaskListener {
    void onComplete(List<File> compressed, String imagename);

    void onError(Throwable error);
}
