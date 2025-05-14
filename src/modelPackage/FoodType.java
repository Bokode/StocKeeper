package modelPackage;

public class FoodType {
    private String label;

    public FoodType(String label) {
        setLabel(label);
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}