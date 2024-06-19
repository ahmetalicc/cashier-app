package org.sau.toyota.backend.core.results;

/** @author Ahmet Alıç
 * @since 14-06-2024
 *
 * Represents a success data result, extending from DataResult, with a success status of true and an optional success message.
 * This class is typically used to indicate a successful operation along with data.
 * @param <T> The type of data held by this result.
 */
public class SuccessDataResult<T> extends DataResult<T> {

    /**
     * Constructs a new SuccessDataResult with the specified data and success message.
     * @param data The data to be wrapped by this result.
     * @param message The success message providing additional information about the success.
     */
    public SuccessDataResult(T data, String message) {
        super(data, true ,message);
    }
    /**
     * Constructs a new SuccessDataResult with the specified data and a default success message.
     * @param data The data to be wrapped by this result.
     */
    public SuccessDataResult(T data) {
        super(data,true);
    }
    /**
     * Constructs a new SuccessDataResult with a default success message.
     * @param message The success message providing additional information about the success.
     */
    public SuccessDataResult(String message) {
        super(null, true ,message);
    }

    public SuccessDataResult() {
        super(null, true);
    }

}