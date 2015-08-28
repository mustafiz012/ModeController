package ara.kuet.musta;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TimePicker;

/**
 * Created by Aatik on 14-Feb-15.
 */
public class IshaTimePickerDialogFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{
    //Define constants for date-time picker.

    private Fragment mCurrentFragment;
    public IshaTimePickerDialogFragment(Fragment fragment) {
        mCurrentFragment = fragment;
    }

    public Dialog onCreateDialog(Bundle bundle) {
        //Bundle bundle = new Bundle();
        bundle = getArguments();
        int hour = bundle.getInt("hour");
        int minute = bundle.getInt("minute");

                return new TimePickerDialog(getActivity(),
                        (TimePickerDialog.OnTimeSetListener)mCurrentFragment,hour,
                        minute, false);

        }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

    }
}