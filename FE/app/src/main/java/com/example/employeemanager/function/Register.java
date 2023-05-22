package com.example.employeemanager.function;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.employeemanager.R;
import com.example.employeemanager.api.ApiService;
import com.example.employeemanager.model.TaiKhoan;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {
    private static final String MY_PREFERENCE_NAME = "USER_ID";
    private List<TaiKhoan> list;
    TextInputEditText etTaiKhoan, etHoTen, etMatKhau, etEmail, etNhapLaiMatKhau;
    Button btnDangKy, btnDangNhap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        getTaiKhoan();
        btnDangNhap.setOnClickListener(v -> clickQuayLai());
        btnDangKy.setOnClickListener(v -> clickDangKy());
    }

    private void clickDangKy() {
        String name = Objects.requireNonNull(etTaiKhoan.getText()).toString().trim();
        String hoten = Objects.requireNonNull(etHoTen.getText()).toString().trim();
        String pass = Objects.requireNonNull(etMatKhau.getText()).toString().trim();
        String mail = Objects.requireNonNull(etEmail.getText()).toString().trim();

        if (Validation()) {
            TaiKhoan taikhoan = new TaiKhoan(name, pass, mail, hoten);
            ApiService.apiService.PostTaiKhoan(taikhoan).enqueue(new Callback<TaiKhoan>() {
                @Override
                public void onResponse(@NonNull Call<TaiKhoan> call, @NonNull Response<TaiKhoan> response) {
                    TaiKhoan taiKhoan1 = response.body();
                    if (taiKhoan1 != null) {
                        SharedPreferences sharedPref = getSharedPreferences(MY_PREFERENCE_NAME, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("value", taiKhoan1.get_id());
                        editor.apply();
                        Dialog();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<TaiKhoan> call, @NonNull Throwable t) {
                }
            });
        }
    }

    private boolean Validation() {
        String name = Objects.requireNonNull(etTaiKhoan.getText()).toString().trim();
        String pass = Objects.requireNonNull(etMatKhau.getText()).toString().trim();
        String repass = Objects.requireNonNull(etNhapLaiMatKhau.getText()).toString().trim();
        String mail = Objects.requireNonNull(etEmail.getText()).toString().trim();
        String hoten = Objects.requireNonNull(etHoTen.getText()).toString().trim();

        if (TextUtils.isEmpty(name)) {
            setErrorAndFocus(etTaiKhoan, "Tên tài khoản không được để trống");
            return false;
        }
        if (name.length() < 6 || name.length() > 20) {
            setErrorAndFocus(etTaiKhoan, "Tên tài khoản phải từ 6 đến 20 kí tự");
            return false;
        }
        if (isTaiKhoanExist(name)) {
            setErrorAndFocus(etTaiKhoan, "Tên tài khoản đã được sử dụng");
            return false;
        }
        if (TextUtils.isEmpty(pass)) {
            setErrorAndFocus(etMatKhau, "Mật khẩu không được để trống");
            return false;
        }
        if (pass.length() < 6) {
            setErrorAndFocus(etMatKhau, "Mật khẩu phải ít nhất 6 kí tự");
            return false;
        }
        if (!repass.equals(pass)) {
            setErrorAndFocus(etNhapLaiMatKhau, "Mật khẩu không trùng khớp");
            return false;
        }
        if (TextUtils.isEmpty(mail)) {
            setErrorAndFocus(etEmail, "Email không được để trống");
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            setErrorAndFocus(etEmail, "Email không đúng định dạng");
            return false;
        }
        if (isEmailExist(mail)) {
            setErrorAndFocus(etEmail, "Email đã được sử dụng");
            return false;
        }
        if (TextUtils.isEmpty(hoten)) {
            setErrorAndFocus(etHoTen, "Tên không được để trống");
            return false;
        }

        return true;
    }

    private void setErrorAndFocus(TextInputEditText editText, String errorMessage) {
        editText.setError(errorMessage);
        editText.requestFocus();
    }

    private boolean isTaiKhoanExist(String taiKhoan) {
        for (TaiKhoan tk : list) {
            if (tk.getTaiKhoan().equals(taiKhoan)) {
                return true;
            }
        }
        return false;
    }

    private boolean isEmailExist(String email) {
        for (TaiKhoan tk : list) {
            if (tk.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }


    private void clickQuayLai() {
        startActivity(new Intent(this, Login.class));
        finish();
    }

    public void init() {
        etTaiKhoan = findViewById(R.id.edt_TaiKhoan);
        etMatKhau = findViewById(R.id.edt_MatKhau);
        etNhapLaiMatKhau = findViewById(R.id.edt_NhapLaiMatKhau);
        etEmail = findViewById(R.id.edt_Email);
        etHoTen = findViewById(R.id.edt_HoTen);
        btnDangNhap = findViewById(R.id.btnDangNhap);
        btnDangKy = findViewById(R.id.btnDangKy);
    }


    private void Dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Tạo tài khoản thành công")
                .setIcon(R.drawable.ic_notifications_red)
                .setTitle("Thông báo");
        builder.setPositiveButton("OK", (dialog, which) -> {
            Intent intent = new Intent(((Dialog) dialog).getContext(), Login.class);
            startActivity(intent);
            finish();
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        // Set button text color
        Button okButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        okButton.setTextColor(Color.BLACK);
    }

    private void getTaiKhoan() {
        ApiService.apiService.GetTaiKhoan().enqueue(new Callback<List<TaiKhoan>>() {
            @Override
            public void onResponse(@NonNull Call<List<TaiKhoan>> call, @NonNull Response<List<TaiKhoan>> response) {
                list = response.body();
            }

            @Override
            public void onFailure(@NonNull Call<List<TaiKhoan>> call, @NonNull Throwable t) {
                Log.e("lỗi ở đăng ký", t.toString());
            }
        });
    }

}
