package com.example.employeemanager.function;

import android.Manifest;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.employeemanager.MainActivity;
import com.example.employeemanager.R;
import com.example.employeemanager.api.ApiService;
import com.example.employeemanager.model.NhanVien;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddNhanVien extends AppCompatActivity {

    private static final int PICK_IMAGE_PERMISSION_REQUEST_CODE = 1;
    private static final int PICK_IMAGE_REQUEST = 2;
    TextView txtImage;
    private TextInputEditText hoTen, tuoi, diaChi, chucVu, phongBan, gioiTinh;
    private Button btnAddNhanVien;
    private ImageView imageView;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_nhan_vien);
        init();
        toolBar();
        setupListeners();
    }

    private void setupListeners() {
        btnAddNhanVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNhanVien();
            }
        });

        txtImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });
    }

    private void addNhanVien() {
        if (validateFields()) {
            String hoTenValue = hoTen.getText().toString().trim();
            String tuoiValue = tuoi.getText().toString().trim();
            String diaChiValue = diaChi.getText().toString().trim();
            String chucVuValue = chucVu.getText().toString().trim();
            String phongBanValue = phongBan.getText().toString().trim();
            String gioiTinhValue = gioiTinh.getText().toString().trim();
            String hinhAnhValue = getImagePathFromUri(imageUri);

            NhanVien nhanVien = new NhanVien(hoTenValue, Integer.parseInt(tuoiValue), gioiTinhValue, diaChiValue, chucVuValue, phongBanValue, hinhAnhValue);
            ApiService.apiService.addNhanVien(nhanVien).enqueue(new Callback<NhanVien>() {
                @Override
                public void onResponse(Call<NhanVien> call, Response<NhanVien> response) {
                    if (response.isSuccessful()) {
                        String successMessage = "Nhân viên đã được thêm thành công.";
                        Toast.makeText(AddNhanVien.this, successMessage, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddNhanVien.this, MainActivity.class);
                        intent.putExtra("addSuccess", true);
                        startActivity(intent);
                        finish();
                    } else {
                        String errorMessage = "Đã xảy ra lỗi khi thêm nhân viên.";
                        Toast.makeText(AddNhanVien.this, errorMessage, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<NhanVien> call, Throwable t) {
                    String errorMessage = "Đã xảy ra lỗi khi thêm nhân viên.";
                    Toast.makeText(AddNhanVien.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private boolean validateFields() {
        if (TextUtils.isEmpty(hoTen.getText().toString().trim())) {
            hoTen.setError("Vui lòng nhập họ tên.");
            return false;
        }
        if (TextUtils.isEmpty(tuoi.getText().toString().trim())) {
            tuoi.setError("Vui lòng nhập tuổi.");
            return false;
        }
        if (TextUtils.isEmpty(gioiTinh.getText().toString().trim())) {
            gioiTinh.setError("Vui lòng nhập giới tính.");
            return false;
        }
        if (TextUtils.isEmpty(diaChi.getText().toString().trim())) {
            diaChi.setError("Vui lòng nhập địa chỉ.");
            return false;
        }
        if (TextUtils.isEmpty(chucVu.getText().toString().trim())) {
            chucVu.setError("Vui lòng nhập chức vụ.");
            return false;
        }
        if (TextUtils.isEmpty(phongBan.getText().toString().trim())) {
            phongBan.setError("Vui lòng nhập phòng ban.");
            return false;
        }
        if (imageUri == null) {
            Toast.makeText(this, "Vui lòng chọn ảnh.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void chooseImage() {
        // Kiểm tra quyền truy cập
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES)
                != PackageManager.PERMISSION_GRANTED) {
            // Quyền truy cập chưa được cấp, yêu cầu quyền từ người dùng
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_MEDIA_IMAGES},
                    PICK_IMAGE_PERMISSION_REQUEST_CODE);
        } else {
            // Quyền truy cập đã được cấp, mở cửa sổ chọn ảnh
            openImagePicker();
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
                Toast.makeText(this, "Permission denied. Cannot access images.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }

    private String getImagePathFromUri(Uri uri) {
        String imagePath = null;
        if (DocumentsContract.isDocumentUri(this, uri)) {
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
        Cursor cursor = getContentResolver().query(uri, projection, selection, selectionArgs, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                path = cursor.getString(columnIndex);
            }
            cursor.close();
        }
        return path;
    }

    public void init() {
        hoTen = findViewById(R.id.editHoTen);
        tuoi = findViewById(R.id.editTuoi);
        diaChi = findViewById(R.id.editDiaChi);
        chucVu = findViewById(R.id.editChucVu);
        phongBan = findViewById(R.id.editPhongBan);
        gioiTinh = findViewById(R.id.editGioiTinh);
        btnAddNhanVien = findViewById(R.id.buttonAdd);
        txtImage = findViewById(R.id.txtAddImage);
        imageView = findViewById(R.id.imageViewNV);
    }

    public void toolBar() {
        Toolbar toolbar = findViewById(R.id.toolbarDetailNhanvien);
        setSupportActionBar(toolbar);
        // Set the toolbar title
        getSupportActionBar().setTitle("Thêm nhân viên");
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
