package at.jku.seproject.gpsrace.web;

import java.awt.image.BufferedImage;
//import java.awt.image.Bitmap;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;

public class QRCode {
	
	 public static void writeQRCode() { 
			QRCodeWriter writer = new QRCodeWriter();
			int width = 256, height = 256;
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); // create an empty image
			int white = 255 << 16 | 255 << 8 | 255;
			int black = 0;
			try {
				BitMatrix bitMatrix = writer.encode("http://www.codepool.biz/zxing-write-read-qrcode.html", BarcodeFormat.QR_CODE, width, height);
		        for (int i = 0; i < width; i++) {
		            for (int j = 0; j < height; j++) {
		            	image.setRGB(i, j, bitMatrix.get(i, j) ? black : white); // set pixel one by one
		            }
		        }

		        try {
					ImageIO.write(image, "jpg", new File("dynamsoftbarcode.jpg")); // save QR image to disk
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} catch (WriterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	 
	 public static String readQRCode(String fileName) { //Reader
			File file = new File(fileName);
			BufferedImage image = null;
			BinaryBitmap bitmap = null;
			Result result = null;

			try {
				image = ImageIO.read(file);
				int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
				RGBLuminanceSource source = new RGBLuminanceSource(image.getWidth(), image.getHeight(), pixels);
				bitmap = new BinaryBitmap(new HybridBinarizer(source));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (bitmap == null)
				return null;

			QRCodeReader reader = new QRCodeReader();	
			try {
				result = reader.decode(bitmap);
				return result.getText();
			} catch (NotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ChecksumException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}
}


