package test;

import dataAccessPackage.FoodInDAO;
import modelPackage.FoodInToSearch;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class TestGetFoodInToSearch {

    @Test
    void testGetFoodInToSearch() throws SQLException {
        FoodInDAO dao = new FoodInDAO(); // adapte selon ton interface
        List<FoodInToSearch> result = dao.getFoodInToSearch();

        assertNotNull(result);
        assertFalse(result.isEmpty());

        FoodInToSearch item = result.get(0);
        assertEquals("Pomme", item.getFood().getLabel());
        assertEquals("Fruit", item.getFoodType().getLabel());
    }
}
