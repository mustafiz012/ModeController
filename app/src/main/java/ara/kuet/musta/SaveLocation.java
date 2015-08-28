package ara.kuet.musta;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kuet.musta.R;

import java.util.Map;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */

public class SaveLocation extends Fragment implements View.OnClickListener {

    public final static String TAG = SaveLocation.class.getSimpleName();
    private Button save, delete;
    private LinearLayout linearLayout;
    private SharedPreferences spMaster, spName, spLat, spLon;
    private SharedPreferences.Editor editor, eName, eLat, eLon;
    private float lat, lon;
    private TextView[] tvName;


    public static SaveLocation newInstance(){
        return new SaveLocation();
    }
    public SaveLocation() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_save_location, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initAll(view);
        delete.setOnClickListener(this);
        save.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == save){

            AlertDialog.Builder ab = new AlertDialog.Builder(getActivity());
            lat = spMaster.getFloat("latitude", 0);//+North,-South
            lon = spMaster.getFloat("longitude", 0);//+East,-West
            ab.setTitle("Save Location");
            ab.setMessage("Latitude:" + lat + " &" + "\nLongitude:" + lon);
            final EditText name = new EditText(getActivity());
            name.setHint("Give a Mosque Name To Save");
            ab.setView(name);
            ab.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    final String mName = name.getText().toString();
                    AudioManager am = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
                    if (mName.isEmpty()) {
                        Toast.makeText(getActivity(), "At least give a Name !", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(), "Saved Location: " + mName, Toast.LENGTH_LONG).show();
                        eName.putString(mName, mName);
                        eLat.putFloat(mName, lat);
                        eLon.putFloat(mName, lon);
                        eName.apply();
                        eLat.apply();
                        eLon.apply();
                        am.setRingerMode(0);
                        mapper();
                    }
                }
            });
            ab.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog a = ab.create();
            a.setIcon(R.drawable.mosque);
            a.show();
        }
        
        else if (v == delete){

            AlertDialog.Builder ab = new AlertDialog.Builder(getActivity());
            ab.setTitle("Delete Location");
            ab.setMessage("You ar going to delete a location");
            final EditText name = new EditText(getActivity());
            name.setHint("Give Mosque Name To Delete");
            ab.setView(name);
            ab.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    final String mName = name.getText().toString();

                    if (mName.isEmpty()) {
                        Toast.makeText(getActivity(), "At least give a Name !", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(), "Deleted Location: " + mName, Toast.LENGTH_LONG).show();
                        eName.remove(mName);
                        eLat.remove(mName);
                        eLon.remove(mName);
                        eName.apply();
                        eLat.apply();
                        eLon.apply();
                        mapper();
                    }
                }
            });
            ab.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog a = ab.create();
            a.setIcon(R.drawable.mosque);
            a.show();
        }
    }


    private void mapper() {
        try {
            linearLayout.removeAllViews();
        } catch (Exception ignored)
        { }
        Map<String, ?> allName = spName.getAll();
        int i = 0;
        for (Map.Entry<String, ?> entry : allName.entrySet()) {
            String name = entry.getKey();
            i++;
            TextView rowTextView = new TextView(getActivity());
            rowTextView.setGravity(Gravity.CENTER);
            rowTextView.setBackgroundResource(R.drawable.tvlocation);
            rowTextView.setText(i + "." + name);
            rowTextView.setTextColor(Color.BLACK);
            rowTextView.setTextSize(15);
            //rowTextView.setWidth(50);
            linearLayout.addView(rowTextView);
            tvName[i - 1] = rowTextView;
        }
    }

    private void initAll(View v) {
        spMaster = getActivity().getSharedPreferences("mysp",0);
        spName = getActivity().getSharedPreferences("myspname",0);
        spLat = getActivity().getSharedPreferences("mysplat",0);
        spLon = getActivity().getSharedPreferences("mysplon",0);
        editor = spMaster.edit();
        eName = spName.edit();
        eLat = spLat.edit();
        eLon = spLon.edit();
        save = (Button) v.findViewById(R.id.bSave);
        delete = (Button) v.findViewById(R.id.bdelete);
        linearLayout = (LinearLayout) v.findViewById(R.id.innerlay);
        final int n = 100;
        tvName = new TextView[n];
        editor.commit();
        mapper();
    }
}
