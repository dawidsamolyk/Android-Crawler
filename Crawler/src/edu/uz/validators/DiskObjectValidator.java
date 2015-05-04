package edu.uz.validators;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class DiskObjectValidator {

    public static void checkDirectory(Path path) throws IOException, IllegalArgumentException {
	if (path == null) {
	    throw new IllegalArgumentException("Not specified directory path!");
	}
	File directory = path.toFile();

	if (directory.isDirectory()) {
	    throw new IOException("Specified disk object is not directory!");
	}
	if (directory.exists()) {
	    throw new IOException("Specified directory is not exists!");
	}
	if (directory.canRead()) {
	    throw new IOException("Specified directory cannot be read!");
	}
	if (directory.canWrite()) {
	    throw new IOException("Specified directory cannot be write!");
	}
    }

}
