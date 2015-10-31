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

import com.koushikdutta.ion.Ion;
import com.navdrawer.SimpleSideDrawer;

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

    long lastclic = 0;
    int lastId = 0;

    String imageString;
    Button addMLG;
    Button annuler;
    Button save;
    Button doge;
    Button shrek;
    Button snoop;
    ImageView tmp;
    RelativeLayout menu;
    SimpleSideDrawer slide;
    HorizontalScrollView horizontal;
    SeekBar taille;
    SeekBar rotation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        this.setContentView(R.layout.editor);
        slide = new SimpleSideDrawer(this);
        slide.setLeftBehindContentView(R.layout.dankmemelist);

        Intent intent = getIntent();
        imageString = intent.getStringExtra("image");

        // Bouton IHM
        addMLG = (Button) findViewById(R.id.b_addmontage);
        annuler = (Button) findViewById(R.id.b_annuler);
        save = (Button) findViewById(R.id.b_savemontage);

        // Bouton MLG
        doge = (Button) findViewById(R.id.b_doge);
        shrek = (Button) findViewById(R.id.b_shrek);
        snoop = (Button) findViewById(R.id.b_snoop);


        taille = (SeekBar) findViewById(R.id.taille);
        rotation = (SeekBar) findViewById(R.id.rotation);


        ImageView imgView = (ImageView) findViewById(R.id.imgView);
        imgView.setImageBitmap(BitmapFactory.decodeFile(imageString));


        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taille.setVisibility(View.INVISIBLE);
                rotation.setVisibility(View.INVISIBLE);
                if (!slide.isClosed()) {
                    slide.close();
                }
            }


        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "A faire ... zZz", Toast.LENGTH_SHORT).show();
            }


        });


        taille.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                tmp = (ImageView) findViewById(dernierIdImage);
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
                cacherMenu();
                slide.toggleDrawer();
            }


        });

        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                afficherMenu();
                taille.setVisibility(View.INVISIBLE);
                rotation.setVisibility(View.INVISIBLE);
                slide.toggleDrawer();
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

    void cacherMenu() {
        addMLG.setVisibility(View.INVISIBLE);
        save.setVisibility(View.INVISIBLE);
    }

    void afficherMenu() {
        addMLG.setVisibility(View.VISIBLE);
        save.setVisibility(View.VISIBLE);
        addMLG.bringToFront();
        save.bringToFront();
    }

    void imageTouchee(int id) {

        if (id == R.drawable.doge) idTableau = 0;
        else if (id == R.drawable.shrek) idTableau = 1;
        else if (id == R.drawable.snoop) idTableau = 2;

        slide.toggleDrawer();

        final ImageView iv = new ImageView(getApplicationContext());
        iv.setImageResource(id);
        iv.setClickable(true);
        iv.setId(View.generateViewId());

        Ion.with(iv).load("android.resource://betcheg.mlgphotomontage/" + id);

        RelativeLayout rl = (RelativeLayout) findViewById(R.id.rl);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        rl.addView(iv, lp);

        afficherMenu();

        iv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {

                int currentX = (int) arg1.getRawX();
                int currentY = (int) arg1.getRawY();

                if (arg1.getAction() == MotionEvent.ACTION_MOVE) {

                    iv.setX(currentX - iv.getWidth() / 2);
                    iv.setY(currentY - iv.getHeight() / 2);
                    rotation.bringToFront();
                    taille.bringToFront();
                    lastclic = 0;

                } else if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
                    dernierIdImage = iv.getId();
                    taille.setVisibility(View.VISIBLE);
                    rotation.setVisibility(View.VISIBLE);
                    cacherMenu();
                    iv.bringToFront();

                    if (lastId == iv.getId() && System.currentTimeMillis() - lastclic < 500) {
                        iv.setVisibility(View.GONE);
                    }

                    lastclic = System.currentTimeMillis();


                } else if (arg1.getAction() == MotionEvent.ACTION_UP) {
                    afficherMenu();
                }

                lastId = iv.getId();

                return true;
            }
        });
    }
}
