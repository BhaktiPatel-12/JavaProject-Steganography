import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;
import javax.imageio.ImageIO;

public class LSBEncoder {

    // Encode msg in img
    public static void encode(String inputImagePath, String outputImagePath, String message) throws Exception {
        BufferedImage image = ImageIO.read(new File(inputImagePath));
        byte[] messageBytes = message.getBytes();
        int messageLength = messageBytes.length;

        int maxMessageLength = (image.getWidth() * image.getHeight() * 3) / 8;
        if (messageLength > maxMessageLength) {
            throw new IllegalArgumentException("Message is too long to be encoded in the provided image.");
        }

        int[] binaryMessageLength = new int[32];
        for (int i = 0; i < 32; i++) {
            binaryMessageLength[i] = (messageLength >> (31 - i)) & 1;
        }

        int[] binaryMessage = new int[messageLength * 8];
        for (int i = 0; i < messageLength; i++) {
            for (int j = 0; j < 8; j++) {
                binaryMessage[i * 8 + j] = (messageBytes[i] >> (7 - j)) & 1;
            }
        }

        int width = image.getWidth();
        int height = image.getHeight();
        int index = 0;

        for (int i = 0; i < width && index < 32; i++) {
            for (int j = 0; j < height && index < 32; j++) {
                int pixel = image.getRGB(i, j);
                int red = (pixel >> 16) & 0xFF;//performing &,masks all other color components(11111111)
                red = (red & 0xFE) | binaryMessageLength[index++];//changing the lest singnificant bit of red pixel
                pixel = (pixel & 0xFF00FFFF) | (red << 16);//reseting the pixel value
                image.setRGB(i, j, pixel);
            }
        }

        index = 0;
        for (int i = 0; i < width && index < binaryMessage.length; i++) {
            for (int j = 0; j < height && index < binaryMessage.length; j++) {
                if ((i * height + j) >= 32) { //as first 32 bits are for message length
                    int pixel = image.getRGB(i, j);
                    int red = (pixel >> 16) & 0xFF;
                    red = (red & 0xFE) | binaryMessage[index++];
                    pixel = (pixel & 0xFF00FFFF) | (red << 16);
                    image.setRGB(i, j, pixel);
                }
            }
        }

        ImageIO.write(image, "bmp", new File(outputImagePath));
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the path of the BMP image: ");
        String inputImagePath = scanner.nextLine();

        System.out.print("Enter the path where you want to save the encoded BMP image: ");
        String outputImagePath = scanner.nextLine();

        System.out.print("Enter the secret message you want to encode: ");
        String secretMessage = scanner.nextLine();

        try {
            encode(inputImagePath, outputImagePath, secretMessage);
            System.out.println("Encoding complete.");
            
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
            
        } finally {
            scanner.close();
            
        }
    }
}
