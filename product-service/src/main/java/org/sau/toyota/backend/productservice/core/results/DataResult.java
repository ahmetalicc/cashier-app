package org.sau.toyota.backend.productservice.core.results;

import lombok.Getter;

/** @author Ahmet Alıç
 * @since 14-06-2024
 *
 * Represents a data result containing a generic type of data along with success status and an optional message.
 * This class is typically used to wrap data along with a success flag and an optional message.
 * @param <T> The type of data held by this result.
 */
@Getter
public class DataResult<T> extends Result {


    private T data;

    /**
     * Constructs a new DataResult with the specified data, success status, and message.
     * @param data The data to be wrapped by this result.
     * @param success The success status indicating whether the operation was successful.
     * @param message An optional message providing additional information about the result.
     */
    public DataResult(T data, boolean success, String message) {
        super(success, message);
        this.data = data;
    }

    /**
     * Constructs a new DataResult with the specified data and success status.
     * @param data The data to be wrapped by this result.
     * @param success The success status indicating whether the operation was successful.
     */
    public DataResult(T data, boolean success) {
        super(success);
        this.data = data;
    }


}



