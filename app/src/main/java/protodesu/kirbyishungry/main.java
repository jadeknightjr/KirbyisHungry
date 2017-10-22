package protodesu.kirbyishungry;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
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
        startLabel.setVisibility(View.INVISIBLE);
        kirbyY = 500;

    }

    public boolean onTouchEvent(MotionEvent me) {

        if (me.getAction() == MotionEvent.ACTION_DOWN) {
            kirbyY -= 20;
        }
        kirby.setY(kirbyY);
        return true;
    }
}
