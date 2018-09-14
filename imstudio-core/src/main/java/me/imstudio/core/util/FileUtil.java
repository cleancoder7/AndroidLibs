package me.imstudio.core.util;

import android.text.TextUtils;
import android.webkit.MimeTypeMap;

import java.io.File;

public final class FileUtil {

    public static String getMimeType(File file) {

        String extension = getExtension(file.getName());

        if (extension.length() > 0)
            return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension.substring(1));

        return "application/octet-stream";
    }

    public static String getExtension(String uri) {
        if (uri == null)
            return null;
        int dot = uri.lastIndexOf(".");
        if (dot >= 0)
            return uri.substring(dot);
        else
            return "";
    }
    
    public static boolean isExist(String filePath) {
        if (TextUtils.isEmpty(filePath))
            return false;
        File file = new File(filePath);
        return file.exists();
    }
}
