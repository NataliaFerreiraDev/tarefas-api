package br.com.tarefas_api.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Classe utilitária para manipulação de datas e formatos.
 * Contém métodos para formatar datas em formatos amigáveis.
 */
public class DateUtils {

    private static final String DD_MM_YYYY_HH_MM = "dd/MM/yyyy HH:mm";

    /**
     * Converte um objeto LocalDateTime para uma string formatada de forma amigável.
     * @param dateTime O LocalDateTime a ser formatado.
     * @return A data formatada como uma string.
     */
    public static String formatDate(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DD_MM_YYYY_HH_MM);
        return dateTime.format(formatter);
    }

    /**
     * Converte uma string de data no formato amigável para um objeto LocalDateTime.
     * @param dateTimeString A string representando a data.
     * @return O LocalDateTime convertido.
     */
    public static LocalDateTime parseDate(String dateTimeString) {
        if (dateTimeString == null || dateTimeString.isEmpty()) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DD_MM_YYYY_HH_MM);
        return LocalDateTime.parse(dateTimeString, formatter);
    }

}
