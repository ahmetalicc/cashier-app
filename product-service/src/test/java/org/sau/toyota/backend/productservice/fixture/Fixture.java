package org.sau.toyota.backend.productservice.fixture;

import com.github.javafaker.Faker;

public abstract class Fixture<T> {
    protected Faker faker;
    protected Fixture(){
        this.faker = new Faker();
    }
    protected Faker getFaker(){
        return faker;
    }
}
