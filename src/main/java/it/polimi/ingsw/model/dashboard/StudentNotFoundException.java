package it.polimi.ingsw.model.dashboard;

class StudentNotFoundException extends Exception{
    StudentNotFoundException(String errorMessage){
        super(errorMessage);
    }
}
