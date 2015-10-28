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
    int lDoge = 225;
    int hDoge = 225;
    int lShrek = 190;
    int hShrek = 169;

    int dernierId = 0;
    String imageString;
    Button addMLG;
    Button annuler;
    Button doge;
    Button shrek;
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
                progress = progresValue;
                Log.i("progres:", Integer.toString(progresValue));
                Log.i("hauteur doge:", Integer.toString(hDoge));
                Log.i("largeur doge:", Integer.toString(lDoge));
                Log.i("progresbar <1", Float.toString((float)(progresValue / 100)));

                Log.i("nouvelle largeur doge:", Integer.toString((2 * lDoge * progresValue )/ 100) + 30);


                tmp = (ImageView) findViewById(dernierId);
                //LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((lDoge*progresValue)+30,(hDoge*progresValue)+30);
                //tmp.setLayoutParams(layoutParams);
                tmp.requestLayout();
                tmp.getLayoutParams().height = ((2 * hDoge * progresValue )/ 100) + 30;
                tmp.getLayoutParams().width = ((2 * lDoge * progresValue )/ 100) + 30;
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
                menu.setVisibility(View.GONE);
                horizontal.setVisibility(View.VISIBLE);
            }


        });

        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.setVisibility(View.VISIBLE);
                horizontal.setVisibility(View.GONE);
            }

        });

        shrek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ImageView iv = new ImageView(getApplicationContext());
                iv.setImageResource(R.drawable.shrek);
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
                            dernierId = iv.getId();
                            taille.setVisibility(View.VISIBLE);
                            rotation.setVisibility(View.VISIBLE);

                        }
                        return true;
                    }
                });
            }


        });


        doge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ImageView iv = new ImageView(getApplicationContext());
                iv.setImageResource(R.drawable.doge);
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
                            dernierId = iv.getId();
                            taille.setVisibility(View.VISIBLE);
                            rotation.setVisibility(View.VISIBLE);

                        }
                        return true;
                    }
                });
            }


        });

    }
}
