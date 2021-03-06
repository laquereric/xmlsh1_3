/**
 * $Id$
 * $Date$
 *
 */

package org.xmlsh.core;


import java.io.File;
import java.util.List;

import org.xmlsh.sh.shell.Module;
import org.xmlsh.sh.shell.Shell;
import org.xmlsh.xpath.ShellContext;

public abstract class XCommand extends AbstractCommand {
	
	private Module mModule;
	
	
	protected void throwInvalidArg(String string)
			throws InvalidArgumentException {
				printErr(string);
				throw new InvalidArgumentException( string );
				
	}

	public Module getModule()
	{
		return mModule;
	}

	public String getName()
	{
		String name = getClass().getSimpleName();
		if (name.lastIndexOf('.') > 0) {
		    name = name.substring(name.lastIndexOf('.')+1);  
		}
		
		
		/*
		 * Convert from camelCase to hyphen-case
		 */
		
		
		return fromCamelCase(name);
	}
	
	

	private String fromCamelCase(String name) {
		StringBuffer result = new StringBuffer( name.length() + 5 );
		
		for( char c : name.toCharArray() ){
			if( Character.isUpperCase(c)){
				result.append('-');
				result.append( Character.toLowerCase(c));
			} else
				result.append(c);
				
			
			
		}
		
		return result.toString();
		
		
		
	}

	/* (non-Javadoc)
	 * @see org.xmlsh.core.ICommand#run(org.xmlsh.sh.shell.Shell, java.lang.String[])
	 */
	public int run(Shell shell, String cmd, List<XValue> args) throws Exception 
	{
		Shell saved_shell = ShellContext.set(shell);

		
		try {
			return run(  args , shell.getEnv() );
		} 
		catch( UnknownOption e )
		{
			usage( e.getMessage() );
			return -1;
		}
		
		
		
		finally{
			ShellContext.set(saved_shell);
		}
		
	}
	
	abstract public int run( List<XValue>  args ) throws Exception;

	
   public int run( List<XValue>  args, XEnvironment env ) throws Exception
   {
	   mEnvironment = env ;
	   mShell = env.getShell();
	   return run(args);
	   
   }
	
	/* (non-Javadoc)
	 * @see org.xmlsh.core.ICommand#getType()
	 */
	public CommandType getType() {
		return CommandType.CMD_TYPE_INTERNAL ;
	}
	
	public File getFile() {
		return null ; // no associated file 
		
	}

	public void setModule(Module module) {
		mModule = module ;
		
	}



	
	
}
//
//
//Copyright (C) 2008-2014    David A. Lee.
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
