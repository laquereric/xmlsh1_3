/**
 * $Id: $
 * $Date: $
 *
 */

package org.xmlsh.sh.ui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.UIManager;

import org.xmlsh.core.Options;
import org.xmlsh.core.XValue;
import org.xmlsh.sh.shell.SerializeOpts;
import org.xmlsh.sh.shell.Shell;
import org.xmlsh.util.Util;

public class XShell {

	private JFrame mframe;
	private JTextArea mCommandTextArea;
	private ShellThread mShell = null;
	private JTextArea mResultTextArea;
	private File mCurdir;
	private LogFrame mLogWindow = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		run(args);
	}

	public static void run(final String[] args) {

		run(Util.toXValueList(args));
	}

	public static void run(List<XValue> args) {
		run(new File("."), args ,new SerializeOpts() );

	}

	public static void run(File curdir , List<XValue> args) {
		run(curdir, args ,new SerializeOpts() );

	}

	public static void run(final File curdir, final List<XValue> args , final SerializeOpts sopts   ) {
		
		 
		 
		 try {
	            // Set System L&F
	        UIManager.setLookAndFeel(
	            UIManager.getSystemLookAndFeelClassName());
	    } 
	    catch (Exception e) {
	       // handle exception
	    }

		 
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					XShell window = new XShell(curdir, args , sopts );
					window.mframe.setVisible(true);
					window.mCommandTextArea.requestFocusInWindow();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public XShell(File curdir, List<XValue> args,SerializeOpts sopts) throws Exception {
		mCurdir = curdir;
		Options opts = new Options( "c=command:,cf=command-file:,f=file:", SerializeOpts.getOptionDefs() );
		opts.parse(args);
		
		sopts.setOptions(opts);
		String command = opts.getOptString("c", null);
		if( command == null ){
			String fname = opts.getOptString("cf", null );
			if( fname != null ){
					command =  Util.convertPath(fname , false );
				
			}
			if( command == null ){
			   fname = opts.getOptString("f", null);
		   	   if( fname != null )
				command = Util.readString(new File(curdir, fname ) ,  sopts.getInput_text_encoding() );
			}
	
		}
		initialize(command,opts.getRemainingArgs());
	}


	/**
	 * Initialize the contents of the frame.
	 * @param list 
	 * @param command 
	 * 
	 * @throws Exception
	 */
	private void initialize(String command, List<XValue> args) throws Exception {

		
		
		
		mframe = new JFrame();
		mframe.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				mShell.close();
			}
		});
		mframe.setBounds(100, 100, 709, 604);
		mframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		mframe.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mframe.dispose();
			}
		});
		
		JMenuItem mntmNew = new JMenuItem("New");
		mntmNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				
			}
		});
		mnFile.add(mntmNew);
		
		JMenuItem mntmOpen = new JMenuItem("Open...");
		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser(mCurdir);

				// Show open dialog; this method does not return until the dialog is closed
				if( fc.showSaveDialog(mframe) == JFileChooser.APPROVE_OPTION ){
					File selFile = fc.getSelectedFile();
					String data = readFrom( selFile );
					if( data != null )
						mCommandTextArea.setText(data);
				}
				
				


			}
		});
		mnFile.add(mntmOpen);
		
		JMenuItem mntmSaveAs = new JMenuItem("Save As...");
		mntmSaveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				JFileChooser fc = new JFileChooser(mCurdir);

				// Show open dialog; this method does not return until the dialog is closed
				fc.showSaveDialog(mframe);
				if( fc.showSaveDialog(mframe) == JFileChooser.APPROVE_OPTION ){
					File selFile = fc.getSelectedFile();
					saveTo( mCommandTextArea.getText() , selFile );
					
				}
				
			}
		});
		mnFile.add(mntmSaveAs);
		
		JMenuItem mntmSaveResults = new JMenuItem("Save Results..");
		mntmSaveResults.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser(mCurdir);

				// Show open dialog; this method does not return until the dialog is closed
				if( fc.showSaveDialog(mframe) == JFileChooser.APPROVE_OPTION ){
					File selFile = fc.getSelectedFile();
					saveTo( mResultTextArea.getText() , selFile );
					
				}

				
			}


		});
		mnFile.add(mntmSaveResults);
		
		JSeparator separator = new JSeparator();
		mnFile.add(separator);
		mnFile.add(mntmExit);
		
		JMenu mnView = new JMenu("View");
		menuBar.add(mnView);
		
		JMenuItem mntmLogWindow = new JMenuItem("Log Window");
		mntmLogWindow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				mLogWindow.setVisible( ! mLogWindow.isVisible());
			}
		});
		mnView.add(mntmLogWindow);
		
		JToolBar toolBar = new JToolBar();
		menuBar.add(toolBar);
		
		JButton btnRun = new JButton("Run");
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mShell.putCommand(mCommandTextArea.getText());
			}

			
		});
		toolBar.add(btnRun);
		
		JButton btnStop = new JButton("Stop");
		btnStop.setEnabled(false);
		toolBar.add(btnStop);
		mframe.getContentPane().setLayout(new BoxLayout(mframe.getContentPane(), BoxLayout.X_AXIS));
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		mframe.getContentPane().add(splitPane);
		
		mResultTextArea = new JTextArea();
		mResultTextArea.setRows(10);
		
		
		mShell = new ShellThread( null ,  mResultTextArea , btnRun , btnStop);
		
		mCommandTextArea = new JTextArea();
		
		
        JScrollPane scrollCommand = new JScrollPane(mCommandTextArea,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        JScrollPane scrollResult = new JScrollPane(mResultTextArea,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		
		splitPane.setRightComponent(scrollResult);
		splitPane.setLeftComponent(scrollCommand);
		mCommandTextArea.setRows(10);
		splitPane.setDividerLocation(0.5);

		
		mResultTextArea.setEditable(false);
		
		new TextAreaPopupMenu( mResultTextArea );
		new TextAreaPopupMenu( mCommandTextArea );
		

		mLogWindow = new LogFrame();
		
		if( command != null  )
			mCommandTextArea.setText( command + " "  + Util.stringJoin( Util.toStringList(args)," ") );

		mShell.start();
		
	}


	protected void saveTo(String text, File selFile) {
		try {
			OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(selFile),
					"utf8");
			writer.write(text);
			writer.close();

		} catch (IOException e) {
			JOptionPane.showMessageDialog(mframe,
					"Error writing to file: " + e.getLocalizedMessage());

		}

	}

	protected String readFrom(File selFile) {
		try {
			return Util.readString(selFile, "utf8");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(mframe,
					"Error reading from file: " + e.getLocalizedMessage());

			return null;
		}

	}
}

//
//
// Copyright (C) 2008-2014   David A. Lee.
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
