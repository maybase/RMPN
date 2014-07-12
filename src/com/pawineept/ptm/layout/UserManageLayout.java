package com.pawineept.ptm.layout;

import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.internal.win32.POINT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;

import com.pawineept.ptm.app.ApplicationMain;
import com.pawineept.ptm.dao.TbCTitleDAO;
import com.pawineept.ptm.dao.TbMUserDAO;
import com.pawineept.ptm.model.TbCTitle;
import com.pawineept.ptm.model.TbMUser;
import com.pawineept.ptm.util.DBUtil;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class UserManageLayout extends Composite {
	private Combo txtTitleDesc;
	private Text txtUser;
	private Text txtPassword;
	private Text txtFirstName;
	private Text txtLastName;
	private Label txtErrorMsg;
	//private Integer userid;
	private Button chkStatus;
	public static Integer userid;
	public static boolean editMode = false;
	private Label lblNewLabel;
	private Button btnNewButton_1;
	private Button btnClear;
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public UserManageLayout(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(9, false));
		
		lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 9, 1));
		lblNewLabel.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		lblNewLabel.setText("จัดการผู้ใช้งาน");
		
		Label lblNewLabel_1 = new Label(this, SWT.NONE);
		lblNewLabel_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_1.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		lblNewLabel_1.setText("คำนำหน้า");
		
		txtTitleDesc = new Combo(this, SWT.NONE);
		txtTitleDesc.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		GridData gd_txtTitleDesc = new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1);
		gd_txtTitleDesc.widthHint = 93;
		txtTitleDesc.setLayoutData(gd_txtTitleDesc);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Label lblNewLabel_2 = new Label(this, SWT.NONE);
		lblNewLabel_2.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		lblNewLabel_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_2.setText("User");
		
		txtUser = new Text(this, SWT.BORDER);
		txtUser.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		GridData gd_txtUser = new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1);
		gd_txtUser.widthHint = 138;
		txtUser.setLayoutData(gd_txtUser);
		new Label(this, SWT.NONE);
		
		Label lblNewLabel_3 = new Label(this, SWT.NONE);
		lblNewLabel_3.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		lblNewLabel_3.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_3.setText("Password");
		
		txtPassword = new Text(this, SWT.BORDER);
		txtPassword.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		txtPassword.setText("");
		GridData gd_txtPassword = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_txtPassword.widthHint = 128;
		txtPassword.setLayoutData(gd_txtPassword);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Label lblFirstname = new Label(this, SWT.NONE);
		lblFirstname.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		lblFirstname.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblFirstname.setText("ชื่อ");
		
		txtFirstName = new Text(this, SWT.BORDER);
		txtFirstName.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		GridData gd_txtFirstName = new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1);
		gd_txtFirstName.widthHint = 134;
		txtFirstName.setLayoutData(gd_txtFirstName);
		new Label(this, SWT.NONE);
		
		Label lblNewLabel_4 = new Label(this, SWT.NONE);
		lblNewLabel_4.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		lblNewLabel_4.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_4.setText("นามสกุล");
		
		txtLastName = new Text(this, SWT.BORDER);
		txtLastName.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		txtLastName.setText("");
		txtLastName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Label lblNewLabel_5 = new Label(this, SWT.NONE);
		lblNewLabel_5.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		lblNewLabel_5.setText("สถานะการใช้งาน");
		
		chkStatus = new Button(this, SWT.CHECK);
		chkStatus.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		chkStatus.setText("ใช้งาน");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		txtErrorMsg = new Label(this, SWT.NONE);
		txtErrorMsg.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		txtErrorMsg.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 8, 1));
		new Label(this, SWT.NONE);
		
		Button btnNewButton = new Button(this, SWT.NONE);
		btnNewButton.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				setActionSubmit();
			}
		});
		btnNewButton.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		btnNewButton.setText("บันทึกข้อมูล");
		new Label(this, SWT.NONE);
		
		btnClear = new Button(this, SWT.NONE);
		btnClear.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		btnClear.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				txtUser.setText("");
				txtPassword.setText("");
				txtFirstName.setText("");
				txtLastName.setText("");
				chkStatus.setSelection(false);
				UserManageLayout.editMode = false;
				userid = null;
				txtErrorMsg.setText("");
				txtTitleDesc.select(0);
			}
		});
		btnClear.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		btnClear.setText("ล้างข้อมูล");
		new Label(this, SWT.NONE);
		
		btnNewButton_1 = new Button(this, SWT.NONE);
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ApplicationMain.closeShell();
				UserManageSearchLayout uss = new UserManageSearchLayout(ApplicationMain.shell,SWT.NONE);
				ApplicationMain.composite = uss;
				ApplicationMain.openShell();
				UserManageLayout.editMode = false;
			}
		});
		btnNewButton_1.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		btnNewButton_1.setText("ค้นหาผู้ใช้งาน");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		initTitleDesc();
		if(editMode){
			findEditMode();
		}else{
			lblNewLabel.setText("เพิ่มข้อมูลผู้ใช้งาน");
		}
		
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
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
		}finally{
			DBUtil.close(conn);
		}
	}
	
	@SuppressWarnings("deprecation")
	private void findEditMode() {
		Connection conn = null;
		try{
			conn = DBUtil.connect();
			TbMUserDAO dao = new TbMUserDAO();
			TbMUser us = new TbMUser();
			us.setId(userid);
			dao.select(conn,us);
			lblNewLabel.setText("แก้ไขข้อมูลผู้ใช้งาน");
			txtTitleDesc.setText(us.getTitle().getTitleDescTh()==null?"":us.getTitle().getTitleDescTh());
			txtUser.setText(us.getUser()==null?"":us.getUser());
			txtPassword.setText(us.getPwd()==null?"":us.getPwd());
			txtFirstName.setText(us.getFirst_name()==null?"":us.getFirst_name());
			txtLastName.setText(us.getLast_name()==null?"":us.getLast_name());
			
			if(us.getStatus().equals("1")){
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
			TbMUser obj = new TbMUser();
			TbCTitleDAO titleDAO = new TbCTitleDAO();
			TbMUserDAO dao = new TbMUserDAO();
			TbCTitle title = new TbCTitle();
			title.setTitleDescTh(txtTitleDesc.getText());
			title.setTitleid(titleDAO.findIdForNameTH(conn, txtTitleDesc.getText()));
			
			obj.setPrefixId(title.getTitleid());
			obj.setUser(txtUser.getText());
			obj.setPwd(txtPassword.getText());
			obj.setFirst_name(txtFirstName.getText());
			obj.setLast_name(txtLastName.getText());
			obj.setLast_active(null); // !!! Change Login Again after finish logic login => when login then it will auto update "date" into this field
			
			if(chkStatus.getSelection()){
			     obj.setStatus("1");  // 1= Active 
			}else{
				 obj.setStatus("0");  // 0 = InActive
			}

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.US);
			java.util.Date date = new java.util.Date();
			
			if(editMode){
				
				obj.setId(userid);
				obj.setUpdated_by(ApplicationMain.USERNAME);
				obj.setUpdated_date(dateFormat.format(date));
				dao.update(conn, obj);
				okMsg("แก้ไขข้อมูลเรียบร้อยแล้ว");
			}else{
				
				obj.setCreated_by(ApplicationMain.USERNAME);
				obj.setCreated_date(dateFormat.format(date));
				int id = dao.insert(conn, obj);
				obj.setId(id);
				userid = id;
				editMode = true;
				okMsg("เพิ่มข้อมูลเรียบร้อยแล้ว");
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


}
