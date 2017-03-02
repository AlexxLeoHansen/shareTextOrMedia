package com.example.jos.sharetextormedia;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private static int REQUEST_IMG_CODE = 1; //code for get intent result
    private Uri media;  // media result uri

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    protected void shareText(View view) {

        EditText sText; // //text box id:s_text
        sText = (EditText) findViewById(R.id.s_text); //get the button

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

    protected void preview(){

        InputStream in;
        Bitmap b;
        ImageView iv = (ImageView) findViewById(R.id.imageView);

        try{
            //Two ways of visualize an img:
            //Get the content resolver and open the image in an input stream
            in = getContentResolver().openInputStream(media);
            //Decode stream with BitmapFactory to create a Bitmap object
            b = BitmapFactory.decodeStream(in);
            //Set the Bitmap in the ImageView
            iv.setImageBitmap(b);

            // or just use: iv.setImageURI(media); and set the URI

        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    protected void shareMedia(View v){

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
                    preview();
                }

                else if (resultCode==RESULT_CANCELED){
                    Toast.makeText(this, "Getting media failed", Toast.LENGTH_SHORT).show();
                }
            }
    }
}
