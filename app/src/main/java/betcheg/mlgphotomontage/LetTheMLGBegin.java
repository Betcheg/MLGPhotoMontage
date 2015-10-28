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
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

/**
 * Created by bastien on 28/10/15.
 */
public class LetTheMLGBegin extends ActionBarActivity {

String imageString;
Button addMLG;
Button annuler;
HorizontalScrollView horizontal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        this.setContentView(R.layout.editor);

        Intent intent = getIntent();
        imageString = intent.getStringExtra("image");

        addMLG= (Button) findViewById(R.id.b_addmontage);
        annuler= (Button) findViewById(R.id.b_annuler);
        horizontal= (HorizontalScrollView) findViewById(R.id.horizontal);
        ImageView imgView = (ImageView) findViewById(R.id.imgView);
        imgView.setImageBitmap(BitmapFactory.decodeFile(imageString));



        addMLG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMLG.setVisibility(View.GONE);
                horizontal.setVisibility(View.VISIBLE);
            }


        });

        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMLG.setVisibility(View.VISIBLE);
                horizontal.setVisibility(View.GONE);
            }


        });

    }
}