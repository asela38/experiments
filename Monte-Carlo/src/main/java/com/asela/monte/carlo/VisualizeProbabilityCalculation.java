package com.asela.monte.carlo;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

public class VisualizeProbabilityCalculation {

    private static final Logger LOG    = Logger.getGlobal();
    private static final String JPEG   = "jpg";
    private static final String FOLDER = VisualizeProbabilityCalculation.class.getSimpleName();

    public enum ImageType {
        RANDOM, PI, ARGB_SPECTRUM, RANDOM_WALK, RANDOM_WALK2, RANDOM_WALK3, ACKERMAN;
    }

    public static void main(String[] args) {
        int iterations = 20;

        for(int i = 0; i < iterations; i ++)
            drawImage(i);

    }

    private static void drawImage(int i) {
        int width = 200, height = 200;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        ImageType type = ImageType.ACKERMAN;
        switch (type) {
        case RANDOM:
            randomPixelImage(width, height, image);
            break;
        case PI:
            doRandomPixelsInAndOutQuadrent(width, image);
            break;
        case ARGB_SPECTRUM:
            drawARBSpectra(width, height, image);
            break;
        case RANDOM_WALK:
            randomWalk(width, height, i, 0, 0,  image);
            break;
        case RANDOM_WALK3:
            randomWalk(width, height, i, width/2, height/2,  image);
            break;
        case RANDOM_WALK2:
            randomWalk(width, height, i, 0, 0,  image);
            randomWalk(width, height, i, width, height,  image);
            break;
        case ACKERMAN:
            ackerman( ( i % 3) + 1 , (i / 3) + 1, image);
            break;
        }

        try {
            File file = File.createTempFile("/" + FOLDER + "_"+ i + "_" + ThreadLocalRandom.current().nextLong(1,100), "." + JPEG);
            ImageIO.write(image, JPEG, file);
            LOG.info("file written : " + file.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Integer ackerman(int m, int n, BufferedImage image)  {
            image.setRGB( Math.max(Math.min(m * 2, image.getWidth() -1), 0) ,
                    Math.max(Math.min(n * 2, image.getHeight() - 1), 0), argbPixelFromColor(Color.BLUE));
            if(m == 0) return n+1;
            if(m > 0 && n == 0 ) return ackerman(m-1, 1, image);
            return ackerman(m-1, ackerman(m, n-1, image), image);
    }

    private static void drawARBSpectra(int width, int height, BufferedImage image) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int p = createARGBPixel(255, 255, 255, 0); // pixel
                image.setRGB(x, y, 0x0000ff);
                LOG.fine(String.format("[ x = %d, y = %d, p= %d", x, y, p));

            }
        }

    }

    private static void randomWalk(int width, int height, int iteration, int startX, int startY, BufferedImage image) {

        int x = startX, y = startY;
        long limit = (long) Math.pow(2, iteration);
        int[] outcomes = new int[] {-1,0,1};
        
        for (long i = 1; i < limit; i++) {

            x += outcomes[ ThreadLocalRandom.current().nextInt(1, 4) - 1 ];
            y += outcomes[ ThreadLocalRandom.current().nextInt(1, 4) - 1 ];

            x = Math.min(Math.max(x, 0), width-1);
            y = Math.min(Math.max(y, 0), height-1);
          //  LOG.info(String.format("(%d,%d)", x,y));
            if(startX == 0) 
            image.setRGB(x, y, argbPixelFromColor(Color.BLUE));
            else
            image.setRGB(x, y, argbPixelFromColor(Color.GREEN));

        }

    }

    public static void doRandomPixelsInAndOutQuadrent(int length, BufferedImage image) {

        long limit = length * length * 100;
        long insideCount = 0;
        for (long i = 1; i < limit; i++) {
            int x = ThreadLocalRandom.current().nextInt(length);
            int y = ThreadLocalRandom.current().nextInt(length);

            double d = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
            if (d <= length) {
                insideCount++;
                image.setRGB(x, y, createARGBPixel(0xff, 0xed, 0x1b, 0x24));
            } else {
                image.setRGB(x, y, argbPixelFromColor(Color.BLUE));
            }
        }

        LOG.info("PI : " + (insideCount * 4 / limit));
    }

    public static void randomPixelImage(int width, int height, BufferedImage image) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int a = getRandom(); // alpha
                int r = getRandom(); // red
                int g = getRandom(); // green
                int b = getRandom(); // blue

                int p = createARGBPixel(a, r, g, b); // pixel

                image.setRGB(x, y, p);
                LOG.fine(String.format("[ x = %d, y = %d, p= %d", x, y, p));

            }
        }
    }

    private static int argbPixelFromColor(Color color) {
        int pixel = createARGBPixel(color.getAlpha(), color.getRed(), color.getGreen(), color.getBlue());
        //  LOG.info("Pixel Hex : " + Integer.toHexString(pixel));
        return pixel;
    }

    private static int createARGBPixel(int a, int r, int g, int b) {
        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    private static int getRandom() {
        return ThreadLocalRandom.current().nextInt(100, 256);
    }
}
