package gui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import utils.DateCalculator;

/**
 * The class is the UI class where user will interact with the age calculation process.
 */
public class HomeActivity extends Activity {

    /**
     * The method get called by the system when user enter the app for the first time.
     * @param bundle the system send a {@link Bundle} object associate
     *               with this {@link HomeActivity} class.
     */
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        try {
            DateCalculator.Year startingYear = new DateCalculator.Year(19, 11, 1994);
            DateCalculator.Year endingYear = new DateCalculator.Year(11, 8, 2016);
            DateCalculator.Result result = DateCalculator.calculate(startingYear, endingYear);
            Log.d(HomeActivity.class.getName(), "Age = " + result.toString());
        } catch (Exception error) {
            error.printStackTrace();
        }

    }

    /**
     * The method get called by the system when user press the back button to exit the app.
     */
    @Override
    public void onBackPressed() {

    }

}