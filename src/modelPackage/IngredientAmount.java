package modelPackage;

public class IngredientAmount {
    private String food;
    private String recipe;
    private double quantity;

    public IngredientAmount(String recipe, String food, double quantity) {
        setRecipe(recipe);
        setFood(food);
        setQuantity(quantity);
    }

    public String getRecipe() {
        return recipe;
    }

    public String getFood() {
        return food;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public void setQuantity(double quantity)
    {
        this.quantity = quantity;
    }
}
