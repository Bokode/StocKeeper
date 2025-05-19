package modelPackage;

import java.time.LocalDate;
import java.util.Date;

public class FoodIn {
    private Integer id;
    private Date expirationDate;
    private Integer quantity;
    private Boolean isOpen;
    private Character nutriScore; // Facultatif;
    private Date purchaseDate; // Facultatif
    private Integer food; // Clé étrangère
    private Integer storageType; // Clé étrangère

    public FoodIn(Integer id, Date expirationDate, Integer quantity, Boolean isOpen,
                  Character nutriScore, Date purchaseDate, Integer food, Integer storageType) {
        setId(id);
        setExpirationDate(expirationDate);
        setQuantity(quantity);
        setIsOpen(isOpen);
        setNutriScore(nutriScore);
        setPurchaseDate(purchaseDate);
        setFood(food);
        setStorageType(storageType);
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setIsOpen(Boolean isOpen) {
        this.isOpen = isOpen;
    }

    public void setNutriScore(Character nutriScore) {
        this.nutriScore = nutriScore;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public void setFood(Integer food) {
        this.food = food;
    }

    public void setStorageType(Integer storageType) {
        this.storageType = storageType;
    }

    public Integer getId() {
        return id;
    }

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
