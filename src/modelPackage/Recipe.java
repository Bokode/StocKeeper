package modelPackage;

import java.sql.Date;

public class Recipe {
    private Integer id;
    private String label;
    private String description;
    private Integer caloricIntake;
    private Date lastDayDone; // Facultatif
    private Integer timeToMake; // Facultatif
    private Boolean isCold; // Facultatif
    private Integer type; // Clé étrangère

    public Recipe(Integer id, String label, String description, Integer caloricIntake, Date lastDayDone, Integer timeToMake, Boolean isCold, Integer type) {
        setId(id);
        setLabel(label);
        setDescription(description);
        setCaloricIntake(caloricIntake);
        setLastDayDone(lastDayDone);
        setTimeToMake(timeToMake);
        setIsCold(isCold);
        setType(type);
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCaloricIntake(Integer caloricIntake) {
        if (caloricIntake <= 0) {
            // throw error
        } else {
            this.caloricIntake = caloricIntake;
        }
    }

    public void setLastDayDone(Date lastDayDone) {
        Date today = new Date(System.currentTimeMillis());
        if (lastDayDone.after(today)) {
            // throw error
        } else {
            this.lastDayDone = lastDayDone;
        }
    }

    public void setTimeToMake(Integer timeToMake) {
        if (timeToMake <= 0) {
            // throw error
        } else {
            this.timeToMake = timeToMake;
        }
    }

    public void setIsCold(Boolean isCold) {
        this.isCold = isCold;
    }

    public void setType(Integer type) {
        this.type = type;
    }



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

    public int getId() {
        return id;
    }

    public Integer getCaloricIntake() {
        return caloricIntake;
    }

    public Integer getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }
}