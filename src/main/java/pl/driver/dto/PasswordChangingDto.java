package pl.driver.dto;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class PasswordChangingDto {

    private String currentPassword;

    /**
     * Password pattern: length 8-20, should contain at leas 1 digit/uppercase/lowercase. NO whitespaces allowed.
     */
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).{8,20}$")
    private String newPassword;
}