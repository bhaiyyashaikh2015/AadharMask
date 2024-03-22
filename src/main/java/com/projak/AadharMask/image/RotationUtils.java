package com.projak.AadharMask.image;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class RotationUtils {

    public static BufferedImage rotateImage(BufferedImage originalImage, double angle) {
        AffineTransform tx = new AffineTransform();
        tx.rotate(Math.toRadians(angle), originalImage.getWidth() / 2.0, originalImage.getHeight() / 2.0);

        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        return op.filter(originalImage, null);
    }
}
