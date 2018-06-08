package com.example.t2sadmin.sampleapp.utils;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.t2sadmin.sampleapp.R;
import com.example.t2sadmin.sampleapp.main.Application;
import com.example.t2sadmin.sampleapp.main.BaseActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.channels.FileChannel;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Utility {


    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;

        }
        return isValid;
    }

    public static boolean isInternetConnected() {
        ConnectivityManager CManager =
                (ConnectivityManager) Application.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo NInfo = CManager.getActiveNetworkInfo();
        boolean isConnected = false;
        if (NInfo != null && NInfo.isAvailable() && NInfo.isConnectedOrConnecting()) {
            try {
                isConnected = InetAddress.getByName("https://www.google.co.in/").isReachable(1000);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return isConnected;
    }


    public static void setupUI(View view, final Context mContext,
                               final Dialog mDialog) {
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(mContext, mDialog);
                    return false;
                }
            });
        }
        if (view instanceof ViewGroup) {

            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View mInnerView = ((ViewGroup) view).getChildAt(i);
                setupUI(mInnerView, mContext, mDialog);
            }
        }
    }

    public static void hideSoftKeyboard(Context mContext, Dialog mDialog) {
        try {
            if (mDialog != null) {
                InputMethodManager mInputMethodManager = (InputMethodManager) mContext
                        .getSystemService(Context.INPUT_METHOD_SERVICE);

                if (mDialog.getCurrentFocus() != null
                        && mDialog.getCurrentFocus().getWindowToken() != null) {
                    mInputMethodManager.hideSoftInputFromWindow(mDialog
                            .getCurrentFocus().getWindowToken(), 0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Uri getCameraCaptureImgUri(Context mContext) {
        return FileProvider.getUriForFile(mContext,
                mContext.getString(R.string.file_provider_authority),
                Utility.getNewFile(mContext, "CAPTURE_IMG"));
    }

    public static Uri getCameraImgUri(Context mContext) {
        return Uri.fromFile(Utility.getNewFile(mContext, "CAPTURE_IMG"));
    }

    public static Uri getUriFromFile(Context mContext, Uri imageUri, String fileType) {
        return Uri.fromFile(getFileFromUri(mContext, imageUri, fileType));
    }

    public static File getFileFromUri(Context mContext, Uri imageUri, String fileType) {
        File destinationFile = null;
        try {
            //TODO For use From MediaStore
//            String[] filePathColumn = {MediaStore.Images.Media.DATA};
//            Cursor cursor = mContext.getContentResolver().query(imageUri, filePathColumn, null, null, null);
//            assert cursor != null;
//            cursor.moveToFirst();
//            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//            String picturePath = cursor.getString(columnIndex);
//            cursor.close();
            File srcFile = new File(imageUri.getPath());
//            destinationFile = new File(Environment.getExternalStorageDirectory(), getNewFileName(mContext, fileType));
            destinationFile = getNewFile(mContext, fileType);
            ((BaseActivity) mContext).sysOut("DestinationFile: " + destinationFile.getPath());
            FileChannel srcFileChannel = new FileInputStream(srcFile).getChannel();
            FileChannel destFileChannel = new FileOutputStream(destinationFile).getChannel();

            if (srcFileChannel != null) {
                destFileChannel.transferFrom(srcFileChannel, 0, srcFileChannel.size());
                srcFileChannel.close();
            }
            destFileChannel.close();
        } catch (IOException e) {
            ((BaseActivity) mContext).sysOut("" + e.getMessage());
        }
        return destinationFile;
    }

    private static File getNewFile(Context mContext, String fileType) {
        File mMainFile = new File(Environment.getExternalStorageDirectory().toString() + "/SpotYa");
        if (!mMainFile.exists()) mMainFile.mkdir();

        File mGetMainProfileFile = new File(mMainFile.getPath() + "/Profile");
        if (!mGetMainProfileFile.exists()) mGetMainProfileFile.mkdir();

        File mGetMainFeedFile = new File(mMainFile.getPath() + "/Feed");
        if (!mGetMainFeedFile.exists()) mGetMainFeedFile.mkdir();

        switch (fileType) {
            case "1":
                return new File(mGetMainProfileFile.getPath(), getNewFileName(mContext, fileType));
            case "2":
                return new File(mGetMainFeedFile.getPath(), getNewFileName(mContext, fileType));
            case "CAPTURE_IMG":
                return new File(mMainFile.getPath(), getNewFileName(mContext, fileType));
            default:
                return new File(mMainFile.getPath(), getNewFileName(mContext, fileType));
        }

    }

    private static String getNewFileName(Context mContext, String fileType) {
        String userId = "";

        switch (fileType) {
            case "1":
                return "1" + "_" + userId + ".jpg";
            case "2":
                return "2" + "_" + userId + "_" + System.currentTimeMillis() + ".jpg";
            case "CAPTURE_IMG":
                return "CAPTURE_IMG" + ".jpg";
            default:
                return userId + "_" + System.currentTimeMillis() + ".jpg";
        }
    }

    public static String compressImage(Context mContext, String imageUri) {

        String filePath = getRealPathFromURI(mContext, imageUri);
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

//      max Height and width values of the compressed image is taken as 816x612

        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

//      width and height values are set maintaining the aspect ratio of the image

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

//      setting inSampleSize value allows to load a scaled down version of the original image

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

//      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
//          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

//      check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
            } else if (orientation == 3) {
                matrix.postRotate(180);
            } else if (orientation == 8) {
                matrix.postRotate(270);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        String filename = getNewFile(mContext, "").getPath();
        try {
            out = new FileOutputStream(filename);
//          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return filename;

    }

    private static String getRealPathFromURI(Context mContext, String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = mContext.getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

    public static void deleteFiles(Context mContext) {
        //TODO Future Use
        try {
            File mMainFile = new File(Environment.getExternalStorageDirectory().toString() + "/SpotYa");
            if (mMainFile.exists() && mMainFile.isDirectory()) {
                String[] children = mMainFile.list();
                for (int i = 0; i < children.length; i++)
                    new File(mMainFile, children[i]).delete();
            }
            File mProfileFile = new File(Environment.getExternalStorageDirectory().toString() + "/SpotYa/Profile");
            if (mProfileFile.exists() && mProfileFile.isDirectory()) {
                String[] children = mProfileFile.list();
                for (int i = 0; i < children.length; i++)
                    new File(mProfileFile, children[i]).delete();
            }
            File mFeedFile = new File(Environment.getExternalStorageDirectory().toString() + "/SpotYa/Feed");
            if (mFeedFile.exists() && mFeedFile.isDirectory()) {
                String[] children = mFeedFile.list();
                for (int i = 0; i < children.length; i++)
                    new File(mFeedFile, children[i]).delete();
            }
//            File mWorkoutFile = new File(Environment.getExternalStorageDirectory().toString() + "/SpotYa/Feed");
//            if (mWorkoutFile.exists() && mWorkoutFile.isDirectory()) {
//                String[] children = mWorkoutFile.list();
//                for (int i = 0; i < children.length; i++)
//                    new File(mWorkoutFile, children[i]).delete();
//            }

        } catch (Exception e) {
            ((BaseActivity) mContext).sysOut("" + e.getMessage());
        }

    }

}
