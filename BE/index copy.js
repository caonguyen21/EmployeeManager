const express = require("express");
const cors = require("cors");
const app = express();
const mongoose = require("mongoose");
var bodyParser = require("body-parser");
const morgan = require("morgan");
const dotenv = require("dotenv");
dotenv.config();

//Model
const { TaiKhoan, NhanVien } = require("./model/model");

//Connect Database
mongoose
  .connect(process.env.MONGODB_URL, {
    useNewUrlParser: true,
    useUnifiedTopology: true,
  })
  .then(() => {
    console.log("Connected to MongoDB");
  })
  .catch((error) => {
    console.error("Error connecting to MongoDB:", error);
  });

app.use(bodyParser.json({ limit: "50mb" }));
app.use(cors());
app.use(morgan("common"));

app.listen(8000, () => {
  console.log("Server is running...");
});

//Routes
app.use("/TaiKhoan", require("./routes/taikhoan"));
app.use("/NhanVien", require("./routes/nhanvien"));
