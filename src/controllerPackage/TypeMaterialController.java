package controllerPackage;

import businessPackage.TypeMaterialManager;

public class TypeMaterialController {
    private TypeMaterialManager typeMaterialManager;

    public TypeMaterialController() {
        setTypeMaterialManager(new TypeMaterialManager());
    }

    public void setTypeMaterialManager(TypeMaterialManager typeMaterialManager) {
        this.typeMaterialManager = typeMaterialManager;
    }
}
