package modelPackage;

import java.util.ArrayList;
import java.util.List;

public class RecipeWithExpiredFood {
    private Recipe recipe;
    private List<Food> foods;

    public RecipeWithExpiredFood(Recipe recipe) {
        setRecipe(recipe);
        foods = new ArrayList<Food>();
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public void addFood(Food food) {
        foods.add(food);
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public List<Food> getFoods() {
        return foods;
    }
}
