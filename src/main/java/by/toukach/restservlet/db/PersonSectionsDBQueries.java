package by.toukach.restservlet.db;

public class PersonSectionsDBQueries {
    public static final String SAVE_SQL = """
            INSERT INTO public.sections (name)
            VALUES (?);
            """;
    public static final String UPDATE_SQL = """
            UPDATE public.sections
            SET name = ?
            WHERE id = ?;
            """;
    public static final String DELETE_SQL = """
            DELETE FROM public.sections
            WHERE id = ?;
            """;
    public static final String FIND_BY_ID_SQL = """
            SELECT id, name FROM public.sections
            WHERE id = ?
            LIMIT 1;
            """;
    public static final String FIND_ALL_SQL = """
            SELECT id, name FROM public.sections;
            """;
    public static final String EXIST_BY_ID_SQL = """
                SELECT exists (
                SELECT 1
                    FROM public.sections
                        WHERE id = ?
                        LIMIT 1);
            """;
}
