package by.toukach.restservlet.db;

public class PersonsDataBaseQueries {

    public static final String SAVE_SQL = """
            INSERT INTO public.persons (name, surname, age)
            VALUES (?, ? ,?) ;
            """;
    public static final String UPDATE_SQL = """
            UPDATE public.persons
            SET name = ?,
                surname = ?,
                age =?
            WHERE id = ?  ;
            """;
    public static final String DELETE_SQL = """
            DELETE FROM public.persons
            WHERE id = ? ;
            """;
    public static final String FIND_BY_ID_SQL = """
            SELECT id, name, surname, age FROM public.persons
            WHERE id = ?
            LIMIT 1;
            """;
    public static final String FIND_ALL_SQL = """
            SELECT id, name, surname, age FROM public.persons;
            """;
    public static final String EXIST_BY_ID_SQL = """
                SELECT exists (
                SELECT 1
                    FROM persons
                        WHERE id = ?
                        LIMIT 1);
            """;
}