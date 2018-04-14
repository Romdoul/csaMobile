package com.totalmaster.csa;

import java.util.regex.Pattern;

/**
 * Created by KEEN on 3/18/2018.
 */

class ValidationHelper {
    private String userName, userPassword, userConfirmPassword;
    private String country, fullName, age, day, month, year, address, phoneNumber, email;
    private String workPosition, workLocation, expectedSalary, gettingSalary, receiveSalary;
    private String currentDesignation, currentYearExperience, skill, fullExperience, coWorker, disability, fovorite, currentSalary, receiveSalaryWhen;
    private String memberInFamily, fatherJob, numberOfSibling, numberOfRelative, currentAsset;


    // accept input from CreateAccount
    ValidationHelper(String userName, String userPassword, String userConfirmPassword){
        this.userName = userName;
        this.userPassword = userPassword;
        this.userConfirmPassword = userConfirmPassword;
    }

    // accept input from LoginActivity
    ValidationHelper(String userName, String userPassword){
        this.userName = userName;
        this.userPassword = userPassword;
    }

    //accept input from register form
    ValidationHelper (String country, String fullName, String age, String day, String month, String year,
                      String address, String phoneNumber, String email){
        this.country = country;
        this.fullName = fullName;
        this.age = age;
        this.day = day;
        this.month = month;
        this.year = year;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email =email;
    }

//    accept input from working condition form
    ValidationHelper(String workLocation, String workPosition, String expectedSalary,
                      String gettingSalary, String receiveSalary){
        this.workLocation = workLocation;
        this.workPosition = workPosition;
        this.expectedSalary = expectedSalary;
        this.gettingSalary = gettingSalary;
        this.receiveSalary = receiveSalary;
    }


    boolean isReadyForRegisterForm(){
        return !isCountryEmpty() && isValidCounyty() && !isFullnameEmpty() && !isAgeEmpty() && !isDayEmpty() && !isMonthEmpty() && !isYearEmpty() && !isAddressEmpty() && !isPhoneNumberEmpty() && isValidPhoneNumber() && !isEmailEmpty() && isValidEmail();
    }

    boolean isReadyForFamilyDetailForm(){
        return !isFatherJobEmpty() && !isNumberOfSiblingEmpty() && !isNumberOfRelativeEmpty() && !isCurrentAssetEmpty();
    }

    boolean isReadyForLogin(){
        return !isNameEmpty() && isValidName() && isValidPassword();
    }

    boolean isReadyForCreateAccount(){
        return !isNameEmpty() && !isNameExceedLength() && isValidPassword() && isPasswordMatched();
    }

    boolean isNameEmpty(){
        return userName.length() == 0;
    }

    //screen 1, registration form
    boolean isCountryEmpty(){
        return country.length() == 0;
    }
    boolean isValidCounyty(){
        return country.matches("[a-zA-Z.? ]*");
    }
    boolean isFullnameEmpty(){
        return fullName.length() == 0;
    }
    boolean isValidFullName(){
        return userName.matches("[a-zA-Z.? ]*");
    }
    boolean isAgeEmpty(){
        return age.equals("អាយុ | Age");
    }
    boolean isDayEmpty(){
        return day.equals("ថ្ងៃ | Day");
    }
    boolean isMonthEmpty(){
        return month.equals("ខែ | Month");
    }
    boolean isYearEmpty(){
        return year.equals("ឆ្នាំ | Year");
    }
    boolean isAddressEmpty(){
        return address.length() == 0;
    }
    boolean isPhoneNumberEmpty(){
        return phoneNumber.length() == 0;
    }
    boolean isValidPhoneNumber(){
        return phoneNumber.length()>=9;
    }

    //screen 4, family form
//    boolean isMemberInFamilyEmpty(){
//        return memberInFamily.equals("សូមជ្រើសរើស | Please select");
//    }
    boolean isFatherJobEmpty(){
        return memberInFamily.equals("សូមជ្រើសរើស | Please select");
    }
    boolean isNumberOfSiblingEmpty(){
        return memberInFamily.equals("សូមជ្រើសរើស | Please select");
    }
    boolean isNumberOfRelativeEmpty(){
        return memberInFamily.equals("សូមជ្រើសរើស | Please select");
    }
    boolean isCurrentAssetEmpty(){
        return memberInFamily.equals("សូមជ្រើសរើស | Please select");
    }

    //screen 5, education form



    boolean isNameExceedLength(){
        return userName.length() >= 30;
    }

    boolean isValidName(){
        return userName.matches("[a-zA-Z.? ]*");
    }

    boolean isEmailEmpty(){
        return email.length() == 0;
    }

    boolean isValidPassword(){
        return userPassword.length() >= 6 && userPassword.length() > 0;
    }

    boolean isPasswordMatched(){
        return userPassword.equals(userConfirmPassword);
    }

    boolean isPasswordEmpty(){
        return userPassword.length() == 0;
    }

    boolean isValidEmail(){
        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }
}
