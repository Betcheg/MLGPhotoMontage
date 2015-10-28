package betcheg.mlgphotomontage;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by bastien on 28/10/15.
 */
public class LetTheMLGBegin extends ActionBarActivity {

String imageString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        this.setContentView(R.layout.editor);

        Intent intent = getIntent();
        imageString = intent.getStringExtra("image");

        ImageView imgView = (ImageView) findViewById(R.id.imgView);
        // Set the Image in ImageView after decoding the String
        imgView.setImageBitmap(BitmapFactory.decodeFile(imageString));

    }
}
