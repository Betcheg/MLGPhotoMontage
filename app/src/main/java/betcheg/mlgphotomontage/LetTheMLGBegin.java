package betcheg.mlgphotomontage;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
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
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.ion.Ion;
import com.tjeannin.apprate.AppRate;

import com.navdrawer.SimpleSideDrawer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by bastien on 28/10/15.
 */
public class LetTheMLGBegin extends ActionBarActivity {

    // Original image size, has to be hardcoded :'(
    long[] tableauId = { R.drawable.doge, R.drawable.shrek, R.drawable.snoop,R.drawable.hitmarker, R.drawable.eightbit,
            R.drawable.thuglife, R.drawable.yolo, R.drawable.joint,R.drawable.cana, R.drawable.illuminatitriangle,R.drawable.fedora,
            R.drawable.thugcap, R.drawable.goldchain, R.drawable.patrick, R.drawable.getrekt, R.drawable.cash, R.drawable.notdoritochip,
            R.drawable.notmdcan, R.drawable.trollface, R.drawable.vuvuzela, R.drawable.bong,R.drawable.ak,R.drawable.snipe,
            R.drawable.dootdoottrumpet, R.drawable.skelet ,R.drawable.peperonni,R.drawable.lenny};

    int[] tableauLargeur = {225, 190, 290, 124, 200, 290, 327,240, 128, 127, 250, 280, 205, 159, 299, 300, 167, 108, 225,
            250, 115, 296, 290, 184, 207, 240, 383};
    int[] tableauHauteur = {255, 169, 292, 104, 41, 73, 106, 83  ,128, 117, 172, 209, 230, 240, 189, 300, 190, 200, 209,
            89, 280, 84, 87, 194, 279, 233, 140};

    int idTableau;
    int dernierIdImage = 0;

    long lastclic = 0;
    int lastId = 0;

    int nombreCourantImage = 0;
    String imageString;
    Button addMLG;
    Button save;
    TextView imagecreated;

    Button doge;
    Button shrek;
    Button snoop;
    Button hitmarker;
    Button cana;
    Button eightbit;
    Button thuglife;
    Button thugcap;
    Button goldchain;
    Button patrick;
    Button getrekt;
    Button illuminatitriangle;
    Button joint;
    Button cash;
    Button notmdcan;
    Button notdoritochip;
    Button trollface;
    Button fedora;
    Button yolo;
    Button vuvuzela;
    Button bong;
    Button ak;
    Button snipe;
    Button dootdoottrumpet;
    Button skelet;
    Button peperonni;
    Button lenny;


    ImageView tmp;
    SimpleSideDrawer slide;
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
        addMLG.setTextColor(Color.parseColor("white"));
        save = (Button) findViewById(R.id.b_savemontage);
        save.setTextColor(Color.parseColor("white"));
        imagecreated = (TextView) findViewById(R.id.t_imagecreated);
        imagecreated.setTextColor(Color.parseColor("white"));

        // Bouton MLG
        doge = (Button) findViewById(R.id.b_doge);
        shrek = (Button) findViewById(R.id.b_shrek);
        snoop = (Button) findViewById(R.id.b_snoop);
        ak = (Button) findViewById(R.id.b_ak);
        bong = (Button) findViewById(R.id.b_bong);
        cana = (Button) findViewById(R.id.b_cana);
        cash = (Button) findViewById(R.id.b_cash);
        hitmarker = (Button) findViewById(R.id.b_hitmarker);
        skelet = (Button) findViewById(R.id.b_skelet);
        eightbit = (Button) findViewById(R.id.b_eightbit);
        fedora = (Button) findViewById(R.id.b_fedora);
        getrekt = (Button) findViewById(R.id.b_getrekt);
        goldchain = (Button) findViewById(R.id.b_goldchain);
        illuminatitriangle = (Button) findViewById(R.id.b_illuminatitriangle);
        joint = (Button) findViewById(R.id.b_joint);
        lenny = (Button) findViewById(R.id.b_lenny);
        notdoritochip = (Button) findViewById(R.id.b_notdoritochip);
        notmdcan = (Button) findViewById(R.id.b_notmdcan);
        dootdoottrumpet = (Button) findViewById(R.id.b_dootdoottrumpet);
        patrick = (Button) findViewById(R.id.b_patrick);
        peperonni = (Button) findViewById(R.id.b_peperonni);
        snipe = (Button) findViewById(R.id.b_snipe);
        thugcap = (Button) findViewById(R.id.b_thugcap);
        thuglife = (Button) findViewById(R.id.b_thuglife);
        trollface = (Button) findViewById(R.id.b_trollface);
        vuvuzela = (Button) findViewById(R.id.b_vuvuzela);
        yolo = (Button) findViewById(R.id.b_yolo);

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
                afficherMenu();
                save.setVisibility(View.VISIBLE);
            }


        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imagecreated.setVisibility(View.VISIBLE);
                taille.setVisibility(View.GONE);
                rotation.setVisibility(View.GONE);
                addMLG.setVisibility(View.GONE);
                save.setVisibility(View.GONE);


                captureScreen();


                addMLG.setVisibility(View.VISIBLE);
                save.setVisibility(View.VISIBLE);

                imagecreated.setVisibility(View.INVISIBLE);
                nombreCourantImage = 0;

            }


        });


        taille.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {


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
                taille.setVisibility(View.INVISIBLE);
                rotation.setVisibility(View.INVISIBLE);
                //cacherMenu();
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

        ak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageTouchee(R.drawable.ak);
            }
        });

        bong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageTouchee(R.drawable.bong);
            }
        });

        cana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageTouchee(R.drawable.cana);
            }
        });

        hitmarker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageTouchee(R.drawable.hitmarker);
            }
        });

        cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageTouchee(R.drawable.cash);
            }
        });

        dootdoottrumpet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageTouchee(R.drawable.dootdoottrumpet);
            }
        });

        eightbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageTouchee(R.drawable.eightbit);
            }
        });

        fedora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageTouchee(R.drawable.fedora);
            }
        });

        getrekt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageTouchee(R.drawable.getrekt);
            }
        });

        goldchain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageTouchee(R.drawable.goldchain);
            }
        });

        lenny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageTouchee(R.drawable.lenny);
            }
        });


        illuminatitriangle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageTouchee(R.drawable.illuminatitriangle);
            }
        });

        joint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageTouchee(R.drawable.joint);
            }
        });

        notdoritochip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageTouchee(R.drawable.notdoritochip);
            }
        });

        notmdcan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageTouchee(R.drawable.notmdcan);
            }
        });

        patrick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageTouchee(R.drawable.patrick);
            }
        });

        peperonni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageTouchee(R.drawable.peperonni);
            }
        });

        skelet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageTouchee(R.drawable.skelet);
            }
        });

        snipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageTouchee(R.drawable.snipe);
            }
        });

        thugcap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageTouchee(R.drawable.thugcap);
            }
        });

        thuglife.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageTouchee(R.drawable.thuglife);
            }
        });

        vuvuzela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageTouchee(R.drawable.vuvuzela);
            }
        });

        yolo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageTouchee(R.drawable.yolo);
            }
        });

        trollface.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageTouchee(R.drawable.trollface);
            }
        });




    }

    @Override
    public void onBackPressed() {
        if (nombreCourantImage != 0) {
            new AlertDialog.Builder(LetTheMLGBegin.this)
                    .setTitle("WARNING")
                    .setMessage("R U SURE M8 ?THIS CHEF-D-OEUVRE WILL BE DESTROYED")
                    .setNegativeButton(android.R.string.cancel, null) // dismisses by default
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .create()
                    .show();
        } else finish();
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

        nombreCourantImage++;
       /* if (id == R.drawable.doge) idTableau = 0;
        else if (id == R.drawable.shrek) idTableau = 1;
        else if (id == R.drawable.snoop) idTableau = 2;
        else if (id == R.drawable.frog) idTableau = 3; */

        idTableau = 1;

            for (int idCourant = 0; idCourant < tableauId.length; idCourant++) {
                if (tableauId[idCourant] == id) idTableau = idCourant;
            }

        Log.i("Integer id courant: ", Integer.toString(idTableau));

        slide.toggleDrawer();

        final ImageView iv = new ImageView(getApplicationContext());
        iv.setImageResource(id);
        iv.setClickable(true);
        iv.setId(View.generateViewId());

        //Ion.with(iv).load("android.resource://betcheg.mlgphotomontage/" + id);

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
                        taille.setVisibility(View.INVISIBLE);
                        rotation.setVisibility(View.INVISIBLE);
                        nombreCourantImage--;
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

    private void captureScreen() {
        Date now = new Date();
        now.getYear();
        now.getMonth();
        now.getDay();

        View v = findViewById(R.id.rl);
        v.setDrawingCacheEnabled(true);
        Bitmap bitmap  = v.getDrawingCache();
        String dest = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                +File.separator+"Camera"+File.separator+"MLG_"+
                now.getDay()+now.getMonth()+now.getYear()+"_"+now.getHours()+now.getMinutes()+now.getSeconds()+".jpg";
        File file = new File(dest);
        try {
            FileOutputStream stream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.flush();
            stream.close();
            Toast.makeText(getApplicationContext(), "Saved !", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "An error occured", Toast.LENGTH_LONG).show();
        }finally{
            v.setDrawingCacheEnabled(false);
        }
        MediaScannerConnection.scanFile(this, new String[]{file.getPath()}, new String[]{"image/jpeg"}, null);
    }
}
