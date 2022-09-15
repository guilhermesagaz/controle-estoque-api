package com.br.controleestoqueapi.shared.util;

public class StringUtils extends org.springframework.util.StringUtils {
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }
    public static boolean isNotEmpty(String str) {
        return !StringUtils.isEmpty(str);
    }

    public static String messageEntityNotFound(String resourceName, Long resourceId) {
        return String.format("%s não encontrado(a) para o ID %d", resourceName, resourceId);
    }

    public static String messageEntityNotFound(String resourceName, String codigo) {
        return String.format("%s não encontrado(a) para o Código %s", resourceName, codigo);
    }
}
