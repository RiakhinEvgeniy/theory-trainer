package com.evgeniy.riakhin.backend.util;

public class NameException {
    public static final String USERS_NOT_FOUND_IN_DB = "User not found. In data base isn't users registered.";
    public static final String USER_NOT_FOUND_BY_ID = "User not found by id: ";
    public static final String USER_NOT_FOUND_BY_NAME = "User not found by name: ";
    public static final String USER_NOT_FOUND_FOR_UPDATING = "User not found for updating by id: ";
    public static final String USER_NOT_FOUND_FOR_DELETING = "User not found for deleting by id: ";
    public static final String USER_MAPPING_FAILED = "User mapping failed";
    public static final String FAILED_TO_MAP_SAVED_USER_TO_DTO = "Failed to map saved user to DTO";
    public static final String USER_CREATE_DTO = "UserCreateDTO is NULL!";
    public static final String USER_MODIFIED_BY_ANOTHER_THREAD = "User modified by another thread: ";
    public static final String QUESTION_NOT_FOUND_BY_ID = "Question not found by id: ";
    private NameException() {}
}
