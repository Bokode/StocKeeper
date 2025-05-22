package modelPackage;

import java.util.ArrayList;
import java.util.List;

public class RecipeWithExpiredFood {
    private Recipe recipe;
    private List<Material> materials;
    private List<Food> foods;

    public RecipeWithExpiredFood(Recipe recipe) {
        setRecipe(recipe);
        materials = new ArrayList<Material>();
        foods = new ArrayList<Food>();
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public void addMaterial(Material material) {
        if (!materials.contains(material)) {
            materials.add(material);
        }
    }

    public void addFood(Food food) {
        foods.add(food);
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public List<Material> getMaterials() {
        return materials;
    }

    public List<Food> getFoods() {
        return foods;
    }
}
