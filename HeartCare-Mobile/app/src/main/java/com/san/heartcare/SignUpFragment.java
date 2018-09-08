package com.san.heartcare;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.san.heartcare.models.Disease;
import com.san.heartcare.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SignUpFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private final List<String> diseases = new ArrayList<>();
    private final Map<String, String> diseaseId = new HashMap<>();
    private DatabaseReference mDB;
    private FirebaseAuth mAuth;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SignUpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignUpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
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
        mDB = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        final Spinner spinner = view.findViewById(R.id.disease);
        Button submit = view.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        diseases.add("Enfermedad (opcional)");
        mDB.child("diseases").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Disease disease = snapshot.getValue(Disease.class);
                    System.out.println(snapshot.getKey() + " " + disease.name);
                    diseaseId.put(disease.name, snapshot.getKey());
                    diseases.add(disease.name);
                }
                ArrayAdapter<String> adp = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, diseases) {
                    @Override
                    public boolean isEnabled(int position) {
                        return true;
                    }

                    @Override
                    public View getDropDownView(int position, View convertView,
                                                @NonNull ViewGroup parent) {
                        View view = super.getDropDownView(position, convertView, parent);
                        TextView tv = (TextView) view;
                        if (position == 0) {
                            // Set the disable item text color
                            tv.setTextColor(Color.GRAY);
                        } else {
                            tv.setTextColor(Color.BLACK);
                        }
                        return view;
                    }
                };
                spinner.setAdapter(adp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public boolean registerUser() {

        final TextInputEditText emailView = getActivity().findViewById(R.id.email);
        final TextInputEditText passwordView = getActivity().findViewById(R.id.password);
        final TextInputEditText ageView = getActivity().findViewById(R.id.age);
        final TextInputEditText wristbandView = getActivity().findViewById(R.id.wristband);
        final Spinner diseaseView = getActivity().findViewById(R.id.disease);
        final String name = ((TextInputEditText) getActivity().findViewById(R.id.name)).getText().toString();
        final String last_name = ((TextInputEditText) getActivity().findViewById(R.id.last_name)).getText().toString();
        final int age = (int) Double.parseDouble(ageView.getText().toString());
        final double height = Double.parseDouble(((TextInputEditText) getActivity().findViewById(R.id.height)).getText().toString());
        final double weight = Double.parseDouble(((TextInputEditText) getActivity().findViewById(R.id.weight)).getText().toString());
        final String email = emailView.getText().toString();
        final String password = passwordView.getText().toString();
        final String wristband = wristbandView.getText().toString();
        final String disease = diseaseView.getSelectedItem().toString();

        if (!LogicRules.isValidSignUpForm(emailView, email, passwordView, password, ageView, age))
            return false;
        mDB.child("wristbands").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChild(wristband)) {
                    if (dataSnapshot.child(wristband).getValue(String.class).equals("0")) {
                        mAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            // Sign in success, update UI with the signed-in user's information
                                            FirebaseUser user = mAuth.getCurrentUser();
                                            Map<String, Boolean> diseases = null;

                                            if (diseaseView.getSelectedItemPosition() != 0) {
                                                diseases = new HashMap<>();
                                                diseases.put(diseaseId.get(disease), true);
                                                mDB.child("diseases").child(diseaseId.get(disease)).child("users").child(user.getUid()).setValue(true);
                                            }

                                            mDB.child("users").child(user.getUid()).setValue(new User(name, last_name, age, height, weight, wristband, diseases, null, null));
                                            mDB.child("wristbands").child(wristband).setValue(user.getUid());

                                            Toast.makeText(getActivity(), "Bienvenido a HeartCare", Toast.LENGTH_SHORT).show();
                                            AppSharedPreferences.setLoggedStatus(getContext(), true);
                                            Intent intent = new Intent(getActivity(), MainActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                            getActivity().finish();
                                        } else {
                                            System.out.println("fallo XD");
                                        }
                                    }
                                });
                    } else {
                        wristbandView.setError("Id de pulsera en uso");
                    }
                } else {
                    wristbandView.setError("Id de pulsera invalido");
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return true;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
