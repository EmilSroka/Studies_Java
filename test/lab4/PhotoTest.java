package lab4;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class PhotoTest {

    @Test
    void writeHTML_returnStringWithSingleImgTag() {
        Photo photo = new Photo("./img.jpg");
        assertTrue(Pattern.matches("^<img [^<>]*/>[^<>]*", photo.writeHTML()));
    }

    @Test
    void writeHTML_returnStringEndsWithNewLine() {
        Photo photo = new Photo("./img.jpg");
        assertTrue(Pattern.matches(".*[\\r\\n]+$", photo.writeHTML()));
    }

    @Test
    void writeHTML_returnStringContainAltAttribute() {
        Photo photo = new Photo("./img.jpg");
        assertTrue(Pattern.matches("(?s).*alt=\"[\\w ]*\".*", photo.writeHTML()));
    }

    @Test
    void writeHTML_returnStringContainNotEmptySrcAttribute() {
        Photo photo = new Photo("./img.jpg");
        assertTrue(Pattern.matches("(?s).*src=\"[\\w/\\.]+\"(?s).*", photo.writeHTML()));
    }

}