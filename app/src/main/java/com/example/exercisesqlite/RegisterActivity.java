package com.example.exercisesqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    int from_Where_I_Am_Coming = 0;
    private DBHelper mydb;

    TextView nama;
    TextView no_telepon;
    TextView email;
    TextView alamat;

    int name_To_Update = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nama = (EditText) findViewById(R.id.editName);
        no_telepon = (EditText) findViewById(R.id.editnotelepon);
        email = (EditText) findViewById(R.id.editemail);
        alamat = (EditText) findViewById(R.id.editalamat);
        Button b = (Button) findViewById(R.id.btnsimpan);
        mydb = new DBHelper(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int Value = extras.getInt("id");
            if (Value > 0) {
                //means this is the view part not the add contact part.
                Cursor rs = mydb.getData(Value);
                name_To_Update = Value;
                rs.moveToFirst();

                final String nam = rs.getString(rs.getColumnIndex(DBHelper.MHS_COLUMN_NAMA));
                final String no_telepon = rs.getString(rs.getColumnIndex(DBHelper.MHS_COLUMN_NO_TELEPON));
                final String email = rs.getString(rs.getColumnIndex(DBHelper.MHS_COLUMN_EMAIL));
                final String alamat = rs.getString(rs.getColumnIndex(DBHelper.MHS_COLUMN_ALAMAT));
                if (!rs.isClosed()) {
                    rs.close();
                }

                b.setVisibility(View.INVISIBLE);
                nama.setText((CharSequence) nam);
                nama.setFocusable(false);
                nama.setClickable(false);

            }
        }
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydb.insertContact(
                        nama.getText().toString(),
                        no_telepon.getText().toString(),
                        email.getText().toString(),
                        alamat.getText().toString()
                );
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String Value = extras.getString("nama");
            } else {
                getMenuInflater().inflate(R.menu.menu_display, menu);
            }
        return true;
    }
}

