package br.com.courses.util;

public class CPFCNPJValidator {

    public static boolean isValidCpf(String cpf) {
        // Remove non-numeric characters
        cpf = cpf.replaceAll("[^0-9]", "");

        // Check if CPF has 11 digits
        if (cpf.length() != 11) return false;

        // Check if all digits are equal (e.g., "111.111.111-11")
        if (cpf.matches("(\\d)\\1{10}")) return false;

        // CPF validation using the digits (Verification Digits)
        int[] cpfNumbers = new int[11];
        for (int i = 0; i < 11; i++) {
            cpfNumbers[i] = Character.getNumericValue(cpf.charAt(i));
        }

        int sum1 = 0;
        for (int i = 0; i < 9; i++) {
            sum1 += cpfNumbers[i] * (10 - i);
        }
        int remainder1 = sum1 % 11;
        int digit1 = (remainder1 < 2) ? 0 : 11 - remainder1;

        if (cpfNumbers[9] != digit1) return false;

        int sum2 = 0;
        for (int i = 0; i < 10; i++) {
            sum2 += cpfNumbers[i] * (11 - i);
        }
        int remainder2 = sum2 % 11;
        int digit2 = (remainder2 < 2) ? 0 : 11 - remainder2;

        return cpfNumbers[10] == digit2;
    }

    public static boolean isValidCnpj(String cnpj) {

        cnpj = cnpj.replaceAll("[^0-9]", "");

        if (cnpj.length() != 14) return false;

        if (cnpj.matches("(\\d)\\1{13}")) return false;

        int[] cnpjNumbers = new int[14];
        for (int i = 0; i < 14; i++) {
            cnpjNumbers[i] = Character.getNumericValue(cnpj.charAt(i));
        }

        int[] firstWeights = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] secondWeights = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

        int sum1 = 0;
        for (int i = 0; i < 12; i++) {
            sum1 += cnpjNumbers[i] * firstWeights[i];
        }
        int remainder1 = sum1 % 11;
        int digit1 = (remainder1 < 2) ? 0 : 11 - remainder1;

        if (cnpjNumbers[12] != digit1) return false;

        int sum2 = 0;
        for (int i = 0; i < 13; i++) {
            sum2 += cnpjNumbers[i] * secondWeights[i];
        }
        int remainder2 = sum2 % 11;
        int digit2 = (remainder2 < 2) ? 0 : 11 - remainder2;

        return cnpjNumbers[13] == digit2;
    }

}
