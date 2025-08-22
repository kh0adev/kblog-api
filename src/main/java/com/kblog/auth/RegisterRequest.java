package com.kblog.auth;

public record RegisterRequest(String userName, String password, String firstName, String lastName, String email, String phoneNumber){

}