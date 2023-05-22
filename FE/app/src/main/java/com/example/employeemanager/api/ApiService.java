package com.example.employeemanager.api;

import com.example.employeemanager.model.NhanVien;
import com.example.employeemanager.model.TaiKhoan;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    OkHttpClient http = new OkHttpClient().newBuilder().build();
    ApiService apiService = new Retrofit.Builder()
            .baseUrl("http://192.168.1.9:8000/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(http)
            .build()
            .create(ApiService.class);

    //Tài khoản
    @GET("TaiKhoan")
    Call<List<TaiKhoan>> GetTaiKhoan();
    @POST("TaiKhoan")
    Call<TaiKhoan> PostTaiKhoan(@Body TaiKhoan taiKhoan);

    @GET("TaiKhoan/{id}")
    Call<TaiKhoan> thongtintaikhoan(@Path("id") String ID);

    @PUT("TaiKhoan/{id}")
    Call<TaiKhoan> updateTaiKhoan(@Path("id") String ID, @Body TaiKhoan updateTaiKhoan);

    @POST("TaiKhoan/login")
    Call<TaiKhoan> Login(@Body TaiKhoan taiKhoan);

    @PUT("TaiKhoan/UpdateMatKhau/{id}")
    Call<TaiKhoan> updateMatKHau(@Path("id") String ID, @Body TaiKhoan taiKhoan);

    // Thêm nhân viên
    @POST("/nhanvien")
    Call<NhanVien> addNhanVien(@Body NhanVien nhanVien);

    // Lấy thông tin 1 nhân viên
    @GET("/nhanvien/{id}")
    Call<NhanVien> get1NhanVien(@Path("id") String id);

    // Cập nhật thông tin nhân viên
    @PUT("/nhanvien/{id}")
    Call<Void> update1NhanVien(@Path("id") String id, @Body NhanVien nhanVien);

    // Xóa nhân viên
    @DELETE("/nhanvien/{id}")
    Call<Void> delete1NhanVien(@Path("id") String id);

    // Lấy toàn bộ thông tin nhân viên
    @GET("/nhanvien")
    Call<List<NhanVien>> getAllNhanVien();
}

