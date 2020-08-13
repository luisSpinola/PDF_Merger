package controllers;

import org.apache.pdfbox.multipdf.PDFMergerUtility;

import ui.Interface;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.apache.commons.io.comparator.NameFileComparator;

public class MainController {
	private int pdfs = 0;
	private int compacted = 0;
	private int badPdfs = 0;
	private int filtered = 0;
	private int deleted = 0;
	private String finalPath = "";
	private ArrayList<ArrayList<File>> filesToMerge;
	public File allFiles[] = null;
	public ArrayList<File> allFilesFiltered;
	private ArrayList<File> compactedPdfs;
	
	public MainController() throws IOException {
		allFilesFiltered = new ArrayList<File>();
		filesToMerge = new ArrayList<ArrayList<File>>();
		compactedPdfs = new ArrayList<File>();
		System.out.println("Logic Running");
	}
	
	public void setAllFiles(File files[]) {
		allFiles = files;
		filterPdfFiles();
	}
	private void selectPdfs() {
		Arrays.sort(allFiles, NameFileComparator.NAME_INSENSITIVE_COMPARATOR);
		for(File file : allFiles) {
			String extension = "";
			int i = file.getName().lastIndexOf('.');
	        if (i > 0) {
	             extension = file.getName().substring(i+1);
	        }
	        if(extension.equals("pdf")) {
	        	if(countChar(file.getName(), '_') == 3 && file.getName().length() > 15) {
	        		allFilesFiltered.add(file);
			        pdfs++;
	        	} else if(countChar(file.getName(), '_') == 2){
	        		compactedPdfs.add(file);
	        		compacted++;
	        	}else {
	        		badPdfs++;
	        	}    
	        } else {
	        	filtered++;
	        }
	        
		}
	}
	
	private void distributePdfPerClass() {
		if(allFilesFiltered != null) {
			for(File file : allFilesFiltered) {
				boolean found = false;
				if(filesToMerge.isEmpty()) {
					ArrayList<File> tempList = new ArrayList<File>();
					tempList.add(file);
					filesToMerge.add(tempList);
					found = true;
				} else {
					for(int i = 0; i< filesToMerge.size(); i++) {
						if(filesToMerge.get(i).get(0).getName().substring(0, 15).equals(file.getName().substring(0, 15))) {
							filesToMerge.get(i).add(file);
							found = true;
						}
					}
				}
				
				if(!found) {
					ArrayList<File> tempList = new ArrayList<File>();
					tempList.add(file);
					filesToMerge.add(tempList);
				}
				
		        //System.out.println("File path: "+ file.getAbsolutePath());
		        //System.out.println("Size :"+ file.getTotalSpace());
			}
			String allText = "";
			for(int i = 0; i < filesToMerge.size(); i++) {
				System.out.println("INDEX: " + i);
				allText += "\n" + "   " + filesToMerge.get(i).get(0).getName().substring(0, 14) + ":";
				for(File cena : filesToMerge.get(i)) {
					allText += "\n " + "	" + cena.getName();
					System.out.println("File name: "+ cena.getName());
					
				}
			}
			Interface.extraInfo.setText(allText);
			System.out.println(" ");
		}
	}
	
	public void Execute() {
		if(finalPath.equals("")) {
			Interface.setMainInfoTextColor(new Color(255, 0, 0));
			Interface.setMainInfoText("Invalid directory");

			Interface.setGeneratedLabel(false);
			Interface.setDeletedTextEnable(false);
		} else if(filesToMerge.size() == 0) {
			Interface.setMainInfoTextColor(new Color(255, 0, 0));
			Interface.setMainInfoText("No files to merge");

			Interface.setGeneratedLabel(false);
			Interface.setDeletedTextEnable(false);
		}
		else {

			
			try {
				if(Interface.getBackupCheck()) {
					backupPdf();
				}
				mergeRelatedFiles();
			} catch (IOException e) { e.printStackTrace(); }
			
			deleteUsedPdf();
			System.out.println("Path: " + finalPath);
			Interface.setMainInfoTextColor(new Color(0, 255, 0));
			Interface.setMainInfoText("Job done");
			Interface.setGeneratedLabel(true);
		}
		
		Interface.setOutputLabel(false);
		Interface.setInputLabel(false);
		Interface.setCompatibleLabel(false);
		Interface.setCompatibleText("");
		Interface.setNonCompatibleLabel(false);
		Interface.setNonCompatibleText("");
		
		cleanAllStuff();
	}
	
	private void backupPdf() {
		File myFile = new File(finalPath + "/" + "PdfMerger_Backup");	 
		if (!myFile.exists()){
			myFile.mkdir();
		}else{
			System.out.println("File already exists");
		}

        
        
		for(File file : compactedPdfs) {
			File tempFile = new File(finalPath + "/" + "PdfMerger_Backup" + "/" + file.getName());
			try {
				Files.copy(file.toPath(), tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) { e.printStackTrace(); }
		}
		
		for(int i=0; i<filesToMerge.size(); i++) {
			for(File file : filesToMerge.get(i)) {
				File tempFile = new File(finalPath + "/" + "PdfMerger_Backup" + "/" + file.getName());
				try {
					Files.copy(file.toPath(), tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e) { e.printStackTrace(); }
			}
		}
		
	}
	
	public void cleanAllStuff() {
		pdfs = 0;
		badPdfs = 0;
		filtered = 0;
		deleted = 0;
		finalPath = "";
		filesToMerge.clear();
		compacted = 0;
		compactedPdfs.clear();
		allFiles = null;
		allFilesFiltered.clear();
		Interface.setInputText("");
		Interface.setOutputText("");
	}
	private void filterPdfFiles() {
		selectPdfs();
		distributePdfPerClass();
		
		System.out.println("Compatible Pdf files: " + pdfs);
		System.out.println("Compacted Pdf files: " + compacted);
		System.out.println("Non Compatible Pdf files: " + badPdfs);
		System.out.println("Non Pdf files: " + filtered);
		
		Interface.setCompatibleText(Integer.toString(pdfs));
		Interface.setNonCompatibleText(Integer.toString(badPdfs + filtered));
		
		
	}
	
	public void mergeRelatedFiles() throws IOException {
		System.out.println("Merging...");
		Interface.setMainInfoTextColor(new Color(0, 0, 0));
		Interface.setMainInfoText("Merging...");
		
		String finalName = "";
		for(int i=0; i<filesToMerge.size(); i++) {
			PDFMergerUtility PDFmerger = new PDFMergerUtility();
			finalName = filesToMerge.get(i).get(0).getName().substring(0, 14);
			System.out.println(finalName);
			for(File file : compactedPdfs) {
				if(file.getName().length() > 13) {
					if(file.getName().substring(0, 14).equals(finalName)) {
						PDFmerger.addSource(file);
					}
				}
			}
			for(File file : filesToMerge.get(i)) {
					finalName = file.getName().substring(0, 14);
					PDFmerger.addSource(file);
			}
			PDFmerger.setDestinationFileName(finalPath + "/" + finalName + ".pdf");
			PDFmerger.mergeDocuments(null);	
		}
		
		Interface.setGeneratedText(Integer.toString(filesToMerge.size()));
		System.out.println("Merging done.");
		
		Interface.setMainInfoText("Pdfs Merged");
	}
	
	public void deleteUsedPdf() {
		System.out.println("Deleting...");
		Interface.setMainInfoText("Deleting...");
		for(int i=0; i<filesToMerge.size(); i++) {
			for(File file : filesToMerge.get(i)) {
				if(file.delete()) {
					deleted++;
				} else {
					System.out.println("Failed to delete file: " + file.getName());
				}
			}
		}
		
		Interface.setMainInfoText("Deleted");
		System.out.println("Deleted: " + deleted + " used pdfs.");
		Interface.setDeletedText(Integer.toString(deleted));
		Interface.setDeletedTextEnable(true);
	}
	private int countChar(String str, char c) {
	    int count = 0;

	    for(int i=0; i < str.length(); i++) {   
	    	if(str.charAt(i) == c)
	          count++;
	    }
	    return count;
	}
	
	public void setFinalPath(String path) {
		finalPath = path;
	}
}
