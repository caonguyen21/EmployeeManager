const nhanVienController = require("../controllers/nhanVienController");
const router = require("express").Router();

// Thêm nhân viên
router.post("/", nhanVienController.AddNhanVien);

// Lấy thông tin 1 nhân viên
router.get("/:id", nhanVienController.Get1NhanVien);

// Cập nhật thông tin nhân viên
router.put("/:id", nhanVienController.Update1NhanVien);

// Xóa nhân viên
router.delete("/:id", nhanVienController.Delete1NhanVien);

// Lấy toàn bộ thông tin nhân viên
router.get("/", nhanVienController.GetAllNhanVien);

// Xuất router
module.exports = router;