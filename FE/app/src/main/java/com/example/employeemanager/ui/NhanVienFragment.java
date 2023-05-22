package com.example.employeemanager.ui;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.employeemanager.R;
import com.example.employeemanager.adapter.NhanVienAdapter;
import com.example.employeemanager.api.ApiService;
import com.example.employeemanager.function.AddNhanVien;
import com.example.employeemanager.function.SwipeOptionsCallback;
import com.example.employeemanager.model.NhanVien;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NhanVienFragment extends Fragment implements NhanVienAdapter.OnSwipeListener {
    private static final int PICK_IMAGE_PERMISSION_REQUEST_CODE = 1;
    private static final int PICK_IMAGE_REQUEST = 2;
    Toolbar toolbar;
    RecyclerView rcvNhanVien;
    NhanVienAdapter nhanVienAdapter;
    List<NhanVien> listNhanVien;
    FloatingActionButton floatingActionButton;
    private ImageView imageView;
    private Uri imageUri;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_nhan_vien, container, false);
        init(view);
        toolBar();
        getAllNhanVien();
        initGridView();
        addNhanVien();
        // Lấy reference đến BottomNavigationView
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_navigation_view);
        // Áp dụng màu icon và màu chữ
        bottomNavigationView.setItemIconTintList(ContextCompat.getColorStateList(getActivity(), R.color.color_navigation));
        bottomNavigationView.setItemTextColor(ContextCompat.getColorStateList(getActivity(), R.color.color_navigation));

        return view;
    }

    private void addNhanVien() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddNhanVien.class);
                startActivity(intent);
            }
        });
    }

    private void initGridView() {
        // Tạo LinearLayoutManager với hướng dọc (VERTICAL)
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        // Thiết lập LinearLayoutManager cho RecyclerView
        rcvNhanVien.setLayoutManager(layoutManager);
        // Tắt hiệu ứng cuộn trong RecyclerView
        rcvNhanVien.setNestedScrollingEnabled(false);
        // Tắt sự tập trung vào item khi được nhấn
        rcvNhanVien.setFocusable(false);
    }

    //ToolBar
    private void toolBar() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle("Danh sách nhân viên");
    }

    private void init(View view) {
        toolbar = view.findViewById(R.id.toolbar_nhanvien);
        rcvNhanVien = view.findViewById(R.id.rcvNhanVien);
        floatingActionButton = view.findViewById(R.id.fab_button);
    }

    private void resetRecyclerView() {
        listNhanVien = new ArrayList<>(); // Khởi tạo một danh sách mới
        nhanVienAdapter = new NhanVienAdapter(getContext(), listNhanVien, this);
        // Khởi tạo adapter với danh sách mới
        rcvNhanVien.setAdapter(nhanVienAdapter); // Đặt adapter cho RecyclerView
        SwipeOptionsCallback callback = new SwipeOptionsCallback(this, getContext());
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(rcvNhanVien);
    }

    private void getAllNhanVien() {
        ApiService.apiService.getAllNhanVien().enqueue(new Callback<List<NhanVien>>() {
            @Override
            public void onResponse(@NonNull Call<List<NhanVien>> call, @NonNull Response<List<NhanVien>> response) {
                if (response.isSuccessful()) {
                    List<NhanVien> newNhanVienList = response.body();
                    if (newNhanVienList != null) {
                        resetRecyclerView(); // Thiết lập lại RecyclerView
                        listNhanVien.addAll(newNhanVienList); // Thêm dữ liệu mới vào danh sách
                        nhanVienAdapter.notifyDataSetChanged(); // Thông báo cho RecyclerView hiển thị dữ liệu mới
                        Log.e("Size", String.valueOf(listNhanVien.size()));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<NhanVien>> call, @NonNull Throwable t) {
                // Xử lý lỗi khi gọi API thất bại
            }
        });
    }

    public void onLeftSwipe(int position) {
        NhanVien deletedNhanVien = listNhanVien.get(position);
        String nhanVienId = deletedNhanVien.get_id();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Xác nhận xóa");
        builder.setMessage("Bạn có muốn xóa nhân viên này không?");

        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Delete the item from the API
                ApiService.apiService.delete1NhanVien(nhanVienId).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            // Remove the item from the data list
                            listNhanVien.remove(position);
                            // Notify the adapter about the item removal
                            nhanVienAdapter.notifyItemRemoved(position);
                        } else {
                            // Handle API error
                            // ...
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        // Handle API call failure
                        // ...
                    }
                });
            }
        });

        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Cancel the deletion, do nothing
                // Add code to reset the item to its normal state if needed
                // ...
                // Notify the adapter about the item change
                nhanVienAdapter.notifyItemChanged(position);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

        // Set button text color
        Button okButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        Button noButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        if (okButton != null && noButton != null) {
            okButton.setTextColor(Color.BLACK);
            noButton.setTextColor(Color.BLACK);
        } else {
            Log.e("DialogButtons", "Positive and Negative buttons not found.");
        }
    }

    // Method for handling right swipe (update)
    public void onRightSwipe(int position) {
        NhanVien updatedNhanVien = listNhanVien.get(position);
        String nhanVienId = updatedNhanVien.get_id();

        // Create a dialog for editing the data
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        // Set a custom layout for the title
        TextView titleView = new TextView(getContext());
        titleView.setText("Cập nhật thông tin");
        titleView.setGravity(Gravity.CENTER);
        titleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
        titleView.setPadding(0, 20, 0, 20);
        titleView.setTypeface(null, Typeface.BOLD); // Set the text to bold
        builder.setCustomTitle(titleView);

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View dialogView = inflater.inflate(R.layout.edit_dialog_layout, null);

        EditText editTextHoTen = dialogView.findViewById(R.id.editTextHoTen);
        EditText editTextTuoi = dialogView.findViewById(R.id.editTextTuoi);
        EditText editTextGioiTinh = dialogView.findViewById(R.id.editTextGioiTinh);
        EditText editTextDiaChi = dialogView.findViewById(R.id.editTextDiaChi);
        EditText editTextChucVu = dialogView.findViewById(R.id.editTextChucVu);
        EditText editTextPhongBan = dialogView.findViewById(R.id.editTextPhongBan);
        imageView = dialogView.findViewById(R.id.imageViewNVedt);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView txtUpdateImg = dialogView.findViewById(R.id.txtUpdadeImg);
        editTextHoTen.setText(updatedNhanVien.getHoTen());
        editTextTuoi.setText(String.valueOf(updatedNhanVien.getTuoi()));
        editTextGioiTinh.setText(updatedNhanVien.getGioiTinh());
        editTextDiaChi.setText(updatedNhanVien.getDiaChi());
        editTextChucVu.setText(updatedNhanVien.getChucVu());
        editTextPhongBan.setText(updatedNhanVien.getPhongBan());

        txtUpdateImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check for the READ_MEDIA_IMAGES permission
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_MEDIA_IMAGES)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Permission not granted, request it
                    ActivityCompat.requestPermissions((Activity) getActivity(),
                            new String[]{Manifest.permission.READ_MEDIA_IMAGES},
                            PICK_IMAGE_PERMISSION_REQUEST_CODE);
                } else {
                    // Quyền truy cập đã được cấp, mở cửa sổ chọn ảnh
                    openImagePicker();
                }
            }
        });

        Glide.with(getContext())
                .load(updatedNhanVien.getHinhAnh())
                .placeholder(R.drawable.baseline_image_24)
                .into(imageView);

        builder.setTitle("Cập nhật thông tin");
        builder.setView(dialogView);

        builder.setPositiveButton("Cập nhật", null);
        builder.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Cancel the update, do nothing
                // Add code to reset the item to its normal state if needed
                // ...
                // Notify the adapter about the item change
                nhanVienAdapter.notifyItemChanged(position);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the updated values from the EditText fields
                String updatedHoTen = editTextHoTen.getText().toString().trim();
                String updatedTuoiStr = editTextTuoi.getText().toString().trim();
                String updatedGioiTinh = editTextGioiTinh.getText().toString().trim();
                String updatedDiaChi = editTextDiaChi.getText().toString().trim();
                String updatedChucVu = editTextChucVu.getText().toString().trim();
                String updatedPhongBan = editTextPhongBan.getText().toString().trim();
                //update image
                String updatedHinhAnh = getImagePathFromUri(imageUri);

                // Perform validation on the input data
                boolean isValid = true;

                if (updatedHoTen.isEmpty()) {
                    editTextHoTen.setError("Họ tên không được để trống");
                    editTextHoTen.requestFocus();
                    isValid = false;
                }

                if (updatedTuoiStr.isEmpty()) {
                    editTextTuoi.setError("Tuổi không được để trống");
                    editTextTuoi.requestFocus();
                    isValid = false;
                }

                if (updatedGioiTinh.isEmpty()) {
                    editTextGioiTinh.setError("Giới tính không được để trống");
                    editTextGioiTinh.requestFocus();
                    isValid = false;
                }

                if (updatedDiaChi.isEmpty()) {
                    editTextDiaChi.setError("Địa chỉ không được để trống");
                    editTextDiaChi.requestFocus();
                    isValid = false;
                }

                if (updatedChucVu.isEmpty()) {
                    editTextChucVu.setError("Chức vụ không được để trống");
                    editTextChucVu.requestFocus();
                    isValid = false;
                }

                if (updatedPhongBan.isEmpty()) {
                    editTextPhongBan.setError("Phòng ban không được để trống");
                    editTextPhongBan.requestFocus();
                    isValid = false;
                }

                if (isValid) {
                    // Update the corresponding fields in the NhanVien object
                    updatedNhanVien.setHoTen(updatedHoTen);
                    updatedNhanVien.setTuoi(Integer.parseInt(updatedTuoiStr));
                    updatedNhanVien.setGioiTinh(updatedGioiTinh);
                    updatedNhanVien.setDiaChi(updatedDiaChi);
                    updatedNhanVien.setChucVu(updatedChucVu);
                    updatedNhanVien.setPhongBan(updatedPhongBan);
                    // Perform the image upload or update logic
                    updatedNhanVien.setHinhAnh(updatedHinhAnh);

                    // Once the update is completed, you can make the API call to update the server-side data
                    ApiService.apiService.update1NhanVien(nhanVienId, updatedNhanVien).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                // Update the item in the data list if needed
                                // ...
                                Toast.makeText(getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                // Notify the adapter about the item change
                                nhanVienAdapter.notifyItemChanged(position);
                                dialog.dismiss();
                            } else {
                                // Handle API error
                                // ...
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            // Handle API call failure
                            // ...
                        }
                    });
                }
            }
        });

        // Set button text color
        Button positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        Button negativeButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        if (positiveButton != null && negativeButton != null) {
            positiveButton.setTextColor(Color.BLACK);
            negativeButton.setTextColor(Color.BLACK);
        } else {
            Log.e("DialogButtons", "Positive and Negative buttons not found.");
        }
    }

    private void openImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PICK_IMAGE_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Quyền truy cập đã được cấp, mở cửa sổ chọn ảnh
                openImagePicker();
            } else {
                // Quyền truy cập bị từ chối, hiển thị thông báo cho người dùng
                Toast.makeText(getContext(), "Permission denied. Cannot access images.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String getImagePathFromUri(Uri uri) {
        String imagePath = null;
        if (DocumentsContract.isDocumentUri(getContext(), uri)) {
            // Nếu là tài liệu Media
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String documentId = DocumentsContract.getDocumentId(uri);
                String[] split = documentId.split(":");
                String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                }
                String selection = MediaStore.Images.Media._ID + "=?";
                String[] selectionArgs = new String[]{split[1]};
                imagePath = getImagePath(contentUri, selection, selectionArgs);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                // Nếu là tài liệu tải xuống
                String id = DocumentsContract.getDocumentId(uri);
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.parseLong(id));
                imagePath = getImagePath(contentUri, null, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Nếu là URI của nội dung tổng hợp
            imagePath = getImagePath(uri, null, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // Nếu là URI của file
            imagePath = uri.getPath();
        }
        return imagePath;
    }

    private String getImagePath(Uri uri, String selection, String[] selectionArgs) {
        String path = null;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContext().getContentResolver().query(uri, projection, selection, selectionArgs, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                path = cursor.getString(columnIndex);
            }
            cursor.close();
        }
        return path;
    }

}
