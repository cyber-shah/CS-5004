package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * This class represents an ImageLoader.
 * It takes in the file path of a PPM file and returns an Image object.
 */
public class ImageLoader {

  /**
   * Loads a PPM file and returns an Image object.
   *
   * @param filename String value of the file path.
   * @return Image object.
   *
   * @throws FileNotFoundException if the file is not found.
   * @throws NoSuchElementException if the file is not a valid PPM file.
   * @throws IllegalArgumentException if the file is not a 'P3' PPM file.
   */
  public Image loadPPM(String filename) throws FileNotFoundException {
    Scanner scanner;
    try {
      scanner = new Scanner(new FileInputStream(filename));
    }
    catch (FileNotFoundException e) {
      throw new FileNotFoundException("File " + filename + " not found!");
    }

    // 1. get the image parameters, and check if they are valid

    int[] imageParameters = readImageParameters(scanner);
    int width; int height; int maxValue;
    width = imageParameters[0];
    height = imageParameters[1];
    maxValue = imageParameters[2];

    // 2. if valid parameters, create the image
    return new Image(width, height, maxValue);
  }

  /**
   * Reads the image parameters from the PPM file.
   *
   * @param scanner Scanner object.
   * @return int[] array of the image parameters.
   *
   * @throws NoSuchElementException if the file does not have enough lines to read.
   * @throws IllegalArgumentException if the file is not a 'P3' PPM file.
   * @throws IllegalArgumentException if the file does not have valid ints as parameters.
   */
  private int[] readImageParameters(Scanner scanner) {
    int[] params = new int[3];
    String line;

    try {
      // Read the first two lines
      for (int lineCount = 0; lineCount < 3; lineCount++) {
        line = scanner.nextLine();
        // Ignore comments
        if (line.charAt(0) == '#') {
          lineCount--;
          continue;
        }

        // Get the type of the image (line 1)
        if (lineCount == 0) {
          if (!line.equals("P3")) {
            throw new IllegalArgumentException("Invalid PPM file: plain RAW file should begin with P3");
          }
        }
        // Get the width and height of the image (line 2)
        else if (lineCount == 1) {
          String[] dimensions = line.split(" ");
          params[0] = Integer.parseInt(dimensions[0]);
          params[1] = Integer.parseInt(dimensions[1]);
        }
        // Get the max value (line 3)
        else if (lineCount == 2) {
          params[2] = Integer.parseInt(line);
        }
      }
      return params;
    }
    catch (NoSuchElementException e) {
      throw new NoSuchElementException("Invalid PPM file: not enough lines to read");
    }
  }

  /*    Image image = null;
    Scanner scanner;

    // 0. Try to load the file
    try {
      scanner = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      System.out.println("File " + filename + " not found!");
      return null;
    }

    String line;
    int width;
    int height;
    int maxValue;
    int lineCount = 0;


    // 1. try to create the image object --------------
    // with the FIRST TWO LINES -----------------------
    while (lineCount <= 2) {
      line = scanner.nextLine();
      // ignore comments
      if (line.charAt(0) != '#') {
        continue;
      }
      // 1.1 get the type of the image
      if (lineCount == 0) {
        if (line != "P3") {
          throw new IllegalArgumentException("Invalid PPM file: plain RAW file should begin with P3");
          return null;
        }
        lineCount++;
      }
      // 1.2 Get the width and height of the image
      else if (lineCount == 1) {
        String[] dimensions = line.split(" ");
        width = Integer.parseInt(dimensions[0]);
        height = Integer.parseInt(dimensions[1]);
        lineCount++;
      }
      // 1.3 get the max value
      else if (lineCount == 2) {
        maxValue = Integer.parseInt(line);
        lineCount++;
      }
    }

    try {
      image = new Image(width, height, maxValue);
    } catch (NullPointerException e) {
      e.getStackTrace();
      return null;
    }

    return image;*/

  /*




    // 1. while the file has lines
    while (scanner.hasNextLine()) {

      line = scanner.nextLine();
      // 2. if the line is not a comment
      if (line.charAt(0) != '#') {
        continue;
      }

      // 3. Get the type of the image
      if (lineCount == 0) {
        String type = line;
      }

      // 4. Get the width and height of the image
      //    create an image object
      else if (lineCount == 1) {
        String[] dimensions = line.split(" ");
        width = Integer.parseInt(dimensions[0]);
        height = Integer.parseInt(dimensions[1]);
      }

      // 4. get the max value
      else if (lineCount == 2) {
        maxValue = Integer.parseInt(line);
        try {
          image = new Image(width, height, maxValue);
        }
        catch (NullPointerException e) {
          e.getStackTrace();
        }
      }

      // 5. once that is done
      else {
        try {
          // 5.1 try to extract the pixel values
          //      and assign them to the image
          for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
              int red = Integer.parseInt(line);
              int green = Integer.parseInt(String.valueOf(scanner.nextInt()));
              int blue = Integer.parseInt(String.valueOf(scanner.nextInt()));
              Pixel pixel = new Pixel(red, green, blue, maxValue);
              image.setPixel(j, i, pixel);
            }
          }
        }
        catch (NoSuchElementException e) {
          e.getStackTrace();
          // end of file
          break;
        }
        catch (IllegalArgumentException i) {
          i.getStackTrace();
          break;
        }
      }
      // 6. increment the line count
      lineCount++;
    }
    return image;
  }
*/
}
