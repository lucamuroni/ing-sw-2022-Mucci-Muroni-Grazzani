package it.polimi.ingsw.model.dashboard;

public class StudentNotFoundException extends Exception{
    StudentNotFoundException(String errorMessage){
        super(errorMessage);
    }
}
