// calculator.StringCalculator.java (최종 정리)

package calculator;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculator {
    private static final Pattern CUSTOM_DELIMITER_PATTERN = Pattern.compile("//(.)\\\\n(.*)");
    public static int calculate(String text) {
        if (text == null || text.isEmpty()) {
            return 0;
        }

        if (text.startsWith("//")) {
            return calculateWithCustomDelimiter(text);
        }
        return calculateWithBasicDelimiters(text);
    }

    private static int calculateWithBasicDelimiters(String text) {
        String[] numberTokens = text.split("[,:]");
        return sumAndValidateTokens(numberTokens);
    }

    private static int calculateWithCustomDelimiter(String text) {
        Matcher m = CUSTOM_DELIMITER_PATTERN.matcher(text);
        if (!m.find()) {
            throw new IllegalArgumentException("커스텀 구분자 정의 형식이 잘못되었습니다.");
        }

        String customDelimiter = Pattern.quote(m.group(1));
        String numbersText = m.group(2);


        String[] numberTokens = numbersText.split(customDelimiter);
        return sumAndValidateTokens(numberTokens);
    }

    private static int sumAndValidateTokens(String[] numberTokens) {
        int sum = 0;

        for (String token : numberTokens) {
            if (token.trim().isEmpty()) {
                continue;
            }

            sum += parseAndValidateNumber(token);
        }
        return sum;
    }

    private static int parseAndValidateNumber(String s) {
        int number;
        try {
            number = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("입력값에 유효하지 않은 숫자 형식(" + s + ")이 포함되어 있습니다.");
        }

        if (number < 0) {
            throw new IllegalArgumentException("입력값에 음수(" + number + ")가 포함되어 있습니다.");
        }

        return number;
    }
}