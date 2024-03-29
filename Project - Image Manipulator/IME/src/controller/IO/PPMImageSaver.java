package controller.IO;

import model.Image.ImageState;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PPMImageSaver implements ImageSaverInterface{

  /**
   * Saves the image to a file.
   * @param image the image to be saved.
   *              NOTE : ImageState because we don't want to anyone to modify it here.
   * @param path the path to the file.
   * @throws IOException if the file cannot be written to.
   */
  public void save(ImageState image, String path) throws IllegalArgumentException {
    StringBuilder sb = new StringBuilder();

    // 1. add the header and the dimensions of the image.
    sb.append("P3\n");
    sb.append(image.getWidth() + " " + image.getHeight() + "\n");
    sb.append(image.getMaxValue() + "\n");

    // 2. add the pixels.
    for (int i = 0; i < image.getHeight(); i++) {
      for (int j = 0; j < image.getWidth(); j++) {
        sb.append(image.getPixel(i, j).getRed() + " ");
        sb.append(image.getPixel(i, j).getGreen() + " ");
        sb.append(image.getPixel(i, j).getBlue() + " ");
      }
      sb.append("\n");
    }

    // 3. write to the file.
    File file = new File(path);
    try {
      if (file.exists()) {
        throw new IOException("File " + path + " already exists!");
      }
      file.createNewFile();
      FileWriter writer = new FileWriter(file);
      writer.write(sb.toString());
      writer.close();
    }
    catch (IOException e) {
      throw new IllegalArgumentException("File " + path + " cannot be written to!");
    }

  }
}
