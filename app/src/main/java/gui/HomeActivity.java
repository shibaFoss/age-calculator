package gui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.softc.age_calculator.R;

import java.util.Calendar;

import utils.DateCalculator;

/**
 * The class is the UI class where user will interact with the age calculation process.
 */
public class HomeActivity extends Activity {
    private static final String TAG = HomeActivity.class.getName();

    /**
     * The variable is used in {@link #exitActivityOnDoublePress()} method.
     */
    int isBackPressEventFired = 0;

    /**
     * The referenced UI variable of the activity layout.
     */
    private EditText editStartDay, editStartMonth, editStartYear;

    /**
     * The referenced UI variable of the activity layout.
     */
    private EditText editEndDay, editEndMonth, editEndYear;

    /**
     * The referenced object
     */
    private InputMethodManager inputMethodManager;

    /**
     * The referenced variable of {@link TextView} from activity layout is used to
     * show the age calculation result.
     */
    private TextView resultView;

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

        //initialize the referenced variable.
        editStartDay = (EditText) findViewById(R.id.edit_txt_start_days);
        editStartMonth = (EditText) findViewById(R.id.edit_txt_start_month);
        editStartYear = (EditText) findViewById(R.id.edit_txt_start_year);

        editEndDay = (EditText) findViewById(R.id.edit_txt_end_days);
        editEndMonth = (EditText) findViewById(R.id.edit_txt_end_month);
        editEndYear = (EditText) findViewById(R.id.edit_txt_end_year);

        resultView = (TextView) findViewById(R.id.txt_result);
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        updateStartEditFields();
    }

    /**
     * The method get called by the system when user press the back button to exit the app.
     */
    @Override
    public void onBackPressed() {
        exitActivityOnDoublePress();
    }

    /**
     * Exit the application when user double clicks on the back button.
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

    /**
     * The method get called by button click, :: Share the age.
     *
     * @param view the view that calls the method.
     */
    public void onShare(View view) {
        if (resultView != null) {
            String ageString = resultView.getText().toString();
            if (ageString.length() > 1) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "My age is " + ageString);
                startActivity(Intent.createChooser(sharingIntent, getString(R.string.str_share_using)));

            } else {
                Toast.makeText(this, getString(R.string.str_calculate_your_age_first),
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * The method get called by button click, :: Calculate the age.
     *
     * @param view the view that calls the method.
     */
    public void onCalculate(View view) {
        if (isAllInputFieldValidated()) {
            inputMethodManager.hideSoftInputFromWindow(editEndYear.getApplicationWindowToken(), 0);

            int startDay, startMonth, startYear;
            int endDay, endMonth, endYear;

            startDay = Integer.parseInt(editStartDay.getText().toString());
            startMonth = Integer.parseInt(editStartMonth.getText().toString());
            startYear = Integer.parseInt(editStartYear.getText().toString());

            endDay = Integer.parseInt(editEndDay.getText().toString());
            endMonth = Integer.parseInt(editEndMonth.getText().toString());
            endYear = Integer.parseInt(editEndYear.getText().toString());

            boolean allValidate = false;

            if (startDay > 0 && startDay < 32) {
                if (startMonth > 0 && startMonth < 13) {
                    if (endDay > 0 && endDay < 32) {
                        if (endMonth > 0 && endMonth < 13) {
                            DateCalculator.Year todaysDate = new DateCalculator.Year(startDay, startMonth, startYear);
                            DateCalculator.Year birthDate = new DateCalculator.Year(endDay, endMonth, endYear);
                            Log.d(TAG, "Todays date :: " + todaysDate.toString());
                            Log.d(TAG, "Birth date :: " + birthDate.toString());
                            calculateAge(birthDate, todaysDate);
                            allValidate = true;
                        }
                    }

                }
            }

            if (!allValidate) {
                Toast.makeText(this, getString(R.string.str_correct_all_the_fields),
                        Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, getString(R.string.str_fill_dates_fields),
                    Toast.LENGTH_LONG).show();
        }
    }

    /**
     * The method get called by button click, :: Clear all edit text fields.
     *
     * @param view the view that calls the method.
     */
    public void onClearEditFields(View view) {
        if (editStartDay != null && editStartMonth != null && editStartYear != null) {
            editStartDay.setText("");
            editStartMonth.setText("");
            editStartYear.setText("");

            editEndDay.setText("");
            editEndMonth.setText("");
            editEndYear.setText("");
            resultView.setText("");

            updateStartEditFields();

            editEndMonth.requestFocus();
            inputMethodManager.showSoftInput(editEndMonth, 0);
        } else {
            throw new NullPointerException("result is false of = " +
                    "(editStartDay != null && editStartMonth != null && editStartYear != null)");
        }
    }

    /**
     * The method updates the {@link #editStartDay}, {@link #editStartMonth} &
     * {@link #editStartYear} with current calender date.
     */
    private void updateStartEditFields() {
        if (editStartDay != null && editStartMonth != null && editStartYear != null) {
            Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH) + 1;
            int year = calendar.get(Calendar.YEAR);

            editStartDay.setText(String.valueOf(day));
            editStartMonth.setText(String.valueOf(month));
            editStartYear.setText(String.valueOf(year));
        } else {
            throw new NullPointerException("result is false of = " +
                    "(editStartDay != null && editStartMonth != null && editStartYear != null)");
        }
    }

    /**
     * Calculate the age of the user.
     *
     * @param startDate the starting year.
     * @param endDate   the ending year.
     */
    private void calculateAge(DateCalculator.Year startDate, DateCalculator.Year endDate) {
        try {
            DateCalculator.Result result = DateCalculator.calculate(startDate, endDate);
            String resultString = result.toString();

            resultView.setText(resultString);

        } catch (Exception e) {
            e.printStackTrace();
            String message = e.getMessage();
            if (message.contains("Today's date")) {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * return true or false :: Check if all edit input fields are filled up or not.
     */
    private boolean isAllInputFieldValidated() {
        return editStartDay.getText().length() > 0 && editStartMonth.getText().length() > 0 &&
                editStartYear.getText().length() > 0 && editEndDay.getText().length() > 0 &&
                editEndMonth.getText().length() > 0 && editEndYear.getText().length() > 0;
    }

}