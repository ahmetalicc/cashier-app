package org.sau.toyota.backend.reportservice.fixture;

import com.github.javafaker.Faker;
/** @author Ahmet Alıç
 * @since 15-06-2024
 *
 * Abstract base class for fixture classes used to generate fake data for testing purposes.
 * @param <T> The type of data this fixture generates.
 */
public abstract class Fixture<T> {
    protected Faker faker;
    protected Fixture(){
        this.faker = new Faker();
    }
    protected Faker getFaker(){
        return faker;
    }
}
