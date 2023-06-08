package com.example.employeemanager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.employeemanager.ui.NhanVienFragment;
import com.example.employeemanager.ui.TaiKhoanFragment;
import com.example.employeemanager.ui.TrangChuFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TrangChuFragment trangChuFragment = new TrangChuFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, trangChuFragment)
                .commit();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.getBoolean("addSuccess")) {
                showFragmentNhanVien();
            } else if (extras.getBoolean("defaultFragment")) {
                showFragmentTrangChu();
            }
        }

        // Thiết lập listener cho BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menuHome:
                        showFragmentTrangChu();
                        return true;
                    case R.id.menuNhanVien:
                        showFragmentNhanVien();
                        return true;
                    case R.id.menuTaiKhoan:
                        showFragmentTaiKhoan();
                        return true;
                }
                return false;
            }
        });
    }

    public void showFragmentTrangChu() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.getMenu().findItem(R.id.menuHome).setChecked(true);

        TrangChuFragment trangChuFragment = new TrangChuFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, trangChuFragment)
                .commit();
    }

    public void showFragmentNhanVien() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.getMenu().findItem(R.id.menuNhanVien).setChecked(true);

        NhanVienFragment nhanVienFragment = new NhanVienFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, nhanVienFragment)
                .commit();
    }

    public void showFragmentTaiKhoan() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.getMenu().findItem(R.id.menuTaiKhoan).setChecked(true);

        TaiKhoanFragment taiKhoanFragment = new TaiKhoanFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, taiKhoanFragment)
                .commit();
    }
}
