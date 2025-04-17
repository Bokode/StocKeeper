import java.sql.Date;
import java.sql.Timestamp;

public class Recipe {
    private int id;
    private String label;
    private String description;
    private Integer caloricIntake;
    private Date lastDayDone; // Facultatif
    private Integer timeToMake; // Facultatif
    private Boolean isCold; // Facultatif
    private Integer type; // Clé étrangère

}
