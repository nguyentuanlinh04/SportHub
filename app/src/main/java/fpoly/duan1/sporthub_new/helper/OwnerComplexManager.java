package fpoly.duan1.sporthub_new.helper;

import java.util.ArrayList;
import java.util.List;

import fpoly.duan1.sporthub_new.model.OwnerComplex;

public class OwnerComplexManager {

    private static OwnerComplexManager instance;
    private final List<OwnerComplex> complexList;

    private OwnerComplexManager() {
        complexList = new ArrayList<>();
        initDefaultComplexes();
    }

    public static synchronized OwnerComplexManager getInstance() {
        if (instance == null) {
            instance = new OwnerComplexManager();
        }
        return instance;
    }

    private void initDefaultComplexes() {
        complexList.add(new OwnerComplex(
                "tl",
                "Cụm Sân Thăng Long",
                "0908 123 456",
                "152 Hoàng Quốc Việt, Cầu Giấy , Hà nội",
                "05:30 – 23:30 · Hằng ngày",
                "ACTIVE",
                5,
                "Cụm sân bóng đá cỏ nhân tạo tiêu chuẩn FIFA",
                false,
                21.0458,
                105.7981,
                "21.0458° B, 105.7981° Đ"
        ));

        complexList.add(new OwnerComplex(
                "md",
                "Cụm sân Mai Dịch",
                "0918 654 321",
                "28 Đường số 2, P. Bình An, TP. Hà Nội",
                "06:00 – 23:00 · Hằng ngày",
                "ACTIVE",
                4,
                "Cụm sân bóng đá cỏ nhân tạo cao cấp",
                false,
                21.0375,
                105.7825,
                "21.0375° B, 105.7825° Đ"
        ));

        complexList.add(new OwnerComplex(
                "rs",
                "Cụm sân Riverside",
                "0909 888 999",
                "86 Nguyễn Văn Hưởng, P. Thảo Điền, TP. Thủ Đức, TP.HCM",
                "06:00 – 22:00 · Hằng ngày",
                "PAUSED",
                0,
                "Đang bảo dưỡng nâng cấp cơ sở vật chất",
                false,
                10.8032,
                106.7324,
                "10.8032° B, 106.7324° Đ"
        ));
    }

    public List<OwnerComplex> getComplexList() {
        return complexList;
    }

    public void addComplex(OwnerComplex complex) {
        if (complex != null) {
            complexList.add(complex);
        }
    }

    public boolean deleteComplex(OwnerComplex complex) {
        if (complex == null) return false;
        return complexList.remove(complex);
    }

    public boolean deleteComplexById(String id) {
        if (id == null) return false;
        for (int i = 0; i < complexList.size(); i++) {
            if (id.equals(complexList.get(i).getId())) {
                complexList.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean deleteComplexByName(String name) {
        if (name == null) return false;
        for (int i = 0; i < complexList.size(); i++) {
            OwnerComplex c = complexList.get(i);
            if (c.getName().equalsIgnoreCase(name.trim()) ||
                name.toLowerCase().contains(c.getName().toLowerCase()) ||
                c.getName().toLowerCase().contains(name.toLowerCase())) {
                complexList.remove(i);
                return true;
            }
        }
        return false;
    }

    public OwnerComplex getComplexByName(String name) {
        if (name == null) return null;
        for (OwnerComplex c : complexList) {
            if (c.getName().equalsIgnoreCase(name.trim())) {
                return c;
            }
        }
        for (OwnerComplex c : complexList) {
            if (name.toLowerCase().contains(c.getName().toLowerCase()) ||
                c.getName().toLowerCase().contains(name.toLowerCase())) {
                return c;
            }
        }
        return null;
    }

    public boolean updateCoordinates(String complexName, double lat, double lng, String coordinatesStr) {
        OwnerComplex complex = getComplexByName(complexName);
        if (complex != null) {
            complex.setLatitude(lat);
            complex.setLongitude(lng);
            complex.setCoordinates(coordinatesStr);
            return true;
        }
        return false;
    }

    public int getTotalCount() {
        return complexList.size();
    }

    public int getActiveCount() {
        int count = 0;
        for (OwnerComplex c : complexList) {
            if ("ACTIVE".equalsIgnoreCase(c.getStatus())) {
                count++;
            }
        }
        return count;
    }

    public int getPausedCount() {
        int count = 0;
        for (OwnerComplex c : complexList) {
            if ("PAUSED".equalsIgnoreCase(c.getStatus())) {
                count++;
            }
        }
        return count;
    }

    public int getTotalSubPitches() {
        int total = 0;
        for (OwnerComplex c : complexList) {
            total += c.getSubPitchesCount();
        }
        return total;
    }

    public List<String> getAllComplexOptionsForSwitcher() {
        List<String> options = new ArrayList<>();
        options.add("🌐 Tất cả các sân (Toàn hệ thống · " + getTotalSubPitches() + " sân)");
        for (OwnerComplex c : complexList) {
            String status = "PAUSED".equalsIgnoreCase(c.getStatus()) ? " (Tạm ngưng)" : " (" + c.getSubPitchesCount() + " sân con)";
            options.add("⚽ " + c.getName() + status);
        }
        return options;
    }
}
