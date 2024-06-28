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



//    public static final String FIND_BY_ID_SQL = """
//            SELECT id, name, surname, age FROM public.persons
//            WHERE id = ?
//            LIMIT 1;
//            """;
    public static final String FIND_BY_ID_SQL = """
        SELECT
            p.id,
            p.name,
            p.surname,
            p.age,
            pn.phone,
        	s.section_name AS section_name
        	FROM persons AS p
        	LEFT JOIN phone_numbers AS pn ON p.id = pn.person_id
        	LEFT JOIN person_section AS ps ON p.id = ps.person_id
        	LEFT JOIN sections AS s ON ps.persons_sections_id = s.id
        WHERE p.id = ?
        LIMIT 1;
            """;



    public static final String FIND_ALL_SQL = """
            SELECT
            p.id, p.name,
            p.surname, p.age,
            ph.id AS phone_id,
            ph.phone,
            s.id AS section_id,
            s.section_name
            FROM persons p
            LEFT JOIN phone_numbers ph ON p.id = ph.person_id
            LEFT JOIN person_section ps ON p.id = ps.person_id
            LEFT JOIN sections s ON ps.section_id = s.id;
                """;


    public static final String EXIST_BY_ID_SQL = """
                SELECT exists (
                SELECT 1
                    FROM persons
                        WHERE id = ?
                        LIMIT 1);
            """;
}
