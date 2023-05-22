const mongoose = require("mongoose");

//#region tài khoản
const TaiKhoanSchema = new mongoose.Schema({
  TaiKhoan: {
    type: String,
    required: true,
    minlength: 6,
    maxlength: 20,
    unique: true,
  },
  MatKhau: {
    type: String,
    required: true,
  },
  Email: {
    type: String,
    unique: true,
    required: true,
  },
  HoTen: {
    type: String,
    require: true,
  },
  NgayTao: {
    type: String,
    required: true,
    default: new Date().toISOString(),
  },
  PhanQuyen: {
    type: Boolean,
    default: false,
    required: true,
  },
  TrangThai: {
    type: Boolean,
    default: true,
    required: true,
  },
});
//#endregion
//#region nhân viên
const NhanVienSchema = new mongoose.Schema({
  HoTen: {
    type: String,
    required: true,
  },
  Tuoi: {
    type: Number,
    required: true,
  },
  GioiTinh: {
    type: String,
    required: true,
  },
  DiaChi: {
    type: String,
    required: true,
  },
  ChucVu: {
    type: String,
    required: true,
  },
  PhongBan: {
    type: String,
    required: true,
  },
  NgayCapNhat: {
    type: String,
    required: true,
    default: new Date().toISOString(),
  },
  HinhAnh: {
    type: String,
    required: true,
  },
});
//#endregion
//=======================tạo model=======================
let TaiKhoan = mongoose.model("TaiKhoan", TaiKhoanSchema);
let NhanVien = mongoose.model("NhanVien", NhanVienSchema);
module.exports = { TaiKhoan, NhanVien };
