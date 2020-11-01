package pl.coderslab.driver.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PasswordGenerator {

    /**
     * @return String randomly generated password 12 characters (3 random uppercase,
     * 3 random lowercase, 3 random digits, 3 totally random uppercase/lowercase/digits characters)
     */
    public static String generatePassword() {
        String upperCaseLetters = RandomStringUtils.random(3, 65, 90, true, true);
        String lowerCaseLetters = RandomStringUtils.random(3, 97, 122, true, true);
        String numbers = RandomStringUtils.randomNumeric(3);
        String totalChars = RandomStringUtils.randomAlphanumeric(3);
        String combinedChars = upperCaseLetters.concat(lowerCaseLetters)
                .concat(numbers)
                .concat(totalChars);
        List<Character> pwdChars = combinedChars.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());
        Collections.shuffle(pwdChars);

        return pwdChars.stream()
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }
}
