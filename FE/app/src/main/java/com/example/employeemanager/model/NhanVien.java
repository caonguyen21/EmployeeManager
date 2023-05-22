package com.example.employeemanager.model;

import java.io.Serializable;
import java.util.Date;

public class NhanVien implements Serializable {
    private String _id;
    private String HoTen;
    private int Tuoi;
    private String GioiTinh;
    private String DiaChi;
    private String ChucVu;
    private String PhongBan;

    private Date NgayCapNhat;
    
    private  String HinhAnh;

    // Default constructor
    public NhanVien() {
    }

    // Parameterized constructor
    public NhanVien(String hoTen, int tuoi, String gioiTinh, String diaChi, String chucVu, String phongBan, String hinhAnh) {
        this.HoTen = hoTen;
        this.Tuoi = tuoi;
        this.GioiTinh = gioiTinh;
        this.DiaChi = diaChi;
        this.ChucVu = chucVu;
        this.PhongBan = phongBan;
        this.HinhAnh = hinhAnh;
    }

    public String getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        HinhAnh = hinhAnh;
    }

    public Date getNgayCapNhat() {
        return NgayCapNhat;
    }

    public void setNgayCapNhat(Date ngayCapNhat) {
        NgayCapNhat = ngayCapNhat;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String hoTen) {
        HoTen = hoTen;
    }

    public int getTuoi() {
        return Tuoi;
    }

    public void setTuoi(int tuoi) {
        Tuoi = tuoi;
    }

    public String getGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        GioiTinh = gioiTinh;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public String getChucVu() {
        return ChucVu;
    }

    public void setChucVu(String chucVu) {
        ChucVu = chucVu;
    }

    public String getPhongBan() {
        return PhongBan;
    }

    public void setPhongBan(String phongBan) {
        PhongBan = phongBan;
    }

    @Override
    public String toString() {
        return "NhanVien{" +
                "_id='" + _id + '\'' +
                ", HoTen='" + HoTen + '\'' +
                ", Tuoi=" + Tuoi +
                ", GioiTinh='" + GioiTinh + '\'' +
                ", DiaChi='" + DiaChi + '\'' +
                ", ChucVu='" + ChucVu + '\'' +
                ", PhongBan='" + PhongBan + '\'' +
                ", NgayCapNhat=" + NgayCapNhat +
                ", HinhAnh=" + HinhAnh +
                '}';
    }
}


