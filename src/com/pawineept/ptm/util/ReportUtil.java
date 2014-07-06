package com.pawineept.ptm.util;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;

import com.pawineept.ptm.model.TbTPatient;

public class ReportUtil {
	private String value(String str){
		return str==null?"-":str.trim().length()==0?"-":str;
	}
	private String value(Timestamp birthDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy",new Locale("th", "TH"));
		return birthDate==null?"-":sdf.format(birthDate);
	}
	private String value(Long age) {
		return age==null?"-":age.toString();
	}
	
	public void pt0101(TbTPatient tbTPatient) throws Exception {
		
		Map<String,Object> param = new HashMap<String,Object>();
        param.put("P_PATIENTID", tbTPatient.getPatientid());
        param.put("P_FNAME", value(tbTPatient.getFirstname()));
        param.put("P_LNAME", value(tbTPatient.getLastname()));
        param.put("P_TAXID", value(tbTPatient.getIdcard()));
        param.put("P_AGE", value(tbTPatient.getAge()));
        param.put("P_BIRTHDATE", value(tbTPatient.getBirthDate()));
        param.put("P_ADDRESS", value(tbTPatient.getAddress()));
        StringBuilder phone = new StringBuilder();
        if(tbTPatient.getTelephone()!=null && tbTPatient.getTelephone().trim().length()>0){
        	phone.append("(บ้าน) "+tbTPatient.getTelephone());
        }
        if(tbTPatient.getMobilephone()!=null && tbTPatient.getMobilephone().trim().length()>0){
        	if(phone.length()>0)
        		phone.append(", ");
        	phone.append("(มือถือ) "+tbTPatient.getMobilephone());
        }
        param.put("P_PHONE", value(phone.toString()));
        param.put("P_SEX", value(tbTPatient.getSex()));
        param.put("P_OCCUPATION", value(tbTPatient.getOccupation()));  
        String destFile = "pdf/patient/"+tbTPatient.getPatientid()+".pdf";
        printReport("report/PT101R1.jasper",destFile,param);
		
        
	}
	private void printReport(String srcReport, String destFile, Map<String, Object> param) {
		FileInputStream fis = null;
		try {
			printMap(param);
			File inFile = new File(srcReport);
			System.out.println("srcReport:"+inFile.getAbsolutePath());
			File outFile = new File(destFile);
			System.out.println("outFile:"+outFile.getAbsolutePath());
			File outPath = new File(outFile.getParent());
			System.out.println("outPath:"+outPath.getAbsolutePath());
			if(!outPath.exists()){
				System.out.println("Create Path:"+outPath.getAbsolutePath());
				outPath.mkdirs();
			}
			boolean printfromFile = true;
			if(printfromFile){
				JasperPrint fillReport = JasperFillManager.fillReport(inFile.getAbsolutePath(), param, new JREmptyDataSource());
				OutputStream output = new FileOutputStream(outFile); 
		        JasperExportManager.exportReportToPdfStream(fillReport, output); 
				output.flush();
				output.close();
				
				Desktop desktop = Desktop.getDesktop();
				desktop.open(outFile); // open pdf
//				desktop.print(outFile); // print pdf A4 only
                
			}else{			
	            JasperPrint fillReport = JasperFillManager.fillReport(inFile.getAbsolutePath(), param, new JREmptyDataSource());
	            JasperPrintManager.printReport(fillReport,false);
			}
            
		} catch (JRException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(fis!=null)fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	private void printMap(Map<String, Object> param) {
		Set<String> set = param.keySet();
		System.out.println("############ MAP DATA ##############");
		for (String key : set) {
			System.out.println(key+":"+param.get(key));
		}
		System.out.println("####################################");
	}
		
}
