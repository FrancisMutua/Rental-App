package com.main.rental;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.eazegraph.lib.charts.PieChart;

public class TenantHome extends AppCompatActivity {
    PieChart pie_chart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_home);
        pie_chart=findViewById(R.id.pie_chart);
    }
}