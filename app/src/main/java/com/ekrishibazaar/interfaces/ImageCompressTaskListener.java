package com.ekrishibazaar.interfaces;

import java.io.File;
import java.util.List;


public interface ImageCompressTaskListener {
    void onComplete(List<File> compressed);

    void onError(Throwable error);
}
