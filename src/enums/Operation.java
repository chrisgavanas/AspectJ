package enums;


public enum Operation {
    PREPEND(0), POP(1), HEAD(2);

    private int value;

    private Operation(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Operation fromInteger(int value) {
        if (values().length - 1 < value)
            return null;
        return values()[value];
    }

}
