package com.sumon.sqlitedatabase1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    DatabaseHelper databaseHelper;

    private EditText nameEditText, ageEditText, genderEditText, idEditText;
    //private AutoCompleteTextView genderAutoCompleteText;
    String[] gender = {"Male", "Female", "Others"};

    private Button addButton, displayAllData, updateData, deleteData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        nameEditText = (EditText) findViewById(R.id.nameEditTextId);
        ageEditText = (EditText) findViewById(R.id.ageEditTextId);
        genderEditText = (EditText) findViewById(R.id.genderEditTextId);

        //Create Array Adapter
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_singlechoice, gender);
        //Find TextView control
        // AutoCompleteTextView acTextView = (AutoCompleteTextView) findViewById(R.id.genderAutoCompleteTextViewId);
        //Set the number of characters the user must type before the drop down list is shown
        //acTextView.setThreshold(1);
        //Set the adapter
        //acTextView.setAdapter(adapter);


        idEditText = (EditText) findViewById(R.id.idEditTextId);

        addButton = (Button) findViewById(R.id.addButtonId);
        displayAllData = (Button) findViewById(R.id.displayAllDataButtonId);
        updateData = (Button) findViewById(R.id.updateDataButtonId);
        deleteData = (Button) findViewById(R.id.deleteDataButtonId);

        addButton.setOnClickListener(this);
        displayAllData.setOnClickListener(this);
        updateData.setOnClickListener(this);
        deleteData.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        String name = nameEditText.getText().toString();
        String age = ageEditText.getText().toString();
        String gender = genderEditText.getText().toString();
        String id = idEditText.getText().toString();

        if (v.getId() == R.id.addButtonId) {

            long rowId = databaseHelper.insertData(name, age, gender);

            if (rowId == -1) {

                Toast.makeText(getApplicationContext(), "Unseccessful", Toast.LENGTH_LONG).show();

            } else {

                Toast.makeText(getApplicationContext(), "Row " + rowId + " is seccessfully inserted", Toast.LENGTH_LONG).show();
            }

            finish();
            startActivity(getIntent());
        }

        if (v.getId() == R.id.displayAllDataButtonId) {

            Cursor cursor = databaseHelper.displayAllData();

            if (cursor.getCount() == 0) {

                //there is no data so we will diaplay message
                showData("Error", "No data found");
                return;
            }

            StringBuffer stringBuffer = new StringBuffer();

            while (cursor.moveToNext()) {

                stringBuffer.append("ID :" + cursor.getString(0) + "\n");
                stringBuffer.append("Name :" + cursor.getString(1) + "\n");
                stringBuffer.append("Age :" + cursor.getString(2) + "\n");
                stringBuffer.append("Gender :" + cursor.getString(3) + "\n\n\n");
            }

            showData("Person Details", stringBuffer.toString());


        } else if (v.getId() == R.id.updateDataButtonId) {

            Boolean isUpdated = databaseHelper.updateData(id, name, age, gender);

            if (isUpdated == true) {

                Toast.makeText(getApplicationContext(), "Data Updated Sucessfully", Toast.LENGTH_LONG).show();

            } else {

                Toast.makeText(getApplicationContext(), "Data is not Updated", Toast.LENGTH_LONG).show();
            }

            finish();
            startActivity(getIntent());

        }

        if (v.getId() == R.id.deleteDataButtonId) {
            int value = databaseHelper.deleteData(id);

            if (value > 0) {
                Toast.makeText(getApplicationContext(), "Data is Deleted", Toast.LENGTH_LONG).show();
            } else {

                Toast.makeText(getApplicationContext(), "Data is not Deleted", Toast.LENGTH_LONG).show();
            }

            finish();
            startActivity(getIntent());

        }

    }

    public void showData(String title, String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.show();

    }
}