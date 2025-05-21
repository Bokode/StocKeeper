package modelPackage;

public class Food {
    private String label;
    private FoodType foodType;

    public Food(String label, FoodType foodType) {
        setLabel(label);
        setFoodType(foodType);
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setFoodType(FoodType foodType) {
        this.foodType = foodType;
    }

    public String getLabel() {
        return label;
    }

    public FoodType getFoodType() {
        return foodType;
    }
}
