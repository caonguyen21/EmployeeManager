package com.example.employeemanager.ui;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.employeemanager.R;
import com.example.employeemanager.api.ApiService;
import com.example.employeemanager.function.ChangePassword;
import com.example.employeemanager.function.Login;
import com.example.employeemanager.function.UpdateInfor;
import com.example.employeemanager.model.TaiKhoan;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaiKhoanFragment extends Fragment {

    private static final String MY_PREFERENCE_NAME = "USER_ID";
    private static final String MY_PREFERENCE_PASS = "USER_PASS";
    private static final int REQUEST_UPDATE_INFOR = 1;
    Toolbar toolbar;
    TextView titleName, titleUsername, profileName, profileEmail, profileUsername, updatePass;
    ImageView profileImg;
    String id = null;
    String pass = null;
    Button logOut, editInfor;
    private boolean isID = true;
    private TaiKhoan user;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_tai_khoan, container, false);
        init(view);
        toolBar();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(MY_PREFERENCE_NAME, MODE_PRIVATE);
        id = sharedPreferences.getString("value", "");
        SharedPreferences shared = getContext().getSharedPreferences(MY_PREFERENCE_NAME, MODE_PRIVATE);
        pass = shared.getString("value", "");
        check(id);
        getThongTinTaiKhoan(id);
        logOut.setOnClickListener(v -> {
            SharedPreferences sharedPref = this.getActivity().getSharedPreferences(MY_PREFERENCE_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("value", "");
            editor.apply();
            Intent intent = new Intent(getActivity(), Login.class);
            startActivity(intent);
        });
        updatePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ChangePassword.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
        editInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UpdateInfor.class);
                intent.putExtra("user", user);
                startActivityForResult(intent, REQUEST_UPDATE_INFOR);
            }
        });
        return view;
    }

    private void check(String id) {
        if (id.equals("")) {
            Dialog();
            isID = false;
        } else {
            ApiService.apiService.thongtintaikhoan(id).enqueue(new Callback<TaiKhoan>() {
                @Override
                public void onResponse(@NonNull Call<TaiKhoan> call, @NonNull Response<TaiKhoan> response) {
                    TaiKhoan taiKhoan = response.body();
                    if (taiKhoan != null) {
                        if (!taiKhoan.isTrangThai()) {
                            Dialog3();
                            isID = false;
                        } else {
                            isID = true;
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<TaiKhoan> call, @NonNull Throwable t) {
                    Log.e("Thông tin tài khoản: ", t.toString());
                }
            });
        }
    }

    private void getThongTinTaiKhoan(String s) {
        ApiService.apiService.thongtintaikhoan(s).enqueue(new Callback<TaiKhoan>() {
            @Override
            public void onResponse(@NonNull Call<TaiKhoan> call, @NonNull Response<TaiKhoan> response) {
                TaiKhoan taiKhoan = response.body();
                if (taiKhoan != null && taiKhoan.isTrangThai()) {
                    user = taiKhoan;
                    titleName.setText(taiKhoan.getTaiKhoan());
                    titleUsername.setText(taiKhoan.getHoTen());
                    profileName.setText(taiKhoan.getTaiKhoan());
                    profileUsername.setText(taiKhoan.getHoTen());
                    profileEmail.setText(taiKhoan.getEmail());
                }
            }

            @Override
            public void onFailure(@NonNull Call<TaiKhoan> call, @NonNull Throwable t) {
                Log.e("Thông tin tài khoản: ", t.toString());
            }
        });
    }

    //ToolBar
    private void toolBar() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle("Tài khoản");
    }

    private void init(View view) {
        toolbar = view.findViewById(R.id.toolbar_taikhoan);
        titleName = view.findViewById(R.id.titleName);
        titleUsername = view.findViewById(R.id.titleUsername);
        profileName = view.findViewById(R.id.profileName);
        profileEmail = view.findViewById(R.id.profileEmail);
        profileUsername = view.findViewById(R.id.profileUsername);
        profileImg = view.findViewById(R.id.profileImg);
        logOut = view.findViewById(R.id.btnLogout);
        updatePass = view.findViewById(R.id.profilePassword);
        editInfor = view.findViewById(R.id.editButton);
    }

    private void Dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Tài khoản chưa đăng nhập")
                .setIcon(R.drawable.ic_notifications_red)
                .setTitle("Thông báo");
        builder.setPositiveButton("OK", (dialog, which) -> {
            Intent intent = new Intent(((Dialog) dialog).getContext(), Login.class);
            startActivity(intent);
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        // Set button text color
        Button okButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        okButton.setTextColor(Color.BLACK);
    }

    private void Dialog3() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Tài khoản đã bị đóng băng! Xin liên hệ quản trị viên ")
                .setIcon(R.drawable.ic_notifications_red)
                .setTitle("Thông báo");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); // Close the dialog
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        // Set button text color
        Button okButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        okButton.setTextColor(Color.BLACK);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_UPDATE_INFOR && resultCode == Activity.RESULT_OK) {
            // Refresh the data in the fragment
            getThongTinTaiKhoan(id);
        }
    }

}