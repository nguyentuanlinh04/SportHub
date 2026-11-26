package fpoly.duan1.sporthub_new.helper;

public class StaffDataStore {
    private static int totalOrders = 14;
    private static int activeOrders = 2;
    private static int pendingCheckIns = 3;
    private static int checkInCount = 5;
    private static int collectedOrders = 4;
    private static int manualOrders = 1;
    private static final long baseDebt = 995000;
    private static long incidentalServiceAdded = 0;
    private static boolean isCheckedIn = false;
    private static String currentFacility = "Cụm sân Thăng Long";

    public static String getCurrentFacility() {
        return currentFacility;
    }

    public static void setCurrentFacility(String facility) {
        currentFacility = facility;
    }

    public static int getTotalOrders() {
        return totalOrders;
    }

    public static int getActiveOrders() {
        return activeOrders;
    }

    public static int getPendingCheckIns() {
        return pendingCheckIns;
    }

    public static int getCheckInCount() {
        return checkInCount;
    }

    public static int getCollectedOrders() {
        return collectedOrders;
    }

    public static int getManualOrders() {
        return manualOrders;
    }

    public static long getDebtAmount() {
        return baseDebt + incidentalServiceAdded;
    }

    public static long getIncidentalServiceAdded() {
        return incidentalServiceAdded;
    }

    public static boolean isCheckedIn() {
        return isCheckedIn;
    }

    public static void checkInOrder() {
        if (!isCheckedIn) {
            isCheckedIn = true;
            if (pendingCheckIns > 0) pendingCheckIns--;
            activeOrders++;
            checkInCount++;
        }
    }

    public static void addNewOrder() {
        totalOrders++;
        manualOrders++;
        pendingCheckIns++;
    }

    public static void addIncidentalService() {
        collectedOrders++;
    }

    public static void setIncidentalServiceAdded(long amount) {
        incidentalServiceAdded = amount;
    }
}
