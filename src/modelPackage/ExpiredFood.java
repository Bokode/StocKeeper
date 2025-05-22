package modelPackage;

import java.util.ArrayList;
import java.util.List;

public class ExpiredFood {
    private FoodIn foodIn;
    private List<Season> seasons;
    private List<Allergen> allergens;

    public ExpiredFood(FoodIn foodIn) {
        setFoodIn(foodIn);
        seasons = new ArrayList<Season>();
        allergens = new ArrayList<Allergen>();
    }

    public void setFoodIn(FoodIn foodIn) {
        this.foodIn = foodIn;
    }

    public void addSeason(Season season) {
        seasons.add(season);
    }

    public void addAllergen(Allergen allergen) {
        allergens.add(allergen);
    }

    public FoodIn getFoodIn() {
        return foodIn;
    }

    public List<Season> getSeasons() {
        return seasons;
    }

    public List<Allergen> getAllergens() {
        return allergens;
    }
}
