package com.warehouse.model;

import java.util.Objects;

public class Technician {

    private String id;
    private String name;
    private String specialty;
    private String phone;
    private int activeJobs;

    public Technician() {}

    public Technician(String id, String name, String specialty, String phone, int activeJobs) {
        this.id = id;
        this.name = name;
        this.specialty = specialty;
        this.phone = phone;
        this.activeJobs = activeJobs;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSpecialty() { return specialty; }
    public void setSpecialty(String specialty) { this.specialty = specialty; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public int getActiveJobs() { return activeJobs; }
    public void setActiveJobs(int activeJobs) { this.activeJobs = activeJobs; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Technician)) return false;
        return Objects.equals(id, ((Technician) o).id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return "Technician{id='" + id + "', name='" + name + "', specialty='" + specialty + "'}";
    }
}