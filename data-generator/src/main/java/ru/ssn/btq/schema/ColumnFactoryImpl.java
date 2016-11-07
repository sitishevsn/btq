package ru.ssn.btq.schema;

import javax.annotation.Nullable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColumnFactoryImpl implements ColumnFactory {

    private static final String IN_ROUND_BRACKET_REGEX = "\\(([^()]+)\\)";

    private static final Pattern IN_ROUND_BRACKET_PATTERN = Pattern.compile(IN_ROUND_BRACKET_REGEX);

    @Override
    public Column getColumn(String name, String type) {
        try {
            if (type.startsWith("varchar") || type.startsWith("nvarchar")) {
                return parseVarchar(name, type);
            } else if (type.startsWith("number")) {
                return parseNumber(name, type);
            } else if (type.startsWith("date")) {
                return parseDate(name, type);
            } else if (type.startsWith("timestamp")) {
                return parseTimestamp(name, type);
            } else if (type.startsWith("char")) {
                return parseChar(name, type);
            } else {
                throw new IllegalArgumentException("Unsupported column type " + type);
            }

        } catch (RuntimeException e) {
            throw new RuntimeException("Error building column for column " + name + " with type " + type, e);
        }
    }


    private Column parseChar(String columnName, String type) {
        String strings = inRoundBrackets(type);

        Integer length = null;
        if (strings != null && strings.length() != 0) {
            length = Integer.parseInt(strings);
        }

        return new CharColumn(columnName, length);
    }

    private Column parseTimestamp(String columnName, String type) {
        String strings = inRoundBrackets(type);

        Integer length = null;
        if (strings != null && strings.length() != 0) {
            length = Integer.parseInt(strings);
        }

        return new TimestampColumn(columnName, length);
    }

    private Column parseDate(String columnName, String type) {
        return new DateColumn(columnName);
    }


    private Column parseVarchar(String columnName, String type) {
        if (columnName.contains("phonenumber")) {
            return new PhoneNumberColumn(columnName);
        }

        String strings = inRoundBrackets(type);

        Integer length = null;
        if (strings != null && strings.length() != 0) {
            length = Integer.parseInt(strings);
        }

        return new StringColumn(columnName, length);
    }


    private Column parseNumber(String columnName, String type) {
        if (columnName.contains("id")) {
            return new SequenceColumn(columnName);
        }

        String strings = inRoundBrackets(type);
        if (strings != null && strings.length() != 0) {
            String[] split = strings.split(",");
            if (split.length == 1) {
                // integer number
                Integer length = Integer.parseInt(split[0].trim());
                return new BigIntegerColumn(columnName, length);
            } else if (split.length == 2) {
                Integer precision = Integer.parseInt(split[0].trim());
                Integer scale = Integer.parseInt(split[1].trim());
                return new BigDecimalColumn(columnName, precision, scale);
            } else {
                throw new RuntimeException("unknown type format for column " + columnName + " with type " + type);
            }
        } else {
            // just number
            return new BigIntegerColumn(columnName, 18);
        }
    }


    @Nullable
    private static String inRoundBrackets(String string) {
        return inBrackets(IN_ROUND_BRACKET_PATTERN, string);
    }

    @Nullable
    private static String inBrackets(Pattern bracketPattern, String string) {
        Matcher matcher = bracketPattern.matcher(string);
        while (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
