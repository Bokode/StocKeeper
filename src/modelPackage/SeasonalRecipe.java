package modelPackage;

public class SeasonalRecipe {
    private Recipe recipe;
    private Diet diet;
    private Material material;

    public SeasonalRecipe(Recipe recipe, Diet diet, Material material) {
        setRecipe(recipe);
        setDiet(diet);
        setMaterial(material);
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public void setDiet(Diet diet) {
        this.diet = diet;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public Diet getDiet() {
        return diet;
    }

    public Material getMaterial() {
        return material;
    }
}
