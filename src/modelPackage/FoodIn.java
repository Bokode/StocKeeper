package modelPackage;

import exceptionPackage.WrongInputException;

import java.util.Date;

public class FoodIn {
    private Date expirationDate;
    private Integer quantity;
    private Boolean isOpen;
    private Character nutriScore; // Facultatif;
    private Date purchaseDate; // Facultatif
    private Food food; // Clé étrangère
    private StorageType storageType; // Clé étrangère

    public FoodIn(Date expirationDate, Integer quantity, Boolean isOpen,
                  Character nutriScore, Date purchaseDate, Food food, StorageType storageType) {
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
        if (quantity <= 0) {
            throw new WrongInputException("La quantité doit être un nombre positif");
        } else {
            this.quantity = quantity;
        }
    }

    public void setIsOpen(Boolean isOpen) {
        this.isOpen = isOpen;
    }

    public void setNutriScore(Character nutriScore) {
        if (nutriScore != null && (nutriScore < 'A' || nutriScore > 'E')) {
            throw new WrongInputException("Le NutriScore doit être compris entre A et E");
        } else {
            this.nutriScore = nutriScore;
        }
    }

    public void setPurchaseDate(Date purchaseDate) {
        java.sql.Date today = new java.sql.Date(System.currentTimeMillis());
        if (purchaseDate != null && purchaseDate.after(today)) {
            throw new WrongInputException("La date d'achat ne peut pas être dans le future.");
        } else {
            this.purchaseDate = purchaseDate;
        }
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public void setStorageType(StorageType storageType) {
        this.storageType = storageType;
    }

    // Getter

    public Integer getQuantity() {
        return quantity;
    }

    public Food getFood() {
        return food;
    }

    public StorageType getStorageType() {
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
