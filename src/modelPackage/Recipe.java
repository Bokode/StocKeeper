package modelPackage;

import exceptionPackage.WrongInputException;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Recipe {
    private String label;
    private String description;
    private Integer caloricIntake;
    private Date lastDayDone; // Facultatif
    private Integer timeToMake; // Facultatif
    private Boolean isCold; // Facultatif
    private RecipeType type; // Clé étrangère
    private List<RecipeMaterial> materials;
    private List<IngredientAmount> ingredients;

    public Recipe(String label, String description, Integer caloricIntake, Date lastDayDone, Integer timeToMake, Boolean isCold, RecipeType type) {
        setLabel(label);
        setDescription(description);
        setCaloricIntake(caloricIntake);
        setLastDayDone(lastDayDone);
        setTimeToMake(timeToMake);
        setIsCold(isCold);
        setType(type);
        materials = new ArrayList<>();
        ingredients = new ArrayList<>();
    }

    // Setter

    public void setLabel(String label) {
        this.label = label;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCaloricIntake(Integer caloricIntake) {
        if (caloricIntake <= 0) {
            throw new WrongInputException("Caloric intake must be a positive number");
        } else {
            this.caloricIntake = caloricIntake;
        }
    }

    public void setLastDayDone(Date lastDayDone) {
        Date today = new Date(System.currentTimeMillis());
        if (lastDayDone != null && lastDayDone.after(today)) {
            throw new WrongInputException("Last day done must be before Now");
        } else {
            this.lastDayDone = lastDayDone;
        }
    }

    public void setTimeToMake(Integer timeToMake) {
        if (timeToMake != null && timeToMake <= 0) {
            throw new WrongInputException("Time to make must be a positive number");
        } else {
            this.timeToMake = timeToMake;
        }
    }

    public void setIsCold(Boolean isCold) {
        this.isCold = isCold;
    }

    public void setType(RecipeType type) {
        this.type = type;
    }

    // Getter

    public Integer getTimeToMake() {
        return timeToMake;
    }
  
    public String getLabel()
    {
        return label;
    }

    public Boolean getCold() {
        return isCold;
    }

    public Date getLastDayDone() {
        return lastDayDone;
    }

    public Integer getCaloricIntake() {
        return caloricIntake;
    }

    public RecipeType getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }
}