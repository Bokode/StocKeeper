package modelPackage;

import exceptionPackage.WrongInputException;

import java.util.Date;

public class Recipe {
    private String label;
    private String description;
    private Integer caloricIntake; // Facultatif
    private Date lastDayDone; // Facultatif
    private Integer timeToMake; // Facultatif
    private Boolean isCold;
    private RecipeType type; // Clé étrangère

    public Recipe(String label, String description, Integer caloricIntake, Date lastDayDone, Integer timeToMake,
                  Boolean isCold, RecipeType type) {
        setLabel(label);
        setDescription(description);
        setCaloricIntake(caloricIntake);
        setLastDayDone(lastDayDone);
        setTimeToMake(timeToMake);
        setIsCold(isCold);
        setType(type);
    }

    // Setter

    public void setLabel(String label) {
        this.label = label;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCaloricIntake(Integer caloricIntake) {
        if (caloricIntake != null && caloricIntake < 0) {
            throw new WrongInputException("L'apport calorique doit être un nombre positif");
        } else {
            this.caloricIntake = caloricIntake;
        }
    }

    public void setLastDayDone(Date lastDayDone) {
        Date today = new Date(System.currentTimeMillis());
        if (lastDayDone != null && lastDayDone.after(today)) {
            throw new WrongInputException("Le dernier jour doit être antérieur à maintenant");
        } else {
            this.lastDayDone = lastDayDone;
        }
    }

    public void setTimeToMake(Integer timeToMake) {
        if (timeToMake != null && timeToMake < 0) {
            throw new WrongInputException("Le temps de préparation doit être un nombre positif");
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