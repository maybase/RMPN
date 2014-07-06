package com.pawineept.ptm.layout;

import java.sql.Connection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import com.pawineept.ptm.app.ApplicationMain;
import com.pawineept.ptm.dao.TbCTitleDAO;
import com.pawineept.ptm.dao.TbRunningnoDAO;
import com.pawineept.ptm.dao.TbTPatientDAO;
import com.pawineept.ptm.model.TbCTitle;
import com.pawineept.ptm.model.TbRunningno;
import com.pawineept.ptm.model.TbTPatient;
import com.pawineept.ptm.util.DBUtil;
import com.pawineept.ptm.util.NumberUtil;
import com.pawineept.ptm.util.ReportUtil;

public class MedicalRecordLayout extends Composite {
	
	public static String patientId;
	public static boolean modeedit = false;
	private Label labeltitle;
	private Text txtFirstName;
	private Text txtLastName;
	private Text txtNickName;
	private Combo txtSex;
	private DateTime txtBirthDate;
	private Text txtAge;
	private Text txtOccupation;
	private Combo txtBlood;
	private Combo txtBloodRh;
	private Text txtIDCard;
	private Text txtAddress;
	private Text txtPhone;
	private Combo txtTitleDesc;
	private Combo txtMaritalStatus;
	
	private Label txtErrorMsg;
	private Text txtptno;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public MedicalRecordLayout(Composite parent, int style) {
		super(parent, style);
		setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		setLayout(new GridLayout(9, false));
		
		labeltitle = new Label(this, SWT.NONE);
		labeltitle.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 9, 1));
		labeltitle.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		labeltitle.setText("สร้างเวชระเบียน");
		new Label(this, SWT.NONE);
		
		Label lblPtno = new Label(this, SWT.NONE);
		lblPtno.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPtno.setText("PT.NO.");
		
		txtptno = new Text(this, SWT.BORDER);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Label label_1 = new Label(this, SWT.NONE);
		label_1.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		label_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_1.setText("ชื่อ");
		
		txtTitleDesc = new Combo(this, SWT.NONE);
		txtTitleDesc.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		initTitleDesc();
		
		txtFirstName = new Text(this, SWT.BORDER);
		txtFirstName.setBackground(SWTResourceManager.getColor(255, 228, 181));
		txtFirstName.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		GridData gd_txtFirstName = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_txtFirstName.widthHint = 104;
		txtFirstName.setLayoutData(gd_txtFirstName);
		
		Label label_2 = new Label(this, SWT.NONE);
		label_2.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		label_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_2.setText("นามสกุล");
		
		txtLastName = new Text(this, SWT.BORDER);
		txtLastName.setBackground(SWTResourceManager.getColor(255, 222, 173));
		txtLastName.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		GridData gd_txtLastName = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_txtLastName.widthHint = 112;
		txtLastName.setLayoutData(gd_txtLastName);
		
		Label label_3 = new Label(this, SWT.NONE);
		label_3.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		label_3.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_3.setText("ชื่อเล่น");
		
		txtNickName = new Text(this, SWT.BORDER);
		txtNickName.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Label label_4 = new Label(this, SWT.NONE);
		label_4.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		label_4.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_4.setText("เพศ");
		
		txtSex = new Combo(this, SWT.NONE);
		txtSex.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		txtSex.setItems(new String[] {"ไม่ระบุ", "ชาย", "หญิง"});
		txtSex.select(0);
		
		Label label_5 = new Label(this, SWT.NONE);
		label_5.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		label_5.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_5.setText("วันเกิด");
		
		txtBirthDate = new DateTime(this, SWT.BORDER);
		txtBirthDate.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		txtBirthDate.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Label label_6 = new Label(this, SWT.NONE);
		label_6.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		label_6.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_6.setText("อายุ");
		
		txtAge = new Text(this, SWT.BORDER);
		txtAge.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		GridData gd_txtAge = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_txtAge.widthHint = 39;
		txtAge.setLayoutData(gd_txtAge);
		
		Label label_7 = new Label(this, SWT.NONE);
		label_7.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		label_7.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_7.setText("สถานะสมรส");
		
		txtMaritalStatus = new Combo(this, SWT.NONE);
		txtMaritalStatus.setEnabled(false);
		txtMaritalStatus.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		txtMaritalStatus.setItems(new String[] {"ไม่ระบุ", "โสด", "แต่งงาน", "แยกกันอยู่", "หย่า", "หม้าย"});
		txtMaritalStatus.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		txtMaritalStatus.select(0);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Label label_8 = new Label(this, SWT.NONE);
		label_8.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		label_8.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_8.setText("อาชีพ");
		
		txtOccupation = new Text(this, SWT.BORDER);
		txtOccupation.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		GridData gd_txtOccupation = new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1);
		gd_txtOccupation.widthHint = 177;
		txtOccupation.setLayoutData(gd_txtOccupation);
		
		Label label_9 = new Label(this, SWT.NONE);
		label_9.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		label_9.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_9.setText("หมู่เลือด");
		
		txtBlood = new Combo(this, SWT.NONE);
		txtBlood.setEnabled(false);
		txtBlood.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		txtBlood.setItems(new String[] {"ไม่ระบุ", "A", "AB", "B", "O"});
		txtBlood.select(0);
		
		Label lblRh = new Label(this, SWT.NONE);
		lblRh.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		lblRh.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblRh.setText("RH");
		
		txtBloodRh = new Combo(this, SWT.NONE);
		txtBloodRh.setEnabled(false);
		txtBloodRh.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		txtBloodRh.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		txtBloodRh.setItems(new String[] {"-", "Rh+ve", "Rh-ve"});
		txtBloodRh.select(0);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Label lblPassport = new Label(this, SWT.NONE);
		lblPassport.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		lblPassport.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPassport.setText("รหัสประจำตัวประชาชน");
		
		txtIDCard = new Text(this, SWT.BORDER);
		txtIDCard.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		GridData gd_txtIDCard = new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1);
		gd_txtIDCard.widthHint = 143;
		txtIDCard.setLayoutData(gd_txtIDCard);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Label label_10 = new Label(this, SWT.NONE);
		label_10.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		label_10.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_10.setText("ที่อยู่");
		
		txtAddress = new Text(this, SWT.BORDER);
		txtAddress.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		GridData gd_txtAddress = new GridData(SWT.FILL, SWT.CENTER, false, false, 6, 1);
		gd_txtAddress.widthHint = 365;
		txtAddress.setLayoutData(gd_txtAddress);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Label label_11 = new Label(this, SWT.NONE);
		label_11.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		label_11.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_11.setText("เบอร์โทรศัพท์ติดต่อ");
		
		txtPhone = new Text(this, SWT.BORDER);
		txtPhone.setBackground(SWTResourceManager.getColor(255, 222, 173));
		txtPhone.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		GridData gd_txtPhone = new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1);
		gd_txtPhone.widthHint = 126;
		txtPhone.setLayoutData(gd_txtPhone);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		txtErrorMsg = new Label(this, SWT.NONE);
		txtErrorMsg.setText(" ");
		txtErrorMsg.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		txtErrorMsg.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		txtErrorMsg.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 6, 1));
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Button button = new Button(this, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {			
			public void widgetSelected(SelectionEvent e) {
				setActionSubmit();				
			}
		});
		button.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		button.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		button.setText("บันทึก");
		
		Button button_1 = new Button(this, SWT.NONE);
		button_1.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				setActionPrint();
			}
		});
		button_1.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		button_1.setText("พิมพ์เวชระเบียน");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Canvas canvas = new Canvas(this, SWT.NONE);
		canvas.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		canvas.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1));
		
		if(modeedit){
			findEditMode();
		}
	}

	private void initTitleDesc() {
		Connection conn = null;
		try{
			conn = DBUtil.connect();
			TbCTitleDAO dao = new TbCTitleDAO();
			List<String> lst = dao.findAllList(conn);
			txtTitleDesc.setItems(lst.toArray(new String[lst.size()]));
			txtTitleDesc.select(0);
		} catch (Exception e) {
			e.printStackTrace();
			errorMsg("Error: "+e.getMessage());
		}finally{
			DBUtil.close(conn);
		}
	}

	@SuppressWarnings("deprecation")
	private void findEditMode() {
		Connection conn = null;
		try{
			conn = DBUtil.connect();
			TbTPatientDAO dao = new TbTPatientDAO();
			TbTPatient pt = new TbTPatient();
			pt.setPatientid(patientId);
			dao.select(conn,pt);
			labeltitle.setText("แก้ไขเวชระเบียน");
			txtptno.setEditable(false);
			txtptno.setText(pt.getPatientid());
			txtTitleDesc.setText(pt.getTitle().getTitleDescTh());
			txtFirstName.setText(pt.getFirstname());
			txtLastName.setText(pt.getLastname());
			txtNickName.setText(pt.getNickname()==null?"":pt.getNickname());
			if(pt.getSex()==null)
				txtSex.select(0);
			else
				txtSex.setText(pt.getSex());
			if(pt.getBirthDate()!=null){
				SimpleDateFormat yyyy = new SimpleDateFormat("yyyy",Locale.US);
				txtBirthDate.setDate(Integer.parseInt(yyyy.format(pt.getBirthDate())), pt.getBirthDate().getMonth(), pt.getBirthDate().getDate());
			}
			txtAge.setText(pt.getAge()==null?"":pt.getAge().toString());
			if(pt.getMaritalStatus()==null)
				txtMaritalStatus.select(0);
			else
				txtMaritalStatus.setText(pt.getMaritalStatus());
			txtOccupation.setText(pt.getOccupation()==null?"":pt.getOccupation());
			txtIDCard.setText(pt.getIdcard()==null?"":pt.getIdcard());
			txtAddress.setText(pt.getAddress()==null?"":pt.getAddress());
			txtPhone.setText(pt.getMobilephone()==null?pt.getTelephone():pt.getMobilephone());
			
		} catch (Exception e) {
			e.printStackTrace();
			errorMsg("Error: "+e.getMessage());
		}finally{
			DBUtil.close(conn);
		}
	}

	protected void setActionPrint() {
		Connection conn = null;
		errorMsg("กำลังปริ้น...");
		try {
			conn = DBUtil.connect();
			TbTPatient tbTPatient = new TbTPatient();
			TbTPatientDAO dao = new TbTPatientDAO();
			tbTPatient.setPatientid(patientId);
			dao.select(conn,tbTPatient);
			ReportUtil report = new ReportUtil();
			report.pt0101(tbTPatient);
			okMsg("ทำการปริ้นเรียบร้อย");
		} catch (Exception e) {
			errorMsg("report error:"+e.getMessage());
			e.printStackTrace();
		}finally{
			DBUtil.close(conn);
		}
		
	}

	protected void setActionSubmit() {
		Connection conn = null;
		try {
			conn = DBUtil.connect();
			TbTPatient obj = new TbTPatient();
			TbCTitleDAO titleDAO = new TbCTitleDAO();
			TbTPatientDAO dao = new TbTPatientDAO();
			TbCTitle title = new TbCTitle();
			title.setTitleDescTh(txtTitleDesc.getText());
			title.setTitleid(titleDAO.findIdForNameTH(conn, txtTitleDesc.getText()));
			
			obj.setTitle(title);
			obj.setFirstname(txtFirstName.getText());
			obj.setLastname(txtLastName.getText());
			obj.setNickname(txtNickName.getText());
			obj.setAge(NumberUtil.getLong(txtAge.getText()));
			SimpleDateFormat sdffull = new SimpleDateFormat("dd/MM/yyyy",Locale.US);
			System.out.println("Date:"+sdffull.parse(txtBirthDate.getDay()+"/"+(txtBirthDate.getMonth()+1)+"/"+txtBirthDate.getYear()).getTime());
			System.out.println("Year:"+txtBirthDate.getYear());
			obj.setBirthDate(new Timestamp(sdffull.parse(txtBirthDate.getDay()+"/"+(txtBirthDate.getMonth()+1)+"/"+txtBirthDate.getYear()).getTime()));
			obj.setMaritalStatus(txtMaritalStatus.getText());
			obj.setOccupation(txtOccupation.getText());
			String blood = txtBlood.getText();
			if(!txtBloodRh.getText().equalsIgnoreCase("-")){
				blood += txtBloodRh.getText();
			}
			obj.setBlood(blood);
			obj.setIdcard(txtIDCard.getText());
			obj.setAddress(txtAddress.getText());
			obj.setMobilephone(txtPhone.getText());
			
			if(modeedit){
				obj.setPatientid(patientId);
				dao.update(conn, obj);
				okMsg("อัพเดทเรียบร้อยแล้ว");
			}else{
				if(txtptno.getText().trim().length()==0){
					TbRunningnoDAO runDAO = new TbRunningnoDAO();
					TbRunningno runno = new TbRunningno();
					SimpleDateFormat sdf = new SimpleDateFormat("yy",new Locale("th", "TH"));
					runno.setYear(sdf.format(new java.util.Date()));
					runno.setPrefix("PT");
					int maxrun = runDAO.selectMaxRunNo(conn, runno);
					if(maxrun==0){
						runno.setRunNum(new Long(1));
						runDAO.insertRunNo(conn, runno);
					}else{
						runDAO.runNoplus(conn, runno);
					}
					obj.setPatientid(runno.getPrefix()+"-"+runno.getYear()+"-"+(maxrun+1));
				}else{
					obj.setPatientid(txtptno.getText());
				}
				obj.setCreateBy(ApplicationMain.USERNAME);
				dao.insert(conn, obj);
				patientId = obj.getPatientid();
				okMsg("เพิ่มข้อมูลเรียบร้อยแล้ว");
				findEditMode();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			errorMsg("Error: "+e1.getMessage());
		}finally{
			DBUtil.close(conn);
		}		
	}
	
	private void errorMsg(String text){
		txtErrorMsg.setText(text);
		txtErrorMsg.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
	}
	private void okMsg(String text){
		txtErrorMsg.setText(text);
		txtErrorMsg.setForeground(SWTResourceManager.getColor(SWT.COLOR_GREEN));
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
