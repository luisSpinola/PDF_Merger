package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import controllers.MainController;

import java.awt.Color;
import javax.swing.UIManager;
import java.awt.Font;
import javax.swing.JLabel;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import java.awt.SystemColor;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JTextPane;
import javax.swing.JCheckBox;

public class Interface extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static MainController mainController;
	private static JTextPane compatibleText;
	private static JTextPane noncompatibleText;
	private static JTextPane generatedText; 
	private static JTextArea mainInfo;
	private static JTextArea inputText; 
	private static JTextArea outputText; 
	private static JTextPane deletedText; 
	public static JTextArea extraInfo;
	private static JCheckBox backupCheck;
	private static Label deletedInfo;
	private static Label generatedInfo; 
	private static Label compatibleInfo;
	private static Label noncompatibleInfo;
	private static Label outputLabel;
	private static Label inputLabel;
	private JScrollPane scrollPane;
	public static void main(String[] args) {
		init();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Interface frame = new Interface();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private static void init() {
	    try {
			mainController = new MainController();
		} catch (IOException e) { e.printStackTrace(); }
	}
	
	public Interface() {
		setFont(new Font("Segoe UI", Font.BOLD, 17));
		setTitle("PDF Merger");
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/logo.png")));
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 873, 313);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(199, 0, 646, 253);
		contentPane.add(panel);
		panel.setLayout(null);
		
		outputLabel = new Label("Output Directory:");
		outputLabel.setEnabled(false);
		outputLabel.setFont(new Font("Dialog", Font.BOLD, 13));
		outputLabel.setBounds(10, 41, 121, 32);
		panel.add(outputLabel);
		
		compatibleInfo = new Label("Compatible files:");
		compatibleInfo.setFont(new Font("Dialog", Font.BOLD, 13));
		compatibleInfo.setEnabled(false);
		compatibleInfo.setBounds(10, 101, 121, 32);
		panel.add(compatibleInfo);
		
		generatedInfo = new Label("Output pdfs:");
		generatedInfo.setFont(new Font("Dialog", Font.BOLD, 13));
		generatedInfo.setEnabled(false);
		generatedInfo.setBounds(10, 143, 121, 32);
		panel.add(generatedInfo);
		
		deletedInfo = new Label("Files deleted:");
		deletedInfo.setFont(new Font("Dialog", Font.BOLD, 13));
		deletedInfo.setEnabled(false);
		deletedInfo.setBounds(214, 143, 121, 32);
		panel.add(deletedInfo);
		
		noncompatibleInfo = new Label("Noncompatible files:");
		noncompatibleInfo.setFont(new Font("Dialog", Font.BOLD, 13));
		noncompatibleInfo.setEnabled(false);
		noncompatibleInfo.setBounds(214, 101, 144, 32);
		panel.add(noncompatibleInfo);
		
		inputLabel = new Label("Input Directory:");
		inputLabel.setEnabled(false);
		inputLabel.setFont(new Font("Dialog", Font.BOLD, 13));
		inputLabel.setBounds(10, 6, 121, 32);
		panel.add(inputLabel);
		
		inputText = new JTextArea();
		inputText.setFont(new Font("Monospaced", Font.PLAIN, 12));
		inputText.setBackground(UIManager.getColor("Button.background"));
		inputText.setEditable(false);
		inputText.setBounds(137, 16, 580, 22);
		panel.add(inputText);
		
		outputText = new JTextArea();
		outputText.setFont(new Font("Monospaced", Font.PLAIN, 12));
		outputText.setEditable(false);
		outputText.setBackground(SystemColor.menu);
		outputText.setBounds(137, 51, 580, 22);
		panel.add(outputText);
		
		JLabel imageLabel = new JLabel("");
		imageLabel.setBounds(445, 199, 200, 54);
		panel.add(imageLabel);
		imageLabel.setIcon(new ImageIcon(Interface.class.getResource("/dclogo1.png")));
		
		deletedText = new JTextPane();
		deletedText.setFont(new Font("Tahoma", Font.BOLD, 13));
		deletedText.setEditable(false);
		deletedText.setBackground(SystemColor.menu);
		deletedText.setBounds(364, 148, 57, 22);
		panel.add(deletedText);
		
		noncompatibleText = new JTextPane();
		noncompatibleText.setFont(new Font("Tahoma", Font.BOLD, 13));
		noncompatibleText.setEditable(false);
		noncompatibleText.setBackground(SystemColor.menu);
		noncompatibleText.setBounds(364, 106, 57, 22);
		panel.add(noncompatibleText);
		
		generatedText = new JTextPane();
		generatedText.setFont(new Font("Tahoma", Font.BOLD, 13));
		generatedText.setEditable(false);
		generatedText.setBackground(SystemColor.menu);
		generatedText.setBounds(140, 148, 57, 22);
		panel.add(generatedText);
		
		compatibleText = new JTextPane();
		compatibleText.setEditable(false);
		compatibleText.setBackground(UIManager.getColor("Button.background"));
		compatibleText.setFont(new Font("Tahoma", Font.BOLD, 13));
		compatibleText.setBounds(140, 106, 57, 22);
		panel.add(compatibleText);
		
		mainInfo = new JTextArea();
		mainInfo.setBackground(UIManager.getColor("Button.background"));
		mainInfo.setBounds(10, 199, 235, 41);
		panel.add(mainInfo);
		mainInfo.setFont(new Font("Monospaced", Font.BOLD, 17));
		mainInfo.setForeground(SystemColor.inactiveCaption);
		mainInfo.setEditable(false);
		
		JButton InputButton = new JButton("Input Directory");
		InputButton.setForeground(Color.BLACK);
		InputButton.setFocusable(false);
		InputButton.setFont(new Font("Tahoma", Font.BOLD, 18));
		InputButton.setBackground(SystemColor.menu);
		InputButton.setBounds(10, 13, 177, 49);
		contentPane.add(InputButton);
		
		InputButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int option = fileChooser.showOpenDialog(contentPane);
				if(option == JFileChooser.APPROVE_OPTION){
					
					mainController.cleanAllStuff();
					File file = fileChooser.getSelectedFile();
					
					inputText.setText(file.getPath());
					inputLabel.setEnabled(true);
					
					outputText.setText(file.getPath());
					
					mainController.setFinalPath(file.getPath());
					mainController.setAllFiles(file.listFiles());
					setMainInfoText("");
					
					setDeletedText("");
					setDeletedTextEnable(false);
					
					setNonCompatibleLabel(true);
					setCompatibleLabel(true);
					
					noncompatibleInfo.setEnabled(true);
					
					setOutputLabel(true);
				}else{
					setOutputLabel(false);
					setOutputText("");
					setDeletedText("");
					setDeletedTextEnable(false);
					setCompatibleText("");
					setOutputText("");
					setNonCompatibleText("");
					setNonCompatibleLabel(false);
					setCompatibleLabel(false);
					setGeneratedLabel(false);
					setGeneratedText("");
					
					inputText.setText("No input folder selected.");
					inputLabel.setEnabled(false);
					mainController.setFinalPath("");
					mainController.cleanAllStuff();
				}
			}
		});
		
		JButton OutputButton = new JButton("Output Directory");
		OutputButton.setEnabled(false);
		OutputButton.setForeground(Color.BLACK);
		OutputButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		OutputButton.setFocusable(false);
		OutputButton.setBackground(SystemColor.menu);
		OutputButton.setBounds(10, 75, 177, 49);
		contentPane.add(OutputButton);
		
		JButton ExecuteButton = new JButton("Execute");
		ExecuteButton.setForeground(SystemColor.text);
		ExecuteButton.setFont(new Font("Tahoma", Font.BOLD, 18));
		ExecuteButton.setFocusable(false);
		ExecuteButton.setBackground(SystemColor.textHighlight);
		ExecuteButton.setBounds(10, 188, 177, 49);
		contentPane.add(ExecuteButton);
		
		backupCheck = new JCheckBox("Backup used pdfs");
		backupCheck.setSelected(true);
		backupCheck.setFocusable(false);
		backupCheck.setBounds(10, 140, 177, 25);
		contentPane.add(backupCheck);
		
		
		
		extraInfo = new JTextArea();
		extraInfo.setRows(25);
		extraInfo.setText("v 1.0");
		extraInfo.setEditable(false);
		
		scrollPane = new JScrollPane(extraInfo);
		scrollPane.setBounds(10, 279, 833, 518);
		contentPane.add(scrollPane);
		
		
		
		ExecuteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainController.Execute();
			}
		});
	}
	
	public static void setCompatibleText(String value) {
		compatibleText.setText(value);
	}
	public static void setDeletedText(String value) {
		deletedText.setText(value);
	}
	public static void setDeletedTextEnable(boolean value) {
		deletedInfo.setEnabled(value);
	}
	
	public static void setNonCompatibleText(String value) {
		noncompatibleText.setText(value);
	}
	
	public static void setGeneratedText(String value) {
		generatedText.setText(value);
	}
	
	public static void setMainInfoText(String value) {
		mainInfo.setText(value);
	}
	public static void setMainInfoTextColor(Color color) {
		mainInfo.setForeground(color);
	}
	
	public static void setInputText(String value) {
		inputText.setText(value);
	}
	
	public static void setOutputText(String value) {
		outputText.setText(value);
	}
	
	public static boolean getBackupCheck() {
		return backupCheck.isSelected();
	}
	
	public static void setNonCompatibleLabel(boolean value) {
		noncompatibleInfo.setEnabled(value);
	}
	public static void setCompatibleLabel(boolean value) {
		compatibleInfo.setEnabled(value);
	}
	public static void setOutputLabel(boolean value) {
		outputLabel.setEnabled(value);
	}
	public static void setInputLabel(boolean value) {
		inputLabel.setEnabled(value);
	}
	public static void setGeneratedLabel(boolean value) {
		generatedInfo.setEnabled(value);
	}
}
