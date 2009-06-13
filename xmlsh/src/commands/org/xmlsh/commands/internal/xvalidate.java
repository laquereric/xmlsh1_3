/**
 * $Id: $
 * $Date: $
 *
 */

package org.xmlsh.commands.internal;

import java.util.List;

import org.xmlsh.core.InputPort;
import org.xmlsh.core.Options;
import org.xmlsh.core.XCommand;
import org.xmlsh.core.XValue;
import org.xmlsh.sh.shell.SerializeOpts;
import org.xmlsh.util.DTDValidator;
import org.xmlsh.util.RNGValidator;
import org.xmlsh.util.XSDValidator;


public class xvalidate extends XCommand {

	
	@Override
	public int run( List<XValue> args )
	throws Exception 
	{

		
		Options opts = new Options( "xsd:,dtd:,rng:" , args );
		opts.parse();
		
		
		
		String schema = null;
		String dtd = null;
		String rng = null;
		if( opts.hasOpt("dtd"))
			dtd = opts.getOptStringRequired("dtd");
		else
		if( opts.hasOpt("rng"))
			rng = opts.getOptStringRequired("rng");
		else
			schema = opts.getOptStringRequired("xsd");
		
		SerializeOpts sopts = getSerializeOpts();
		args= opts.getRemainingArgs();
		InputPort in = null;
		if( args.size() > 0 )
			in = getInput(args.get(0));
		else
			in = getStdin();
		
		if( schema != null ){
			XSDValidator v = new XSDValidator( getEnv().getShell().getURI(schema).toString() );
			v.validate( in.asInputStream(sopts));
		} else 
		if( dtd != null )
		{
			DTDValidator v = new DTDValidator( getEnv().getShell().getURI(dtd).toURL() );
			v.validate( in.getSystemId() , in.asInputStream(sopts));
		}
		else
		if( rng != null )
		{
			RNGValidator v = new RNGValidator( getEnv().getShell().getURI(rng).toURL() );
			v.validate( in.asInputStream(sopts));
		}
		in.close();
		
		return 0;

	}
	
	


	


	

}

//
//
//Copyright (C) 2008,2009 , David A. Lee.
//
//The contents of this file are subject to the "Simplified BSD License" (the "License");
//you may not use this file except in compliance with the License. You may obtain a copy of the
//License at http://www.opensource.org/licenses/bsd-license.php 
//
//Software distributed under the License is distributed on an "AS IS" basis,
//WITHOUT WARRANTY OF ANY KIND, either express or implied.
//See the License for the specific language governing rights and limitations under the License.
//
//The Original Code is: all this file.
//
//The Initial Developer of the Original Code is David A. Lee
//
//Portions created by (your name) are Copyright (C) (your legal entity). All Rights Reserved.
//
//Contributor(s): none.
//