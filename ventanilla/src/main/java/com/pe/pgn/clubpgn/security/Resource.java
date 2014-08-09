package com.pe.pgn.clubpgn.security;

public class Resource {
    private String url;
    private String role;

    public Resource(String url, String role) {
            this.url = url;
            this.role = role;
    }

    public String getUrl() {
            return url;
    }

    public String getRole() {
            return role;
    }
}