package com.example.employeemanager.function;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.employeemanager.MainActivity;
import com.example.employeemanager.R;
import com.example.employeemanager.api.ApiService;
import com.example.employeemanager.model.TaiKhoan;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    private static final String MY_PREFERENCE_NAME = "USER_ID";
    private static final String MY_PREFERENCE_PASS = "USER_PASS";
    private Button btnDangNhap, dangky;
    private TextInputEditText matkhau, tentaikhoan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        btnDangNhap.setOnClickListener(v -> clickDangNhap());
        dangky.setOnClickListener(v -> clickDangKy());
    }

    private void clickDangKy() {
        startActivity(new Intent(this, Register.class));
        finish();
    }

    private void clickDangNhap() {
        String name = Objects.requireNonNull(tentaikhoan.getText()).toString().trim();
        String pass = Objects.requireNonNull(matkhau.getText()).toString().trim();
        TaiKhoan taiKhoan = new TaiKhoan(name, pass);
        if (Validation()) {
            ApiService.apiService.Login(taiKhoan).enqueue(new Callback<TaiKhoan>() {
                @Override
                public void onResponse(@NonNull Call<TaiKhoan> call, @NonNull Response<TaiKhoan> response) {
                    TaiKhoan taiKhoan1 = response.body();
                    if (taiKhoan1 != null) {
                        if (taiKhoan1.isTrangThai()) {
                            SharedPreferences sharedPref = getSharedPreferences(MY_PREFERENCE_NAME, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("value", taiKhoan1.get_id());
                            editor.apply();
                            //share password
                            SharedPreferences sharedPreferences = getSharedPreferences(MY_PREFERENCE_PASS, Context.MODE_PRIVATE);
                            SharedPreferences.Editor edit = sharedPreferences.edit();
                            edit.putString("value", pass);
                            edit.apply();
                            Dialog();
                        } else {
                            Dialog3();
                        }
                    } else {
                        Dialog2();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<TaiKhoan> call, @NonNull Throwable t) {
                    Log.e("Sign in:Lỗi", t.toString());
                }
            });
        }
    }

    private void setErrorAndFocus(TextInputEditText editText, String errorMessage) {
        editText.setError(errorMessage);
        editText.requestFocus();
    }

    private boolean Validation() {
        String name = Objects.requireNonNull(tentaikhoan.getText()).toString().trim();
        String pass = Objects.requireNonNull(matkhau.getText()).toString().trim();

        if (TextUtils.isEmpty(name)) {
            setErrorAndFocus(tentaikhoan, "Tên tài khoản không được để trống");
            return false;
        }
        if (name.length() < 6 || name.length() > 20) {
            setErrorAndFocus(tentaikhoan, "Tên tài khoản phải từ 6 đến 20 kí tự");
            return false;
        }
        if (TextUtils.isEmpty(pass)) {
            setErrorAndFocus(matkhau, "Mật khẩu không được để trống");
            return false;
        }
        if (pass.length() < 6) {
            setErrorAndFocus(matkhau, "Mật khẩu phải ít nhất 6 kí tự");
            return false;
        }
        return true;
    }

    //Tạo dialog thông báo
    private void Dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Đăng nhập thàng công")
                .setIcon(R.drawable.ic_notifications_red)
                .setTitle("Thông báo");
        builder.setPositiveButton("OK", (dialog, which) -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        // Set button text color
        Button okButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        okButton.setTextColor(Color.BLACK);
    }

    private void Dialog2() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Tài khoản hay mật khẩu không đúng!!!")
                .setIcon(R.drawable.ic_notifications_red)
                .setTitle("Thông báo");
        builder.setPositiveButton("OK", (dialog, which) -> {
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        // Set button text color
        Button okButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        okButton.setTextColor(Color.BLACK);
    }

    private void Dialog3() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Tài khoản đã bị đóng băng! Xin liên hệ quản trị viên ")
                .setIcon(R.drawable.ic_notifications_red)
                .setTitle("Thông báo");
        builder.setPositiveButton("OK", (dialog, which) -> {
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        // Set button text color
        Button okButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        okButton.setTextColor(Color.BLACK);
    }

    public void init() {
        tentaikhoan = findViewById(R.id.etTaiKhoanlog);
        matkhau = findViewById(R.id.etMatKhaulog);
        btnDangNhap = findViewById(R.id.btnDangNhaplog);
        dangky = findViewById(R.id.btnDangKylog);
    }
}