package modelPackage;

public class RecipeType {
    private String label;

    public RecipeType(String label) {
        setLabel(label);
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
