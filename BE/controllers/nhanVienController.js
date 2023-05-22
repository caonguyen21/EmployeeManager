const { NhanVien } = require("../model/model");

const nhanVienController = {
  // Thêm nhân viên
  AddNhanVien: async (req, res) => {
    try {
      const nhanVien = new NhanVien(req.body);
      const saveNhanVien = await nhanVien.save();
      res.status(200).json(saveNhanVien);
    } catch (err) {
      res.status(500).json(err);
    }
  },

  // Lấy thông tin 1 nhân viên
  Get1NhanVien: async (req, res) => {
    try {
      const nhanVien = await NhanVien.findById(req.params.id);
      res.status(200).json(nhanVien);
    } catch (err) {
      res.status(500).json(err);
    }
  },

  // Cập nhật thông tin nhân viên
  Update1NhanVien: async (req, res) => {
    try {
      const nhanVien = await NhanVien.findByIdAndUpdate(req.params.id, req.body, { new: true });
      res.status(200).json(nhanVien);
    } catch (err) {
      res.status(500).json(err);
    }
  },

  // Xóa nhân viên
  Delete1NhanVien: async (req, res) => {
    try {
      await NhanVien.findByIdAndDelete(req.params.id);
      res.status(200).json("Deleted successful");
    } catch (err) {
      res.status(500).json(err);
    }
  },

  // Lấy toàn bộ thông tin nhân viên
  GetAllNhanVien: async (req, res) => {
    try {
      const nhanVienList = await NhanVien.find();
      res.status(200).json(nhanVienList);
    } catch (err) {
      res.status(500).json(err);
    }
  },
};

module.exports = nhanVienController;
