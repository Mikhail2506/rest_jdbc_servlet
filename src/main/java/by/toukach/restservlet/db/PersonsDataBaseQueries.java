package by.toukach.restservlet.db;

public class PersonsDataBaseQueries {

    public static final String SAVE_PERSON_SQL = """
            INSERT INTO public.persons (name, surname, age)
                 VALUES (?, ? ,?);
            """;

    public static final String SAVE_PERSON_PHONE_NUMBER_SQL = """
            INSERT INTO phone_numbers (phone, person_id)
            VALUES (?, ?);
            """;

    public static final String SAVE_PERSON_TO_SECTION_SQL = """
            INSERT INTO person_section (person_id, section_id)
            VALUES (?, ?);
            """;

    public static final String FIND_SECTION_BY_NAME_SQL = """
            SELECT id FROM public.sections
            WHERE section_name = ?
            LIMIT 1;
            """;

    public static final String UPDATE_PERSON_SQL = """
            UPDATE public.persons
                SET name = ?,
                surname = ?,
                age =?
                WHERE id = ?;
            """;


    public static final String DELETE_PERSON_BY_ID_SQL = """
            DELETE
                FROM public.persons
                WHERE id = ?;
            """;

    public static final String DELETE_PERSON_FROM_SECTIONS_BY_PERSON_ID_SQL = """
            DELETE
                FROM person_section WHERE person_id = ?;
            """;

    public static final String DELETE_PHONE_NUMBERS_BY_PERSON_ID_SQL = """
            DELETE
                FROM phone_numbers WHERE person_id = ?;
            """;

    public static final String FIND_PERSON_BY_ID_SQL = """
            SELECT
                p.id, p.name, p.surname, p.age,
                pn.phone, pn.id AS phone_id, s.id AS section_id,
                s.section_name AS section_name FROM persons AS p
                LEFT JOIN phone_numbers AS pn ON p.id = pn.person_id
                LEFT JOIN person_section AS ps ON p.id = ps.person_id
                LEFT JOIN sections AS s ON ps.section_id = s.id
                WHERE p.id = ?
            """;

    public static final String FIND_ALL_SQL = """
            SELECT
                p.id, p.name, p.surname, p.age,
                ph.id AS phone_id, ph.phone, s.id AS section_id,
                s.section_name FROM persons AS p
                LEFT JOIN phone_numbers AS ph ON p.id = ph.person_id
                LEFT JOIN person_section AS ps ON p.id = ps.person_id
                LEFT JOIN sections AS s ON ps.section_id = s.id;
            """;

    public static final String EXIST_BY_ID_SQL = """
            SELECT exists (
                SELECT 1
                FROM persons
                WHERE id = ?
                LIMIT 1);
            """;
}
