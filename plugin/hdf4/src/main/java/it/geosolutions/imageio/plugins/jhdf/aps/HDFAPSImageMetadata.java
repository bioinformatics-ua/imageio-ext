/*
 *    ImageI/O-Ext - OpenSource Java Image translation Library
 *    http://www.geo-solutions.it/
 *    https://imageio-ext.dev.java.net/
 *    (C) 2007 - 2009, GeoSolutions
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    either version 3 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */
package it.geosolutions.imageio.plugins.jhdf.aps;

import it.geosolutions.imageio.core.CoreCommonImageMetadata;
import it.geosolutions.imageio.ndplugin.BaseImageMetadata;
import it.geosolutions.imageio.ndplugin.BaseImageReader;
import it.geosolutions.imageio.plugins.jhdf.BaseHDF4ImageReader;
import it.geosolutions.imageio.plugins.netcdf.NetCDFUtilities.KeyValuePair;

import java.io.IOException;
import java.util.HashMap;

import javax.imageio.metadata.IIOMetadataNode;

import org.w3c.dom.Node;
/**
 * APS specific image metadata.
 * 
 * @author Daniele Romagnoli, GeoSolutions SAS
 * @author Simone Giannecchini, GeoSolutions SAS
 *
 */
public class HDFAPSImageMetadata extends BaseImageMetadata {
	
    public static final String nativeMetadataFormatName = "it_geosolutions_imageio_plugins_jhdf_aps_APSImageMetadata_1.0";

    private final static String driverName = "HDF4";

    private final static String driverDescription = "Hierarchical Data Format Release 4";
    
    private HashMap<String, String> additionalMetadata;
    
    private IIOMetadataNode nativeTree;

    public HDFAPSImageMetadata(final BaseImageReader reader,final int imageIndex) {
        super(reader, imageIndex);
    }

    protected void setMembers(BaseImageReader imageReader) throws IOException {
        super.setMembers(imageReader);
        final int imageIndex = getImageIndex();
        if (imageReader instanceof HDFAPSImageReader) {
            HDFAPSImageReader reader = (HDFAPSImageReader) imageReader;
            setDriverDescription(driverDescription);
            setDriverName(driverName);
            String scale = reader.getAttributeAsString(imageIndex,HDFAPSProperties.PDSA_SCALINGSLOPE);
            if (scale != null && scale.trim().length() > 0) {
                setScales(new Double[] { Double.parseDouble(scale) });
            }
            String offset = reader.getAttributeAsString(imageIndex,HDFAPSProperties.PDSA_SCALINGINTERCEPT);
            if (offset != null && offset.trim().length() > 0) {
                setOffsets(new Double[] { Double.parseDouble(offset) });
            }
            String noData = reader.getAttributeAsString(imageIndex,HDFAPSProperties.PDSA_INVALID);
            if (noData != null && noData.trim().length() > 0) {
                setNoDataValues(new Double[] { Double.parseDouble(noData) });
            }

            // TODO: Setting valid range as max min is ok?
            String validRange = reader.getAttributeAsString(imageIndex,HDFAPSProperties.PDSA_VALIDRANGE);
            
            // ValidRange not found. Try with BrowseRange. Is that ok?
            if (validRange == null || validRange.trim().length() < 1)
            	validRange = reader.getAttributeAsString(imageIndex, HDFAPSProperties.PDSA_BROWSERANGES);
            if (validRange != null && validRange.trim().length() > 0) {
                String values[] = validRange.split(" ");
                if (values.length == 2) {
                	if (!values[0].equalsIgnoreCase(values[1])){
		                setMinimums(new Double[] { Double.parseDouble(values[0]) });
		                setMaximums(new Double[] { Double.parseDouble(values[1]) });
                	}
                }
            }
            setDatasetName(reader.getDatasetName(imageIndex));
            
            // overviews is always 0, we can just do decimation on reading
            setNumOverviews(new int[] { 0 });
            
            final HDFAPSImageReader directReader = (HDFAPSImageReader) imageReader;
            final int numAttributes = directReader.getNumAttributes(imageIndex);
            this.additionalMetadata = new HashMap<String, String>(numAttributes);
            for (int i = 0; i < numAttributes; i++) {
            	final KeyValuePair attributePair = directReader.getAttribute(imageIndex, i);
                final String attributeName = attributePair.getKey();
                final String attributeValue = attributePair.getValue();
                additionalMetadata.put(attributeName, attributeValue);
            }
        }
    }
    
    /**
     * Returns an XML DOM <code>Node</code> object that represents the root of
     * a tree of common stream metadata contained within this object according
     * to the conventions defined by a given metadata format name.
     * 
     * @param formatName
     *                the name of the requested metadata format.
     */
    public Node getAsTree(String formatName) {
        if (HDFAPSImageMetadata.nativeMetadataFormatName.equalsIgnoreCase(formatName))
            return createNativeTree();
        else if (CoreCommonImageMetadata.nativeMetadataFormatName.equalsIgnoreCase(formatName))
            return super.createCommonNativeTree();
        throw new IllegalArgumentException(formatName+ " is not a supported format name");
    }
    
    private synchronized Node createNativeTree() {
        if (this.nativeTree != null)
            return this.nativeTree;
        nativeTree = new IIOMetadataNode(HDFAPSImageMetadata.nativeMetadataFormatName);

        // ////////////////////////////////////////////////////////////////////
        //
        // DatasetDescriptor
        //
        // ////////////////////////////////////////////////////////////////////
        if (this.additionalMetadata != null) {
            IIOMetadataNode node = new IIOMetadataNode(ATTRIBUTES_NODE);
            for (String key : this.additionalMetadata.keySet()) {
                final String attributeValue = additionalMetadata.get(key);
                node.setAttribute(key, attributeValue);
            }
            nativeTree.appendChild(node);
        }
        return nativeTree;
    }

}