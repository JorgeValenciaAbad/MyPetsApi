package it.jorge.protectora.util;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Constants {

    private static Path imagesDirectory = Paths.get("src//main//resources//images");
    public static String route = imagesDirectory.toFile().getAbsolutePath();

}
