package com.example.employeemanager.function;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.employeemanager.R;
import com.example.employeemanager.api.ApiService;
import com.example.employeemanager.model.TaiKhoan;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateInfor extends AppCompatActivity {
    Button btnUpdate;
    private TextInputEditText hoTen, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_infor);
        toolBar();
        init();
        Intent intent = getIntent();
        TaiKhoan user = (TaiKhoan) intent.getSerializableExtra("user");
        hoTen.setText(user.getHoTen());
        email.setText(user.getEmail());
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateInfor(user);
            }
        });
    }

    private void updateInfor(TaiKhoan user) {
        String id = user.get_id();
        String hoTenValue = hoTen.getText().toString().trim();
        String emailValue = email.getText().toString().trim();

        if (TextUtils.isEmpty(hoTenValue)) {
            hoTen.setError("Họ tên không được để trống");
            hoTen.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(emailValue)) {
            email.setError("Email không được để trống");
            email.requestFocus();
            return;
        }

        TaiKhoan updatedTaiKhoan = new TaiKhoan(hoTenValue, emailValue, false, true);
        ApiService.apiService.updateTaiKhoan(id, updatedTaiKhoan).enqueue(new Callback<TaiKhoan>() {
            @Override
            public void onResponse(Call<TaiKhoan> call, Response<TaiKhoan> response) {
            }

            @Override
            public void onFailure(Call<TaiKhoan> call, Throwable t) {
                Toast.makeText(UpdateInfor.this, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });
    }

    public void init() {
        hoTen = findViewById(R.id.editHoTenUp);
        email = findViewById(R.id.editEmailUp);
        btnUpdate = findViewById(R.id.buttonUpdate);
    }

    public void toolBar() {
        Toolbar toolbar = findViewById(R.id.toolbarUpdateInfor);
        setSupportActionBar(toolbar);
        // Set the toolbar title
        getSupportActionBar().setTitle("Chỉnh sửa thông tin");
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

    @Override
    public void onBackPressed() {
        // Set the result to indicate a successful update
        setResult(RESULT_OK);
        super.onBackPressed();
    }
}