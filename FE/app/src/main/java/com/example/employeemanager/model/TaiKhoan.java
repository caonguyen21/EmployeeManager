package com.example.employeemanager.model;

import java.io.Serializable;
import java.util.Date;

public class TaiKhoan implements Serializable {
    private String _id;
    private String TaiKhoan;
    private String MatKhau;
    private String Email;
    private boolean PhanQuyen;
    private boolean TrangThai;
    private Date NgayTao;
    private String HoTen;

    public TaiKhoan(String hoTen, String email, boolean phanQuyen, boolean trangThai) {
        HoTen = hoTen;
        Email = email;
        PhanQuyen = phanQuyen;
        TrangThai = trangThai;
    }

    public TaiKhoan(String taiKhoan, String matKhau, String email, String hoTen) {
        TaiKhoan = taiKhoan;
        MatKhau = matKhau;
        Email = email;
        HoTen = hoTen;
    }

    public TaiKhoan(String matKhau) {
        MatKhau = matKhau;
    }

    public TaiKhoan() {
    }


    public TaiKhoan(String taiKhoan, String matKhau) {
        TaiKhoan = taiKhoan;
        MatKhau = matKhau;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTaiKhoan() {
        return TaiKhoan;
    }

    public void setTaiKhoan(String taiKhoan) {
        TaiKhoan = taiKhoan;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String matKhau) {
        MatKhau = matKhau;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public boolean isPhanQuyen() {
        return PhanQuyen;
    }

    public void setPhanQuyen(boolean phanQuyen) {
        PhanQuyen = phanQuyen;
    }

    public boolean isTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(boolean trangThai) {
        TrangThai = trangThai;
    }

    public Date getNgayTao() {
        return NgayTao;
    }

    public void setNgayTao(Date ngayTao) {
        NgayTao = ngayTao;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String hoTen) {
        HoTen = hoTen;
    }

    @Override
    public String toString() {
        return "TaiKhoan{" +
                "_id='" + _id + '\'' +
                ", TaiKhoan='" + TaiKhoan + '\'' +
                ", MatKhau='" + MatKhau + '\'' +
                ", Email='" + Email + '\'' +
                ", PhanQuyen=" + PhanQuyen +
                ", TrangThai=" + TrangThai +
                ", NgayTao=" + NgayTao +
                ", HoTen='" + HoTen + '\'' +
                '}';
    }

}
