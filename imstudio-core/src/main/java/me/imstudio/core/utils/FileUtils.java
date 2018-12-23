package me.imstudio.core.utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.util.Preconditions;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public final class FileUtils {

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

    /**
     * Creates the specified directory, along with all parent paths if necessary
     *
     * @param directory directory to be created
     * @throws CreateDirectoryException
     */
    public static void mkdirs(File directory) throws CreateDirectoryException {
        if (directory.exists()) {
            // file exists and *is* a directory
            if (directory.isDirectory()) {
                return;
            }

            // file exists, but is not a directory - delete it
            if (!directory.delete()) {
                throw new CreateDirectoryException(
                        directory.getAbsolutePath(),
                        new FileDeleteException(directory.getAbsolutePath()));
            }
        }

        // doesn't exist. Create one
        if (!directory.mkdirs() && !directory.isDirectory()) {
            throw new CreateDirectoryException(directory.getAbsolutePath());
        }
    }

    /**
     * Represents an exception when the target file/directory cannot be deleted
     */
    public static class FileDeleteException extends IOException {
        public FileDeleteException(String message) {
            super(message);
        }
    }

    /**
     * Renames the source file to the target file. If the target file exists, then we attempt to
     * delete it. If the delete or the rename operation fails, then we raise an exception
     *
     * @param source the source file
     * @param target the new 'name' for the source file
     * @throws IOException
     */
    public static void rename(File source, File target) throws RenameException {
        Preconditions.checkNotNull(source);
        Preconditions.checkNotNull(target);
        // delete the target first - but ignore the result
        target.delete();

        if (source.renameTo(target)) {
            return;
        }

        Throwable innerException = null;
        if (target.exists()) {
            innerException = new FileDeleteException(target.getAbsolutePath());
        } else if (!source.getParentFile().exists()) {
            innerException = new ParentDirNotFoundException(source.getAbsolutePath());
        } else if (!source.exists()) {
            innerException = new FileNotFoundException(source.getAbsolutePath());
        }

        throw new RenameException(
                "Unknown error renaming " + source.getAbsolutePath() + " to " + target.getAbsolutePath(),
                innerException);
    }

    /**
     * A specialization of FileNotFoundException when the parent-dir doesn't exist
     */
    public static class ParentDirNotFoundException extends FileNotFoundException {
        public ParentDirNotFoundException(String message) {
            super(message);
        }
    }


    /**
     * Represents an unknown rename exception
     */
    public static class RenameException extends IOException {
        public RenameException(String message) {
            super(message);
        }

        public RenameException(String message, Throwable innerException) {
            super(message);
            initCause(innerException);

        }
    }

    /**
     * Represents an exception during directory creation
     */
    public static class CreateDirectoryException extends IOException {
        public CreateDirectoryException(String message) {
            super(message);
        }

        public CreateDirectoryException(String message, Throwable innerException) {
            super(message);
            initCause(innerException);
        }
    }

}
