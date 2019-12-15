package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

    String payment_type;
    Date paymentDate;
    Boolean dataPickerView_Flag = false;
    EditText to;
    EditText from;
    TextView errorFrom, errorTo, errorDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Spinner paymentType_spinner = findViewById(R.id.spinner_paymentType);
        to = findViewById(R.id.editText_to);
        from = findViewById(R.id.editText_from);
        errorFrom = findViewById(R.id.errorFrom_text);
        errorDate = findViewById(R.id.error_data_text);
        errorTo = findViewById(R.id.errorTo_text);
        to.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                errorTo.setVisibility(View.GONE);
                return false;
            }
        });
        from.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                errorFrom.setVisibility(View.GONE);
                return false;
            }
        });
        final TextView date_display = findViewById(R.id.text_date);
        final DatePicker datePicker = findViewById(R.id.picker_date);
        Button selectDate = findViewById(R.id.button_chooseData);
        Button saveButton = findViewById(R.id.saveButton);
        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!dataPickerView_Flag) {
                    errorDate.setVisibility(View.GONE);
                    datePicker.setVisibility(View.VISIBLE);
                }
                else
                    datePicker.setVisibility(View.GONE);

                dataPickerView_Flag = !dataPickerView_Flag;
            }
        });
        final ArrayList<String> list = new ArrayList<>();
        list.add("Cash");
        list.add("Debit Card");
        list.add("Credit Card");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this, R.layout.spinner_layout ,list);
        paymentType_spinner.setAdapter(arrayAdapter);
        paymentType_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                payment_type = list.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                datePicker.setVisibility(View.GONE);
                String date = " "+monthOfYear+"/"+dayOfMonth+"/"+year;
                dataPickerView_Flag = false;
                date_display.setText(date);
                paymentDate = new GregorianCalendar(year, monthOfYear, dayOfMonth).getTime();
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(allValid())
                    storeDatainDB();
            }

        });
    }

    Boolean allValid(){
        Boolean flag = false;
        if(this.from.getText().equals("")){
            this.errorFrom.setVisibility(View.VISIBLE);
            flag = true;
        }
        if(this.to.getText().equals("")) {
            this.errorTo.setVisibility(View.VISIBLE);
            flag = true;
        }
        if(payment_type.equals("")) {
            this.errorDate.setVisibility(View.VISIBLE);
            flag = true;
        }
        return flag;
    }

    void storeDatainDB(){

    }
}
