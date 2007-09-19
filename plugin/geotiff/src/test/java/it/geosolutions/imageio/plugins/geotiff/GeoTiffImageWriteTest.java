/*
 *    JImageIO-extension - OpenSource Java Image translation Library
 *    http://www.geo-solutions.it/
 *	  https://imageio-ext.dev.java.net/
 *    (C) 2007, GeoSolutions
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    version 2.1 of the License.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */
package it.geosolutions.imageio.plugins.geotiff;

import it.geosolutions.imageio.gdalframework.Viewer;
import it.geosolutions.resources.TestData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.media.jai.JAI;
import javax.media.jai.ParameterBlockJAI;
import javax.media.jai.RenderedOp;

import junit.framework.Test;
import junit.framework.TestSuite;

public class GeoTiffImageWriteTest extends AbstractGeoTiffTestCase {

	public GeoTiffImageWriteTest(String name) {
		super(name);
	}

	/**
	 * Test Writing capabilities.
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
//	public void testWrite() throws IOException, FileNotFoundException {
//
//		final File outputFile = new File("d:/work/data/geotiff/writing.tif");
//
//		final File inputFile = new File("D:/work/data/geotiff/bogota.tif");
//
//		// ////////////////////////////////////////////////////////////////
//		// preparing to read
//		// ////////////////////////////////////////////////////////////////
//		final ParameterBlockJAI pbjImageRead;
//		final ImageReadParam irp = new ImageReadParam();
//
//		Integer xSubSampling = new Integer(2);
//		Integer ySubSampling = new Integer(2);
//		Integer xSubSamplingOffset = new Integer(0);
//		Integer ySubSamplingOffset = new Integer(0);
//
//		irp.setSourceSubsampling(xSubSampling.intValue(), ySubSampling
//				.intValue(), xSubSamplingOffset.intValue(), ySubSamplingOffset
//				.intValue());
//
//		pbjImageRead = new ParameterBlockJAI("ImageRead");
//		pbjImageRead.setParameter("Input", inputFile);
//		pbjImageRead.setParameter("readParam", irp);
//		RenderedOp image = JAI.create("ImageRead", pbjImageRead);
//
//		// ////////////////////////////////////////////////////////////////
//		// preparing to crop
//		// ////////////////////////////////////////////////////////////////
//		final ParameterBlockJAI pbjCrop = new ParameterBlockJAI("Crop");
//		pbjCrop.addSource(image);
//
//		Float xCrop = new Float(1000);
//		Float yCrop = new Float(1000);
//		Float cropWidth = new Float(2000);
//		Float cropHeigth = new Float(2000);
//
//		pbjCrop.setParameter("x", xCrop);
//		pbjCrop.setParameter("y", yCrop);
//		pbjCrop.setParameter("width", cropWidth);
//		pbjCrop.setParameter("height", cropHeigth);
//
//		final RenderedOp croppedImage = JAI.create("Crop", pbjCrop);
//
//		// ////////////////////////////////////////////////////////////////
//		// preparing to translate
//		// ////////////////////////////////////////////////////////////////
//		final ParameterBlockJAI pbjTranslate = new ParameterBlockJAI(
//				"Translate");
//		pbjTranslate.addSource(croppedImage);
//
//		Float xTrans = new Float(-1000);
//		Float yTrans = new Float(-1000);
//
//		pbjTranslate.setParameter("xTrans", xTrans);
//		pbjTranslate.setParameter("yTrans", yTrans);
//
//		final RenderedOp translatedImage = JAI
//				.create("Translate", pbjTranslate);
//
//		// ////////////////////////////////////////////////////////////////
//		// preparing to rotate
//		// ////////////////////////////////////////////////////////////////
//		final ParameterBlockJAI pbjRotate = new ParameterBlockJAI("Rotate");
//		pbjRotate.addSource(translatedImage);
//
//		Float xOrigin = new Float(cropWidth.floatValue() / 2);
//		Float yOrigin = new Float(cropHeigth.floatValue() / 2);
//		Float angle = new Float(java.lang.Math.PI / 2);
//
//		pbjRotate.setParameter("xOrigin", xOrigin);
//		pbjRotate.setParameter("yOrigin", yOrigin);
//		pbjRotate.setParameter("angle", angle);
//
//		final RenderedOp rotatedImage = JAI.create("Rotate", pbjRotate);
//
//		// ////////////////////////////////////////////////////////////////
//		// preparing to write
//		// ////////////////////////////////////////////////////////////////
//		final ParameterBlockJAI pbjImageWrite = new ParameterBlockJAI(
//				"ImageWrite");
//		pbjImageWrite.setParameter("Output", outputFile);
//		pbjImageWrite.addSource(rotatedImage);
//		final RenderedOp op = JAI.create("ImageWrite", pbjImageWrite);
//
//		// ////////////////////////////////////////////////////////////////
//		// preparing to read again
//		// ////////////////////////////////////////////////////////////////
//		final ParameterBlockJAI pbjImageReRead = new ParameterBlockJAI(
//				"ImageRead");
//		pbjImageReRead.setParameter("Input", outputFile);
//		final RenderedOp image2 = JAI.create("ImageRead", pbjImageReRead);
//
//		StringBuffer title = new StringBuffer("SUBSAMP:").append("X[").append(
//				xSubSampling.toString()).append("]-Y[").append(
//				ySubSampling.toString()).append("]-Xof[").append(
//				xSubSamplingOffset.toString()).append("]-Yof[").append(
//				ySubSamplingOffset).append("]  CROP:X[").append(
//				xCrop.toString()).append("]-Y[").append(yCrop.toString())
//				.append("]-W[").append(cropWidth.toString()).append("]-H[")
//				.append(cropHeigth.toString()).append("]  TRANS:X[").append(
//						xTrans.toString()).append("]-Y[").append(
//						yTrans.toString()).append("]  ROTATE:xOrig[").append(
//						xOrigin.toString()).append("]-yOrig[").append(
//						yOrigin.toString()).append("]-ang[").append(
//						angle.toString()).append("]");
//
//		Viewer.visualize(image2, title.toString());
//	}

	public void testTrivialWrite() throws IOException, FileNotFoundException {

		final File outputFile = TestData.temp(this, "writetest.tif",false);
		outputFile.deleteOnExit();
		final File inputFile = TestData.file(this, "bogota.tif");

		final ParameterBlockJAI pbjImageRead = new ParameterBlockJAI(
				"ImageRead");
		pbjImageRead.setParameter("Input", inputFile);
		RenderedOp image = JAI.create("ImageRead", pbjImageRead);
		Viewer.visualize(image);
		
		// ////////////////////////////////////////////////////////////////
		// preparing to write
		// ////////////////////////////////////////////////////////////////
		final ParameterBlockJAI pbjImageWrite = new ParameterBlockJAI(
				"ImageWrite");
		pbjImageWrite.setParameter("Output", outputFile);
		pbjImageWrite.addSource(image);
		final RenderedOp op = JAI.create("ImageWrite", pbjImageWrite);

		// ////////////////////////////////////////////////////////////////
		// preparing to read again
		// ////////////////////////////////////////////////////////////////
		final ParameterBlockJAI pbjImageReRead = new ParameterBlockJAI(
				"ImageRead");
		pbjImageReRead.setParameter("Input", outputFile);
		final RenderedOp image2 = JAI.create("ImageRead", pbjImageReRead);
		Viewer.visualize(image2);
	}

	public static Test suite() {
		TestSuite suite = new TestSuite();

		suite.addTest(new GeoTiffImageWriteTest("testTrivialWrite"));

//		suite.addTest(new GeoTiffImageWriteTest("testWrite"));

		return suite;
	}

	public static void main(java.lang.String[] args) {
		junit.textui.TestRunner.run(suite());
	}

}