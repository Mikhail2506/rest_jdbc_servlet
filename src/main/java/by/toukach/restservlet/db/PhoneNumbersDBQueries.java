package by.toukach.restservlet.db;

public class PhoneNumbersDBQueries {
    public static final String SAVE_SQL = """
            INSERT INTO public.phone_numbers (phone, person_id)
            VALUES (?, ?);
            """;

    public static final String UPDATE_SQL = """
            UPDATE public.phone_numbers
            SET phone = ?,
               person_id = ?
            WHERE id = ?;
            """;

    public static final String DELETE_SQL = """
            DELETE FROM public.phone_numbers
            WHERE id = ?;
            """;

    public static final String FIND_BY_ID_SQL = """
            SELECT id, phone, person_id FROM public.phone_numbers
            WHERE id = ?
            LIMIT 1;
            """;

    public static final String FIND_BY_NUMBER_SQL = """
            SELECT id, phone, person_id FROM public.phone_numbers
            WHERE phone = ?
            LIMIT 1;
            """;

    public static final String EXIST_BY_NUMBER_SQL = """
            SELECT exists (
                SELECT 1
                    FROM public.phone_numbers
                        WHERE phone = LOWER(?)
                        LIMIT 1
            );
            """;

    public static final String FIND_ALL_BY_USERID_SQL = """
            SELECT DISTINCT id, phone, person_id FROM public.phone_numbers
            WHERE person_id = ?;
            """;

    public static final String DELETE_ALL_BY_USERID_SQL = """
            DELETE FROM public.phone_numbers
            WHERE person_id = ?;
            """;

    public static final String FIND_ALL_SQL = """
            SELECT id, phone, person_id FROM public.phone_numbers;
            """;

    public static final String EXIST_BY_ID_SQL = """
                SELECT exists (
                SELECT 1
                    FROM public.phone_numbers
                        WHERE id = ?
                        LIMIT 1);
            """;
}
