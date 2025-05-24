package dataAccessPackage;

import exceptionPackage.*;
import interfacePackage.FoodInDAOInterface;
import modelPackage.*;

import java.sql.*;
import java.util.*;

public class FoodInDAO implements FoodInDAOInterface {

    /* ───────────────────────────
     *  Constantes table / colonnes
     * ─────────────────────────── */
    private static final String TBL_FOOD_IN        = "foodin";
    private static final String COL_ID             = "id";
    private static final String COL_EXPIRATION     = "expirationDate";
    private static final String COL_QTY            = "quantity";
    private static final String COL_IS_OPEN        = "isOpen";
    private static final String COL_NUTRI          = "nutriScore";
    private static final String COL_PURCHASE       = "purchaseDate";
    private static final String COL_FOOD_ID        = "food_id";
    private static final String COL_STORAGE_ID     = "storageType_id";

    /* ───────────────────────────
     *  DAO dépendants injectables
     * ─────────────────────────── */
    private final FoodDAO        foodDAO;
    private final FoodTypeDAO    foodTypeDAO;

    public FoodInDAO() {
        this(new FoodDAO(), new FoodTypeDAO());
    }
    public FoodInDAO(FoodDAO foodDAO, FoodTypeDAO foodTypeDAO) {
        this.foodDAO     = foodDAO;
        this.foodTypeDAO = foodTypeDAO;
    }

    /* ───────────────────────────
     *  CRUD : read all
     * ─────────────────────────── */
    @Override
    public List<FoodIn> getAllFoodIns() throws AppException {
        String sql = """
            SELECT fi.*,
                   f.label        AS food_label,
                   ft.label       AS foodType_label,
                   st.label       AS storage_label
              FROM foodin fi
              JOIN food f          ON fi.food_id       = f.id
              JOIN foodtype ft    ON f.foodType      = ft.id
              JOIN storagetype st ON fi.storageType_id = st.id
        """;

        List<FoodIn> list = new ArrayList<>();
        try (Connection c = FridgeDBAccess.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) list.add(mapResultSetToFoodIn(rs));

        } catch (SQLException e) { exceptionHandler(e); }
        return list;
    }

    /* ───────────────────────────
     *  CRUD : insert
     * ─────────────────────────── */
    public void addFoodIn(FoodIn fi) throws AppException {
        String sql = """
            INSERT INTO foodin
                (expirationDate, quantity, isOpen,
                 nutriScore, purchaseDate, food_id, storageType_id)
             VALUES (?,?,?,?,?,?,?)
        """;

        try (Connection c = FridgeDBAccess.getInstance().getConnection()) {

            int typeId     = getOrInsertFoodType(c, fi.getFood().getFoodType());
            int foodId     = getOrInsertFood(c, fi.getFood(), typeId);
            int storageId  = getOrInsertStorageType(c, fi.getStorageType());

            try (PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setDate   (1, new java.sql.Date(fi.getExpirationDate().getTime()));
                ps.setInt    (2, fi.getQuantity());
                ps.setBoolean(3, Boolean.TRUE.equals(fi.getOpen()));
                if (fi.getNutriScore() != null)
                    ps.setString(4, fi.getNutriScore().toString());
                else
                    ps.setNull  (4, Types.CHAR);
                if (fi.getPurchaseDate() != null)
                    ps.setDate(5, new java.sql.Date(fi.getPurchaseDate().getTime()));
                else
                    ps.setNull(5, Types.DATE);
                ps.setInt(6, foodId);
                ps.setInt(7, storageId);
                ps.executeUpdate();
            }

        } catch (SQLException e) { exceptionHandler(e); }
    }

    /* ───────────────────────────
     *  CRUD : update
     * ─────────────────────────── */
    @Override
    public Integer updateFoodIn(Food food,
                                StorageType storage,
                                Integer quantity,
                                boolean isOpen,
                                Character nutri,
                                java.util.Date purchase,
                                java.util.Date expiration) throws AppException {

        String sql = """
            UPDATE foodin
               SET quantity = ?, isOpen = ?, nutriScore = ?,
                   purchaseDate = ?, expirationDate = ?, storageType_id = ?
             WHERE food_id = ?
        """;

        try (Connection c = FridgeDBAccess.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            int typeId   = getOrInsertFoodType(c, food.getFoodType());
            int foodId   = getOrInsertFood    (c, food, typeId);
            int storageId= getOrInsertStorageType(c, storage);

            ps.setInt    (1, quantity);
            ps.setBoolean(2, isOpen);
            if (nutri != null)
                ps.setString(3, String.valueOf(nutri));
            else
                ps.setNull  (3, Types.CHAR);

            if (purchase != null)
                ps.setDate(4, new java.sql.Date(purchase.getTime()));
            else
                ps.setNull(4, Types.DATE);
            ps.setDate   (5, new java.sql.Date(expiration.getTime()));
            ps.setInt    (6, storageId);
            ps.setInt    (7, foodId);

            return ps.executeUpdate();

        } catch (SQLException e) { exceptionHandler(e); return 0; }
    }

    /* ───────────────────────────
     *  CRUD : delete (par id)
     * ─────────────────────────── */
    @Override
    public Integer deleteFoodInByFoodLabel(String foodLabel) throws AppException {
        String sql = "DELETE FROM foodin WHERE food_id = ?";

        try (Connection c = FridgeDBAccess.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            // Obtenir l'id de la food via son label
            int foodId = foodDAO.getFoodIdByLabel(foodLabel);
            if (foodId == -1) {
                throw new NotFoundException("Aucune food trouvée avec le label : " + foodLabel);
            }

            ps.setInt(1, foodId);
            return ps.executeUpdate();

        } catch (SQLException e) {
            exceptionHandler(e);
            return 0;
        }
    }

    /* ───────────────────────────
     *  CRUD : read unique (par id)
     * ─────────────────────────── */
    @Override
    public FoodIn getFoodInByFoodLabel(String foodLabel) throws AppException {
        String sql = """
        SELECT fi.*, f.label AS food_label, ft.label AS foodType_label, st.label AS storage_label
          FROM foodin fi
          JOIN food f          ON fi.food_id        = f.id
          JOIN foodtype ft    ON f.foodType       = ft.id
          JOIN storagetype st ON fi.storageType_id = st.id
         WHERE f.label = ?
         LIMIT 1
    """;

        try (Connection c = FridgeDBAccess.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, foodLabel);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapResultSetToFoodIn(rs);
            }

        } catch (SQLException e) {
            exceptionHandler(e);
        }

        return null;
    }

    /* ───────────────────────────
     *  Mapping ResultSet → objet
     * ─────────────────────────── */
    private FoodIn mapResultSetToFoodIn(ResultSet rs) throws SQLException {
        FoodType ft = new FoodType(rs.getString("foodType_label"));
        Food     f  = new Food   (rs.getString("food_label"), ft);
        StorageType st = new StorageType(rs.getString("storage_label"));

        Character nutri = null;
        String nutriStr = rs.getString(COL_NUTRI);
        if (nutriStr != null && !nutriStr.isEmpty()) nutri = nutriStr.charAt(0);

        return new FoodIn(
                rs.getDate(COL_EXPIRATION),
                rs.getInt(COL_QTY),
                rs.getBoolean(COL_IS_OPEN),
                nutri,
                rs.getDate(COL_PURCHASE),
                f,
                st
        );
    }

    /* ───────────────────────────
     *  Helpers d’insertion ou récupération
     * ─────────────────────────── */
    private int getOrInsertFoodType(Connection c, FoodType ft) throws SQLException {
        if (ft == null || ft.getLabel() == null)
            throw new SQLException("FoodType ou label nul");

        final String label = ft.getLabel();

        /* 1) Cherche d’abord */
        String select = "SELECT id FROM foodtype WHERE label = ?";
        try (PreparedStatement ps = c.prepareStatement(select)) {
            ps.setString(1, label);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("id");
            }
        }

        /* 2) Tente l’insert */
        String insert = "INSERT INTO foodtype (label) VALUES (?)";
        try (PreparedStatement ps = c.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, label);
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
        } catch (SQLException dup) {
            if ("23000".equals(dup.getSQLState())) {
                try (PreparedStatement ps = c.prepareStatement(select)) {
                    ps.setString(1, label);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) return rs.getInt("id");
                    }
                }
            }
            throw dup;
        }

        throw new SQLException("Impossible d’obtenir/insérer food_type pour label=" + label);
    }

    private int getOrInsertFood(Connection c, Food f, int typeId) throws SQLException {
        String s = "SELECT id FROM food WHERE label = ? AND foodType = ?";
        try (PreparedStatement ps = c.prepareStatement(s)) {
            ps.setString(1, f.getLabel()); ps.setInt(2, typeId);
            try (ResultSet rs = ps.executeQuery()) { if (rs.next()) return rs.getInt(1); }
        }
        String i = "INSERT INTO food (label, foodType) VALUES (?, ?)";
        try (PreparedStatement ps = c.prepareStatement(i, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, f.getLabel()); ps.setInt(2, typeId);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) { if (rs.next()) return rs.getInt(1); }
        }
        throw new SQLException("food insert failed");
    }

    private int getOrInsertStorageType(Connection c, StorageType st) throws SQLException {
        String s = "SELECT id FROM storagetype WHERE label = ?";
        try (PreparedStatement ps = c.prepareStatement(s)) {
            ps.setString(1, st.getLabel());
            try (ResultSet rs = ps.executeQuery()) { if (rs.next()) return rs.getInt(1); }
        }
        String i = "INSERT INTO storagetype (label) VALUES (?)";
        try (PreparedStatement ps = c.prepareStatement(i, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, st.getLabel());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) { if (rs.next()) return rs.getInt(1); }
        }
        throw new SQLException("storage_type insert failed");
    }

    /* ───────────────────────────
     *  Exception handler centralisé
     * ─────────────────────────── */
    private void exceptionHandler(SQLException e) throws AppException {
        switch (e.getSQLState()) {
            case "08S01" -> throw new DataBaseUnavailableException("Base de données indisponible.", e);
            case "28000" -> throw new AuthenticationFailureException("Identifiants incorrects.", e);
            case "22001" -> throw new DataSizeException("Valeur trop longue pour la colonne.", e);
            case "23000" -> throw new AlreadyExistException("Nourriture déja présente.", e);
            default      -> throw new RecipeOperationException("Erreur SQL " + e.getSQLState(), e);
        }
    }

    // Task 2
    public List<FoodInToSearch> getFoodInToSearch() throws AppException {
        List<FoodInToSearch> result = new ArrayList<>();

        String query = """
           SELECT fi.id AS foodIn_id,
                  fi.expirationDate,
                  fi.quantity,
                  fi.nutriScore,
                  fi.purchaseDate,
                  fi.isOpen,
                  f.id AS food_id,
                  f.label AS food_label,
                  ft.id AS foodType_id,
                  ft.label AS foodType_label
           FROM FoodIn fi
           JOIN Food f ON fi.food_id = f.id
           JOIN FoodType ft ON f.foodType = ft.id
           """;



        FridgeDBAccess dbAccess = FridgeDBAccess.getInstance();

        try (Connection conn = dbAccess.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                FoodType foodType = new FoodType(
                        rs.getString("foodType_label")
                );

                Food food = new Food(
                        rs.getString("food_label"),
                        foodType
                );

                Character nutriScore = null;
                String nutriScoreStr = rs.getString("nutriScore");
                if (nutriScoreStr != null && !nutriScoreStr.isEmpty()) {
                    nutriScore = nutriScoreStr.charAt(0);
                }

                FoodIn foodIn = new FoodIn(
                        rs.getDate("expirationDate"),
                        rs.getInt("quantity"),
                        rs.getBoolean("isOpen"),
                        nutriScore,
                        rs.getDate("purchaseDate"),
                        food,
                        null
                );

                result.add(new FoodInToSearch(foodIn, food, foodType));
            }

        } catch (SQLException e) {
            exceptionHandler(e);
        }

        return result;
    }

    // Search 2
    public List<ExpiredFood> expiredFood(StorageType storageType, FoodType foodType) throws AppException {
        List<ExpiredFood> result = new ArrayList<>();

        FridgeDBAccess dbAccess = FridgeDBAccess.getInstance();

        String query = """
        SELECT fi.expirationDate, fi.quantity, fi.isOpen, fi.nutriScore, fi.purchaseDate,
               f.label AS food_label,
               ft.label AS food_type_label,
               st.label AS storage_label,
               s.label AS season_label,
               a.label AS allergen_label
        FROM foodin fi
        JOIN food f ON fi.food_id = f.Id
        JOIN foodtype ft ON f.foodType = ft.Id
        JOIN storagetype st ON fi.storageType_id = st.Id
        LEFT JOIN seasonfood sf ON f.Id = sf.food
        LEFT JOIN season s ON sf.season = s.label
        LEFT JOIN foodallergen fa ON f.Id = fa.food
        LEFT JOIN allergen a ON fa.allergen = a.label
        WHERE fi.expirationDate < CURRENT_DATE()
          AND st.label = ?
          AND ft.label = ?
    """;

        try (Connection conn = dbAccess.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, storageType.getLabel());
            stmt.setString(2, foodType.getLabel());

            try (ResultSet rs = stmt.executeQuery()) {
                Map<String, ExpiredFood> map = new HashMap<>();

                while (rs.next()) {
                    // Clé unique pour éviter les doublons
                    String foodKey = rs.getString("food_label") + "_" + rs.getDate("expirationDate");

                    ExpiredFood expiredFood;
                    if (!map.containsKey(foodKey)) {
                        java.sql.Date expirationDate = rs.getDate("expirationDate");
                        Integer quantity = rs.getInt("quantity");
                        Boolean isOpen = rs.getBoolean("isOpen");
                        Character nutriScore = rs.getString("nutriScore") != null ? rs.getString("nutriScore").charAt(0) : null;
                        java.sql.Date purchaseDate = rs.getDate("purchaseDate");

                        FoodType ft = new FoodType(rs.getString("food_type_label"));
                        Food food = new Food(rs.getString("food_label"), ft);
                        StorageType st = new StorageType(rs.getString("storage_label"));

                        FoodIn foodIn = new FoodIn(
                                expirationDate,
                                quantity,
                                isOpen,
                                nutriScore,
                                purchaseDate,
                                food,
                                st
                        );

                        expiredFood = new ExpiredFood(foodIn);
                        map.put(foodKey, expiredFood);
                    } else {
                        expiredFood = map.get(foodKey);
                    }

                    String seasonLabel = rs.getString("season_label");
                    if (seasonLabel != null && expiredFood.getSeasons().stream().noneMatch(s -> s.getLabel().equals(seasonLabel))) {
                        expiredFood.addSeason(new Season(seasonLabel));
                    }

                    String allergenLabel = rs.getString("allergen_label");
                    if (allergenLabel != null && expiredFood.getAllergens().stream().noneMatch(a -> a.getLabel().equals(allergenLabel))) {
                        expiredFood.addAllergen(new Allergen(allergenLabel));
                    }
                }

                result.addAll(map.values());
            }
        } catch (SQLException e) {
            exceptionHandler(e);
        }

        return result;
    }
}