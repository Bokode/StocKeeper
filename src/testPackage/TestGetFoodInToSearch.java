package testPackage;

import dataAccessPackage.FoodInDAO;
import modelPackage.FoodInToSearch;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class TestGetFoodInToSearch {

    @Test
    void testGetFoodInToSearch() throws SQLException {
        FoodInDAO dao = new FoodInDAO();
        List<FoodInToSearch> result = dao.getFoodInToSearch();

        assertNotNull(result);
        assertFalse(result.isEmpty());

        FoodInToSearch item = result.get(0);
        assertEquals("Carotte", item.getFood().getLabel());
        assertEquals("Légume", item.getFoodType().getLabel());
    }
}
