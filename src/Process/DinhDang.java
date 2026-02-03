package Process;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DinhDang {

    // Format ngày: 25/01/2026
    public static String formatNgay(Date date) {
        if (date == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(date);
    }

    // Format tiền: 1,000,000 VNĐ
    public static String formatTien(double tien) {
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        return currencyVN.format(tien);
    }
    
    // Parse ngày từ String (nếu cần nhập liệu)
    public static Date parseNgay(String dateStr) {
        try {
             SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
             return sdf.parse(dateStr);
        } catch (Exception e) {
            return null;
        }
    }
}