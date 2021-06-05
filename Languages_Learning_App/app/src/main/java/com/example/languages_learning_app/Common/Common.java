package com.example.languages_learning_app.Common;

import com.example.languages_learning_app.DTO.Language;
import com.example.languages_learning_app.DTO.User;

public class Common {
    public static User user;
    public static Language language;
    public static String role = "Admin";

    public static String RoleAdmin = "Admin";
    public static String RoleManager = "Manager";
    public static String RoleTrainee = "Trainee";

    // Set for practice type
    public static int PRACTICE_TYPE_EASY = 0;
    public static int PRACTICE_TYPE_HARD = 1;

    public static String defaltPass = "12345678";

    public enum mode{
        create,
        read,
        update,
        delete
    }
}
