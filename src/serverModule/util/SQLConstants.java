package serverModule.util;

public class SQLConstants {

    public static final String LABWORK_TABLE = "lab_work";
    public static final String USER_TABLE = "users";
    public static final String COORDINATES_TABLE = "coordinates";
    public static final String DISCIPLINE_TABLE = "discipline";

    public static final String LABWORK_TABLE_ID_COLUMN = "id";
    public static final String LABWORK_TABLE_NAME_COLUMN = "name";
    public static final String LABWORK_TABLE_COORDINATES_ID_COLUMN = "coordinates_id";
    public static final String LABWORK_TABLE_CREATION_DATE_COLUMN = "creation_date";
    public static final String LABWORK_TABLE_STUDENTS_MINIMAL_POINT_COLUMN = "minimal_point";
    public static final String LABWORK_TABLE_AVERAGE_POINT_COLUMN = "average_point";
    public static final String LABWORK_TABLE_DIFFICULTY_COLUMN = "difficulty";
    public static final String LABWORK_TABLE_DISCIPLINE_ID_COLUMN = "discipline_id";
    public static final String LABWORK_TABLE_USER_ID_COLUMN = "user_id";

    public static final String USER_TABLE_ID_COLUMN = "id";
    public static final String USER_TABLE_USERNAME_COLUMN = "username";
    public static final String USER_TABLE_PASSWORD_COLUMN = "password";

    public static final String COORDINATES_TABLE_ID_COLUMN = "id";
    public static final String COORDINATES_TABLE_X_COLUMN = "x";
    public static final String COORDINATES_TABLE_Y_COLUMN = "y";

    public static final String DISCIPLINE_ID_COLUMN = "id";
    public static final String DISCIPLINE_TABLE_NAME_COLUMN = "name";
    public static final String DISCIPLINE_LECTURE_HOURS_COLUMN = "lecture_hours";
    public static final String DISCIPLINE_PRACTICE_HOURS_COLUMN = "practice_hours";
    public static final String DISCIPLINE_LABS_COUNT_COLUMN = "labs_count";

    public static final String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS " + USER_TABLE + " ("
            + USER_TABLE_ID_COLUMN + " bigserial primary key,"
            + USER_TABLE_USERNAME_COLUMN + " text not null,"
            + USER_TABLE_PASSWORD_COLUMN + " text not null"
            + ");";

    public static final String CREATE_LABWORK_TABLE = "CREATE TABLE IF NOT EXISTS " + LABWORK_TABLE + " ("
            + LABWORK_TABLE_ID_COLUMN + " bigserial primary key,"
            + LABWORK_TABLE_NAME_COLUMN + " text not null,"
            + LABWORK_TABLE_COORDINATES_ID_COLUMN + " bigint references " + COORDINATES_TABLE + ", "
            + LABWORK_TABLE_CREATION_DATE_COLUMN + " timestamp not null,"
            + LABWORK_TABLE_STUDENTS_MINIMAL_POINT_COLUMN + " float,"
            + LABWORK_TABLE_AVERAGE_POINT_COLUMN + " bigint,"
            + LABWORK_TABLE_DIFFICULTY_COLUMN + " text not null,"
            + LABWORK_TABLE_DISCIPLINE_ID_COLUMN + " bigint references " + DISCIPLINE_TABLE + ", "
            + LABWORK_TABLE_USER_ID_COLUMN + " bigint references " + USER_TABLE
            + ");";

    public static final String CREATE_COORDINATES_TABLE = "CREATE TABLE IF NOT EXISTS " + COORDINATES_TABLE + " ("
            + COORDINATES_TABLE_ID_COLUMN + " bigserial primary key,"
            + COORDINATES_TABLE_X_COLUMN + " bigint not null,"
            + COORDINATES_TABLE_Y_COLUMN + " integer not null"
            + ");";

    public static final String CREATE_DISCIPLINE_TABLE = "CREATE TABLE IF NOT EXISTS " + DISCIPLINE_TABLE + " ("
            + DISCIPLINE_ID_COLUMN + " bigserial primary key,"
            + DISCIPLINE_TABLE_NAME_COLUMN + " text not null,"
            + DISCIPLINE_LECTURE_HOURS_COLUMN + " bigint not null,"
            + DISCIPLINE_PRACTICE_HOURS_COLUMN + " integer not null,"
            + DISCIPLINE_LABS_COUNT_COLUMN + " bigint not null"
            + ");";

    public static final String LABWORK_NAME = "LABWORK_NAME";
    public static final String LABWORK_ID = "LABWORK_ID";

    public static final String DISCIPLINE_ID = "DISCIPLINE_ID";
    public static final String DISCIPLINE_NAME = "DISCIPLINE_NAME";

    public static final String COORDINATES_ID = "COORDINATES_ID";


    public static final String SELECT_ALL_USERS = "SELECT * FROM " + USER_TABLE;
    public static final String SELECT_ALL_COORDINATES = "SELECT * FROM " + COORDINATES_TABLE;
    public static final String SELECT_ALL_DISCIPLINES = "SELECT * FROM " + DISCIPLINE_TABLE;


    public static final String SELECT_ALL_LABWORKS_WITH_DISCIPLINES_AND_COORDINATES =
            "SELECT lab_work.id as " + LABWORK_ID + ",\n" +
                    "       lab_work.name as " + LABWORK_NAME + ",\n" +
                    "       creation_date,\n" +
                    "       minimal_point,\n" +
                    "       average_point,\n" +
                    "       difficulty,\n" +
                    "       discipline.id as " + DISCIPLINE_ID + ",\n" +
                    "       discipline.name as " + DISCIPLINE_NAME + " ,\n" +
                    "       lecture_hours,\n" +
                    "       practice_hours,\n" +
                    "       labs_count,\n" +
                    "       coordinates.id as " + COORDINATES_ID + ",\n" +
                    "       x,\n" +
                    "       y\n" +
                    "FROM lab_work JOIN discipline ON lab_work.discipline_id = discipline.id JOIN coordinates ON coordinates.id = lab_work.coordinates_id";
    public static final String INSERT_LABWORK = "INSERT INTO " +
            LABWORK_TABLE + " (" +
            LABWORK_TABLE_NAME_COLUMN + ", " +
            LABWORK_TABLE_COORDINATES_ID_COLUMN + ", " +
            LABWORK_TABLE_CREATION_DATE_COLUMN + ", " +
            LABWORK_TABLE_STUDENTS_MINIMAL_POINT_COLUMN + ", " +
            LABWORK_TABLE_AVERAGE_POINT_COLUMN + ", " +
            LABWORK_TABLE_DIFFICULTY_COLUMN + ", " +
            LABWORK_TABLE_DISCIPLINE_ID_COLUMN + ", " +
            LABWORK_TABLE_USER_ID_COLUMN + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String INSERT_COORDINATES = "INSERT INTO " +
            COORDINATES_TABLE + " (" +
            COORDINATES_TABLE_X_COLUMN + ", " +
            COORDINATES_TABLE_Y_COLUMN + ") VALUES (?, ?)";
    public static final String INSERT_DISCIPLINE = "INSERT INTO " +
            DISCIPLINE_TABLE + " (" +
            DISCIPLINE_TABLE_NAME_COLUMN + ", " +
            DISCIPLINE_LECTURE_HOURS_COLUMN + ", " +
            DISCIPLINE_PRACTICE_HOURS_COLUMN + ", " +
            DISCIPLINE_LABS_COUNT_COLUMN + ") VALUES (?, ?, ?, ?)";


    public static final String DELETE_LABWORK_BY_ID = "DELETE FROM " + LABWORK_TABLE +
            " WHERE " + LABWORK_TABLE_ID_COLUMN + " = ?";

    public static final String SELECT_USER_BY_ID = "SELECT * FROM " + USER_TABLE +
            " WHERE " + USER_TABLE_ID_COLUMN + " = ?";
    public static final String SELECT_USER_BY_USERNAME = "SELECT * FROM " + USER_TABLE +
            " WHERE " + USER_TABLE_USERNAME_COLUMN + " = ?";
    public static final String SELECT_USER_BY_USERNAME_AND_PASSWORD = SELECT_USER_BY_USERNAME + " AND " +
            USER_TABLE_PASSWORD_COLUMN + " = ?";

    public static final String SELECT_LAST_COORDINATES_ID = "SELECT " + COORDINATES_TABLE_ID_COLUMN + " FROM "
            + COORDINATES_TABLE + " ORDER BY " + COORDINATES_TABLE_ID_COLUMN + " DESC LIMIT 1";
    public static final String SELECT_LAST_DISCIPLINE_ID = "SELECT " + COORDINATES_TABLE_ID_COLUMN + " FROM "
            + COORDINATES_TABLE + " ORDER BY " + COORDINATES_TABLE_ID_COLUMN + " DESC LIMIT 1";


    public static final String SELECT_COORDINATES_BY_X_AND_Y = "SELECT * FROM " + COORDINATES_TABLE +
            " WHERE " + COORDINATES_TABLE_X_COLUMN + " = ?" + " AND " +
            COORDINATES_TABLE_Y_COLUMN + " = ?";

    public static final String SELECT_DISCIPLINE_BY_NAME = "SELECT * FROM " + DISCIPLINE_TABLE +
            " WHERE " + DISCIPLINE_TABLE_NAME_COLUMN + " = ?";

    public static final String DELETE_LABWORK_BY_ID_AND_USER_ID = "DELETE FROM " + LABWORK_TABLE +
            " WHERE " + LABWORK_TABLE_ID_COLUMN + " = ?" + " AND " +
            LABWORK_TABLE_USER_ID_COLUMN + " = ?";

}
