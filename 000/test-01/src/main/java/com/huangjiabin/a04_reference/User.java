package com.huangjiabin.a04_reference;

public class User {
    String id;
    String age;
    String name;

    public User(final String id, final String age, final String name) {
        this.id = id;
        this.age = age;
        this.name = name;
    }

    public String getId() {
        return this.id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getAge() {
        return this.age;
    }

    public void setAge(final String age) {
        this.age = age;
    }
}
