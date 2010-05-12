/**
 * $Id: $
 * $Date: $
 *
 */

package org.xmlsh.marklogic.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;

import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.trans.XPathException;
import org.xmlsh.core.InvalidArgumentException;
import org.xmlsh.core.Options;
import org.xmlsh.core.OutputPort;
import org.xmlsh.core.XCommand;
import org.xmlsh.core.XValue;
import org.xmlsh.core.Options.OptionValue;
import org.xmlsh.sh.shell.SerializeOpts;
import org.xmlsh.util.Util;

import com.marklogic.xcc.ContentSource;
import com.marklogic.xcc.ContentSourceFactory;
import com.marklogic.xcc.ResultItem;
import com.marklogic.xcc.ResultSequence;
import com.marklogic.xcc.ValueFactory;
import com.marklogic.xcc.exceptions.XccConfigException;
import com.marklogic.xcc.types.ItemType;
import com.marklogic.xcc.types.XName;
import com.marklogic.xcc.types.XdmAttribute;
import com.marklogic.xcc.types.XdmItem;
import com.marklogic.xcc.types.XdmVariable;

public abstract class MLCommand extends XCommand {

	public MLCommand() {
		super();
	}

	protected ContentSource getConnection(Options opts) throws URISyntaxException,
			XccConfigException, InvalidArgumentException {
		XValue vc = null;
		String connect;
		OptionValue ov = opts.getOpt("c");
		if (ov != null)
			vc = ov.getValue();
		else
			vc = getEnv().getVarValue("MLCONNECT");
		if (vc == null) {
			throw new InvalidArgumentException("No connection");
		}
		connect = vc.toString();

		URI serverUri = new URI(connect);
		ContentSource cs = ContentSourceFactory.newContentSource(serverUri);
		return cs;
	}

	protected void writeResult(ResultSequence rs, OutputPort out, SerializeOpts sopts,
			boolean asText) throws FactoryConfigurationError, IOException,
			InvalidArgumentException, XMLStreamException {

		XMLInputFactory factory = XMLInputFactory.newInstance();

		while (rs.hasNext()) {
			ResultItem rsItem = rs.next();

			ItemType type = rsItem.getItemType();
			XdmItem it = rsItem.getItem();

			// NOTE: The following test doesnt work for attributes, known XCC
			// bug as of 2010-03-01

			if (asText || type.isAtomic() || (type.isNode() && it instanceof XdmAttribute)) {
				Writer os = out.asPrintWriter(sopts);

				rsItem.writeTo(os);
				os.close();
			} else {
				Reader isItem = rsItem.asReader();

				XMLEventWriter writer = out.asXMLEventWriter(sopts);

				XMLEventReader xmlItem = factory.createXMLEventReader(isItem);

				writer.add(xmlItem);
				writer.close();
				xmlItem.close();
			}

			if (!asText)
				out.writeSequenceSeperator(sopts);
		}
	}

	protected static String quote(String s) {

		return "'" + s.replace("'", "''").replace("&","&amp;").replace("<", "&lt;") + "'";
	}

	protected XdmVariable newVariable(String name, XValue value,SerializeOpts opts) throws XPathException, InvalidArgumentException, SaxonApiException {
		XName xname = new XName(name);

		com.marklogic.xcc.types.XdmValue xvalue = newValue(value, opts);

		
		XdmVariable var = ValueFactory.newVariable(xname, xvalue);
		return var;
	}

	/*
	 * Create a marklogic XdmValue from an XValue
	 * 
	 * Truncates sequences to 1 element because ML doesnt support sequences
	 * 
	 */
	protected com.marklogic.xcc.types.XdmValue newValue(XValue value,SerializeOpts opts) throws XPathException, InvalidArgumentException, SaxonApiException 
	{
		
		net.sf.saxon.s9api.XdmItem item = value.asXdmItem();
		if( item.isAtomicValue()){
			net.sf.saxon.value.AtomicValue atom = (net.sf.saxon.value.AtomicValue) item.getUnderlyingValue();
			// int type = atom.getItemType(null).getPrimitiveType();
			
			if( atom instanceof net.sf.saxon.value.Base64BinaryValue  ){
				byte[] v = ((net.sf.saxon.value.Base64BinaryValue)atom).getBinaryValue();
				return ValueFactory.newBinaryNode(v);
				
			} else
			if( atom instanceof net.sf.saxon.value.BooleanValue  ){
				boolean v = ((net.sf.saxon.value.BooleanValue)atom).effectiveBooleanValue();
				return ValueFactory.newXSBoolean(v);
			}
			else
			if( atom instanceof net.sf.saxon.value.DateTimeValue  ){
				String v = ((net.sf.saxon.value.DateTimeValue)atom).toString();
				
				return ValueFactory.newXSDateTime(v, null, null);
			}
			else
				if( atom instanceof net.sf.saxon.value.GDateValue  ){
					String v = ((net.sf.saxon.value.GDateValue)atom).toString();
					return  ValueFactory.newXSDate(v, null,null) ;
				}
				else
					if( atom instanceof net.sf.saxon.value.TimeValue  ){
						String v = (( net.sf.saxon.value.TimeValue)atom).toString();
						return  ValueFactory.newXSTime(v, null,null) ;
					}
					else
			if( atom instanceof net.sf.saxon.value.DurationValue  ){
				
				String v = (( net.sf.saxon.value.DurationValue)atom).toString();
				return  ValueFactory.newXSDuration(v) ;
			}
			else
			if( atom instanceof net.sf.saxon.value.HexBinaryValue  ){
				byte b[] = (( net.sf.saxon.value.HexBinaryValue )atom).getBinaryValue() ;
				return ValueFactory.newBinaryNode(b);
				
			}
			else
				if( atom instanceof net.sf.saxon.value.IntegerValue  ){
					
			
					BigInteger v = ((net.sf.saxon.value.IntegerValue)atom).asBigInteger();

					return ValueFactory.newXSInteger(v); 
					
						
				}
			
			else
			if( atom instanceof net.sf.saxon.value.NumericValue  ){
				long v = ((net.sf.saxon.value.IntegerValue)atom).longValue();

				return ValueFactory.newXSInteger(v); 
					
			}
			else
			{
				return ValueFactory.newXSString( atom.getStringValue() );
						
			}
			
			
	
			
			
		}
		
		// Node node = NodeOverNodeInfo.wrap(value.asXdmNode().getUnderlyingNode());
		ByteArrayOutputStream buf = new ByteArrayOutputStream();

		Util.writeXdmValue(value.asXdmNode(), Util.streamToDestination(buf, opts));

		InputStream is = new ByteArrayInputStream(buf.toByteArray());
		
		return ValueFactory.newElement(is);

	}

}

//
//
// Copyright (C) 2008,2009,2010 , David A. Lee.
//
// The contents of this file are subject to the "Simplified BSD License" (the
// "License");
// you may not use this file except in compliance with the License. You may
// obtain a copy of the
// License at http://www.opensource.org/licenses/bsd-license.php
//
// Software distributed under the License is distributed on an "AS IS" basis,
// WITHOUT WARRANTY OF ANY KIND, either express or implied.
// See the License for the specific language governing rights and limitations
// under the License.
//
// The Original Code is: all this file.
//
// The Initial Developer of the Original Code is David A. Lee
//
// Portions created by (your name) are Copyright (C) (your legal entity). All
// Rights Reserved.
//
// Contributor(s): none.
//
