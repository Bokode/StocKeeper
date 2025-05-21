package modelPackage;

import exceptionPackage.WrongInputException;

import java.util.Date;

public class FoodIn {
    private Date expirationDate;
    private Integer quantity;
    private Boolean isOpen;
    private Character nutriScore; // Facultatif;
    private Date purchaseDate; // Facultatif
    private Integer food; // Clé étrangère
    private Integer storageType; // Clé étrangère

    public FoodIn(Date expirationDate, Integer quantity, Boolean isOpen,
                  Character nutriScore, Date purchaseDate, Integer food, Integer storageType) {
        setExpirationDate(expirationDate);
        setQuantity(quantity);
        setIsOpen(isOpen);
        setNutriScore(nutriScore);
        setPurchaseDate(purchaseDate);
        setFood(food);
        setStorageType(storageType);
    }

    // Setter

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setQuantity(Integer quantity) {
        if (quantity < 0) {
            throw new WrongInputException("Quantity must be a positive number");
        } else {
            this.quantity = quantity;
        }
    }

    public void setIsOpen(Boolean isOpen) {
        this.isOpen = isOpen;
    }

    public void setNutriScore(Character nutriScore) {
        if (nutriScore != null && (nutriScore < 'A' || nutriScore > 'E')) {
            throw new WrongInputException("NutriScore must be between A and E");
        } else {
            this.nutriScore = nutriScore;
        }
    }

    public void setPurchaseDate(Date purchaseDate) {
        java.sql.Date today = new java.sql.Date(System.currentTimeMillis());
        if (purchaseDate != null && purchaseDate.after(today)) {
            throw new WrongInputException("Purchase date cannot be in the future.");
        } else {
            this.purchaseDate = purchaseDate;
        }
    }

    public void setFood(Integer food) {
        this.food = food;
    }

    public void setStorageType(Integer storageType) {
        this.storageType = storageType;
    }

    // Getter

    public Integer getQuantity() {
        return quantity;
    }

    public Integer getFood() {
        return food;
    }

    public Integer getStorageType() {
        return storageType;
    }

    public Boolean getOpen() {
        return isOpen;
    }

    public Character getNutriScore() {
        return nutriScore;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }
}
