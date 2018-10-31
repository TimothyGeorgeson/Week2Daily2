package com.example.user.week2daily2;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;

    private EditText etToFile;
    private TextView tvFromFile;
    private EditText etDesc;
    private EditText etRecordID;
    private TextView tvDisplay;
    private ImageView ivImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etToFile = findViewById(R.id.etToFile);
        tvFromFile = findViewById(R.id.tvFromFile);
        etDesc = findViewById(R.id.etDesc);
        etRecordID = findViewById(R.id.etRecordID);
        tvDisplay = findViewById(R.id.tvDisplay);
        ivImage = findViewById(R.id.ivImage);
    }

    public void writeToFile(View view) {
        //I tried many times putting logic for FileIO in its own class, but the app always crashed
//        FileIO fileIO = new FileIO();
//        fileIO.write(etToFile.getText().toString());

        try {
            FileOutputStream outputStream = openFileOutput("myfile.txt", MODE_PRIVATE);
            OutputStreamWriter outputWriter = new OutputStreamWriter(outputStream);
            outputWriter.write(etToFile.getText().toString());
            outputWriter.close();

            Toast.makeText(getBaseContext(), "Wrote to File!", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getFromFile(View view) {
        //when using FileIO class, I tried saving to preferences file, or the same fileIO code here, and app crashes
//        FileIO fileIO = new FileIO();
//        String fromFileStr = fileIO.read();
//        tvFromFile.setText(fromFileStr);

        try {
            FileInputStream inputStream = openFileInput("myfile.txt");
            InputStreamReader inputReader = new InputStreamReader(inputStream);

            char[] inputBuffer = new char[100];
            String result = "";
            int charRead = inputReader.read(inputBuffer);

            while (charRead >0) {
                // convert to string
                String readstring=String.copyValueOf(inputBuffer,0,charRead);
                result +=readstring;
                charRead = inputReader.read(inputBuffer);
            }
            inputReader.close();
            tvFromFile.setText(result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertRecord(View view) {
        String desc = etDesc.getText().toString();
        Bitmap bm=((BitmapDrawable)ivImage.getDrawable()).getBitmap();
        byte[] imgBlob = getBytes(bm);

        PhotoDatabase photoDatabase = new PhotoDatabase(this);

        long rowId = photoDatabase.savePhoto(desc, imgBlob);
        Toast.makeText(this, "Record Added with ID: " + String.valueOf(rowId), Toast.LENGTH_SHORT).show();

    }

    public void displayPhoto(View view) {
        if (etRecordID.getText().toString().equals(""))
        {
            Toast.makeText(this, "Enter Record ID", Toast.LENGTH_SHORT).show();
        }
        else {
            PhotoDatabase photoDatabase = new PhotoDatabase(this);
            long recordID = Long.parseLong(etRecordID.getText().toString());
            String result = "";
            byte[] image = null;
            Cursor cursor = photoDatabase.getPhoto(recordID);
            if(cursor.getCount() != 0) {

                while (cursor.moveToNext()) {
                    result += "Id :"+ cursor.getString(0)+" ";
                    result += "Desc :"+ cursor.getString(1)+" ";
                    //result += "Photo :"+ cursor.getString(2)+" ";
                    image = cursor.getBlob(2);
                }
            }
            tvDisplay.setText(result);

            try {
                Bitmap imgResult = BitmapFactory.decodeByteArray(image, 0, image.length);
                ivImage.setImageBitmap(imgResult);
            }
            catch (Exception e){

            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ivImage.setImageBitmap(imageBitmap);
        }
    }

    public void takePicture(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }
}
