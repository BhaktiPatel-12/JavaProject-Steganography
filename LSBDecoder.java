import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;
import javax.imageio.ImageIO;

public class LSBDecoder {

    // Decode msg from img
    public static String decode(String inputImagePath) throws Exception {
        BufferedImage image = ImageIO.read(new File(inputImagePath));

        int width = image.getWidth();
        int height = image.getHeight();

        int messageLength = 0;
        int bitIndex = 0;
        for (int i = 0; i < width && bitIndex < 32; i++) {
            for (int j = 0; j < height && bitIndex < 32; j++) {
                int pixel = image.getRGB(i, j);
                int red = (pixel >> 16) & 1;
                messageLength = (messageLength << 1) | red;
                bitIndex++;
            }
        }

        if (messageLength <= 0 || messageLength > (width * height * 3) / 8) {
            throw new IllegalArgumentException("Invalid message length detected.");
        }

        byte[] messageBytes = new byte[messageLength];

        int index = 0;
        for (int i = 0; i < width && index < messageLength * 8; i++) {
            for (int j = 0; j < height && index < messageLength * 8; j++) {
                if ((i * height + j) >= 32) { 
                    int pixel = image.getRGB(i, j);
                    int red = (pixel >> 16) & 1;
                    messageBytes[index / 8] <<= 1;
                    messageBytes[index / 8] |= red;
                    index++;
                }
            }
        }

        return new String(messageBytes);
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the path of the BMP image to decode: ");
        
String inputImagePath = scanner.nextLine();

try {
    String decodedMessage = decode(inputImagePath);
    System.out.println("Decoded Message: " + decodedMessage);
    
} catch (IllegalArgumentException e) {
    System.err.println("Error: " + e.getMessage());
    
} finally {
    scanner.close();
    
}
}
}
