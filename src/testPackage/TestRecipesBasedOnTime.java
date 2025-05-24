package testPackage;

import businessPackage.RecipeManager;

import modelPackage.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TestRecipesBasedOnTime {
    private RecipeManager recipeManager;

    @BeforeEach
    public void setUp() {
        recipeManager = new RecipeManager();
    }

    @Test
    public void testShowRecipesBasedOnTimeNotNull() {
        List<Recipe> recipesTest = recipeManager.showRecipesBasedOnTime(0, 55);
        assertNotEquals(0, recipesTest.size());
    }

    @Test
    public void testShowRecipesBasedOnTimeNull() {
        List<Recipe> recipesTest = recipeManager.showRecipesBasedOnTime(300, 350);
        assertEquals(0, recipesTest.size());
    }
}