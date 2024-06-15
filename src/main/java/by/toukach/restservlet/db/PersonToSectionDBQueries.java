package by.toukach.restservlet.db;

public class PersonToSectionDBQueries {
    public static final String SAVE_SQL = """
            INSERT INTO person_section (person_id, section_id)
            VALUES (?, ?);
            """;
    public static final String UPDATE_SQL = """
            UPDATE person_section
            SET person_id = ?,
               section_id = ?
            WHERE persons_sections_id = ?;
            """;
    public static final String DELETE_SQL = """
            DELETE FROM person_section
            WHERE person_section_id = ? ;
            """;
    private static final String FIND_BY_ID_SQL = """
            SELECT person_section_id, person_id, section_id FROM person_section
            WHERE person_section_id = ?
            LIMIT 1;
            """;
    private static final String FIND_ALL_SQL = """
            SELECT person_section_id, person_id, section_id FROM person_section;
            """;
    public static final String FIND_ALL_BY_USERID_SQL = """
            SELECT person_section_id, person_id, section_id FROM person_section
            WHERE person_id = ?;
            """;
    public static final String FIND_ALL_BY_DEPARTMENT_ID_SQL = """
            SELECT person_section_id, person_id, section_id FROM person_section
            WHERE section_id = ?;
            """;
    public static final String FIND_BY_USERID_AND_DEPARTMENT_ID_SQL = """
            SELECT person_section_id, person_id, section_id FROM person_section
            WHERE person_id = ? AND section_id = ?
            LIMIT 1;
            """;
    public static final String DELETE_BY_USERID_SQL = """
            DELETE FROM person_section
            WHERE person_id = ?;
            """;
    public static final String DELETE_BY_DEPARTMENT_ID_SQL = """
            DELETE FROM person_section
            WHERE section_id = ?;
            """;
    private static final String EXIST_BY_ID_SQL = """
                SELECT exists (
                SELECT 1
                    FROM person_section
                        WHERE person_section_id = ?
                        LIMIT 1);
            """;
}
