package ru.ssn.btq;

public final class Properties {

    public static final String INPUT_TABLES_KEY = "input.table";

    public static final String DEFAULT_INPUT_TABLE = "bill_docum_2,link_history,phone_history,register_journal,suo_opcat_day,suo_opcat_hist,suo_subbranch_hist,user_links";
//    public static final String DEFAULT_INPUT_TABLE = "test";

    public static final String ALL_INPUT_TABLES = "bill_docum_2,link_history,phone_history,register_journal,suo_opcat_day,suo_opcat_hist,suo_subbranch_hist,user_links";
//    public static final String ALL_INPUT_TABLES = "test";

    public static final String DATA_LENGTH_KEY = "data.length";

    public static final String DEFAULT_DATA_LENGTH_VALUE = "10000";

    public static final String OUTPUT_PATH_KEY = "output.path";

    public static final String DEFAULT_OUTPUT_PATH_VALUE = "out";

    public static final String PER_PARTITION_SIZE_KEY = "per.partition";

    public static final String DEFAULT_PER_PARTITION_SIZE = "10000";

    private Properties() {

    }

}
