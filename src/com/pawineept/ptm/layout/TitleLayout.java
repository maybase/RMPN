package com.pawineept.ptm.layout;

import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import com.pawineept.ptm.app.ApplicationMain;
import com.pawineept.ptm.dao.TbCTitleDAO;
import com.pawineept.ptm.model.TbCTitle;
import com.pawineept.ptm.util.DBUtil;

public class TitleLayout extends Composite {
	private Text txtTitleTH;
	private Text txtTitleEN;
	private Button chkStatus;
	private Button btnSave;
	private Button btnClear;
	private Button btnSearch;
	private Label txtErrorMsg;
	public static Integer titleid;
	public static boolean editMode = false;
	private Label lblNewLabel;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public TitleLayout(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(6, false));
		
		lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1));
		lblNewLabel.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		lblNewLabel.setText("จัดการคำนำหน้า");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Label lblNewLabel_1 = new Label(this, SWT.NONE);
		lblNewLabel_1.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		lblNewLabel_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_1.setText("คำนำหน้า แบบไทย");
		
		txtTitleTH = new Text(this, SWT.BORDER);
		GridData gd_txtTitleTH = new GridData(SWT.FILL, SWT.CENTER, false, false, 5, 1);
		gd_txtTitleTH.widthHint = 259;
		txtTitleTH.setLayoutData(gd_txtTitleTH);
		txtTitleTH.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		
		Label lblNewLabel_2 = new Label(this, SWT.NONE);
		lblNewLabel_2.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		lblNewLabel_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_2.setText("คำนำหน้า แบบอังกฤษ");
		
		txtTitleEN = new Text(this, SWT.BORDER);
		txtTitleEN.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		GridData gd_txtTitleEN = new GridData(SWT.FILL, SWT.CENTER, false, false, 5, 1);
		gd_txtTitleEN.widthHint = 251;
		txtTitleEN.setLayoutData(gd_txtTitleEN);
		
		Label lblNewLabel_3 = new Label(this, SWT.NONE);
		lblNewLabel_3.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		lblNewLabel_3.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_3.setText("สถานะการใช้งาน");
		
		chkStatus = new Button(this, SWT.CHECK);
		chkStatus.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		chkStatus.setText("ใช้งาน");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		txtErrorMsg = new Label(this, SWT.NONE);
		GridData gd_txtErrorMsg = new GridData(SWT.FILL, SWT.CENTER, false, false, 5, 1);
		gd_txtErrorMsg.widthHint = 295;
		txtErrorMsg.setLayoutData(gd_txtErrorMsg);
		txtErrorMsg.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
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
		btnClear.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				txtTitleTH.setText("");
				txtTitleEN.setText("");
				chkStatus.setSelection(false);
				TitleLayout.editMode = false;
				titleid = null;
				txtErrorMsg.setText("");
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
				TitleSearchLayout ts = new TitleSearchLayout(ApplicationMain.shell,SWT.NONE);
				ApplicationMain.composite = ts;
				ApplicationMain.openShell();
				TitleLayout.editMode = false;
			}
		});
		btnSearch.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		btnSearch.setText("ค้นหาคำนำหน้า");
		
		if(editMode){
			
			findEditMode();
		}else{
			lblNewLabel.setText("เพิ่มข้อมูลคำนำหน้า");
			
		}

	}
	
	@SuppressWarnings("deprecation")
	private void findEditMode() {
		Connection conn = null;
		try{
			conn = DBUtil.connect();
			TbCTitleDAO dao = new TbCTitleDAO();
			TbCTitle tic = new TbCTitle();
			tic.setId(titleid);
			dao.select(conn,tic);
			lblNewLabel.setText("แก้ไขข้อมูลคำนำหน้า");
			txtTitleTH.setText(tic.getTitleDescTh()==null?"":tic.getTitleDescTh());
			txtTitleEN.setText(tic.getTitleDescEn()==null?"":tic.getTitleDescEn());
			
			if(tic.getStatus() !=null && tic.getStatus().equals("1")){
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
			TbCTitle obj = new TbCTitle();
			TbCTitleDAO dao = new TbCTitleDAO();
			
			obj.setTitleDescTh(txtTitleTH.getText());
			obj.setTitleDescEn(txtTitleEN.getText());
			
			if(chkStatus.getSelection()){
			     obj.setStatus("1");  // 1= Active 
			}else{
				 obj.setStatus("0");  // 0 = InActive
			}

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.US);
			java.util.Date date = new java.util.Date();
			
			if(editMode){
				
				obj.setId(titleid);
				obj.setUpdated_by(ApplicationMain.USERNAME);
				obj.setUpdated_date(dateFormat.format(date));
				dao.update(conn, obj);
				okMsg("แก้ไขข้อมูลเรียบร้อยแล้ว");
			}else{
				
				obj.setCreated_by(ApplicationMain.USERNAME);
				obj.setCreated_date(dateFormat.format(date));
				int id = dao.insert(conn, obj);
				obj.setId(id);
				titleid = id;
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


	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
