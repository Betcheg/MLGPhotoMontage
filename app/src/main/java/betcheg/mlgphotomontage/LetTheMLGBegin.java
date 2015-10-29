package betcheg.mlgphotomontage;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

/**
 * Created by bastien on 28/10/15.
 */
public class LetTheMLGBegin extends ActionBarActivity {

    // Original image size, has to be hardcoded :'(
    String[] tableauNom = {"Doge", "Shrek", "Snoop", "Joint", "8bitGlasses", "Fedora", "Sniper", "Hitmarker", "Illuminati", "Vuvuzela", "Frog"};
    int[] tableauLargeur = {225, 190, 290};
    int[] tableauHauteur = {255, 169, 292};

    int idTableau;
    int dernierIdImage = 0;

    String imageString;
    Button addMLG;
    Button annuler;
    Button doge;
    Button shrek;
    Button snoop;
    ImageView tmp;
    RelativeLayout menu;

    HorizontalScrollView horizontal;
    SeekBar taille;
    SeekBar rotation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        this.setContentView(R.layout.editor);

        Intent intent = getIntent();
        imageString = intent.getStringExtra("image");

        addMLG = (Button) findViewById(R.id.b_addmontage);
        annuler = (Button) findViewById(R.id.b_annuler);
        doge = (Button) findViewById(R.id.b_doge);
        shrek = (Button) findViewById(R.id.b_shrek);
        snoop = (Button) findViewById(R.id.b_snoop);

        taille = (SeekBar) findViewById(R.id.taille);
        rotation = (SeekBar) findViewById(R.id.rotation);
        menu = (RelativeLayout) findViewById(R.id.menu);
        horizontal = (HorizontalScrollView) findViewById(R.id.horizontal);

        ImageView imgView = (ImageView) findViewById(R.id.imgView);
        imgView.setImageBitmap(BitmapFactory.decodeFile(imageString));


        taille.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                tmp = (ImageView) findViewById(dernierIdImage);
                //LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((lDoge*progresValue)+30,(hDoge*progresValue)+30);
                //tmp.setLayoutParams(layoutParams);
                tmp.requestLayout();
                tmp.getLayoutParams().height = ((2 * tableauHauteur[idTableau] * progresValue) / 100) + 30;
                tmp.getLayoutParams().width = ((2 * tableauLargeur[idTableau] * progresValue) / 100) + 30;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }


        });

        rotation.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                tmp = (ImageView) findViewById(dernierIdImage);
                tmp.requestLayout();
                tmp.setRotation((int) ((progresValue * 3.6) - 180));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }


        });


        addMLG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.setVisibility(View.INVISIBLE);
                horizontal.setVisibility(View.VISIBLE);
            }


        });

        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.setVisibility(View.VISIBLE);
                taille.setVisibility(View.INVISIBLE);
                rotation.setVisibility(View.INVISIBLE);
                horizontal.setVisibility(View.INVISIBLE);
            }

        });

        shrek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageTouchee(R.drawable.shrek);
            }
        });


        doge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageTouchee(R.drawable.doge);
            }
        });


        snoop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageTouchee(R.drawable.snoop);
            }
        });

    }

    void imageTouchee(int id) {

        if( id == R.drawable.doge) idTableau = 0;
        else if( id == R.drawable.shrek) idTableau = 1;
        else if( id == R.drawable.snoop) idTableau = 2;

        menu.setVisibility(View.INVISIBLE);
        horizontal.setVisibility(View.VISIBLE);

        final ImageView iv = new ImageView(getApplicationContext());
        iv.setImageResource(id);
        iv.setClickable(true);
        iv.setId(View.generateViewId());

        RelativeLayout rl = (RelativeLayout) findViewById(R.id.rl);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        rl.addView(iv, lp);


        iv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {

                int currentX = (int) arg1.getRawX();
                int currentY = (int) arg1.getRawY();

                if (arg1.getAction() == MotionEvent.ACTION_MOVE) {

                    iv.setX(currentX - iv.getWidth() / 2);
                    iv.setY(currentY - iv.getHeight() / 2);

                } else if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
                    dernierIdImage = iv.getId();
                    taille.setVisibility(View.VISIBLE);
                    rotation.setVisibility(View.VISIBLE);
                    menu.setVisibility(View.INVISIBLE);
                    horizontal.setVisibility(View.VISIBLE);
                    iv.bringToFront();

                }
                return true;
            }
        });
    }
}
