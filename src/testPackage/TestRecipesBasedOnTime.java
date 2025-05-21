package testPackage;

import businessPackage.RecipeManager;

import modelPackage.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TestRecipesBasedOnTime {
    private RecipeManager recipeManager;

    @BeforeEach
    public void setUp() {
        recipeManager = new RecipeManager();
    }

    @Test
    public void testShowRecipesBasedOnTime() {
        List<Recipe> recipesTest = recipeManager.showRecipesBasedOnTime(55);
        assertNotEquals(0, recipesTest.size());
    }
}
