package com.example.txtjson;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;

import com.example.txtjson.questions.Advice;

import com.example.txtjson.questions.Content;
import com.example.txtjson.questions.Data;
import com.example.txtjson.questions.DataBody;
import com.example.txtjson.questions.FileStruct;
import com.example.txtjson.questions.Options;
import com.example.txtjson.questions.Text;
import com.example.txtjson.telovadba.Description;
import com.example.txtjson.telovadba.Photo;
import com.example.txtjson.telovadba.excerciseEnduranceWeeklyPlan;
import com.example.txtjson.telovadba.excerciseResistanceWeeklyPlan;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView1);
    }
    ActivityResultLauncher<Intent> sActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        String checkData = "";
                        Gson gson = new GsonBuilder().create();
                        String json;
                        Intent data = result.getData();
                        Uri uri = data.getData();
                        String fileName = getFileName(uri, getApplicationContext());
                        String[] split = fileName.split("-");
                        byte[] bytes = getBytesFromUri(getApplicationContext(), uri);
                        if(split.length>2) {
                            String file = split[0] ;
                            String type = split[1];
                            String lang = split[2];
                            String vprEdu;
                            String advEdu;
                            String vprBeh;
                            String advBeh;

                            if (type.equals("questions")) {
                                if(file.equals("education")) {
                                    vprEdu = new String(bytes);
                                    File files = new File("/storage/emulated/0/Download/" + split[0] + "-advice-" + lang);
                                    advEdu = readFile(files);
                                    files = new File("/storage/emulated/0/Download/behaviour-advice-" + lang);
                                    advBeh = readFile(files);
                                    files = new File("/storage/emulated/0/Download/behaviour-questions-" + lang);
                                    vprBeh = readFile(files);
                                }
                                else{
                                    vprBeh = new String(bytes);
                                    File files = new File("/storage/emulated/0/Download/education-advice-" + lang);
                                    advEdu = readFile(files);
                                    files = new File("/storage/emulated/0/Download/behaviour-advice-" + lang);
                                    advBeh = readFile(files);
                                    files = new File("/storage/emulated/0/Download/education-questions-" + lang);
                                    vprEdu = readFile(files);
                                }
                            } else {
                                if(file.equals("behaviour")) {
                                    advBeh = new String(bytes);
                                    File files = new File("/storage/emulated/0/Download/education-questions-" + lang);
                                    vprEdu = readFile(files);
                                    files = new File("/storage/emulated/0/Download/education-advice-" + lang);
                                    advEdu = readFile(files);
                                    files = new File("/storage/emulated/0/Download/behaviour-questions-" + lang);
                                    vprBeh = readFile(files);
                                }
                                else{
                                    advEdu = new String(bytes);
                                    System.out.println("/storage/emulated/0/Download/" + split[0] + "-advice-" + lang);
                                    File files = new File("/storage/emulated/0/Download/behaviour-advice-" + lang);
                                    advBeh = readFile(files);
                                    files = new File("/storage/emulated/0/Download/behaviour-questions-" + lang);
                                    vprBeh = readFile(files);
                                    files = new File("/storage/emulated/0/Download/education-questions-" + lang);
                                    vprEdu = readFile(files);
                                }
                            }
                            Scanner vprasanja = new Scanner(vprEdu);
                            Scanner nasveti = new Scanner(advEdu);
                            Scanner vprasanjaBeh = new Scanner(vprBeh);
                            Scanner nasvetiBeh = new Scanner(advBeh);
                            ArrayList<DataBody> dataBodies = new ArrayList<>();
                            while (vprasanja.hasNext() || nasveti.hasNext() || vprasanjaBeh.hasNext() || nasvetiBeh.hasNext()) {
                                if (vprasanja.hasNext()) {
                                    String vprasanjeEdu = vprasanja.nextLine();
                                    String nasvetEdu = nasveti.nextLine();
                                    Content content1;
                                    ArrayList<Options> opts = new ArrayList<>();
                                    String[] contvprEdu = vprasanjeEdu.split(";");
                                    String[] contadvEdu = nasvetEdu.split(";");
                                    String idEdu = contvprEdu[0];
                                    String tipEdu = contvprEdu[1];
                                    Advice advice;
                                    switch (lang) {
                                        case "en":
                                            advice = new Advice(contadvEdu[2], null, null, null);
                                            break;
                                        case "ita":
                                            advice = new Advice(null, contadvEdu[2], null, null);
                                            break;
                                        case "bel":
                                            advice = new Advice(null, null, contadvEdu[2], null);
                                            break;
                                        case "slo":
                                            advice = new Advice(null, null, null, contadvEdu[2]);
                                            break;
                                        default:
                                            throw new IllegalStateException("Unexpected value: " + lang);
                                    }
                                    String answer = contadvEdu[1];
                                    for (int j = 3; j < contvprEdu.length; j++) {
                                        Text text;
                                        switch (lang) {
                                            case "en":
                                                text = new Text(contvprEdu[j], null, null, null);
                                                break;
                                            case "ita":
                                                text = new Text(null, contvprEdu[j], null, null);
                                                break;
                                            case "bel":
                                                text = new Text(null, null, contvprEdu[j], null);
                                                break;
                                            case "slo":
                                                text = new Text(null, null, null, contvprEdu[j]);
                                                break;
                                            default:
                                                throw new IllegalStateException("Unexpected value: " + lang);
                                        }
                                        String ids = String.valueOf(j - 3);
                                        opts.add(new Options(ids, text));

                                    }
                                    switch (lang) {
                                        case "en":
                                            content1 = new Content(contvprEdu[2], null, null, null);
                                            break;
                                        case "ita":
                                            content1 = new Content(null, contvprEdu[2], null, null);
                                            break;
                                        case "bel":
                                            content1 = new Content(null, null, contvprEdu[2], null);
                                            break;
                                        case "slo":
                                            content1 = new Content(null, null, null, contvprEdu[2]);
                                            break;
                                        default:
                                            throw new IllegalStateException("Unexpected value: " + lang);
                                    }
                                    DataBody data1 = new DataBody(idEdu, tipEdu, content1, opts, advice, answer);
                                    dataBodies.add(data1);
                                    System.out.println(idEdu);
                                }


                            if (vprasanjaBeh.hasNext()) {
                                String vprasanjeBeh = vprasanjaBeh.nextLine();
                                String nasvetBeh = nasvetiBeh.nextLine();
                                Content content1;
                                ArrayList<Options> opts = new ArrayList<>();
                                String[] contvprBeh = vprasanjeBeh.split(";");
                                String[] contadvBeh = nasvetBeh.split(";");
                                String idBeh = contvprBeh[0];
                                String tipBeh = contvprBeh[1];
                                Advice advice;
                                switch (lang) {
                                    case "en":
                                        advice = new Advice(contadvBeh[2], null, null, null);
                                        break;
                                    case "ita":
                                        advice = new Advice(null, contadvBeh[2], null, null);
                                        break;
                                    case "bel":
                                        advice = new Advice(null, null, contadvBeh[2], null);
                                        break;
                                    case "slo":
                                        advice = new Advice(null, null, null, contadvBeh[2]);
                                        break;
                                    default:
                                        throw new IllegalStateException("Unexpected value: " + lang);
                                }
                                String answer = contadvBeh[1];
                                for (int j = 3; j < contvprBeh.length; j++) {
                                    Text text;
                                    switch (lang) {
                                        case "en":
                                            text = new Text(contvprBeh[j], null, null, null);
                                            break;
                                        case "ita":
                                            text = new Text(null, contvprBeh[j], null, null);
                                            break;
                                        case "bel":
                                            text = new Text(null, null, contvprBeh[j], null);
                                            break;
                                        case "slo":
                                            text = new Text(null, null, null, contvprBeh[j]);
                                            break;
                                        default:
                                            throw new IllegalStateException("Unexpected value: " + lang);
                                    }
                                    String ids = String.valueOf(j - 3);
                                    opts.add(new Options(ids, text));

                                }
                                switch (lang) {
                                    case "en":
                                        content1 = new Content(contvprBeh[2], null, null, null);
                                        break;
                                    case "ita":
                                        content1 = new Content(null, contvprBeh[2], null, null);
                                        break;
                                    case "bel":
                                        content1 = new Content(null, null, contvprBeh[2], null);
                                        break;
                                    case "slo":
                                        content1 = new Content(null, null, null, contvprBeh[2]);
                                        break;
                                    default:
                                        throw new IllegalStateException("Unexpected value: " + lang);
                                }
                                DataBody data1 = new DataBody(idBeh, tipBeh, content1, opts, advice, answer);
                                dataBodies.add(data1);
                                System.out.println(idBeh);
                            }

                            }
                            Data podatki = new Data("Behaviour list class", dataBodies);
                            FileStruct struktura = new FileStruct("0", "Voeding", podatki);
                            json = gson.toJson(struktura);
                            writeToFile("QuestionnaireNutrition.json", json);
                            System.out.println("done");
                        }
                        else{
                            String level = split[1];
                            String resistance = "";
                            String endurance = "";
                            if(split[0].equals("resistance")){
                                resistance=new String(bytes);
                                File files = new File("/storage/emulated/0/Download/endurance-"+level);
                                endurance = readFile(files);
                            }
                            else{
                                endurance=new String(bytes);
                                File files = new File("/storage/emulated/0/Download/resistance-"+level);
                                resistance = readFile(files);
                            }
                            Scanner res = new Scanner(resistance);
                            Scanner end = new Scanner(endurance);
                            ArrayList<excerciseEnduranceWeeklyPlan>enduranceList=new ArrayList<>();
                            ArrayList<excerciseResistanceWeeklyPlan>resistanceList=new ArrayList<>();
                            while(res.hasNext()|| end.hasNext()){
                                if(end.hasNext()){
                                    String[] endLine=end.nextLine().split(";");
                                    String idEnd = endLine[0];
                                    String endType = endLine[1];
                                    Description descEnd = new Description( endLine[3], endLine[4], endLine[5]);
                                    excerciseEnduranceWeeklyPlan endplan = new excerciseEnduranceWeeklyPlan(idEnd,endType,descEnd);
                                    enduranceList.add(endplan);
                                }
                                if(res.hasNext()) {
                                    String[] resLine = res.nextLine().split(";");
                                    String idRes = resLine[0];
                                    String resType = resLine[1];
                                    Description descRes = new Description(resLine[3], resLine[4], resLine[5]);
                                    ArrayList<Photo> listOfPhotos = new ArrayList<>();
                                    for (int i = 6; i < resLine.length; i++) {
                                        if (!resLine[i].equals("null")) {
                                            Photo photo = new Photo(resLine[i]);
                                            listOfPhotos.add(photo);
                                        }
                                    }
                                    excerciseResistanceWeeklyPlan resplan = new excerciseResistanceWeeklyPlan(idRes, resType, descRes, listOfPhotos);
                                    resistanceList.add(resplan);
                                }

                            }
                            com.example.txtjson.telovadba.DataBody data_body = new com.example.txtjson.telovadba.DataBody(enduranceList,resistanceList);
                            com.example.txtjson.telovadba.Data podatki = new com.example.txtjson.telovadba.Data("WeeklyPlan class",data_body);
                            com.example.txtjson.telovadba.FileStruct struktura = new com.example.txtjson.telovadba.FileStruct("0", "Wijziging weekplan", podatki);
                            json = gson.toJson(struktura);
                            String filename ="";
                            if(split[1].equals("low")){
                                filename="WeeklyPlanPhysicalLow.json";
                            }
                            else{
                                filename="WeeklyPlanPhysicalNormal.json";
                            }
                            writeToFile(filename, json);
                        }


                        textView.setText("hello");
                    }

                }

            });

    public void openFileDialog(View view){
        Intent data = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        data.setType("*/*");
        data= Intent.createChooser(data, "Choose a file");
        sActivityResultLauncher.launch(data);
    }
    public void writeToFile(String fileName, String content){
        File path = getApplicationContext().getFilesDir();
        System.out.println(path.getAbsolutePath());
        try {
            FileOutputStream writer = new FileOutputStream(new File("/storage/emulated/0/Documents/", fileName));
            writer.write(content.getBytes(StandardCharsets.UTF_8));
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    byte[] getBytesFromUri(Context context, Uri uri){
        InputStream iStream=null;
        try {
            iStream = context.getContentResolver().openInputStream(uri);
            ByteArrayOutputStream byteBuffer= new ByteArrayOutputStream();
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int len = 0;
            while((len = iStream.read(buffer))!=-1){
                byteBuffer.write(buffer,0,len);
            }
            return byteBuffer.toByteArray();
        }
        catch (Exception e){
        }
        return null;
    }
    String getFileName(Uri uri, Context context){
        String res = null;
        if(uri.getScheme().equals("content")){
            Cursor cursor = context.getContentResolver().query(uri,null,null,null,null);
            try {
                if(cursor!= null && cursor.moveToFirst()){
                    res = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            }
            finally {
                cursor.close();
            }
            if (res == null){
                res = uri.getPath();
                int cutt = res.lastIndexOf('/');
                if (cutt!=-1){
                    res=res.substring(cutt +1);
                }
            }
        }
        return res;
    }
    private String readFile(File file)
    {
        String myData = "";
        try {
            FileInputStream fis = new FileInputStream(file);
            DataInputStream in = new DataInputStream(fis);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            String strLine;
            while ((strLine = br.readLine()) != null) {
                myData = myData + strLine + "\n";
            }
            br.close();
            in.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myData;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!Utils.isPermissionGranted(this)){
            new AlertDialog.Builder(this).setTitle("All files permissions").setMessage("no permission").setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    takePermission();
                }
            }).setNegativeButton("Deny", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            }).setIcon(android.R.drawable.ic_dialog_alert).show();
        }
    }
    private  void takePermission(){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.R) {
            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.addCategory("android.intent.category.DEFAULT");
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivityForResult(intent, 101);
            } catch (Exception e) {
                e.printStackTrace();
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                startActivityForResult(intent, 101);
            }
        }
        else{
            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},101
        );
            }
        }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length>0){
            if (requestCode==101){
                boolean readExt = grantResults[0]== PackageManager.PERMISSION_GRANTED;
                if (!readExt){
                    takePermission();
                }
            }
        }
    }
}
