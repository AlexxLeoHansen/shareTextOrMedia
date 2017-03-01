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

    private EditText sText; // //text box id:s_text
    private static int REQUEST_IMG_CODE = 1; //code for get intent result
    private Uri media;  // media result uri

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

        Intent iShareText = new Intent(Intent.ACTION_SEND); //Intent calling action ACTION_SEND to send text
        String textSend = sText.getText().toString();

        if(textSend.isEmpty()){         //texbox empty, set default text
            textSend = "Default text!";
        }

        iShareText.setType("text/plain");   //MIME type, helps intent to know what it'll receive
        iShareText.putExtra(Intent.EXTRA_TEXT,textSend); //attach text to the intent

        startActivity(iShareText); //start send text intent
    }

    protected void getMedia(View v){

        Intent iGetMedia = new Intent(); //another way to create Intents

        iGetMedia.setAction(Intent.ACTION_GET_CONTENT); //set the Action GET CONTENT to access to the storage/gallery
        iGetMedia.setType("image/* video/* audio/*"); //MIME type, image, video or audio

        startActivityForResult(iGetMedia,REQUEST_IMG_CODE); //start get media intent with the request code
    }

    protected void shareMedia(){

        Intent iShareMedia = new Intent(Intent.ACTION_SEND); //same above

        iShareMedia.setType("image/* video/* audio/*"); //same above
        iShareMedia.putExtra(Intent.EXTRA_STREAM,media); //set media URI returned by onActivityResult()

        startActivity(iShareMedia);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent iGotMedia) {
        super.onActivityResult(requestCode, resultCode, iGotMedia);

            if(requestCode == REQUEST_IMG_CODE){    //checking if the request code is from getMedia()
                if(resultCode==RESULT_OK){          //checking if result is ok
                    media = iGotMedia.getData();
                    shareMedia();
                }

                else if (resultCode==RESULT_CANCELED){
                    Toast.makeText(this, "Getting media failed", Toast.LENGTH_SHORT).show();
                }
            }
    }
}
