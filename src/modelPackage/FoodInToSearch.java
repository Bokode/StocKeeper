package modelPackage;

public class FoodInToSearch {
    private FoodIn foodIn;
    private Food food;
    private FoodType foodType;

    public FoodInToSearch(FoodIn foodIn, Food food, FoodType foodType) {
        setFoodIn(foodIn);
        setFood(food);
        setFoodType(foodType);
    }

    public void setFoodIn(FoodIn foodIn) {
        this.foodIn = foodIn;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public void setFoodType(FoodType foodType) {
        this.foodType = foodType;
    }

    public FoodIn getFoodIn() {
        return foodIn;
    }

    public Food getFood() {
        return food;
    }

    public FoodType getFoodType() {
        return foodType;
    }
}
