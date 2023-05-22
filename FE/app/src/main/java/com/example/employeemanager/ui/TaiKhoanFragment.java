package com.example.employeemanager.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.employeemanager.R;

public class TaiKhoanFragment extends Fragment {

    Toolbar toolbar;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_tai_khoan, container, false);
        init(view);
        toolBar();
        return view;
    }

    //ToolBar
    private void toolBar() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle("Tài khoản");
    }

    private void init(View view) {
        toolbar = view.findViewById(R.id.toolbar_taikhoan);
    }
}