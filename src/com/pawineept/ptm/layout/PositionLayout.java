package com.pawineept.ptm.layout;

import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import com.pawineept.ptm.app.ApplicationMain;
import com.pawineept.ptm.dao.TbMPositionDAO;
import com.pawineept.ptm.model.TbMPosition;
import com.pawineept.ptm.util.DBUtil;

public class PositionLayout extends Composite {
	private Text txtPosition;
	private Text txtRemark;
	private Button chkStatus;
	private Button btnSave;
	private Button btnClear;
	private Button btnSearch;
	private Label txtErrorMsg;
	public static Integer positionid;
	public static boolean editMode = false;
	private Label lblNewLabel;
	public static boolean validReqFieldMode = false;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public PositionLayout(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(6, false));
		
		lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 6, 1));
		lblNewLabel.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		lblNewLabel.setText("จัดการตำแหน่งงาน");
		
		Label lblNewLabel_1 = new Label(this, SWT.NONE);
		lblNewLabel_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_1.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		lblNewLabel_1.setText("ชื่อตำแหน่งงาน");
		
		txtPosition = new Text(this, SWT.BORDER);
		txtPosition.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		txtPosition.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 5, 1));
		
		Label lblNewLabel_2 = new Label(this, SWT.NONE);
		lblNewLabel_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_2.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		lblNewLabel_2.setText("หมายเหตุ");
		
		txtRemark = new Text(this, SWT.BORDER);
		txtRemark.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 5, 1));
		
		Label lblNewLabel_3 = new Label(this, SWT.NONE);
		lblNewLabel_3.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_3.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
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
		GridData gd_txtErrorMsg = new GridData(SWT.LEFT, SWT.CENTER, false, false, 5, 1);
		gd_txtErrorMsg.widthHint = 308;
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
				
				if(!PositionLayout.editMode){
					txtPosition.setText("");
					txtRemark.setText("");
					txtErrorMsg.setText("");
					chkStatus.setSelection(false);
					PositionLayout.editMode = false;
					positionid = null;
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
				PositionSearchLayout ps = new PositionSearchLayout(ApplicationMain.shell, SWT.NONE);
				ApplicationMain.composite = ps;
				ApplicationMain.openShell();
				PositionLayout.editMode = false;
			}
		});
		btnSearch.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		btnSearch.setText("ค้นหาตำแหน่งงาน");

		if(editMode){
			findEditMode();
		}else{
			lblNewLabel.setText("เพิ่มข้อมูลตำแหน่งงาน");
		}
		
	}
	
	@SuppressWarnings("deprecation")
	private void findEditMode() {
		Connection conn = null;
		try{
			conn = DBUtil.connect();
			TbMPosition us = new TbMPosition();
			TbMPositionDAO dao = new TbMPositionDAO();
			us.setId(positionid);
			dao.select(conn,us);
			lblNewLabel.setText("แก้ไขข้อมูลตำแหน่งงาน");

			txtPosition.setText(us.getPosition_name()==null?"":us.getPosition_name());
			txtRemark.setText(us.getRemark()==null?"":us.getRemark());
			
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
			TbMPosition obj = new TbMPosition();
			TbMPositionDAO dao = new TbMPositionDAO();
			
			//Validate Require Field
			if(txtPosition.getText().equals("") || txtPosition.getText() ==null){
				errorMsg("กรุณาระบุ ชื่อตำแหน่งงาน");
				validReqFieldMode = true;
			}else{
				validReqFieldMode = false;
			}
			
			System.out.println("validReqFieldMode : "+validReqFieldMode);
			// No have error about valid field
			if(!validReqFieldMode){
				
				obj.setPosition_name(txtPosition.getText());
				obj.setRemark(txtRemark.getText());
				
				if(chkStatus.getSelection()){
				     obj.setStatus("1");  // 1= Active 
				}else{
					 obj.setStatus("0");  // 0 = InActive
				}
	
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.US);
				java.util.Date date = new java.util.Date();
				
				if(editMode){
					
					obj.setId(positionid);
					obj.setUpdated_by(ApplicationMain.USERNAME);
					obj.setUpdated_date(dateFormat.format(date));
					dao.update(conn, obj);
					okMsg("แก้ไขข้อมูลเรียบร้อยแล้ว");
				}else{
					
					obj.setCreated_by(ApplicationMain.USERNAME);
					obj.setCreated_date(dateFormat.format(date));
					int id = dao.insert(conn, obj);
					obj.setId(id);
					positionid = id;
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
