package com.nicat.asgarzada.emailutil.core.entity;

/**
 * Saves info about recipients with types
 * @author nasgarzada
 * @version 1.0.0
 */
public class RecipientEntity {
    private boolean alias;
    private String address;

    public RecipientEntity() {
    }

    public RecipientEntity(boolean alias, String address) {
        this.alias = alias;
        this.address = address;
    }

    public boolean isAlias() {
        return alias;
    }

    public void setAlias(boolean alias) {
        this.alias = alias;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "RecipientDto{" +
                "alias=" + alias +
                ", address='" + address + '\'' +
                '}';
    }
}
