package org.shop.utils.table;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TableRenderer {

    private static <T> List<Field> collectAnnotatedFields(Class<T> clazz) {
        return collectAnnotatedFields(clazz, null);
    }

    private static <T> List<Field> collectAnnotatedFields(Class<T> clazz, TableConfig config) {
        List<Field> annotated = new ArrayList<>();
        for (Field f : clazz.getDeclaredFields()) {
            if (!f.isAnnotationPresent(TableColumn.class)) {
                continue;
            }
            if (config != null && config.isExcluded(f.getName())) {
                continue;
            }
            f.setAccessible(true);
            annotated.add(f);
        }
        annotated.sort(Comparator.comparingInt(
                f -> f.getAnnotation(TableColumn.class).order()
        ));
        return annotated;
    }

    private static List<String> extractHeaders(List<Field> cols) {
        List<String> headers = new ArrayList<>();
        for (Field f : cols) {
            TableColumn ann = f.getAnnotation(TableColumn.class);
            String h = ann.header();
            if (h == null || h.isEmpty()) {
                h = f.getName();
            }
            headers.add(h);
        }
        return headers;
    }

    private static <T> List<List<String>> collectRows(List<T> items, List<Field> cols) {
        List<List<String>> rows = new ArrayList<>();
        for (T obj : items) {
            List<String> row = new ArrayList<>();
            for (Field f : cols) {
                try {
                    Object val = f.get(obj);
                    row.add(val == null ? "" : val.toString());
                } catch (IllegalAccessException e) {
                    row.add("");
                }
            }
            rows.add(row);
        }
        return rows;
    }

    private static int[] computeColumnWidths(
            List<Field> cols,
            List<String> headers,
            List<List<String>> rows) {
        int n = headers.size();
        int[] widths = new int[n];

        for (int i = 0; i < n; i++) {
            int headerLen = headers.get(i).length();
            TableColumn ann = cols.get(i).getAnnotation(TableColumn.class);
            int annWidth = ann.width();
            widths[i] = (annWidth > 0) ? Math.max(headerLen, annWidth) : headerLen;
        }

        for (List<String> row : rows) {
            for (int i = 0; i < n; i++) {
                int cellLen = row.get(i).length();
                if (cellLen > widths[i]) {
                    widths[i] = cellLen;
                }
            }
        }
        return widths;
    }

    private static String buildSeparator(int[] colWidths) {
        StringBuilder sep = new StringBuilder("+");
        for (int w : colWidths) {
            sep.append("-".repeat(w + 2)).append("+");
        }
        return sep.toString();
    }

    private static String buildDataLine(List<String> cells, int[] colWidths) {
        StringBuilder line = new StringBuilder("|");
        for (int i = 0; i < cells.size(); i++) {
            String text = cells.get(i);
            int padding = colWidths[i] - text.length();
            line.append(" ")
                    .append(text)
                    .append(" ".repeat(padding))
                    .append(" |");
        }
        return line.toString();
    }

    public static <T> String renderAsAsciiTable(List<T> items) {
        return renderAsAsciiTable(items, null);
    }

    public static <T> String renderAsAsciiTable(List<T> items, TableConfig config) {
        if (items == null || items.isEmpty()) {
            return "";
        }

        Class<?> clazz = items.get(0).getClass();
        List<Field> cols = collectAnnotatedFields(clazz, config);

        List<String> headers = extractHeaders(cols);
        List<List<String>> rows = collectRows(items, cols);
        int[] colWidths = computeColumnWidths(cols, headers, rows);

        String separator = buildSeparator(colWidths);
        StringBuilder sb = new StringBuilder();

        sb.append(separator).append("\n");
        sb.append(buildDataLine(headers, colWidths)).append("\n");
        sb.append(separator).append("\n");
        for (List<String> row : rows) {
            sb.append(buildDataLine(row, colWidths)).append("\n");
        }
        sb.append(separator);

        return sb.toString();
    }
}
