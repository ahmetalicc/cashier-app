package org.sau.toyota.backend.authenticationservice.core.results;

public class ErrorResult extends Result{
    public ErrorResult() {
        super(false);
    }

    public ErrorResult(String message) {
        super(false,message);
    }
}