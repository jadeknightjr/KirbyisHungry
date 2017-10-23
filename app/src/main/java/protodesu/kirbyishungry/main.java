package protodesu.kirbyishungry;

import android.graphics.Point;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Map;
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
    private int screenWidth;
    private int screenHeight;

    ///position
    private int kirbyY;
    private int cookieX;
    private int cookieY;
    private int cakeX;
    private int cakeY;
    private int pizzaX;
    private int pizzaY;
    private int bombX;
    private int bombY;

    //score
    private int score = 0;
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

        //Get screen size
        WindowManager wm = getWindowManager();
        Display disp = wm.getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);

        screenWidth = size.x;
        screenHeight = size.y;


        //Move to out of screen
        cookie.setX(-80);
        cookie.setY(-80);
        cake.setX(-80);
        cake.setY(-80);
        pizza.setX(-80);
        pizza.setY(-80);
        bomb.setX(-80);
        bomb.setY(-80);

        //will change from code at bottom
        scoreLabel.setText("Score : 0");

    }

    public void changePos() {

        //check hits first
        hitCheck();

        //cookie
        cookieX -=12;
        if(cookieX <0){
            cookieX = screenWidth + 10;
            cookieY = (int) Math.floor(Math.random() * (frameHeight-cookie.getHeight()));
        }
        cookie.setX(cookieX);
        cookie.setY(cookieY);

        //cake
        cakeX -=16;
        if(cakeX<0){
            cakeX = screenWidth + 20;
            cakeY = (int) Math.floor(Math.random() * (frameHeight-cake.getHeight()));
        }
        cake.setX(cakeX);
        cake.setY(cakeY);


        //pizza
        pizzaX -= 20;
        if(pizzaX <0){
            pizzaX= screenWidth+ 5000;
            pizzaY = (int) Math.floor(Math.random() * (frameHeight-pizza.getHeight()));
        }
        pizza.setX(pizzaX);
        pizza.setY(pizzaY);

        //bomb
        bombX -=25;
        if(bombX<0){
            bombX = screenWidth + 20;
            bombY = (int) Math.floor(Math.random() * (frameHeight-bomb.getHeight()));
        }
        bomb.setX(bombX);
        bomb.setY(bombY);


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

        scoreLabel.setText("Score : "+ score);

    }

    public void hitCheck(){
        //if center of the food/bomb is in kirby, it countas as a hit

        //cookie
        int cookieCenterX = cookieX + cookie.getWidth()/2;
        int cookieCenterY = cookieY + cookie.getHeight()/2;

        //0 <= cookieCenterX <= kirbyWidth
        //kirbyY <= cookieCenterY <= kirbyY + cookieHeight
        if(0 <= cookieCenterX && cookieCenterX <= kirbySize &&
                kirbyY <= cookieCenterY && cookieCenterY <= kirbyY + kirbySize){

            //weakest score cookie
            score +=5;
            cookieX = -10;
        }

        //cake
        int cakeCenterX = cakeX + cake.getWidth()/2;
        int cakeCenterY = cakeY + cake.getHeight()/2;

        if(0 <= cakeCenterX && cakeCenterX <= kirbySize &&
                kirbyY <= cakeCenterY && cakeCenterY <= kirbyY + kirbySize){

            //meh avg.
            score +=15;
            cakeX = -10;
        }

        //pizza
        int pizzaCenterX = pizzaX + pizza.getWidth()/2;
        int pizzaCenterY = pizzaY + pizza.getHeight()/2;
        if(0 <= pizzaCenterX && pizzaCenterX <= kirbySize &&
                kirbyY <= pizzaCenterY && pizzaCenterY <= kirbyY + kirbySize) {

            score += 30;
            pizzaX = -10;   //remember when copy past own code... CHANGE cookieX INTO pizzaX !!!
        }

        //bomb
        int bombCenterX = bombX + bomb.getWidth()/2;
        int bombCenterY = bombY + bomb.getHeight()/2;

        if(0 <= bombX && bombCenterX <= kirbySize &&
                kirbyY <= bombCenterY && bombCenterY <= kirbyY + kirbySize){

            //stop timer...
            timer.cancel();
            timer = null;

            //show result...
        }


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
