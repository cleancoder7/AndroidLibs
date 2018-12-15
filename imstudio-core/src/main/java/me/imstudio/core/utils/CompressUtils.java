package me.imstudio.core.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.annotation.NonNull;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public final class CompressUtils {

    public static String getDefaultFolder(Context context) {
        return String.valueOf(context.getFilesDir().getAbsolutePath());
    }

    public static String getDefaultFolderPath(Context context) {
        return String.valueOf(context.getFilesDir().getAbsolutePath()) + File.separator;
    }

    public static final class ZIP {

        public static boolean decompress(String source, String destination) {
            try {

                ZipFile zipFile = new ZipFile(source);
                Enumeration<? extends ZipEntry> e = zipFile.entries();
                while (e.hasMoreElements()) {
                    ZipEntry entry = e.nextElement();
                    File destFile = new File(destination, entry.getName());
                    File destinationParent = destFile.getParentFile();
                    if (destinationParent != null)
                        destinationParent.mkdirs();
                    if (!entry.isDirectory()) {
                        BufferedInputStream in = new BufferedInputStream(zipFile.getInputStream(entry));
                        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(destFile));
                        byte[] buffer = new byte[4096];
                        while (true) {
                            int bytesWrite = in.read(buffer);
                            if (bytesWrite == -1)
                                break;
                            try {
                                out.write(buffer, 0, bytesWrite);
                            } catch (Exception ioe) {
                                ioe.printStackTrace();
                                return false;
                            }
                        }
                        out.flush();
                        out.close();
                        in.close();
                    }
                }
                zipFile.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }

        public static List<String> unpackFromAsset(@NonNull Context context, String fileName) {

            List<String> fonts = new ArrayList<>();
            AssetManager manager = context.getAssets();
            final String OUTPUT_FOLDER = String.valueOf(context.getFilesDir().getAbsolutePath());
            File folder = new File(OUTPUT_FOLDER);
            if (!folder.exists())
                folder.mkdir();
            try {
                BufferedInputStream bis = new BufferedInputStream(manager.open(fileName));
                ZipInputStream zis = new ZipInputStream(bis);
                ZipEntry ze = zis.getNextEntry();
                while (ze != null) {
                    String fileEntry = ze.getName();
                    File newFile = new File(OUTPUT_FOLDER + File.separator + fileEntry);
                    if (fileEntry.endsWith("/"))
                        newFile.mkdirs();
                    File parent = newFile.getParentFile();
                    if (parent != null) {
                        parent.mkdirs();
                    }
                    FileOutputStream fos = context.openFileOutput(newFile.getName(), 0);
                    byte[] buffer = new byte[4096];
                    int length;
                    while ((length = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, length);
                        fos.flush();
                    }
                    fos.close();
                    ze = zis.getNextEntry();
                    fonts.add(OUTPUT_FOLDER + File.separator + fileEntry);
                }
                bis.close();
                zis.closeEntry();
                zis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return fonts;
        }
    }

}