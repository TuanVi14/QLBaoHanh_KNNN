# QLBaoHanh
# 🛠️ Hệ thống Quản lý Bảo hành - Phân hệ Nghiệp vụ (Task 2)

Dự án xây dựng phần mềm quản lý bảo hành thiết bị điện tử (Laptop, Điện thoại) sử dụng **Java Swing** và mô hình **3 lớp (3-Layer Architecture)**. Repository này chứa mã nguồn của **Nhiệm vụ 2: Nghiệp vụ Bảo hành & Báo cáo**.

---

## 🚀 Tính năng đã hoàn thiện

### 1. Tiếp nhận bảo hành (Check-in)
- **Tra cứu thông tin:** Tìm kiếm sản phẩm bằng số **Serial Number**.
- **Kết nối dữ liệu đa bảng:** Tự động truy xuất thông tin từ 4 bảng (`SanPhamDaBan`, `HoaDon`, `KhachHang`, `SanPhamModel`).
- **Auto Check bảo hành:** Hệ thống tự động tính toán ngày mua + thời hạn bảo hành để kết luận:
  - 🟢 **Còn bảo hành** (Hiển thị màu xanh).
  - 🔴 **Hết bảo hành** (Hiển thị màu đỏ).
- **Tạo phiếu:** Cho phép nhập mô tả lỗi và tạo phiếu tiếp nhận vào CSDL.

### 2. Xử lý sửa chữa (Processing)
- **Danh sách phiếu:** Hiển thị `JTable` danh sách các máy đang nằm tại cửa hàng.
- **Cập nhật tiến độ:** Kỹ thuật viên thay đổi trạng thái (*Đang kiểm tra, Đang sửa, Hoàn thành...*).
- **Ghi nhật ký (Log):** Mỗi lần cập nhật đều lưu lại lịch sử (Ngày giờ, Nội dung xử lý, Linh kiện thay thế).
- **Xem chi tiết:** Popup hiển thị bảng lịch sử sửa chữa của từng phiếu.

### 3. Thống kê (Dashboard)
- **Real-time:** Hiển thị số lượng máy "Đang sửa" và "Đã hoàn thành".
- **Giao diện:** Dạng thẻ (Card) trực quan, tự động làm mới dữ liệu khi chuyển tab.

---

## 🛠️ Công nghệ sử dụng

- **Ngôn ngữ:** Java (JDK 8+).
- **Giao diện:** Java Swing.
- **Cơ sở dữ liệu:** MySQL.
- **Kết nối:** JDBC (`mysql-connector-j`).
- **Kiến trúc:** Mô hình 3 lớp (GUI - BUS - DAO).
- **Tiện ích:** JCalendar, JFreeChart (dự kiến).

---

## 📂 Cấu trúc dự án

```text
src/com/quanlybaohanh/
├── dto/            # Data Transfer Object (KhachHang, PhieuBaoHanh...)
├── dao/            # Data Access Object (Truy vấn SQL, Kết nối CSDL)
├── bus/            # Business Logic Layer (Xử lý nghiệp vụ Auto Check)
├── gui/            # Giao diện người dùng
│   ├── baohanh/    # Panel Tiếp nhận, Xử lý, Thống kê, Dialog chi tiết
│   └── chung/      # MainFrame (Khung chương trình chính)
└── util/           # Tiện ích (Format tiền tệ, ngày tháng)
