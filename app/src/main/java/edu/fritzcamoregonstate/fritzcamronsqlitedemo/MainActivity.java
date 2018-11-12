package edu.fritzcamoregonstate.fritzcamronsqlitedemo;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.database.Cursor;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper myDb;
    private EditText fNameField, lNameField, gradeField;
    private Button addDataButton, viewAllButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);

        fNameField = findViewById(R.id.first_name_field);
        lNameField = findViewById(R.id.last_name_field);
        gradeField = findViewById(R.id.grade_field);

        addDataButton = findViewById(R.id.add_data_button);
        viewAllButton = findViewById(R.id.view_all_button);

        addDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFields();
            }
        });


    }

    private void getFields() {
        boolean isInserted = myDb.insertData(fNameField.getText().toString(), lNameField.getText().toString(), gradeField.getText().toString());

        if(isInserted) {
            Toast.makeText(MainActivity.this, "Record Inserted", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(MainActivity.this, "Record NOT Inserted", Toast.LENGTH_SHORT).show();
        }
    }

    private void viewAll() {
        Cursor cursor = myDb.getAllData();

        if(cursor.getCount() == 0 ){
            showMessage("Error", "No data found");
            return;
        }

        StringBuffer buffer = new StringBuffer();
        while(cursor.moveToNext()) {
            buffer.append("Id: " + cursor.getString(0) + "\n");
            buffer.append("First: " + cursor.getString(1) + "\n");
            buffer.append("Last: " + cursor.getString(2) + "\n");
            buffer.append("Grade: " + cursor.getString(3) + "\n");
        }

        showMessage("Data", buffer.toString());

    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
