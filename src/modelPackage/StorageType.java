package modelPackage;

public class StorageType {
    private String label;

    public StorageType(String label) {
        setLabel(label);
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
