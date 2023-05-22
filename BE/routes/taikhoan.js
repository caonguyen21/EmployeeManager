const taiKhoanController = require("../controllers/taiKhoanController");
const router = require("express").Router();

//Thêm tài khoản
router.post("/", taiKhoanController.AddTaiKhoan);

//Lấy thông tin 1 tài khoản
router.get("/:id", taiKhoanController.Get1TaiKhoan);

//Cập nhật thông tin tài khoản
router.put("/:id", taiKhoanController.Update1TaiKhoan);

//Cập nhật thông tin mật khẩu
router.put("/UpdateMatKhau/:id", taiKhoanController.UpdateMatKhau);

//Đăng nhập
router.post("/login", taiKhoanController.loginUser);

//Lấy toàn bộ thông tin tài khoản
router.get("/", taiKhoanController.GetAllTaiKhoan);

//xuất router
module.exports = router;