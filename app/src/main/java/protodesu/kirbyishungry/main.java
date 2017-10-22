package protodesu.kirbyishungry;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class main extends AppCompatActivity {

    private TextView scoreLabel;
    private TextView startLabel;
    private ImageView kirby;
    private ImageView cookie;
    private ImageView cake;
    private ImageView pizza;
    private ImageView bomb;

    //Size
    private int frameHeight;
    private int kirbySize;

    ///position
    private int kirbyY;
    private int cookiesX;
    private int cookiesY;
    private int cakeX;
    private int cakeY;
    private int pizzaX;
    private int pizzaY;
    private int bombX;
    private int bombY;

    //Initialize Class
    private Handler handler = new Handler();
    private Timer timer = new Timer();

    //Status Check
    private boolean action_flg = false;
    private boolean start_flg = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scoreLabel = (TextView) findViewById(R.id.scoreLabel);
        startLabel = (TextView) findViewById(R.id.startLabel);
        kirby = (ImageView) findViewById(R.id.kirby);
        cookie = (ImageView) findViewById(R.id.cookie);
        cake = (ImageView) findViewById(R.id.cake);
        pizza = (ImageView) findViewById(R.id.pizza);
        bomb = (ImageView) findViewById(R.id.bomb);

        //Move to out of screen
        cookie.setX(-80);
        cookie.setY(-80);
        cake.setX(-80);
        cake.setY(-80);
        pizza.setX(-80);
        pizza.setY(-80);
        bomb.setX(-80);
        bomb.setY(-80);

        //will remove later...TEMPORARY
        kirbyY = 500;


    }

    public void changePos() {

        //move kirby
        if(action_flg == true){
            //touch
            kirbyY -=20;
        }else{
            //no touch
            kirbyY+=20;
        }

        //Check box position
        //don't go to space!!!
        if(kirbyY <0) kirbyY = 0;

        //don't fall off bottom
        if(kirbyY>frameHeight - kirbySize) kirbyY = frameHeight-kirbySize;

        kirby.setY(kirbyY);
    }


    public boolean onTouchEvent(MotionEvent me) {

        if(start_flg == false){

            start_flg = true;

            //WHy get frame height and box height here???
            //because the UI has not been set on the screen in OnCreate() !!!

            FrameLayout frame = (FrameLayout)  findViewById(R.id.frame);
            frameHeight = frame.getHeight();

            kirbyY = (int)kirby.getY();

            //THis si fine, since kirby is a square(height and width are the same)
            kirbySize = kirby.getHeight();




            startLabel.setVisibility(View.GONE);

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            changePos();
                        }
                    });
                }
            },0,20);

        }else{
            if (me.getAction() == MotionEvent.ACTION_DOWN) {
                action_flg = true;
            }else if (me.getAction() == MotionEvent.ACTION_UP){
                action_flg = false;
            }
        }
        return true;
    }
}
