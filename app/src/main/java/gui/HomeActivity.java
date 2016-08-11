package gui;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Toast;

import com.softc.age_calculator.R;

import utils.DateCalculator;

/**
 * The class is the UI class where user will interact with the age calculation process.
 */
public class HomeActivity extends Activity {


    int isBackPressEventFired = 0;

    /**
     * The method get called by the system when user enter the app for the first time.
     *
     * @param bundle the system send a {@link Bundle} object associate
     *               with this {@link HomeActivity} class.
     */
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_home);

    }

    /**
     * The method get called by the system when user press the back button to exit the app.
     */
    @Override
    public void onBackPressed() {
        exitActivityOnDoublePress();
    }

    /**
     * Exit the application by double click on the back button.
     */
    public void exitActivityOnDoublePress() {

        if (isBackPressEventFired == 0) {
            String msg = getString(R.string.str_press_back_once_more_to_exit);
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            isBackPressEventFired = 1;
            new CountDownTimer(2000, 1000) {
                @Override
                public void onTick(long time) {
                }


                @Override
                public void onFinish() {
                    isBackPressEventFired = 0;
                }
            }.start();
        } else if (isBackPressEventFired == 1) {
            isBackPressEventFired = 0;
            finish();
        }
    }


}