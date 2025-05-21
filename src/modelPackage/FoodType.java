package modelPackage;

public class FoodType {
    private Integer id;
    private String label;

    public FoodType(Integer id, String label) {
        setId(id);
        setLabel(label);
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public Integer getId() {
        return id;
    }
}