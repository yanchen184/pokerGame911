package yc1125;

public enum PokerColor {
    CLUB("梅花", 0),
    DIAMOND("方塊", 1),
    HEART("紅心", 2),
    SPADE("黑桃", 3);

    private String color;

    private int colorNumber;

    private PokerColor(String color, int value) {
        this.color = color;
        this.colorNumber = value;
    }

    public static String getPokerColor(int colorNumber) {
        for (PokerColor pokerColor : PokerColor.values()) {
            if (pokerColor.colorNumber == colorNumber) {
                return pokerColor.color;
            }
        }
        return null;
    }

    public static String getPokerName(int colorNumber, int value) {
        return getPokerColor(colorNumber) + getPokerNameByValue(value);
    }

    public static String getPokerNameByValue(int value) {
        switch (value) {
            case 14:
                return "A";
            case 11:
                return "J";
            case 12:
                return "Q";
            case 13:
                return "K";
            default:
                return String.valueOf(value);
        }
    }
}
