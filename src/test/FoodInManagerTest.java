package test;

import businessPackage.FoodInManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class FoodInManagerTest {
    private FoodInManager foodInManager;

    @BeforeEach
    public void setUp() {
        foodInManager = new FoodInManager();
    }
    
    @Test
    public void testShowQuantityLeft() {
        Integer quantityTest = foodInManager.showQuantityLeft("Meat");
        assertNotEquals(0, quantityTest);
    }
}
