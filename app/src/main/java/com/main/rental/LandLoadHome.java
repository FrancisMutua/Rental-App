package com.main.rental;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LandLoadHome extends AppCompatActivity {
    CardView add_tenant,payment_history,remove_tenant,complains_id,add_rental,my_tenants_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land_load_home);
        add_tenant=findViewById(R.id.add_admin);
        payment_history=findViewById(R.id.payment_history);
        remove_tenant=findViewById(R.id.remove_tenant);
        complains_id=findViewById(R.id.complains_id);
        my_tenants_id=findViewById(R.id.my_tenants_id);
        add_tenant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LandLoadHome.this,AddTenant.class));
            }
        });
        payment_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LandLoadHome.this,PaymentsAnalysis.class));
            }
        });
        remove_tenant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LandLoadHome.this,ClearTenant.class));            }
        });
        complains_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LandLoadHome.this,Complains.class));
            }
        });
        my_tenants_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LandLoadHome.this,MyTenants.class));
            }
        });
    }
}