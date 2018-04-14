package com.totalmaster.csa;

/**
 * Created by KEEN on 3/27/2018.
 */

class ValidationHelper2 {
    private String workLocation, workPosition, expectedSalary, receiveSalaryHow, receiveSalaryWhen;
    private String currentDesignation, workingExperience, skill, yearOfExperience, numberOfCoworker,
            disability, workingField, currentSalary, receiveSalary;
    private String training, getTraining,trainingDuration;

    ValidationHelper2(String workLocation, String workPosition, String expectedSalary, String receiveSalaryHow,
                      String receiveSalaryWhen){
        this.workLocation = workLocation;
        this.workPosition = workPosition;
        this.expectedSalary = expectedSalary;
        this.receiveSalaryHow = receiveSalaryHow;
        this.receiveSalaryWhen = receiveSalaryWhen;
    }

    ValidationHelper2(String currentDesignation, String workingExperience, String skill,
                      String yearOfExperience, String numberOfCoworker, String disability,
                      String workingField, String currentSalary, String receiveSalary){
        this.currentDesignation = currentDesignation;
        this.workingExperience = workingExperience;
        this.skill =skill;
        this.yearOfExperience = yearOfExperience;
        this.numberOfCoworker = numberOfCoworker;
        this.disability = disability;
        this.workingField = workingField;
        this.currentSalary = currentSalary;
        this.receiveSalary = receiveSalary;
    }

    //accept input from training form
    ValidationHelper2(String training, String getTraining, String trainingDuration){
        this.training = training;
        this.getTraining = getTraining;
        this.trainingDuration = trainingDuration;
    }

    //screen2 working condition
    boolean isReadyForWorkingConditionForm(){
        return !isWorkPositionEmpty() && !isWorkLocationEmpty() && !isExpectedSalaryEmpty() &&
                !isGettingSalaryEmpty() && !isReceiveSalaryWhenEmpty();
    }
    boolean isWorkPositionEmpty(){
        return workPosition.equals("សូមជ្រើសរើស | Please select");
    }
    boolean isWorkLocationEmpty(){
        return workLocation.length()==0;
    }
    boolean isExpectedSalaryEmpty(){
        return expectedSalary.equals("");
    }
    boolean isGettingSalaryEmpty(){
        return receiveSalaryHow.equals("");
    }
    boolean isReceiveSalaryWhenEmpty(){
        return receiveSalaryWhen.equals("");
    }

    //screen3 working detail
    boolean isReadyForWorkingDetailForm(){
        return !isCurrentDesignationEmpty() && !isWorkExperienceEmpty() && !isSkillEmpty() &&
                !isYearOfExperienceEmpty() && !isNumberOfCoworker() && !isDisabilityEmpty() &&
                !isWorkingFieldEmpty() && !isCurrentSalaryEmpty() && !isReceiveSalaryEmpty();
    }
    boolean isCurrentDesignationEmpty(){
        return currentDesignation.equals("សូមជ្រើសរើស | Please select");
    }
    boolean isWorkExperienceEmpty(){
        return workingExperience.equals("សូមជ្រើសរើស | Please select");
    }
    boolean isSkillEmpty(){
        return skill.equals("សូមជ្រើសរើស | Please select");
    }
    boolean isYearOfExperienceEmpty(){
        return yearOfExperience.equals("សូមជ្រើសរើស | Please select");
    }
    boolean isNumberOfCoworker(){
        return numberOfCoworker.length()==0;
    }
    boolean isDisabilityEmpty(){
        return disability.equals("សូមជ្រើសរើស | Please select");
    }
    boolean isWorkingFieldEmpty(){
        return workingField.equals("សូមជ្រើសរើស | Please select");
    }
    boolean isCurrentSalaryEmpty(){
        return currentSalary.equals("");
    }
    boolean isReceiveSalaryEmpty(){
        return receiveSalary.equals("");
    }

    boolean isReadyforTrainingForm(){
        return !isTrainingEmpty() && !isGetTrainingEmpty() && !isTrainingDurationEmpty();
    }
    boolean isTrainingEmpty(){
        return training.equals("");
    }
    boolean isGetTrainingEmpty(){
        return getTraining.equals("សូមជ្រើសរើស | Please select");
    }
    boolean isTrainingDurationEmpty(){
        return trainingDuration.equals("");
    }

}
