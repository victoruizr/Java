package com.example.salas.Vistas;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.salas.Modelos.Artistas;
import com.example.salas.R;


public class Email extends Fragment {

    EditText mEditTextTo, mEditTextSubject, mEditTextMessage,asunto;

    Artistas art;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    private boolean compruebaEmail(String email) {
        boolean estado = false;
        if (email.matches("[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")) {
            estado = true;
        }
        return estado;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_email, container, false);
        mEditTextTo = view.findViewById(R.id.edit_text_to);
        mEditTextMessage = view.findViewById(R.id.edit_text_message);
        mEditTextSubject = view.findViewById(R.id.edit_text_subject);
        asunto = view.findViewById(R.id.edit_text_contenido);

        if (getArguments() != null) {
            art = (Artistas) getArguments().getSerializable("artis");
        }
        mEditTextTo.setText(art.getEmail());
        Button buttonSend = view.findViewById(R.id.button_send);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
            }
        });

        return view;
    }

    private void sendMail() {

        if (!compruebaEmail(mEditTextSubject.getText().toString())) {
            Toast.makeText(getContext(), R.string.emailnotvalid, Toast.LENGTH_SHORT).show();
        } else if(mEditTextTo.getText().toString()==""||mEditTextMessage.getText().toString()=="" || mEditTextSubject.getText().toString()==""|| asunto.getText().toString()==""){
            Toast.makeText(getContext(), R.string.vacio, Toast.LENGTH_SHORT).show();
        }else{
            String recipientList = mEditTextSubject.getText().toString();
            String[] recipients = recipientList.split(",");

            String subject = asunto.getText().toString();

            String message = mEditTextMessage.getText().toString();

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_EMAIL, recipients);
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(Intent.EXTRA_TEXT, message);

            intent.setType("message/rfc822");
            startActivity(Intent.createChooser(intent, "Chooses an email client"));

        }


    }
}