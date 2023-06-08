package com.example.employeemanager.function;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.employeemanager.R;
import com.example.employeemanager.api.ApiService;
import com.example.employeemanager.model.TaiKhoan;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassword extends AppCompatActivity {

    private static final String MY_PREFERENCE_NAME = "USER_ID";
    private TextInputEditText pass;
    private TextInputEditText newpass;
    private TextInputEditText verifypass;
    private Button tdmk_btn_QuayLai, tdmk_btn_CapNhat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Intent intent = getIntent();
        TaiKhoan user = (TaiKhoan) intent.getSerializableExtra("user");
        init();
        tdmk_btn_QuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tdmk_btn_CapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update(user);
            }
        });
    }

    private void update(TaiKhoan user) {
        if (Validation()) {
            String old = pass.getText().toString().trim();
            TaiKhoan taiKhoan = new TaiKhoan(user.getTaiKhoan(), old);
            ApiService.apiService.Login(taiKhoan).enqueue(new Callback<TaiKhoan>() {
                @Override
                public void onResponse(Call<TaiKhoan> call, Response<TaiKhoan> response) {
                    if (response.body() != null) {
                        String newp = newpass.getText().toString().trim();
                        TaiKhoan taiKhoan1 = new TaiKhoan(newp);
                        ApiService.apiService.updateMatKHau(user.get_id(), taiKhoan1).enqueue(new Callback<TaiKhoan>() {
                            @Override
                            public void onResponse(Call<TaiKhoan> call, Response<TaiKhoan> response) {

                            }

                            @Override
                            public void onFailure(Call<TaiKhoan> call, Throwable t) {
                                Dialog();
                            }
                        });
                    } else {
                        Dialog2();
                    }
                }

                @Override
                public void onFailure(Call<TaiKhoan> call, Throwable t) {

                }
            });
        }
    }

    private boolean Validation() {
        String pass = Objects.requireNonNull(newpass.getText()).toString().trim();
        String repass = Objects.requireNonNull(verifypass.getText()).toString().trim();
        if (TextUtils.isEmpty(pass)) {
            newpass.setError("Mật khẩu không được để trống");
            newpass.requestFocus();
            return false;
        }
        if (pass.length() < 6) {
            newpass.setError("Mật khẩu không được ít hơn 6 kí tự");
            newpass.requestFocus();
            return false;
        }
        if (!repass.equals(pass)) {
            verifypass.setError("Mật khẩu không trùng khớp");
            verifypass.requestFocus();
            return false;
        }
        return true;
    }

    //Tạo dialog thông báo
    private void Dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Thay đổi mật khẩu thàng công!Vui lòng đăng nhập lại!")
                .setIcon(R.drawable.ic_notifications_red)
                .setTitle("Thông báo");
        builder.setPositiveButton("OK", (dialog, which) -> {
            SharedPreferences sharedPref = getSharedPreferences(MY_PREFERENCE_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("value", "");
            editor.apply();
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

    private void Dialog2() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Mật khẩu cũ không đúng")
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

    private void init() {
        pass = findViewById(R.id.tiet_MatKhauCu);
        newpass = findViewById(R.id.tiet_MatKhauMoi);
        verifypass = findViewById(R.id.tiet_XacNhanMatKhauMoi);
        tdmk_btn_QuayLai = findViewById(R.id.tdmk_btn_QuayLai);
        tdmk_btn_CapNhat = findViewById(R.id.tdmk_btn_CapNhat);
    }
}