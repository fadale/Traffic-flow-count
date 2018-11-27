package com.example.alawael.trafficflowcount;


import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class Count_Cars_main extends AppCompatActivity {

    SQL_DB sql_db=new SQL_DB(this);

    int Busses=0;int LGV=0;int HGV=0;int bicycle=0;int Motorcycle=0;int Cara=0;int Tuktuk=0;
    int passenger_car=0;
    int sheetno;
    ArrayList<String> cars;
    ArrayList<Integer>count_cars = new ArrayList<>();
    ImageButton btn_excel,img_btn_passengercat,img_btn_busses,img_btn_lgv,img_btn_hgv,img_btn_bicycle,img_btn_motocycle,img_btn_cara,img_btn_tuktuk;

    TextView car_name,count_car;
    Button btn_stop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count__cars_main);
        car_name=findViewById(R.id.car_name);
        count_car=findViewById(R.id.count_car);
        img_btn_passengercat=findViewById(R.id.passenger_car);
        img_btn_busses=findViewById(R.id.busses);
        img_btn_lgv=findViewById(R.id.lgv);
        img_btn_hgv=findViewById(R.id.hgv);
        img_btn_bicycle=findViewById(R.id.bicycle);
        img_btn_motocycle=findViewById(R.id.motorcycle);
        img_btn_cara=findViewById(R.id.cara);
        img_btn_tuktuk=findViewById(R.id.tuktuk);
        btn_stop=findViewById(R.id.stop_count_btn);
        btn_excel=findViewById(R.id.export_excel_btn);

        sheetno= Integer.parseInt(getIntent().getStringExtra("sheetnumber"));

        Thread th=new Thread(){

            @Override
            public void run() {
                while (!isInterrupted()){

                    try{
                        Thread.sleep(900000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                count_cars=new ArrayList<>();
                                count_cars.add(0,passenger_car);
                                count_cars.add(1,Busses);
                                count_cars.add(2,LGV);
                                count_cars.add(3,HGV);
                                count_cars.add(4,bicycle);
                                count_cars.add(5,Motorcycle);
                                count_cars.add(6,Cara);
                                count_cars.add(7,Tuktuk);

                                for(int i=0;i<cars.size()&& i<count_cars.size();i++) {

                                    boolean result =sql_db.inset_count_car(cars.get(i),String.valueOf(count_cars.get(i)),sheetno);
                                    if (!result) {
                                        Toast.makeText(Count_Cars_main.this,"Not save Data",Toast.LENGTH_LONG).show();
                                    }
                                }
                                Busses=0; LGV=0; HGV=0; bicycle=0; Motorcycle=0; Cara=0; Tuktuk=0;
                                passenger_car=0;

                            }
                        });
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        };
        th.start();
        cars=new ArrayList<>();
        cars.add(0,"Passenger Car");
        cars.add(1,"Busses");
        cars.add(2,"LGV");
        cars.add(3,"HGV");
        cars.add(4,"Bicycle");
        cars.add(5,"Motorcycle");
        cars.add(6,"Carat");
        cars.add(7,"Toktok");

        img_btn_passengercat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                passenger_car+=1;
                count_car.setText(""+passenger_car);
                car_name.setText(""+cars.get(0));
            }
        });

        img_btn_busses.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Busses+=1;
                count_car.setText(""+Busses);
                car_name.setText(cars.get(1));
            }
        });

        img_btn_lgv.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                LGV+=1;
                count_car.setText(""+LGV);
                car_name.setText(cars.get(2));
            }
        });

        img_btn_hgv.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                HGV+=1;
                count_car.setText(""+HGV);
                car_name.setText(cars.get(3));
            }
        });

        img_btn_bicycle.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                bicycle+=1;
                count_car.setText(""+bicycle);
                car_name.setText(cars.get(4));
            }
        });

        img_btn_motocycle.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                Motorcycle+=1;
                count_car.setText(""+Motorcycle);
                car_name.setText(cars.get(5));
            }
        });

        img_btn_cara.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                Cara+=1;

                count_car.setText(""+Cara);
                car_name.setText(cars.get(6));
            }
        });

        img_btn_tuktuk.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                Tuktuk+=1;
                count_car.setText(""+Tuktuk);
                car_name.setText(cars.get(7));
            }
        });

        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_btn_passengercat.setEnabled(false);
                img_btn_busses.setEnabled(false);
                img_btn_bicycle.setEnabled(false);
                img_btn_cara.setEnabled(false);
                img_btn_hgv.setEnabled(false);
                img_btn_lgv.setEnabled(false);
                img_btn_tuktuk.setEnabled(false);
                img_btn_motocycle.setEnabled(false);
                Calendar calendar = Calendar.getInstance();
                @SuppressLint("SimpleDateFormat") SimpleDateFormat mdtime = new SimpleDateFormat("HH:mm");
                String timeto=mdtime.format(calendar.getTime());
                sheetno= Integer.parseInt(getIntent().getStringExtra("sheetnumber"));
                sql_db.update_timeto(timeto,sheetno);


                count_cars=new ArrayList<>();
                count_cars.add(0,passenger_car);
                count_cars.add(1,Busses);
                count_cars.add(2,LGV);
                count_cars.add(3,HGV);
                count_cars.add(4,bicycle);
                count_cars.add(5,Motorcycle);
                count_cars.add(6,Cara);
                count_cars.add(7,Tuktuk);

                for(int i=0;i<cars.size()&& i<count_cars.size();i++) {

                    boolean result =sql_db.inset_count_car(cars.get(i),String.valueOf(count_cars.get(i)),sheetno);
                    if (!result)
                        Toast.makeText(Count_Cars_main.this,"Not save Data",Toast.LENGTH_LONG).show();

                }
                btn_stop.setEnabled(false);
            }

        });

        btn_excel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cur_sheet=sql_db.select_sheet(sheetno);
                Cursor cur_cars=sql_db.select_cars(sheetno);

                File sd = Environment.getExternalStorageDirectory();
                String csvFile = "mydata.xls";
                File directory = new File(sd.getAbsolutePath());
//create directory if not exist
                if (!directory.isDirectory()) {
                    directory.mkdirs();
                }

                File file = new File(directory,csvFile);
                WorkbookSettings wbSettings = new WorkbookSettings();
                wbSettings.setLocale(new Locale("en", "EN"));

                try {
                    WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
                    WritableSheet sheet = workbook.createSheet("Traffic Flow Count", 0);
                    sheet.addCell(new Label(0, 0, "University College of Applied Sciences"));
                    sheet.addCell(new Label(0, 1, "Faculty of Engineering"));
                    sheet.addCell(new Label(0, 2, "Civil Engineer Department"));
                    sheet.addCell(new Label(0, 3, "Building Techonlogy"));
                    sheet.addCell(new Label(1, 13, "Sheet No:"));
                    sheet.addCell(new Label(1, 14, "Street Name:"));
                    sheet.addCell(new Label(1, 15, "Time:"));
                    sheet.addCell(new Label(1, 16, "Position:"));
                    sheet.addCell(new Label(1, 20, "Passenger Car\t\t"));
                    sheet.addCell(new Label(2, 8, "Traffic Flow count"));
                    sheet.addCell(new Label(2, 20, "Busses"));
                    sheet.addCell(new Label(3, 20, "Trucks\t"));
                    sheet.addCell(new Label(3, 21, "L.G.V"));
                    sheet.addCell(new Label(3, 15, "To"));
                    sheet.addCell(new Label(4, 21, "H.G.V"));
                    sheet.addCell(new Label(5, 20, "bicycle"));
                    sheet.addCell(new Label(5, 0, "1st semester 2018/2019"));
                    sheet.addCell(new Label(5, 1, "Instructor:Dr.Yahya Sarraj"));
                    sheet.addCell(new Label(5, 2, "Highway & transpotation (I)"));
                    sheet.addCell(new Label(6, 13, "Day:"));
                    sheet.addCell(new Label(6, 14, "Date:"));
                    sheet.addCell(new Label(6, 15, "Weather:"));
                    sheet.addCell(new Label(6, 20, "Motorcycle"));
                    sheet.addCell(new Label(7, 20, "Toktok"));
                    sheet.addCell(new Label(8, 20, "Carat"));

                    if (cur_sheet.moveToFirst()) {
                        do {
                            sheet.addCell(new Label(2, 13, cur_sheet.getString(cur_sheet.getColumnIndex("SHEET_NO"))));
                            sheet.addCell(new Label(2, 14, cur_sheet.getString(cur_sheet.getColumnIndex("STREET_NAME"))));
                            sheet.addCell(new Label(2, 15, cur_sheet.getString(cur_sheet.getColumnIndex("TIME_FROM"))));
                            sheet.addCell(new Label(2, 16, cur_sheet.getString(cur_sheet.getColumnIndex("POSITION_A"))));
                            sheet.addCell(new Label(4, 15, cur_sheet.getString(cur_sheet.getColumnIndex("TIME_TO"))));
                            sheet.addCell(new Label(4, 16, cur_sheet.getString(cur_sheet.getColumnIndex("POSITION_B"))));
                            sheet.addCell(new Label(7, 14, cur_sheet.getString(cur_sheet.getColumnIndex("SHEET_DATE"))));
                            sheet.addCell(new Label(7, 15, cur_sheet.getString(cur_sheet.getColumnIndex("WEATHER"))));
                            sheet.addCell(new Label(7, 13, cur_sheet.getString(cur_sheet.getColumnIndex("SHEET_DAY"))));
                        } while (cur_sheet.moveToNext());
                    }//end if curser sheet data

                    if (cur_cars.moveToFirst()) {
                        int j = 22;
                        int i = 1;
                        String count_car;
                        do {
                            count_car = cur_cars.getString(cur_cars.getColumnIndex("COUNT_CTG"));
                            sheet.addCell(new Label(i, j, count_car));
                            i ++;
                            if (i == 9) {
                                j++;
                                i = 1;
                            }
                        } while (cur_cars.moveToNext());
                    }
                    //closing cursor

                    cur_sheet.close();
                    cur_cars.close();
                    workbook.write();
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (RowsExceededException e) {
                    e.printStackTrace();
                } catch (WriteException e) {
                    e.printStackTrace();
                }

            }
        });

    }
    public String getdatetime(){
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat mdtime = new SimpleDateFormat("HH-mm-ss");
        String excel=mdformat.format(calendar.getTime());
        String ex=mdtime.format(calendar.getTime());
        return excel+ex;
    }
}