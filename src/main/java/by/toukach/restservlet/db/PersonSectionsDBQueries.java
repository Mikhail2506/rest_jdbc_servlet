package by.toukach.restservlet.db;

public class PersonSectionsDBQueries {
    public static final String SAVE_SQL = """
            INSERT INTO sections (name)
            VALUES (?);
            """;
    public static final String UPDATE_SQL = """
            UPDATE sections
            SET name = ?
            WHERE id = ?;
            """;
    public static final String DELETE_SQL = """
            DELETE FROM sections
            WHERE id = ?;
            """;
    public static final String FIND_BY_ID_SQL = """
            SELECT id, name FROM sections
            WHERE id = ?
            LIMIT 1;
            """;
    public static final String FIND_ALL_SQL = """
            SELECT id, name FROM sections;
            """;
    public static final String EXIST_BY_ID_SQL = """
                SELECT exists (
                SELECT 1
                    FROM sections
                        WHERE id = ?
                        LIMIT 1);
            """;
}
