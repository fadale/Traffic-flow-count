package com.example.alawael.trafficflowcount;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Main2Activity extends AppCompatActivity {

    String sheetno,day,date,timefrom,timeto,weather,street,positiona,postionb;
    SQL_DB sql_db=new SQL_DB(this);

    EditText edit_sheet,edit_street,edit_position_A,edit_position_B,daymenu,edit_weather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        edit_sheet=findViewById(R.id.edit_sheet);
        edit_street = findViewById(R.id.edit_street);
        edit_position_A=findViewById(R.id.edit_position_from);
        edit_position_B=findViewById(R.id.edit_position_to);
        edit_weather=findViewById(R.id.edit_text_weather_menu);
        daymenu=findViewById(R.id.edit_text_day_menu);
        final Button button = findViewById(R.id.start_btn);
        daymenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu =new PopupMenu(Main2Activity.this,daymenu);
                popupMenu.getMenuInflater().inflate(R.menu.day_menu,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        daymenu.setText(item.getTitle());
                        day=daymenu.getText().toString();
                        return  true;
                    }
                });
                popupMenu.show();
            }
        });




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sheetno = edit_sheet.getText().toString();
                Calendar calendar = Calendar.getInstance();
                @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("yyyy / MM / dd ");
                @SuppressLint("SimpleDateFormat") SimpleDateFormat mdtime = new SimpleDateFormat("HH:mm");
                timefrom =mdtime.format(calendar.getTime());
                date =mdformat.format(calendar.getTime());
                timeto=mdtime.format(calendar.getTime());
                street=edit_street.getText().toString();
                positiona=edit_position_A.getText().toString();
                postionb=edit_position_B.getText().toString();
                weather=edit_weather.getText().toString();
                if(sheckresult(Integer.parseInt(sheetno))){

                    Toast.makeText(Main2Activity.this,"The sheet is Already exists",Toast.LENGTH_SHORT).show();

                }else{
                    boolean result= sql_db.insert_sheet(sheetno,day,date,timefrom,timeto,weather,street,positiona,postionb);
                    if(result == true) {
                        Intent intent = new Intent(Main2Activity.this, Count_Cars_main.class);
                        intent.putExtra("sheetnumber",sheetno);
                        Toast.makeText(Main2Activity.this," "+result ,Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }else{
                        Toast.makeText(Main2Activity.this," "+result ,Toast.LENGTH_SHORT).show();
                    }

                    edit_sheet.setText("");
                    daymenu.setText("");
                    edit_weather.setText("");
                    edit_street.setText("");
                    edit_position_A.setText("");
                    edit_position_B.setText("");
                }
            }


        });
    }

    private boolean sheckresult(int sheetno) {

        Cursor cursor = sql_db.shecksheetno(sheetno);
        if(cursor.moveToFirst()){

            do {
                int s = Integer.parseInt(cursor.getString(cursor.getColumnIndex("SHEET_NO")));
                if (s == sheetno) {

                    return true;

                }
            }while (cursor.moveToNext());

        }
        return false;
    }
}
