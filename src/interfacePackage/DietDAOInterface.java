package interfacePackage;

import exceptionPackage.AppException;
import modelPackage.Diet;

import java.util.List;

public interface DietDAOInterface {
    List<Diet> getAllDiets() throws AppException;
}