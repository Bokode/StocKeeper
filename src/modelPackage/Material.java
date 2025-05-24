package modelPackage;

public class Material {
    private String label;
    private String materialTypeLabel;

    public Material(String label, String materialTypeLabel) {
        setLabel(label);
        setMaterialTypeLabel(materialTypeLabel);
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setMaterialTypeLabel(String materialTypeLabel) {
        this.materialTypeLabel = materialTypeLabel;
    }

    public String getLabel() {
        return label;
    }

    public String getMaterialTypeLabel() {
        return materialTypeLabel;
    }


}
