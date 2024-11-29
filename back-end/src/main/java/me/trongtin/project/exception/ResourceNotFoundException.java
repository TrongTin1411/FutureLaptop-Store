package me.trongtin.project.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String id) {
        super(id);
    }

}
