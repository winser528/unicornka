package com.fit.config.qr;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;

import javax.imageio.ImageIO;

import com.google.zxing.common.BitMatrix;

/**
 * @AUTO
 * @Author AIM
 * @DATE 2024/7/23
 */
public class MatrixToImgWriter {

    public static final int BLACK = 0xFF000000;
    public static final int WHITE = 0xFFFFFFFF;

    private final int onColor;
    private final int offColor;

    public MatrixToImgWriter() {
        this(BLACK, WHITE);
    }

    public MatrixToImgWriter(int onColor, int offColor) {
        this.onColor = onColor;
        this.offColor = offColor;
    }

    public int getPixelOnColor() {
        return onColor;
    }

    public int getPixelOffColor() {
        return offColor;
    }

    private static int getBufferedImageColorModel(int onColor, int offColor) {
        return onColor == BLACK && offColor == WHITE ? BufferedImage.TYPE_BYTE_BINARY : BufferedImage.TYPE_INT_RGB;
    }

    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        return toBufferedImage(matrix, BLACK, WHITE);
    }

    public static BufferedImage toBufferedImage(BitMatrix matrix, int onColor, int offColor) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, getBufferedImageColorModel(onColor, offColor));
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? onColor : offColor);
            }
        }
        return image;
    }

    public void writeToFile(BitMatrix matrix, String format, File file) throws IOException {
        writeToPath(matrix, format, file.toPath(), onColor, offColor);
    }

    public void writeToPath(BitMatrix matrix, String format, Path file, int onColor, int offColor) throws IOException {
        BufferedImage image = toBufferedImage(matrix, onColor, offColor);
        if (!ImageIO.write(image, format, file.toFile())) {
            throw new IOException("Could not write an image of format " + format + " to " + file);
        }
    }

    public static void writeToStream(BitMatrix matrix, String format, OutputStream stream) throws IOException {
        writeToStream(matrix, format, stream, BLACK, WHITE);
    }

    public static void writeToStream(BitMatrix matrix, String format, OutputStream stream, int onColor, int offColor) throws IOException {
        BufferedImage image = toBufferedImage(matrix, onColor, offColor);
        if (!ImageIO.write(image, format, stream)) {
            throw new IOException("Could not write an image of format " + format);
        }
    }
}

