/**
 * $Id: $
 * $Date: $
 *
 */

package org.xmlsh.util;


/*
 * Default implementation of a managed object
 * 
 */
public abstract class ManagedObject implements IManagedObject {

	private		int mRef = 1;

	protected void finalize()
	{
		close();
	}
	
	@Override
	public synchronized void addRef() {
		mRef++;

	}

	@Override
	public synchronized void release()  {
		if( --mRef <= 0 )
			close();

	}
	
	 abstract protected void close();
}



//
//
//Copyright (C) 2008,2009,2010,2011,2012 David A. Lee.
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
