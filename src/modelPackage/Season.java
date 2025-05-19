package modelPackage;

public class Season {
    private String label;

    public Season(String label) {
        setLabel(label);
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
