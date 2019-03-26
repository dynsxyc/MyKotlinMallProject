package com.zhongjiang.dyn.imageselector.model;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import com.zhongjiang.dyn.imageselector.entry.Folder;
import com.zhongjiang.dyn.imageselector.entry.Image;
import com.zhongjiang.dyn.imageselector.utils.ImageSelectorFileUtils;
import com.zhongjiang.dyn.imageselector.utils.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.fragment.app.FragmentActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import static android.provider.BaseColumns._ID;
import static android.provider.MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME;
import static android.provider.MediaStore.Images.ImageColumns.BUCKET_ID;
import static android.provider.MediaStore.MediaColumns.DATA;
import static android.provider.MediaStore.MediaColumns.DATE_ADDED;
import static android.provider.MediaStore.MediaColumns.SIZE;

public class ImageModel {


    public static final String EXTRA_SHOW_GIF = "EXTRA_SHOW_GIF";
    public final static int INDEX_ALL_PHOTOS = 0;
    private static LoaderManager mLoaderManager;
    private static PhotoDirLoaderCallbacks mPhotoDirLoaderCallbacks;
    private static Bundle mBundle;


    public static void getPhotoDirs(FragmentActivity activity, Bundle args, DataCallback resultCallback) {
        mLoaderManager = LoaderManager.getInstance(activity);
        mPhotoDirLoaderCallbacks= new PhotoDirLoaderCallbacks(activity, resultCallback);
        mBundle = args;
        mLoaderManager.restartLoader(0, mBundle, mPhotoDirLoaderCallbacks);
    }

    private static class PhotoDirLoaderCallbacks implements LoaderManager.LoaderCallbacks<Cursor> {

        private Context context;
        private DataCallback resultCallback;

        public PhotoDirLoaderCallbacks(Context context, DataCallback resultCallback) {
            this.context = context;
            this.resultCallback = resultCallback;
        }

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            return new PhotoDirectoryLoader(context, args.getBoolean(EXTRA_SHOW_GIF, false));
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            Log.i("test","onLoadFinished");
            if (data == null || data.isClosed()) {
                mLoaderManager.restartLoader(0,mBundle,mPhotoDirLoaderCallbacks);
                return;
            }
            /**文件夹数组*/
            List<Folder> directories = new ArrayList<>();
            Folder photoDirectoryAll = new Folder();
            photoDirectoryAll.setName("所有图片");
            photoDirectoryAll.setId("ALL");

            while (data.moveToNext()) {

                int imageId = data.getInt(data.getColumnIndexOrThrow(_ID));
                String bucketId = data.getString(data.getColumnIndexOrThrow(BUCKET_ID));
                String name = data.getString(data.getColumnIndexOrThrow(BUCKET_DISPLAY_NAME));
                String path = data.getString(data.getColumnIndexOrThrow(DATA));
                long addtime = data.getLong(data.getColumnIndexOrThrow(DATE_ADDED));
                long size = data.getInt(data.getColumnIndexOrThrow(SIZE));

                if (size < 1) {
                    continue;
                }

                Folder photoDirectory = new Folder();
                photoDirectory.setId(bucketId);
                photoDirectory.setName(name);
                Image image = new Image(path,addtime, name);
                if (!directories.contains(photoDirectory)) {
                    photoDirectory.setCoverPath(path);
                    photoDirectory.addImage(image);
                    photoDirectory.setDateAdded(addtime);
                    directories.add(photoDirectory);
                } else {
                    directories.get(directories.indexOf(photoDirectory)).addImage(image);
                }

                photoDirectoryAll.addImage(image);
            }
            if (photoDirectoryAll!= null && photoDirectoryAll.getImages() != null && photoDirectoryAll.getImages().size() > 0) {
                photoDirectoryAll.setCoverPath(photoDirectoryAll.getImages().get(0).getPath());
            }
            //
            directories.add(INDEX_ALL_PHOTOS, photoDirectoryAll);
            if (resultCallback != null) {
                resultCallback.onSuccess(directories);
            }
            data.close();
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            Log.i("test","onLoaderReset");
        }
    }

/***********************************************************************************************************/

    /**
     * 从SDCard加载图片
     *
     * @param context
     * @param callback
     */
    public static void loadImageForSDCard(final Context context, final DataCallback callback) {
        //由于扫描图片是耗时的操作，所以要在子线程处理。
        new Thread(new Runnable() {
            @Override
            public void run() {
                //扫描图片
                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver = context.getContentResolver();

                Cursor mCursor = mContentResolver.query(mImageUri, new String[]{
                                MediaStore.Images.Media.DATA,
                                MediaStore.Images.Media.DISPLAY_NAME,
                                MediaStore.Images.Media.DATE_ADDED,
                                MediaStore.Images.Media._ID},
                        null,
                        null,
                        MediaStore.Images.Media.DATE_ADDED);

                ArrayList<Image> images = new ArrayList<>();

                //读取扫描到的图片
                if (mCursor != null) {
                    while (mCursor.moveToNext()) {
                        // 获取图片的路径
                        String path = mCursor.getString(
                                mCursor.getColumnIndex(MediaStore.Images.Media.DATA));
                        //获取图片名称
                        String name = mCursor.getString(
                                mCursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
                        //获取图片时间
                        long time = mCursor.getLong(
                                mCursor.getColumnIndex(MediaStore.Images.Media.DATE_ADDED));
                        if (!".downloading".equals(ImageSelectorFileUtils.getExtensionName(path))) { //过滤未下载完成的文件
                            images.add(new Image(path, time, name));
                        }
                    }
                    mCursor.close();
                }
                Collections.reverse(images);
                callback.onSuccess(splitFolder(images));
            }
        }).start();
    }

    /**
     * 把图片按文件夹拆分，第一个文件夹保存所有的图片
     *
     * @param images
     * @return
     */
    private static ArrayList<Folder> splitFolder(ArrayList<Image> images) {
        ArrayList<Folder> folders = new ArrayList<>();
        folders.add(new Folder("全部图片", images));

        if (images != null && !images.isEmpty()) {
            int size = images.size();
            for (int i = 0; i < size; i++) {
                String path = images.get(i).getPath();
                String name = getFolderName(path);
                if (StringUtils.isNotEmptyString(name)) {
                    Folder folder = getFolder(name, folders);
                    folder.addImage(images.get(i));
                }
            }
        }
        return folders;
    }

    /**
     * 根据图片路径，获取图片文件夹名称
     *
     * @param path
     * @return
     */
    private static String getFolderName(String path) {
        if (StringUtils.isNotEmptyString(path)) {
            String[] strings = path.split(File.separator);
            if (strings.length >= 2) {
                return strings[strings.length - 2];
            }
        }
        return "";
    }

    private static Folder getFolder(String name, List<Folder> folders) {
        if (!folders.isEmpty()) {
            int size = folders.size();
            for (int i = 0; i < size; i++) {
                Folder folder = folders.get(i);
                if (name.equals(folder.getName())) {
                    return folder;
                }
            }
        }
        Folder newFolder = new Folder(name);
        folders.add(newFolder);
        return newFolder;
    }

    public interface DataCallback {
        void onSuccess(List<Folder> folders);
    }
}
