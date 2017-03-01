package com.example.jos.sharetextormedia;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private EditText sText;
    private static int REQUEST_IMG_CODE = 1;
    private Uri media;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sText = (EditText) findViewById(R.id.s_text);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    protected void shareText(View view) {

        Intent iShareText = new Intent(Intent.ACTION_SEND);
        String textSend = sText.getText().toString();

        if(textSend.isEmpty()){
            textSend = "Default text!";
        }

        iShareText.setType("text/plain");
        iShareText.putExtra(Intent.EXTRA_TEXT,textSend);

        startActivity(iShareText);
    }

    protected void getMedia(View v){

        Intent iGetMedia = new Intent();

        iGetMedia.setAction(Intent.ACTION_GET_CONTENT);
        iGetMedia.setType("image/* video/* audio/*");

        startActivityForResult(iGetMedia,REQUEST_IMG_CODE);
    }

    protected void shareMedia(){

        Intent iShareMedia = new Intent(Intent.ACTION_SEND);

        iShareMedia.setType("image/* video/* audio/*");
        iShareMedia.putExtra(Intent.EXTRA_STREAM,media);

        startActivity(iShareMedia);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent iGotMedia) {
        super.onActivityResult(requestCode, resultCode, iGotMedia);

            if(requestCode == REQUEST_IMG_CODE){
                if(resultCode==RESULT_OK){
                    media = iGotMedia.getData();
                    shareMedia();
                }
            }
    }
}
