package com.share4happy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.share4happy.model.Location;
import com.share4happy.model.ModelCommom;
import com.share4happy.model.OverviewInfo;
import com.share4happy.model.Today;
import com.share4happy.model.Total;
import com.share4happy.service.RetroService;
import com.share4happy.service.ServiceInfo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    TabHost tabHost;
    TextView txtTotalInternaldeath, txtTotalInternaltreating, txtTotalInternalcases, txtTotalInternalrecovered;
    TextView txtTotalWorlddeath, txtTotalWorldtreating, txtTotalWorldcases, txtTotalWorldrecovered;
    TextView txtTodayInternaldeath, txtTodayInternaltreating, txtTodayInternalcases, txtTodayInternalrecovered;
    TextView txtTodayWorlddeath, txtTodayWorldtreating, txtTodayWorldcases, txtTodayWorldrecovered;

    ListView lsOverviews;
    ListView lsLocations;
    List<OverviewInfo> overviewInfoArrayList = new ArrayList<>();
    List<Location> locationList = new ArrayList<>();

    OverviewAdapter overviewAdapter;
    LocationAdapter locationAdapter;
    RetroService retroService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        addEvent();
    }

    private void addEvent() {
        refreshData();
        Toast.makeText(MainActivity.this,"Đang tải dữ liệu sử dụng volley",Toast.LENGTH_SHORT).show();
        useRetro();
    }

    private void refreshData() {
        txtTotalInternaldeath.setText("");
        txtTotalInternaltreating.setText("");
        txtTotalInternalcases.setText("");
        txtTotalInternalrecovered.setText("");

        //Gán dữ liệu cho frame trong Tab tổng, phần Thế giới
        txtTotalWorlddeath.setText("");
        txtTotalWorldtreating.setText("");
        txtTotalWorldcases.setText("");
        txtTotalWorldrecovered.setText("");

        //Gán dữ liệu cho frame trong Tab Hôm nay, phần trong nước
        txtTodayInternaldeath.setText("");
        txtTodayInternaltreating.setText("");
        txtTodayInternalcases.setText("");
        txtTodayInternalrecovered.setText("");

        //Gán dữ liệu cho frame trong Tab Hôm nay, phần thế giới
        txtTodayWorlddeath.setText("");
        txtTodayWorldtreating.setText("");
        txtTodayWorldcases.setText("");
        txtTodayWorldrecovered.setText("");
    }

    private void useRetro() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServiceInfo.Base_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retroService = retrofit.create(RetroService.class);

        Call<ModelCommom> modelCommomCall = retroService.getAll();
        modelCommomCall.enqueue(new Callback<ModelCommom>() {
            @Override
            public void onResponse(Call<ModelCommom> call, Response<ModelCommom> response) {
                List<Location> locations = response.body().getLocations();
                Today today = response.body().getToday();
                Total total = response.body().getTotal();
                List<OverviewInfo> overviewInfos = response.body().getOverview();

                //Gán dữ liệu cho frame trong Tab tổng, phần Trong nước
                txtTotalInternaldeath.setText(String.valueOf(total.getInfoInternal().getDeath()));
                txtTotalInternaltreating.setText(String.valueOf(total.getInfoInternal().getTreating()));
                txtTotalInternalcases.setText(String.valueOf(total.getInfoInternal().getCases()));
                txtTotalInternalrecovered.setText(String.valueOf(total.getInfoInternal().getRecovered()));

                //Gán dữ liệu cho frame trong Tab tổng, phần Thế giới
                txtTotalWorlddeath.setText(String.valueOf(total.getInfoWorld().getDeath()));
                txtTotalWorldtreating.setText(String.valueOf(total.getInfoWorld().getTreating()));
                txtTotalWorldcases.setText(String.valueOf(total.getInfoWorld().getCases()));
                txtTotalWorldrecovered.setText(String.valueOf(total.getInfoWorld().getRecovered()));

                //Gán dữ liệu cho frame trong Tab Hôm nay, phần trong nước
                txtTodayInternaldeath.setText(String.valueOf(today.getInfoInternal().getDeath()));
                txtTodayInternaltreating.setText(String.valueOf(today.getInfoInternal().getTreating()));
                txtTodayInternalcases.setText(String.valueOf(today.getInfoInternal().getCases()));
                txtTodayInternalrecovered.setText(String.valueOf(today.getInfoInternal().getRecovered()));

                //Gán dữ liệu cho frame trong Tab Hôm nay, phần thế giới
                txtTodayWorlddeath.setText(String.valueOf(today.getInfoWorld().getDeath()));
                txtTodayWorldtreating.setText(String.valueOf(today.getInfoWorld().getTreating()));
                txtTodayWorldcases.setText(String.valueOf(today.getInfoWorld().getCases()));
                txtTodayWorldrecovered.setText(String.valueOf(today.getInfoWorld().getRecovered()));

                //Gán dữ liệu cho frame trong Tab TỔNG
                overviewInfoArrayList = overviewInfos;
                overviewAdapter = new OverviewAdapter(MainActivity.this, R.layout.item_overview, overviewInfoArrayList);
                lsOverviews.setAdapter(overviewAdapter);

                //Gán dữ liệu cho frame trong Tab THEO TỈNH
                locationList = locations;
                locationAdapter = new LocationAdapter(MainActivity.this, R.layout.item_location, locationList);
                lsLocations.setAdapter(locationAdapter);

            }

            @Override
            public void onFailure(Call<ModelCommom> call, Throwable t) {

            }
        });
    }

    private void addControls() {
        txtTotalInternaldeath = findViewById(R.id.deathInternalTotal);
        txtTotalInternaltreating = findViewById(R.id.treatingInternalTotal);
        txtTotalInternalcases = findViewById(R.id.casesInternalTotal);
        txtTotalInternalrecovered = findViewById(R.id.recoveredInternalTotal);

        txtTotalWorlddeath = findViewById(R.id.deathWordTotal);
        txtTotalWorldtreating = findViewById(R.id.treatingWorldTotal);
        txtTotalWorldcases = findViewById(R.id.casesWorldTotal);
        txtTotalWorldrecovered = findViewById(R.id.recoveredWorldTotal);

        txtTodayInternaldeath = findViewById(R.id.deathTodayInternal);
        txtTodayInternaltreating = findViewById(R.id.treatingTodayInternal);
        txtTodayInternalcases = findViewById(R.id.casesTodayInternal);
        txtTodayInternalrecovered = findViewById(R.id.recoveredTodayInternal);

        txtTodayWorlddeath = findViewById(R.id.deathTodayWord);
        txtTodayWorldtreating = findViewById(R.id.treatingTodayWorld);
        txtTodayWorldcases = findViewById(R.id.casesTodayWorld);
        txtTodayWorldrecovered = findViewById(R.id.recoveredTodayWorld);

        lsOverviews = findViewById(R.id.ls_overview);
        lsLocations = findViewById(R.id.ls_locations);


        tabHost = findViewById(R.id.tabhosst);
        tabHost.setup();

        //Cài đặt cho Tab THEO TỈNH
        TabHost.TabSpec tabLocations = tabHost.newTabSpec("t1");
        tabLocations.setIndicator("Theo tỉnh");
        tabLocations.setContent(R.id.tabLocations);
        tabHost.addTab(tabLocations);

        //Cài đặt cho Tab Hôm nay
        TabHost.TabSpec tabToday = tabHost.newTabSpec("t2");
        tabToday.setIndicator("Hôm nay");
        tabToday.setContent(R.id.tabToday);
        tabHost.addTab(tabToday);

        //Cài đặt cho Tab Tổng quan
        TabHost.TabSpec tabOverview = tabHost.newTabSpec("t3");
        tabOverview.setIndicator("Tổng quan");
        tabOverview.setContent(R.id.tabOverview);
        tabHost.addTab(tabOverview);

        //Cài đặt cho Tab Tổng
        TabHost.TabSpec tabTotal = tabHost.newTabSpec("t4");
        tabTotal.setIndicator("Tổng");
        tabTotal.setContent(R.id.tabTotal);
        tabHost.addTab(tabTotal);
    }
}