package by.toukach.restservlet.db;

public class PhoneNumbersDBQueries {
    public static final String SAVE_SQL = """
            INSERT INTO phone_numbers (phonenumber_number, user_id)
            VALUES (?, ?);
            """;
    public static final String UPDATE_SQL = """
            UPDATE phone_numbers
            SET phonenumber_number = ?,
                user_id = ?
            WHERE phonenumber_id = ?;
            """;
    public static final String DELETE_SQL = """
            DELETE FROM phone_numbers
            WHERE phonenumber_id = ?;
            """;
    private static final String FIND_BY_ID_SQL = """
            SELECT phonenumber_id, phonenumber_number, user_id FROM phone_numbers
            WHERE phonenumber_id = ?
            LIMIT 1;
            """;
    public static final String FIND_BY_NUMBER_SQL = """
            SELECT phonenumber_id, phonenumber_number, user_id FROM phone_numbers
            WHERE phonenumber_number = ?
            LIMIT 1;
            """;
    public static final String EXIST_BY_NUMBER_SQL = """
            SELECT exists (
                SELECT 1
                    FROM phone_numbers
                        WHERE phonenumber_number = LOWER(?)
                        LIMIT 1
            );
            """;
    private static final String FIND_ALL_BY_USERID_SQL = """
            SELECT phonenumber_id, phonenumber_number, user_id FROM phone_numbers
            WHERE user_id = ?;
            """;
    private static final String DELETE_ALL_BY_USERID_SQL = """
            DELETE FROM phone_numbers
            WHERE person_id = ?;
            """;
    public static final String FIND_ALL_SQL = """
            SELECT id, phone, person_id FROM phone_numbers;
            """;
    public static final String EXIST_BY_ID_SQL = """
                SELECT exists (
                SELECT 1
                    FROM phone_numbers
                        WHERE id = ?
                        LIMIT 1);
            """;
}
