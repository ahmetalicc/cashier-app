package org.sau.toyota.backend.saleservice.core.results;


import lombok.Getter;

/** author Ahmet Alıç
 * @since 14-06-2024
 *
 * Represents a generic result with a success status and an optional message.
 * This class is typically used as a base class for more specific result types.
 */
@Getter
public class Result {
    private boolean success;
    private String message;

    /**
     * Constructs a new Result with the specified success status.
     * @param success The success status indicating whether the operation was successful.
     */
    public Result(boolean success) {
        this.success = success;
    }

    /**
     * Constructs a new Result with the specified success status and message.
     * @param success The success status indicating whether the operation was successful.
     * @param message The message providing additional information about the result.
     */
    public Result(boolean success,String message) {
        this(success);
        this.message = message;
    }

}
