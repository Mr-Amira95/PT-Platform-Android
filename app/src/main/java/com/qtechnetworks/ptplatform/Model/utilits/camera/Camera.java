package com.qtechnetworks.ptplatform.Model.utilits.camera;

import  static android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;


import com.qtechnetworks.ptplatform.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;


public class Camera {

    public static AlertDialog desDialog = null;
    public static Uri fileUri;
    public static final int CAMERA_REQUEST = 1888;
    public static final int GALLERY_REQUEST = 100;
    private static final String TAG = "TAG";
    public static final int REQUEST_GALLERY_CODE = 200, REQUEST_CAMERA = 400;
    public static final int READ_REQUEST_CODE = 300;


    public static void showGallery(final Activity activity) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View dialogView = inflater.inflate(R.layout.dialog_gallery, null);
        builder.setView(dialogView);

  dialogView.findViewById(R.id.Camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                captureImage(activity);
                desDialog.cancel();
            }
        });

        dialogView.findViewById(R.id.gallery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK, EXTERNAL_CONTENT_URI);
                 activity.startActivityForResult(i, GALLERY_REQUEST);
                desDialog.cancel();
            }
        });

        desDialog = builder.create();
        desDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        desDialog.show();
    }


    public static void showGalleryFromFragment(final Activity activity, final Fragment fragment) {

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View dialogView = inflater.inflate(R.layout.gallery, null);
        builder.setView(dialogView);

        dialogView.findViewById(R.id.gallery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK, EXTERNAL_CONTENT_URI);
                fragment.startActivityForResult(i, GALLERY_REQUEST);
                desDialog.cancel();
            }
        });

        desDialog = builder.create();
        desDialog.show();

    }

//    public static String convertUrlToBase64(final String image_url) {
//        String path = "";
//        try {
//            Bitmap bitmap = new AsyncTask<Void, Void, Bitmap>() {
//                @Override
//                protected Bitmap doInBackground(Void... params) {
//                    try {
//                        return Picasso.get().load(image_url)
//                                .get();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    return null;
//                }
//            }.execute().get();
//
//            bitmap = Bitmap.createScaledBitmap(bitmap, 350, 350, true);
//            path = Camera.convert(bitmap);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//        return path;
//    }



    public static String convert(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        return Base64.encodeToString(outputStream.toByteArray(), Base64.NO_WRAP).replace("\n\t","");
    }

    @SuppressWarnings("deprecation")
    public static String getPath(Activity activity, Uri selectedImaeUri) {
        String[] projection = {MediaStore.Images.Media.DATA};

        Cursor cursor = activity.managedQuery(selectedImaeUri, projection, null, null,
                null);

        if (cursor != null) {
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

            return cursor.getString(columnIndex);
        }

        return selectedImaeUri.getPath();
    }

    public static void captureImage(Activity activity, Fragment fragment) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(activity.getPackageManager()) != null) {
            ContentValues values = new ContentValues(1);
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");
//            fileUri = activity.getContentResolver().insert(EXTERNAL_CONTENT_URI, values);
//
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            fragment.startActivityForResult(intent, CAMERA_REQUEST);

        } else {
            Toast.makeText(activity, "error_no_camera", Toast.LENGTH_SHORT).show();
        }
    }

    public static void captureImage(Activity activity) {
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (intent.resolveActivity(activity.getPackageManager()) != null) {
//            ContentValues values = new ContentValues(1);
//            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");
//            try {
//                fileUri = activity.getContentResolver().insert(EXTERNAL_CONTENT_URI, values);
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
//                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//                activity.startActivityForResult(intent, CAMERA_REQUEST);
//            }catch (Exception e){
//            e.printStackTrace();
//        }
//        } else {
//            Toast.makeText(activity, "error_no_camera", Toast.LENGTH_SHORT).show();
//        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(activity.getPackageManager()) != null) {
//            ContentValues values = new ContentValues(1);
//            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");
//            fileUri = activity.getContentResolver().insert(EXTERNAL_CONTENT_URI, values);

//            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
             activity.startActivityForResult(intent, CAMERA_REQUEST);

        } else {
            Toast.makeText(activity, "error_no_camera", Toast.LENGTH_SHORT).show();
        }
    }

    public static Bitmap decodeUri(Activity activity, Uri selectedImage) throws FileNotFoundException {
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(activity.getContentResolver()
                .openInputStream(selectedImage), null, o);
        final int REQUIRED_SIZE = 72;
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        Bitmap bitmap = BitmapFactory.decodeStream(activity.getContentResolver()
                .openInputStream(selectedImage), null, o2);
        return bitmap;
    }

}
