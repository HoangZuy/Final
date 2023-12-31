package com.example.finalproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;


public class dashboard_profile extends Fragment {
    String name, username, contact, route, usernametop, branch, currentyear, pendingfees, stand;
    TextView usernameonprofiletxt, usernametxt, contacttxt;
    AlertDialog dialog = null;
    Button logoutbtn;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public dashboard_profile() {
        // Required empty public constructor
    }



    // TODO: Rename and change types and number of parameters
    public static dashboard_profile newInstance(String param1, String param2) {
        dashboard_profile fragment = new dashboard_profile();
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

        View v = inflater.inflate(R.layout.fragment_dashboard_profile, container, false);
        final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

        LayoutInflater inflaterdialoge = getActivity().getLayoutInflater();
        alert.setView(inflaterdialoge.inflate(R.layout.retrievingdata, null));
        dialog = alert.create();

        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;

        //  Bundle bundle =this.getArguments();
        //String strtetx=bundle.getString("edttext");


        usernameonprofiletxt = v.findViewById(R.id.usernameonprofiletab);
        usernametxt = v.findViewById(R.id.usernameedit);
        contacttxt = v.findViewById(R.id.contactedit);
        logoutbtn = v.findViewById(R.id.logoutonprofile);
        connectionmanager connectionmanagerOBJ = new connectionmanager();
        connectionmanagerOBJ.checkConnection(getContext(), getActivity());

        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setMessage("Are you sure you want to Log out ?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        sessionmanager sessionmanager_obj = new sessionmanager(getContext());
                        sessionmanager_obj.logoutuserfromsession();

                        Intent intent3 = new Intent(getContext(), Login.class);
                        intent3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent3);
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

//    private void getdetails() {
//        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//        final DatabaseReference ref = firebaseDatabase.getReference("users");
//        final sessionmanager sessionmanagerobj = new sessionmanager(requireContext());
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                //System.out.println(dataSnapshot);
//
//
//                String loginusername = sessionmanagerobj.getuserdetail();
//
//                username = dataSnapshot.child(loginusername).child("username").getValue().toString();
//                contact = dataSnapshot.child(loginusername).child("phoneNo").getValue().toString();
//                route = dataSnapshot.child(loginusername).child("bus_no").getValue().toString();
//                name = dataSnapshot.child(loginusername).child("name").getValue().toString();
//                branch = dataSnapshot.child(loginusername).child("branch").getValue().toString();
//                currentyear = dataSnapshot.child(loginusername).child("currentyear").getValue().toString();
//                stand = dataSnapshot.child(loginusername).child("stand").getValue().toString();
//                pendingfees = dataSnapshot.child(loginusername).child("pendingfees").getValue().toString();
//
//                usernametop = name.concat(username) ;
//                usernameonprofiletxt.setText(usernametop);
//
//                name = ": ".concat(name);
//                username = ": ".concat(username);
//                usernametxt.setText(username);
//                branch = ": ".concat(branch);
//                stand = ": ".concat(stand);
//
//                contact = ": +91-".concat(contact);
//                contacttxt.setText(contact);
//                route = ": ".concat(route);
//
//                currentyear= ": ".concat(currentyear);
//
//                pendingfees =": ".concat(pendingfees);
//
//                dialog.cancel();
//
//                if (ref != null && this != null) {
//                    ref.removeEventListener(this);
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
////                System.out.println("The read failed: " + databaseError.getCode());
//                System.out.println("The read failed:");
//            }
//        });

//    }

}