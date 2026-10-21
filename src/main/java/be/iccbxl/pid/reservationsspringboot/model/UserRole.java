package be.iccbxl.pid.reservationsspringboot.model;

public enum UserRole {
    ADMIN("Administrateur"),
    MEMBER("Membre"),
    AFFILIATE("Affilié"),
    PRESS("Critique de presse"),
    PRODUCER("Producteur");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getValue() {
        return role;
    }
}