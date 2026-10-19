package com.warehouse.model;

import java.util.Objects;

public class Supplier {

    private String id;
    private String name;
    private String contactPerson;
    private String phone;
    private String email;
    private String address;

    public Supplier() {}

    public Supplier(String id, String name, String contactPerson,
                    String phone, String email, String address) {
        this.id = id;
        this.name = name;
        this.contactPerson = contactPerson;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getContactPerson() { return contactPerson; }
    public void setContactPerson(String contactPerson) { this.contactPerson = contactPerson; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Supplier)) return false;
        return Objects.equals(id, ((Supplier) o).id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return "Supplier{id='" + id + "', name='" + name + "', contact='" + contactPerson + "'}";
    }
}