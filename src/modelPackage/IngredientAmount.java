package modelPackage;

public class IngredientAmount {
    private Food food;
    private Recipe recipe;

    IngredientAmount(Recipe recipe, Food food) {
        setRecipe(recipe);
        setFood(food);
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public Food getFood() {
        return food;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public void setFood(Food food) {
        this.food = food;
    }
}
