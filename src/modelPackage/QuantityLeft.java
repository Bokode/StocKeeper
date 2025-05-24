package modelPackage;

public class QuantityLeft {
    private int quantity;
    private int numberDifferentType;

    public QuantityLeft(int quantity, int numberDifferentType) {
        setQuantity(quantity);
        setNumberDifferentType(numberDifferentType);
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setNumberDifferentType(int numberDifferentType) {
        this.numberDifferentType = numberDifferentType;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getNumberDifferentType() {
        return numberDifferentType;
    }
}
