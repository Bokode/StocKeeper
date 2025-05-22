package controllerPackage;

import businessPackage.MaterialManager;
import exceptionPackage.AppException;
import modelPackage.Material;

public class MaterialController {
    private MaterialManager materialManager;

    public MaterialController() {
        setMaterialManager(new MaterialManager());
    }

    public void setMaterialManager(MaterialManager materialManager) {
        this.materialManager = materialManager;
    }

    public void addMaterial(String label, String typeLabel) throws AppException {
        materialManager.addMaterial(label, typeLabel);
    }

    public void deleteMaterial(String label) throws AppException {
        materialManager.deleteMaterial(label);
    }

    public int getMaterialIdByLabel(String label) throws AppException {
        return materialManager.getMaterialIdByLabel(label);
    }

    public String getMaterialLabelById(int id) throws AppException {
        return materialManager.getMaterialLabelById(id);
    }

    public void addMaterial(Material mat) throws AppException {
        materialManager.addMaterial(mat);
    }
}
