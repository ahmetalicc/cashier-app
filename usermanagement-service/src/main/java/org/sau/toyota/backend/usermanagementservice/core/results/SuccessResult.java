package org.sau.toyota.backend.usermanagementservice.core.results;
/** @author Ahmet Alıç
 * @since 14-06-2024
 *
 * Represents a success result with a success status of true and an optional success message.
 * This class is typically used to indicate a successful operation.
 */
public class SuccessResult extends Result{
    public SuccessResult() {
        super(true);
    }

    /**
     * Constructs a new SuccessResult with the specified success message.
     * @param message The success message providing additional information about the success.
     */
    public SuccessResult(String message) {
        super(true,message);
    }
}