package modelPackage;

public class Food {
    private Integer id;
    private String label;
    private Integer foodType;

    public Food(Integer id, String label, Integer foodType) {
        setId(id);
        setLabel(label);
        setFoodType(foodType);
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setFoodType(Integer foodType) {
        this.foodType = foodType;
    }

    public Integer getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public Integer getFoodType() {
        return foodType;
    }
}
