package modelPackage;

public class RecipeWithExpiredFood {
    private Recipe recipe;
    private Material material;
    private Food food;

    public RecipeWithExpiredFood(Recipe recipe, Material material, Food food) {
        setRecipe(recipe);
        setMaterial(material);
        setFood(food);
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public Material getMaterial() {
        return material;
    }

    public Food getFood() {
        return food;
    }
}
