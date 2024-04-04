import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class trysth {
    public static void main(String[] args) {
        // Create a BufferedImage to act as an off-screen canvas
        int width = 400;
        int height = 300;
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // Get graphics context from the BufferedImage
        Graphics2D graphics = bufferedImage.createGraphics();

        // Draw something on the BufferedImage
        graphics.setColor(Color.RED);
        graphics.fillRect(50, 50, 200, 100); // Example drawing operation

        // Dispose the graphics context to release resources
        graphics.dispose();

        // Save the BufferedImage as a PNG file
        saveImageToPNG(bufferedImage, "output.png");
    }

    public static void saveImageToPNG(BufferedImage image, String filePath) {
        try {
            File output = new File(filePath);
            ImageIO.write(image, "png", output);
            System.out.println("Image saved as PNG: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
