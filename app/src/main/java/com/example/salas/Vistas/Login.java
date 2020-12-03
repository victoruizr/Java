package com.example.salas.Vistas;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.salas.R;

public class Login extends Fragment implements View.OnClickListener{
    private Button b1;
    private EditText e1,e2;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        b1 = view.findViewById(R.id.acceder);

        b1.setOnClickListener(this);

        e1=view.findViewById(R.id.editTextTextPersonName);
        e2=view.findViewById(R.id.editTextTextPassword);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.acceder:
                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
                if(pref.getString("user","").equals("")|| pref.getString("pass","").equals("")){
                    SharedPreferences.Editor prefsEditor = pref.edit();
                    prefsEditor.putString("user",e1.getText().toString());
                    prefsEditor.commit();
                    prefsEditor.putString("pass",e2.getText().toString());
                    prefsEditor.commit();
                }else{
                    if(pref.getString("user","").equals(e1.getText().toString())&& pref.getString("pass","").equals(e2.getText().toString())){
                        Navigation.findNavController(v).navigate(R.id.action_login_to_nav_exhibitions);
                    }else{
                        Toast.makeText(getActivity(),"Login fallido",Toast.LENGTH_LONG).show();
                    }
                }

        }

    }
}