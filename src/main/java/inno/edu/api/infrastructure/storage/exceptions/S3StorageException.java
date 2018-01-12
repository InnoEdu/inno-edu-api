package inno.edu.api.infrastructure.storage.exceptions;

public class S3StorageException extends RuntimeException {
    public S3StorageException(Throwable e) {
        super(e);
    }
}
