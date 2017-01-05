package net.java.swingfx.imagecropper.demo;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.java.swingfx.imagecropper.ImageCropper;

/**
 * craig wickesser May 5, 2008 @ 9:00:07 PM
 */
public class Main {
	public static void main(String[] args) {

		try {
			JFrame f = new JFrame();
			f.setSize(600, 800);
			
			InputStream is = Main.class.getResourceAsStream("flower.jpg");
			BufferedImage bi = ImageIO.read(is);
			ImageCropper c = new ImageCropper(bi);
			c.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					ImageCropper c2 = (ImageCropper) e.getSource();
					System.out.println(c2.getCroppedImage().getWidth() + " * "
							+ c2.getCroppedImage().getHeight());
				}
			});
			f.add(c);
			f.setVisible(true);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
