package ui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import util.SourceWorker;

public class SourcePanel extends JFrame {
	/**
	 * Serial version
	 */
	private static final long serialVersionUID = 1L;

	private Container root;
	
	private JTextField directoryPicker;
	private File projectDir = null;
	private JTextField messagesPicker;
	private File messagesFile = null;
	private JTextField langInput;
	private JTextField extInput;
	private JTextField funInput;
	
	public SourcePanel() {
		this.root = this.getRootPane().getContentPane();
		this.root.setLayout(new FlowLayout());
		this.initialize();
		
		this.setSize(605, 400);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public void initialize() {
		this.root.add( getChooserLabel() );
		this.root.add( getChooserComponent() );
		
		this.root.add( getLanguageLabel() );
		this.root.add( getLanguageInput() );
//		
		this.root.add( getExtensionLabel() );
		this.root.add( getExtensionInput() );
//		
		this.root.add( getFunctionsLabel() );
		this.root.add( getFunctionsInput() );
//		
		this.root.add( getOutputFileLabel() );
		this.root.add( getOutputFileInput() );
		
		this.root.add( getGoButton() );
	}
	
	/**
	 * Label for plugin directory chooser
	 * @return label
	 */
	private Component getChooserLabel() {
		JLabel label = new JLabel("Pick your plugin/theme folder:");
		
		return label;
	}
	
	/**
	 * Pick the directory for the project we're browsing
	 * @return DirectoryPicker
	 */
	private Component getChooserComponent() {
		JPanel chooserPanel = new JPanel();
		this.directoryPicker = new JTextField();
		JButton pickerOpener = new JButton("Choose Directory");
		
		// add to main panel
		chooserPanel.add(directoryPicker);
		chooserPanel.add(pickerOpener);
		
		directoryPicker.setPreferredSize(new Dimension(200, 25) );
		// add the event handler
		pickerOpener.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = fileChooser.showOpenDialog(SourcePanel.this);
	            if (returnVal == JFileChooser.APPROVE_OPTION) {
	                projectDir = fileChooser.getSelectedFile();
	                //This is where a real application would open the file.
	                // System.out.println("Folder: " + projectDir.getAbsolutePath());
	                SourcePanel.this.directoryPicker.setText(projectDir.getAbsolutePath());
	            } else {
	                // System.out.println("Open command cancelled by user.");
	            }
			}
		});
		
		
		return chooserPanel;
	}
	
	/**
	 * Label for Languages (PHP, C++, other)
	 * @return JLabel language label
	 */
	private Component getLanguageLabel() {
		JLabel label = new JLabel("Language (PHP, C++, other):");
		
		return label;
	}
	
	/**
	 * Language input for preferred language
	 * @return JTextField language
	 */
	private Component getLanguageInput() {
		langInput = new JTextField();
		langInput.setText("PHP");
		langInput.setPreferredSize(new Dimension(200, 25) );
		
		return langInput;
	}
	
	/**
	 * Label for Extension (php, cpp, other)
	 * @return JLabel extension label
	 */
	private Component getExtensionLabel() {
		JLabel label = new JLabel("Extension group (*.php, me.cpp, py, other):");
		
		return label;
	}
	
	/**
	 * Extension input for extension
	 * @return JTextField extension
	 */
	private Component getExtensionInput() {
		extInput = new JTextField();
		extInput.setText("*.php");
		extInput.setPreferredSize(new Dimension(200, 25) );
		
		return extInput;
	}
	
	/**
	 * Label for Functions (__, _e, _x, other)
	 * @return JLabel functions label
	 */
	private Component getFunctionsLabel() {
		JLabel label = new JLabel("Functions (__, _e, _x, other)");
		
		return label;
	}
	
	/**
	 * Functions input
	 * @return JTextField functions list
	 */
	private Component getFunctionsInput() {
		funInput = new JTextField();
		funInput.setText("__,_e,_x,_n");
		funInput.setPreferredSize(new Dimension(200, 25) );
		
		return funInput;
	}
	
	/**
	 * Label for output .po file chooser
	 * @return label
	 */
	private Component getOutputFileLabel() {
		JLabel label = new JLabel("Pick your messages.po file:");
		
		return label;
	}
	
	private Component getOutputFileInput() {
		JPanel chooserPanel = new JPanel();
		this.messagesPicker = new JTextField();
		JButton pickerOpener = new JButton("Choose File");
		
		// add to main panel
		chooserPanel.add(messagesPicker);
		chooserPanel.add(pickerOpener);
		
		messagesPicker.setPreferredSize(new Dimension(200, 25) );
		// add the event handler
		pickerOpener.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();

				int returnVal = fileChooser.showOpenDialog(SourcePanel.this);
	            if (returnVal == JFileChooser.APPROVE_OPTION) {
	                messagesFile = fileChooser.getSelectedFile();
	                //This is where a real application would open the file.
	                // System.out.println("File: " + messagesFile.getName() + ".");
	                SourcePanel.this.messagesPicker.setText( messagesFile.getAbsolutePath() );
	            } else {
	                // System.out.println("Open command cancelled by user.");
	            }
			}
		});
		
		return chooserPanel;
	}
	
	/**
	 * Get the "Go" button
	 * @return button
	 */
	private Component getGoButton() {
		JButton go = new JButton("Go!");
		go.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SourceWorker worker = new SourceWorker(SourcePanel.this.directoryPicker.getText(), langInput.getText(), extInput.getText(),
														funInput.getText(), messagesPicker.getText());
				
				String message = worker.executeTranslation();
				
				JOptionPane.showMessageDialog(SourcePanel.this, message);
				
			}
		});
		
		return go;
	}
}
