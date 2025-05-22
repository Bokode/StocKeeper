package modelPackage;

import java.util.ArrayList;
import java.util.List;

public class SeasonalRecipe {
    private Recipe recipe;
    private List<Diet> diets;
    private List<Material> materials;

    public SeasonalRecipe(Recipe recipe) {
        setRecipe(recipe);
        diets = new ArrayList<Diet>();
        materials = new ArrayList<Material>();
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public void addDiet(Diet diet) {
        diets.add(diet);
    }

    public void addMaterial(Material material) {
        materials.add(material);
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public List<Diet> getDiets() {
        return diets;
    }

    public List<Material> getMaterials() {
        return materials;
    }
}
