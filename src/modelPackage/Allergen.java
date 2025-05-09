package modelPackage;

public class Allergen {
    private String label;

    public Allergen(String label) {
        setLabel(label);
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
