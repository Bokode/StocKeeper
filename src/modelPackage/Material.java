package modelPackage;

public class Material {
    private String label;

    public Material(String label) {
        setLabel(label);
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
