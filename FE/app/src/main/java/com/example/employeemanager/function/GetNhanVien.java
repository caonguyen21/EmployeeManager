package com.example.employeemanager.function;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.employeemanager.R;
import com.example.employeemanager.model.NhanVien;

public class GetNhanVien extends AppCompatActivity {
    private TextView txthoTen, txtTuoi, txtgioiTinh, txtdiaChi, txtchucVu, txtphongBan;
    NhanVien nhanVien;
    ImageView imgNhanVien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_nhan_vien);
        init();
        toolBar();
        thongtinNhanVien();
    }

    public void init() {
        txthoTen = findViewById(R.id.tvHoTen);
        txtTuoi = findViewById(R.id.tvTuoi);
        txtgioiTinh = findViewById(R.id.tvGioiTinh);
        txtdiaChi = findViewById(R.id.tvDiaChi);
        txtchucVu = findViewById(R.id.tvChucVu);
        txtphongBan = findViewById(R.id.tvPhongBan);
        imgNhanVien = findViewById(R.id.imgNhanVien);
    }

    @SuppressLint("SetTextI18n")
    public void thongtinNhanVien() {
        // lấy intent
        Intent intent = getIntent();
        nhanVien = (NhanVien) intent.getSerializableExtra("clickNV");
        txthoTen.setText("Họ tên: " + nhanVien.getHoTen());
        txtTuoi.setText("Tuổi: " + String.valueOf(nhanVien.getTuoi()));
        txtgioiTinh.setText("Giới tính: " + nhanVien.getGioiTinh());
        txtdiaChi.setText("Địa chỉ: " + nhanVien.getDiaChi());
        txtchucVu.setText("Chức vụ: " + nhanVien.getChucVu());
        txtphongBan.setText("Phòng ban: " + nhanVien.getPhongBan());
        Glide.with(this)
                .load(nhanVien.getHinhAnh())
                .placeholder(R.drawable.baseline_image_24)
                .into(imgNhanVien);
    }

    public void toolBar() {
        Toolbar toolbar = findViewById(R.id.toolbarDetailNhanvien);
        setSupportActionBar(toolbar);
        // Set the toolbar title
        getSupportActionBar().setTitle("Thông tin nhân viên");
        // Set a navigation icon
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform your desired action here (e.g., go back to the previous activity)
                onBackPressed();
            }
        });
    }
}
