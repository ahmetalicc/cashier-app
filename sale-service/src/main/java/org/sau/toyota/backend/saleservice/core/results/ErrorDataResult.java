package org.sau.toyota.backend.saleservice.core.results;

/** @author Ahmet Alıç
 * @since 14-06-2024
 *
 * Represents an error data result, extending from DataResult, with a success status of false and an optional error message.
 * This class is typically used to indicate an error condition along with data.
 * @param <T> The type of data held by this result.
 */
public class ErrorDataResult<T> extends DataResult<T> {

    /**
     * Constructs a new ErrorDataResult with the specified data and error message.
     * @param data The data to be wrapped by this result.
     * @param message The error message providing additional information about the error.
     */
    public ErrorDataResult(T data, String message) {
        super(data, false ,message);
    }

    /**
     * Constructs a new ErrorDataResult with the specified data and a default error message.
     * @param data The data to be wrapped by this result.
     */
    public ErrorDataResult(T data) {
        super(data,false);
    }

    /**
     * Constructs a new ErrorDataResult with a default error message.
     * @param message The error message providing additional information about the error.
     */
    public ErrorDataResult(String message) {
        super(null, false ,message);
    }

    public ErrorDataResult() {
        super(null, false);
    }

}
