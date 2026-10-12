package com.campus.util;

public class PasswordGenerator {

	public static void main(String[] args) {
		String password = "Password1";

        System.out.println(BCryptUtil.hash(password));

	}

}
