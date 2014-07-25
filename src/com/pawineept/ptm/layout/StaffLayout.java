package com.pawineept.ptm.layout;

import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import com.pawineept.ptm.app.ApplicationMain;
import com.pawineept.ptm.dao.TbCTitleDAO;
import com.pawineept.ptm.dao.TbMPositionDAO;
import com.pawineept.ptm.dao.TbTStaffDAO;
import com.pawineept.ptm.model.TbCTitle;
import com.pawineept.ptm.model.TbMPosition;
import com.pawineept.ptm.model.TbTStaff;
import com.pawineept.ptm.util.DBUtil;

public class StaffLayout extends Composite {
	private Combo txtTitleDesc;
	private Text txtFirstname;
	private Text txtLastName;
	private Text txtAddress;
	private Text txtPhone;
	private Combo txtPositionDesc;
	private Button btnSave;
	private Button chkStatus;
	private Button chkFullTime;
	private Button chkPartTime;
	private Button btnClear;
	private Button btnSearch;
	public static boolean validReqFieldMode = false;
	public static Integer staffid;
	public static boolean editMode = false;
	private Label lblNewLabel;
	private Label txtErrorMsg;
	private Label lblXxxxxxxxxx;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public StaffLayout(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(10, false));
		
		lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 8, 1));
		lblNewLabel.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		lblNewLabel.setText("จัดการพนักงาน");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Label label_1 = new Label(this, SWT.NONE);
		label_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_1.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		label_1.setText("คำนำหน้า");
		
		txtTitleDesc = new Combo(this, SWT.NONE);
		txtTitleDesc.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		txtTitleDesc.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 4, 1));
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Label label_2 = new Label(this, SWT.NONE);
		label_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_2.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		label_2.setText("ชื่อ");
		
		txtFirstname = new Text(this, SWT.BORDER);
		txtFirstname.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		txtFirstname.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));
		new Label(this, SWT.NONE);
		
		Label label_3 = new Label(this, SWT.NONE);
		label_3.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_3.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		label_3.setText("นามสกุล");
		
		txtLastName = new Text(this, SWT.BORDER);
		txtLastName.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		GridData gd_txtLastName = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gd_txtLastName.widthHint = 169;
		txtLastName.setLayoutData(gd_txtLastName);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Label label_4 = new Label(this, SWT.NONE);
		label_4.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_4.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		label_4.setText("ที่อยู่");
		
		txtAddress = new Text(this, SWT.BORDER);
		txtAddress.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		txtAddress.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 6, 1));
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Label lblNewLabel2 = new Label(this, SWT.NONE);
		lblNewLabel2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel2.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		lblNewLabel2.setText("โทรศัพท์");
		
		txtPhone = new Text(this, SWT.BORDER);
		txtPhone.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		txtPhone.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 4, 1));
		
		lblXxxxxxxxxx = new Label(this, SWT.NONE);
		lblXxxxxxxxxx.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		lblXxxxxxxxxx.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		lblXxxxxxxxxx.setText("(รูปแบบ XX(X)-XXX-XXXX)");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Label label_5 = new Label(this, SWT.NONE);
		label_5.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_5.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		label_5.setText("ตำแหน่งงาน");
		
		txtPositionDesc = new Combo(this, SWT.NONE);
		txtPositionDesc.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		txtPositionDesc.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 4, 1));
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Label label_6 = new Label(this, SWT.NONE);
		label_6.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_6.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		label_6.setText("ประเภทงาน");
		
		chkFullTime = new Button(this, SWT.RADIO);
		chkFullTime.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		chkFullTime.setText("ประจำ");
		new Label(this, SWT.NONE);
		
		chkPartTime = new Button(this, SWT.RADIO);
		chkPartTime.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		chkPartTime.setText("ชั่วคราว");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Label label_7 = new Label(this, SWT.NONE);
		label_7.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_7.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		label_7.setText("สถานะการใช้งาน");
		
		chkStatus = new Button(this, SWT.CHECK);
		chkStatus.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		chkStatus.setText("ปกติ");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		txtErrorMsg = new Label(this, SWT.NONE);
		GridData gd_txtErrorMsg = new GridData(SWT.LEFT, SWT.CENTER, false, false, 8, 1);
		gd_txtErrorMsg.widthHint = 369;
		txtErrorMsg.setLayoutData(gd_txtErrorMsg);
		txtErrorMsg.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		btnSave = new Button(this, SWT.NONE);
		btnSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				setActionSubmit();
			}
		});
		btnSave.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		btnSave.setText("บันทึกข้อมูล");
		new Label(this, SWT.NONE);
		
		btnClear = new Button(this, SWT.NONE);
		GridData gd_btnClear = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnClear.widthHint = 76;
		btnClear.setLayoutData(gd_btnClear);
		btnClear.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				if(!StaffLayout.editMode){
					txtTitleDesc.select(0);
					txtFirstname.setText("");
					txtLastName.setText("");
					txtAddress.setText("");
					txtPhone.setText("");
					txtPositionDesc.select(0);
					chkFullTime.setSelection(false);
					chkPartTime.setSelection(false);
					chkStatus.setSelection(false);
					txtErrorMsg.setText("");
					staffid = null;
					StaffLayout.editMode = false;
				}else{
					errorMsg("ไม่สามารถล้างข้อมูล สำหรับโหมดแก้ไขข้อมูลได้");
				}
			}
		});
		btnClear.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		btnClear.setText("ล้างข้อมูล");
		new Label(this, SWT.NONE);
		
		btnSearch = new Button(this, SWT.NONE);
		btnSearch.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ApplicationMain.closeShell();
				StaffSearchLayout uss = new StaffSearchLayout(ApplicationMain.shell,SWT.NONE);
				ApplicationMain.composite = uss;
				ApplicationMain.openShell();
				StaffLayout.editMode = false;
			}
		});
		btnSearch.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		btnSearch.setText("ค้นหาข้อมูลพนักงาน");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		initTitleDesc();
		if(editMode){
			findEditMode();
		}else{
			lblNewLabel.setText("เพิ่มข้อมูลพนักงาน");
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
			
			TbMPositionDAO poDAO = new TbMPositionDAO();
			List<String> lstpo = poDAO.findActiveList(conn);
			txtPositionDesc.setItems(lstpo.toArray(new String[lstpo.size()]));
			txtPositionDesc.select(0);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBUtil.close(conn);
		}
	}
	
	@SuppressWarnings("deprecation")
	private void findEditMode() {
		Connection conn = null;
		try{
			conn = DBUtil.connect();
			TbTStaffDAO dao = new TbTStaffDAO();
			TbTStaff us = new TbTStaff();
			us.setId(staffid);
			dao.select(conn,us);
			lblNewLabel.setText("แก้ไขข้อมูลพนักงาน");
			txtTitleDesc.setText(us.getTitle().getTitleDescTh()==null?"":us.getTitle().getTitleDescTh());
			txtPositionDesc.setText(us.getPosition().getPosition_name()==null?"":us.getPosition().getPosition_name());
			txtFirstname.setText(us.getFirstName()==null?"":us.getFirstName());
			txtLastName.setText(us.getLastName()==null?"":us.getLastName());
			txtAddress.setText(us.getAddress()==null?"":us.getAddress());
			txtPhone.setText(us.getPhone()==null?"":us.getPhone());
			
			if(us.getJobType() != null && us.getJobType().equals("1")){
				chkFullTime.setSelection(true);
				//System.out.println(" chkFullTime : "+chkFullTime.getSelection());
			}else{
				chkPartTime.setSelection(true);
				//System.out.println(" chkPartTime : "+chkPartTime.getSelection());
			}
			
			if(us.getStatus() != null && us.getStatus().equals("1")){
				//System.out.println(" checked : checked ");
				chkStatus.setSelection(true);
			}else{
				//System.out.println(" unchecked : unchecked ");
				chkStatus.setSelection(false);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			errorMsg("Error: "+e.getMessage());
		}finally{
			DBUtil.close(conn);
		}
	}
	
	protected void setActionSubmit() {
		Connection conn = null;
		try {
			conn = DBUtil.connect();
			TbTStaff obj = new TbTStaff();
			TbCTitleDAO titleDAO = new TbCTitleDAO();
			TbTStaffDAO dao = new TbTStaffDAO();
			TbCTitle title = new TbCTitle();
			TbMPosition position = new TbMPosition();
			TbMPositionDAO poDAO = new TbMPositionDAO();
			
			//Validate Require Field
			if(txtFirstname.getText().equals("") || txtFirstname.getText() ==null){
				errorMsg("กรุณาระบุ ชื่อ");
				validReqFieldMode = true;
			}else if(txtLastName.getText().equals("") || txtLastName.getText() ==null){
				errorMsg("กรุณาระบุ นามสกุล");
				validReqFieldMode = true;
			}else if(txtAddress.getText().equals("") || txtAddress.getText() ==null){
				errorMsg("กรุณาระบุ ที่อยู่");
				validReqFieldMode = true;
			}else if(txtPhone.getText().equals("") || txtPhone.getText() ==null){
				errorMsg("กรุณาระบุ โทรศัพท์");
				validReqFieldMode = true;
			}else if(txtPositionDesc.getText().equals("") || txtPositionDesc.getText() ==null){
				errorMsg("กรุณาระบุ ตำแหน่งงาน");
				validReqFieldMode = true;
			}else if(!chkFullTime.getSelection() && !chkPartTime.getSelection()){
				errorMsg("กรุณาระบุ ประเภทงาน");
				validReqFieldMode = true;
			}else{
				validReqFieldMode = false;
			}
			
			System.out.println("validReqFieldMode : "+validReqFieldMode);
			// No have error about valid field
			if(!validReqFieldMode){
				
				title.setTitleDescTh(txtTitleDesc.getText());
				title.setId(titleDAO.findIdForNameTH(conn, txtTitleDesc.getText()));
				position.setPosition_name(txtPositionDesc.getText());
				position.setId(poDAO.findIdForNameTH(conn, txtPositionDesc.getText()));
				
				obj.setTitleId(title.getId());
				obj.setPositionId(position.getId());
				obj.setFirstName(txtFirstname.getText());
				obj.setLastName(txtLastName.getText());
				obj.setAddress(txtAddress.getText());
				obj.setPhone(txtPhone.getText());
				
				if(chkFullTime.getSelection()){
					// JobType : Full Time
					obj.setJobType("1");
				}else{
					// JobType : Part Time
					obj.setJobType("0");
				}
				
				if(chkStatus.getSelection()){
				     obj.setStatus("1");  // 1= Active 
				}else{
					 obj.setStatus("0");  // 0 = InActive
				}
	
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.US);
				java.util.Date date = new java.util.Date();
				
				if(editMode){
					
					obj.setId(staffid);
					obj.setUpdated_by(ApplicationMain.USERNAME);
					obj.setUpdated_date(dateFormat.format(date));
					dao.update(conn, obj);
					okMsg("แก้ไขข้อมูลเรียบร้อยแล้ว");
				}else{
					
					obj.setCreated_by(ApplicationMain.USERNAME);
					obj.setCreated_date(dateFormat.format(date));
					int id = dao.insert(conn, obj);
					obj.setId(id);
					staffid = id;
					editMode = false;
					okMsg("เพิ่มข้อมูลเรียบร้อยแล้ว");
				}
			
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
