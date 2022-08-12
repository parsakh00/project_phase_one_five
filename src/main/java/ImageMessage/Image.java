package ImageMessage;

import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

public class Image {
    public static String encode(String path) {
        if (path != null) {
            File image = new File(path.split("file:/")[1]);
            if (image.exists()) {
                try {
                    byte[] bytes = Files.readAllBytes(image.toPath());
                    return Base64.getEncoder().encodeToString(bytes);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static javafx.scene.image.Image decode(String encoded) {
        if (encoded != null) {
            ByteArrayInputStream inStreambj = new ByteArrayInputStream(Base64.getDecoder().decode(encoded));
            BufferedImage newImage = null;
            try {
                newImage = ImageIO.read(inStreambj);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return convertToFxImage(newImage);
        }
        return null;
    }

    private static javafx.scene.image.Image convertToFxImage(BufferedImage image) {
        WritableImage wr = null;
        if (image != null) {
            wr = new WritableImage(image.getWidth(), image.getHeight());
            PixelWriter pw = wr.getPixelWriter();
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    pw.setArgb(x, y, image.getRGB(x, y));
                }
            }
        }

        return new ImageView(wr).getImage();
    }
}
