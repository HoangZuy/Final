package com.example.finalproject;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormatSymbols;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class dashboard_booking extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView location1, location2, date, day_month;
    String currentday, currentmonth;
    String day_Monthstr, currentyear, busnofromdatabase;
    int date_, month_, year_;
    Button serchbtn;
    Boolean bookingsecondtime = false;
    ImageView calenderimage;
    TextInputLayout outbox;
    String selecteddate, selectedday, selectedmonth;
    ProgressBar progressBar;

    public dashboard_booking() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static dashboard_booking newInstance(String param1, String param2) {
        dashboard_booking fragment = new dashboard_booking();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final String[] currentdate = {new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date())};


        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_dashboard_booking, container, false);
        date = v.findViewById(R.id.setdatedate);

        location1 = v.findViewById(R.id.fromlocate);
        location2 = v.findViewById(R.id.tolocate);
        day_month = v.findViewById(R.id.day_year);
        calenderimage = v.findViewById(R.id.calendaricon);
        serchbtn = v.findViewById(R.id.serchbtn);


        Calendar calendartommorw = Calendar.getInstance();
        calendartommorw.add(Calendar.DAY_OF_YEAR, 1);
        final int date_tommorow = calendartommorw.get(Calendar.DATE);

        String TOMMOROW_DAY = (String) DateFormat.format("EEE", calendartommorw);
        String tommorow_month = (String) DateFormat.format("MMM", calendartommorw);
        String cmbinemonth = TOMMOROW_DAY + ",\n" + tommorow_month;

        Calendar calendar = Calendar.getInstance();
        date_ = calendar.get(Calendar.DATE);
        month_ = calendar.get(Calendar.MONTH);
        year_ = calendar.get(Calendar.YEAR);
        currentmonth = (String) DateFormat.format("MMM", new Date());

        currentday = (String) DateFormat.format("EEE", new Date());
        day_Monthstr = currentday + ",\n" + currentmonth;
        date.setText(String.valueOf(date_tommorow));
        day_month.setText(cmbinemonth);


        serchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChooseTrain.class);
                startActivity(intent);
            }
        });


        final PopupMenu popupMenu1 = new PopupMenu(getContext(), location1);
        final PopupMenu popupMenu2 = new PopupMenu(getContext(), location2);
        MenuInflater menuInflater = popupMenu1.getMenuInflater();
        menuInflater.inflate(R.menu.location, popupMenu1.getMenu());
        menuInflater = popupMenu2.getMenuInflater();
        menuInflater.inflate(R.menu.location, popupMenu2.getMenu());

        location1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupMenu1.show();
            }
        });

        location2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupMenu2.show();
            }
        });

        popupMenu1.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.saigon:
                    case R.id.hanoi:
                    case R.id.danang:
                    case R.id.nhatrang:
                    case R.id.hue:
                    case R.id.quangngai:
                        location1.setText(menuItem.getTitle());
                        return true;
                    default:
                        return false;
                }
            }
        });

        popupMenu2.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.saigon:
                    case R.id.hanoi:
                    case R.id.danang:
                    case R.id.nhatrang:
                    case R.id.hue:
                    case R.id.quangngai:
                        location2.setText(menuItem.getTitle());
                        return true;
                    default:
                        return false;
                }
            }
        });


        day_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {


                        Date dateobj = new Date(i, i1, i2 - 1);


                        selecteddate = String.valueOf(i2);
                        year_ = i;
                        month_ = i1;
                        date_ = i2;

                        selectedmonth = (String) DateFormat.format("MMM", dateobj);

                        selectedday = (String) DateFormat.format("EEE", dateobj);
                        day_Monthstr = selectedday + ",\n" + selectedmonth;
                        date.setText(String.valueOf(i2));
                        day_month.setText(day_Monthstr);
                    }
                }, year_, month_, date_);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });

        calenderimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        Date dateobj = new Date(i, i1, i2 - 1);

                        currentdate[0] = String.valueOf(i2);
                        year_ = i;
                        month_ = i1;
                        date_ = i2;

                        currentmonth = (String) DateFormat.format("MMM", dateobj);

                        currentday = (String) DateFormat.format("EEE", dateobj);
                        day_Monthstr = currentday + ",\n" + currentmonth;
                        date.setText(currentdate[0]);
                        day_month.setText(day_Monthstr);
                    }
                }, year_, month_, date_);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        Date dateobj = new Date(i, i1, i2 - 1);

                        currentdate[0] = String.valueOf(i2);
                        year_ = i;
                        month_ = i1;
                        date_ = i2;

                        currentmonth = (String) DateFormat.format("MMM", dateobj);

                        currentday = (String) DateFormat.format("EEE", dateobj);
                        day_Monthstr = currentday + ",\n" + currentmonth;
                        date.setText(currentdate[0]);
                        day_month.setText(day_Monthstr);
                    }
                }, year_, month_, date_);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setMessage("Are you sure you want to Exit ?").setCancelable(true).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        requireActivity().finish();
                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);

        return v;
    }

}



