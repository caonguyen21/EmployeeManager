package com.example.employeemanager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.employeemanager.ui.TaiKhoanFragment;
import com.example.employeemanager.ui.NhanVienFragment;
import com.example.employeemanager.ui.TrangChuFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.getBoolean("addSuccess")) {
            showFragment();
        }

        // Thiết lập listener cho BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menuHome:
                        TrangChuFragment trangChuFragment = new TrangChuFragment();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, trangChuFragment)
                                .commit();
                        return true;
                    case R.id.menuNhanVien:
                        NhanVienFragment nhanVienFragment = new NhanVienFragment();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, nhanVienFragment)
                                .commit();
                        return true;
                    case R.id.menuTaiKhoan:
                        TaiKhoanFragment taiKhoanFragment = new TaiKhoanFragment();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, taiKhoanFragment)
                                .commit();
                        return true;
                }
                return false;
            }
        });
    }
    public void showFragment() {
        NhanVienFragment nhanVienFragment = new NhanVienFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, nhanVienFragment)
                .commit();
    }
}