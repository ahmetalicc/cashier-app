package org.sau.toyota.backend.reportservice.core.results;

/** @author Ahmet Alıç
 * @since 14-06-2024
 *
 * Represents an error result with a success status of false and an optional error message.
 * This class is typically used to indicate an error condition.
 */
public class ErrorResult extends Result{
    public ErrorResult() {
        super(false);
    }

    /**
     * Constructs a new ErrorResult with the specified error message.
     * @param message The error message providing additional information about the error.
     */
    public ErrorResult(String message) {
        super(false,message);
    }
}