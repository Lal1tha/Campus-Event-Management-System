package com.campus.util;

public class PasswordHashTool {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java com.campus.util.PasswordHashTool <password>");
            return;
        }
        System.out.println(BCryptUtil.hash(args[0]));
    }
}
