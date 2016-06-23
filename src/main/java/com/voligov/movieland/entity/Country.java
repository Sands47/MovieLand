package com.voligov.movieland.entity;

public class Country {
    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Country)) {
            return false;
        }
        Country country = (Country) obj;
        if (name == null) {
            return country.getId().equals(id);
        }
        return country.getId().equals(id) && country.getName().equals(name);
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
