package modelPackage;

public class ExpiredFood {
    private FoodIn foodIn;
    private Season season;
    private Allergen allergen;

    public ExpiredFood(FoodIn foodIn, Season season, Allergen allergen) {
        setFoodIn(foodIn);
        setSeason(season);
        setAllergen(allergen);
    }

    public void setFoodIn(FoodIn foodIn) {
        this.foodIn = foodIn;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public void setAllergen(Allergen allergen) {
        this.allergen = allergen;
    }

    public FoodIn getFoodIn() {
        return foodIn;
    }

    public Season getSeason() {
        return season;
    }

    public Allergen getAllergen() {
        return allergen;
    }
}
