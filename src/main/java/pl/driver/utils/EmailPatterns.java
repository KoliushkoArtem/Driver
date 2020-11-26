package pl.driver.utils;

public enum EmailPatterns {

    API_EMAIL("sabeo.pl@gmail.com"),
    USERNAME_TO_REPLACE("usernameToReplace"),
    PASSWORD_TO_REPLACE("passwordToReplace"),
    USER_UPDATE_SUBJECT("Your Driver API account information was successfully updated"),
    USER_UPDATE_TEXT("Your account information was successfully updated.\n\nFor login please use username: " + USERNAME_TO_REPLACE.value + "\nPassword: " + PASSWORD_TO_REPLACE.value + "\n\nIf you will have any questions please contact us by email: " + API_EMAIL.value + "\n\nBest regards\nYour Driver team"),
    USER_PASSWORD_CHANGING_SUBJECT("Driver account password changing"),
    USER_PASSWORD_CHANGING_TEXT("Your account password to Driver API was changed.\n\nIf tht wasn't you, please contact us by email: " + API_EMAIL.value + "\n\nBest regards\nYour Driver team"),
    USER_REGISTRATION_SUBJECT("You was successfully registered on Driver API"),
    USER_REGISTRATION_TEXT("Thank you for registration on DriverApi!\n\nFor login and using API please use your username and password that you are receive in this mail\nUsername: " + USERNAME_TO_REPLACE.value + "\nPassword: " + PASSWORD_TO_REPLACE.value + "\n\nThank you for choosing our product!\n\nIf you will have any questions please contact us by email: " + API_EMAIL.value + "\n\nBest regards\nYour Driver team"),
    ADMIN_REGISTRATION_SUBJECT("Hello new Driver API Admin"),
    ADMIN_REGISTRATION_TEXT("You were chosen to be a Driver API Admin!\n\nFor login and using API please use your username and password that you are receive in this mail\nUsername: " + USERNAME_TO_REPLACE.value + "\nPassword: " + PASSWORD_TO_REPLACE.value + "\n\nIf you will have any questions please contact us by email: " + API_EMAIL.value + "\n\nBest regards\nYour Driver family");

    private final String value;

    EmailPatterns(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getValue(String username, String password) {
        String valueToReturn = value;
        if (password.length() > 20) {
            password = "Your password was not changed, please use your current password for login.";
        }
        valueToReturn = valueToReturn.replace(USERNAME_TO_REPLACE.value, username);
        valueToReturn = valueToReturn.replace(PASSWORD_TO_REPLACE.value, password);
        return valueToReturn;
    }
}