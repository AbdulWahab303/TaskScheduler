package com.example.taskscheduler;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {


    TextView tvName,tvEmail;
    EditText etFirstName,etLastName,etEmail;
    Button btnUpdate;
    SharedPreferences sharedPreferences;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvName=view.findViewById(R.id.tvName);
        tvEmail=view.findViewById(R.id.tvEmail);
        etFirstName=view.findViewById(R.id.etFirstName);
        etLastName=view.findViewById(R.id.etLastName);
        etEmail=view.findViewById(R.id.etEmail);
        btnUpdate=view.findViewById(R.id.btnUpdate);
        sharedPreferences= getActivity().getSharedPreferences("data",MODE_PRIVATE);

        String nameS=sharedPreferences.getString("key_name","");
        String emailS=sharedPreferences.getString("key_email","");
        if(nameS!=null && emailS!=null){
            tvName.setText(nameS);
            tvEmail.setText(emailS);
        }

        btnUpdate.setOnClickListener((v)->{
            String firstName=etFirstName.getText().toString().trim();
            String lastName=etLastName.getText().toString().trim();
            String email=etEmail.getText().toString().trim();

            if(!firstName.isEmpty() && !lastName.isEmpty() && !email.isEmpty()){
                tvName.setText(firstName+" "+lastName);
                tvEmail.setText(email);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("key_name",firstName+" "+lastName);
                editor.putString("key_email",email);
                editor.commit();
            }
        });



    }
}