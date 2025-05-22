package controllerPackage;

import businessPackage.TypeMaterialManager;
import exceptionPackage.AppException;

public class TypeMaterialController {
    private TypeMaterialManager typeMaterialManager;

    public TypeMaterialController() {
        setTypeMaterialManager(new TypeMaterialManager());
    }

    public void setTypeMaterialManager(TypeMaterialManager typeMaterialManager) {
        this.typeMaterialManager = typeMaterialManager;
    }

    public void addTypeMaterial(String label) throws AppException {
        typeMaterialManager.addTypeMaterial(label);
    }

    public void deleteTypeMaterial(String label) throws AppException {
        typeMaterialManager.deleteTypeMaterial(label);
    }

    public int getIdByLabel(String label) throws AppException {
        return typeMaterialManager.getIdByLabel(label);
    }

    public String getLabelById(int id) throws AppException {
        return typeMaterialManager.getLabelById(id);
    }
}
