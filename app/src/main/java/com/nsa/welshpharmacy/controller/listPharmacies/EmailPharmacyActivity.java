package com.nsa.welshpharmacy.controller.listPharmacies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nsa.welshpharmacy.R;

public class EmailPharmacyActivity extends AppCompatActivity implements View.OnClickListener {
    private SharedPreferences pharmacyEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_email_pharmacy);

        pharmacyEmail = getSharedPreferences("emailAddress", Context.MODE_PRIVATE);

        Button sendEmail = findViewById(R.id.send_email_btn);
        sendEmail.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //Get email address first
        String emailAddress = pharmacyEmail.getString("email", "error");
        //Create emailing intent
        if ((emailAddress != "error") && (emailAddress != null)) {
            String text = getEmailContent();
            Intent anEmailIntent = new Intent(Intent.ACTION_SEND);
            //Define mail data
            anEmailIntent.setData(Uri.parse("mailto:"));
            anEmailIntent.setType("text/plain");
            //Define to Who
            anEmailIntent.putExtra(Intent.EXTRA_EMAIL, emailAddress);
            //Receiver/Message content
            anEmailIntent.putExtra(Intent.EXTRA_SUBJECT, "Question");
            if ((text != null) && (text != "")) {
                anEmailIntent.putExtra(Intent.EXTRA_TEXT, text);
            } else {
                anEmailIntent.putExtra(Intent.EXTRA_TEXT, "Dear Sir/Madam. ");
            }
            try {
                startActivity(Intent.createChooser(anEmailIntent, "Send email.."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(this, R.string.no_email_client, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public String getEmailContent() {
        EditText usersMessage = findViewById(R.id.users_msg_edittext);
        return usersMessage.getText().toString();
    }
}
