package modelPackage;

public class RecipeMaterial {
    private Recipe recipe;
    private Material material;

    RecipeMaterial(Recipe recipe, Material material) {
        setRecipe(recipe);
        setMaterial(material);
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public Material getMaterial() {
        return material;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
}