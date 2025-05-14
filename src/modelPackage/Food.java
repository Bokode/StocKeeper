package modelPackage;

public class Food {
    private String SeasonFoodLabel;
    private String FoodAllergenLabel;

    public Food(String SeasonFoodLabel, String FoodAllergenLabel) {
        setSeasonFoodLabel(SeasonFoodLabel);
        setFoodAllergenLabel(FoodAllergenLabel);
    }

    public void setSeasonFoodLabel(String seasonFoodLabel) {
        SeasonFoodLabel = seasonFoodLabel;
    }

    public void setFoodAllergenLabel(String foodAllergenLabel) {
        FoodAllergenLabel = foodAllergenLabel;
    }

    public String getSeasonFoodLabel() {
        return SeasonFoodLabel;
    }

    public String getFoodAllergenLabel() {
        return FoodAllergenLabel;
    }
}
