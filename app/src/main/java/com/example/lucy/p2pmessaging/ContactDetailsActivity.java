package com.example.lucy.p2pmessaging;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.lucy.p2pmessaging.Models.Contact;
import com.example.lucy.p2pmessaging.Networking.AppSingleton;
import com.example.lucy.p2pmessaging.R;

import java.util.HashMap;
import java.util.Map;

public class ContactDetailsActivity extends AppCompatActivity {

    TextView nameSurname;
    TextView number;
    Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        nameSurname = (TextView)findViewById(R.id.imePrezime);
        number = (TextView)findViewById(R.id.brojKontakta);

        contact = (Contact) getIntent().getSerializableExtra("Contact");

        nameSurname.setText(contact.first_name + " " + contact.last_name);
        number.setText(contact.number);
    }

    public void addFriend(View view){
        final ProgressDialog dialog = ProgressDialog.show(ContactDetailsActivity.this, "", "Adding to friend list...", true);

        String url ="http://p2pmessenger.azurewebsites.net/api/users/addfriend";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        Toast.makeText(ContactDetailsActivity.this, "Friend is added to list", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Toast.makeText(ContactDetailsActivity.this, "Error" + error.networkResponse.toString(), Toast.LENGTH_LONG).show();

            }

        }) {

            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                //number of current user on this device
                params.put("user_number", "849845");
                //friend number
                params.put("friend_number", contact.number);
                return params;
            }

        };
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest, "add friend");
    }
}