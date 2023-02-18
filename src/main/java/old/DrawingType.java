package old;

public enum DrawingType {
    CZ_B(1,"Rysunek cz-b"),
    CZ_B_EXTRA(2,"Rysunek cz-b duze zaciemnienie"),
    KOLOR(3, "Rysunek kolorowy"),
    KOLOR_EXTRA(4, "Rysunek kolorowy duze zaciemnienie"),
    SKAN_CZ_B(5,"Skan cz-b"),
    SKAN_KOLOR(6,"Skan kolor");


    private final int value;
    private final String description;

    DrawingType(int value, String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public String toString() {
        return value + " - " + description;
    }
}
