package testPackage;

import businessPackage.FoodInManager;

import modelPackage.QuantityLeft;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TestShowQuantityLeft {
    private FoodInManager foodInManager;

    @BeforeEach
    public void setUp() {
        foodInManager = new FoodInManager();
    }
    
    @Test
    public void testShowQuantityLeftSolide() {
        QuantityLeft quantityTest = foodInManager.showQuantityLeft("Solide");
        assertNotEquals(0, quantityTest.getQuantity());
    }

    @Test
    public void testShowQuantityLeftQuantité() {
        QuantityLeft quantityTest = foodInManager.showQuantityLeft("Quantité");
        assertNotEquals(0, quantityTest.getQuantity());
    }
}