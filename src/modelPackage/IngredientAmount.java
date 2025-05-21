package modelPackage;

public class IngredientAmount {
    private Food food;
    private Recipe recipe;
    private Integer quantity;

    IngredientAmount(Recipe recipe, Food food, Integer quantity) {
        setRecipe(recipe);
        setFood(food);
        setQuantity(quantity);
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public Food getFood() {
        return food;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
