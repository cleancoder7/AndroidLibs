package me.imstudio.core.util;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;

import java.io.ByteArrayOutputStream;
import java.io.File;

public final class FileUtil {

    public static String getMimeType(File file) {

        String extension = getExtension(file.getName());

        if (extension.length() > 0)
            return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension.substring(1));

        return "application/octet-stream";
    }

    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
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
