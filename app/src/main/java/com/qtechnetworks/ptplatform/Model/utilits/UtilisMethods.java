package com.qtechnetworks.ptplatform.Model.utilits;


import static com.qtechnetworks.ptplatform.Model.utilits.AppConstants.Trace;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.qtechnetworks.ptplatform.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.regex.Pattern;


public class UtilisMethods {


    public static String cleanString(String url) {
        url = url.replace("[", "");
        url = url.replace("]", "");

        return url;
    }

    public static String decode(String url) {
        return url.replace("&amp;", "&");
    }

    // Copy an InputStream to a File.
//
    public static File copyInputStreamToFile(InputStream in, File file) {
        OutputStream out = null;

        try {
            out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Ensure that the InputStreams are closed even if there's an exception.
            try {
                if (out != null) {
                    out.close();
                }

                // If you want to close the "in" InputStream yourself then remove this
                // from here but ensure that you close it yourself eventually.
                in.close();

                return file;
            } catch (IOException e) {
                e.printStackTrace();
                return file;
            }
        }
    }


    private static final int BUFFER_SIZE = 1024 * 2;

    private static final String IMAGE_DIRECTORY = "/imageDarApp";

    public static ByteArrayOutputStream copy(InputStream input) {
        try {

            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[BUFFER_SIZE];
            int nRead;
            byte[] data = new byte[16384];

            while ((nRead = input.read(data, 0, data.length)) != -1) {
                output.write(data, 0, nRead);
            }

            return output;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String saveImage(Activity activity, InputStream myBitmap) {
        Trace("saveImage", "1");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        Trace("saveImage", "2");

        Bitmap bmp = BitmapFactory.decodeStream(myBitmap);

        bmp.compress(Bitmap.CompressFormat.JPEG, 90, bytes);


//        bytes = copy(myBitmap);

        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        Trace("saveImage", "3");

        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            Trace("saveImage", "4");

            wallpaperDirectory.mkdirs();
        }
        Trace("saveImage", "5");

        try {
            Trace("saveImage", "6");

            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(activity,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());
            Trace("saveImage", "7");

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    public static boolean checkEditTextIfEmpty(EditText editText) {

        if (!editText.getText().toString().equalsIgnoreCase("")) {
            return true;
        }

        return false;
    }

    public static String returnHour(Calendar calendar) {
        try {
            Date now = calendar.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("K:mm a");
            String formattedTime = sdf.format(now);
            return formattedTime;
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    public static void fillSpinnerData(Context activity, String[] data, Spinner spinner) {

        String[] modifiedData = new String[data.length];

        for (int i = 0; i < data.length; i++) {
            modifiedData[i] = captilizeFirstLetter(data[i]);
        }//end for.

        ArrayAdapter<String> gameKindArray = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item, modifiedData);


        gameKindArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(gameKindArray);
    }

    public static void fillSpinnerData(Context activity, ArrayList<String> data, Spinner spinner) {

        String[] modifiedData = new String[data.size()];

        for (int i = 0; i < data.size(); i++) {
            modifiedData[i] = captilizeFirstLetter(data.get(i).toString());
        }//end for.

        ArrayAdapter<String> gameKindArray = new ArrayAdapter<>(activity, R.layout.spinner_selected_item, modifiedData);

        gameKindArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(gameKindArray);
    }

    static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");

    public static long getTimeFromDate(String date) {
        try {
            Date mDate = sdf.parse(date);
            long timeInMilliseconds = mDate.getTime();

            Trace("Time", "" + timeInMilliseconds);
            return timeInMilliseconds;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String captilizeFirstLetter(String lowerCase) {

        char subString = lowerCase.toUpperCase().charAt(0);


        String string = subString + "" + lowerCase.substring(1);


        return string;
    }

    public static boolean checkMobilePattren(String phone) {
        if (Pattern.matches("^0\\d{9}$", phone)) {
            return true;
        }
        return false;

    }

    public static void ShowAlertDialog(Activity context, String msg) {
        try {

            final AlertDialog alertDialog = new AlertDialog.Builder(context).create(); //Read Update
            alertDialog.setTitle("");
            alertDialog.setMessage(msg);

            alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    alertDialog.dismiss();
//                context.finish();
                }
            });

            alertDialog.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void ShowAlertDialog(final Context context, String msg, final Intent intent) {
        try {
            final AlertDialog alertDialog = new AlertDialog.Builder(context).create(); //Read Update

            alertDialog.setTitle("");
            alertDialog.setMessage(msg);

            alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    alertDialog.dismiss();
                    context.startActivity(intent);
//                context.finish();
                }
            });

            alertDialog.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void openGoogleMapLocation(Context context, String lattitude, String longitude) {

        String geoUri = "http://maps.google.com/maps?q=loc:" + lattitude + "," + longitude;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
        context.startActivity(intent);
    }

    public static void printParams(Map<String, String> map) {

        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            // do stuff
            Log.d("Key ", key);
            Log.d("value ", value);
        }
    }


    public static void Logout(Activity activity, Intent intent) {
        PreferencesUtils.putString(PrefKeys.userID, "-1");
        PreferencesUtils.putString(PrefKeys.userEmail, "-1");
        PreferencesUtils.putString(PrefKeys.username, "-1");
        PreferencesUtils.putString(PrefKeys.userPhone, "-1");
        activity.startActivity(intent);
//        LoginManager.getInstance().logOut();

    }

    public static void callPhone(Activity activity, String phone) {
        Trace("callPhone:", "" + phone);

        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        activity.startActivity(intent);
    }

    public static boolean compareTimeClassBeforeNow(String time) {
        try {
            Trace("compareTimeClassBeforeNow:", "" + time);

            SimpleDateFormat formatDate = new SimpleDateFormat("MM/dd/yyyy hh:mm");
            String strDateFormat = "hh:mm a";
//            formatDate.format(time);
            Trace("compareTimeClassBeforeNow" + time, "" + formatDate.parse(time));


            Date classTime = formatDate.parse(time);
            SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
            Trace("compareTimeClassBeforeNow" + time, "" + sdf.format(classTime));

            Calendar calendar1 = Calendar.getInstance();
//            SimpleDateFormat formatter1 = new SimpleDateFormat("MM/dd/yyyy HH:mm");
//            String currentDate = formatDate.parse(calendar1));

            if (calendar1.getTime().after(classTime)) {
                Trace("compareTimeClassBeforeNow", "True");


                return true;
            } else {
                Trace("compareTimeClassBeforeNow", "False");

                return false;
            }
//            return sdf.format(classTime);

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static String getTimeFormated(String time) {
        try {
            Trace("Time Before Formated:", "" + time);

            SimpleDateFormat formatDate = new SimpleDateFormat("MM/dd/yyyy HH:mm");
            String strDateFormat = "hh:mm a";
//            formatDate.format(time);
            Trace("Time Formated" + time, "" + formatDate.parse(time));


            Date classTime = formatDate.parse(time);
            SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
            Trace("Time Formated" + time, "" + sdf.format(classTime));

            return sdf.format(classTime);

        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }

    }

    public static String getTimeFormatedDays(String time) {
        try {
            Trace("Time Before Formated:", "" + time);

            SimpleDateFormat formatDate = new SimpleDateFormat("MM/dd/yyyy hh:mm");
            String strDateFormat = "MM/dd/yyyy";
//            formatDate.format(time);
            Trace("Time Formated" + time, "" + formatDate.parse(time));


            Date classTime = formatDate.parse(time);
            SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
            Trace("Time Formated" + time, "" + sdf.format(classTime));

            return sdf.format(classTime);

        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    public static String doubleFormat(double num){
        DecimalFormat df = new DecimalFormat("0.0");

        return df.format(num);
    }

    public static String getTimeChat(String time) {
        try {
            Trace("Time Before Formated:", "" + time);

            Date initDate = new SimpleDateFormat("MM/dd/yyyy").parse(time);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

//            formatDate.format(time);
            String parsedDate = formatter.format(initDate);

            Trace("Time Formated" + time, "" + parsedDate);

            return parsedDate;

        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }


/*    public static final String getLangauge() {

        String savedLang = PreferencesUtils.getString(AppConstants.LANG, "");
        String CurrentLang = Locale.getDefault().getLanguage();
        String langToSend = "english";

        if (savedLang.equalsIgnoreCase("")) {
            savedLang = CurrentLang;
            if (CurrentLang.equalsIgnoreCase("ar")) {
                langToSend = "arabic";
            } else {
                langToSend = "english";
            }
        }
        if (savedLang.equalsIgnoreCase("en")) {
            langToSend = "english";
        }
        if (savedLang.equalsIgnoreCase("ar")) {
            langToSend = "arabic";
        }
        Log.d("Lang", savedLang);

        return langToSend;
    }*/
}
