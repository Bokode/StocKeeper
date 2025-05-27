package controllerPackage;

import businessPackage.MaterialManager;
import exceptionPackage.AppException;

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

    public int getMaterialIdByLabel(String label) throws AppException {
        return materialManager.getMaterialIdByLabel(label);
    }
}
