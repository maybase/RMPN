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
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import com.pawineept.ptm.app.ApplicationMain;
import com.pawineept.ptm.dao.TbMBranchDAO;
import com.pawineept.ptm.model.TbMBranch;
import com.pawineept.ptm.util.DBUtil;

public class BranchLayout extends Composite {
	private Text txtBranchName;
	private Text txtAddress;
	private Text txtPhone;
	private Button chkStatus;
	public static Integer branchid;
	public static boolean editMode = false;
	private Label txtErrorMsg;
	private Label lblNewLabel;
	private Button btnClear;
	public static boolean validReqFieldMode = false;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public BranchLayout(Composite parent, int style) {
		super(parent, style);
		setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		setLayout(new GridLayout(7, false));
		
		lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 7, 1));
		lblNewLabel.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		lblNewLabel.setText("จัดการสาขา");
		
		Label lblNewLabel_1 = new Label(this, SWT.NONE);
		lblNewLabel_1.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		lblNewLabel_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_1.setText("ชื่อสาขา");
		
		txtBranchName = new Text(this, SWT.BORDER);
		txtBranchName.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		GridData gd_txtBranchName = new GridData(SWT.LEFT, SWT.CENTER, false, false, 5, 1);
		gd_txtBranchName.widthHint = 289;
		txtBranchName.setLayoutData(gd_txtBranchName);
		new Label(this, SWT.NONE);
		
		Label label = new Label(this, SWT.NONE);
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		label.setText("ที่อยู่");
		
		txtAddress = new Text(this, SWT.BORDER);
		txtAddress.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		GridData gd_txtAddress = new GridData(SWT.LEFT, SWT.FILL, false, false, 6, 1);
		gd_txtAddress.widthHint = 445;
		txtAddress.setLayoutData(gd_txtAddress);
		
		Label label_1 = new Label(this, SWT.NONE);
		label_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_1.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		label_1.setText("โทรศัพท์");
		
		txtPhone = new Text(this, SWT.BORDER);
		txtPhone.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		GridData gd_txtPhone = new GridData(SWT.LEFT, SWT.CENTER, false, false, 5, 1);
		gd_txtPhone.widthHint = 292;
		txtPhone.setLayoutData(gd_txtPhone);
		new Label(this, SWT.NONE);
		
		Label lblNewLabel_2 = new Label(this, SWT.NONE);
		lblNewLabel_2.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		lblNewLabel_2.setText("สถานะการใช้งาน");
		
		chkStatus = new Button(this, SWT.CHECK);
		chkStatus.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		chkStatus.setText("ใช้งาน");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		txtErrorMsg = new Label(this, SWT.NONE);
		txtErrorMsg.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		txtErrorMsg.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 6, 1));
		new Label(this, SWT.NONE);
		
		Button btnSave = new Button(this, SWT.NONE);
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
				
				if(!BranchLayout.editMode){
					txtBranchName.setText("");
					txtAddress.setText("");
					txtPhone.setText("");
					chkStatus.setSelection(false);
					BranchLayout.editMode = false;
					branchid = null;
					txtErrorMsg.setText("");
				}else{
					errorMsg("ไม่สามารถล้างข้อมูล สำหรับโหมดแก้ไขข้อมูลได้");
				}
			}
		});
		btnClear.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		btnClear.setText("ล้างข้อมูล");
		new Label(this, SWT.NONE);
		
		Button btnSearch = new Button(this, SWT.NONE);
		btnSearch.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ApplicationMain.closeShell();
				BranchSearchLayout bs = new BranchSearchLayout(ApplicationMain.shell, SWT.NONE);
				ApplicationMain.composite = bs;
				ApplicationMain.openShell();
				BranchLayout.editMode = false;
			}
		});
		btnSearch.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		btnSearch.setText("ค้นหาสาขา");
		new Label(this, SWT.NONE);
		
		if(editMode){
			findEditMode();
		}else{
			lblNewLabel.setText("เพิ่มข้อมูลสาขา");
		}

	}
	
	@SuppressWarnings("deprecation")
	private void findEditMode() {
		Connection conn = null;
		try{
			conn = DBUtil.connect();
			TbMBranchDAO dao = new TbMBranchDAO();
			TbMBranch br = new TbMBranch();
			br.setId(branchid);
			dao.select(conn,br);
			lblNewLabel.setText("แก้ไขข้อมูลสาขา");
			txtBranchName.setText(br.getBranchName()==null?"":br.getBranchName());
			txtAddress.setText(br.getBranchAddress()==null?"":br.getBranchAddress());
			txtPhone.setText(br.getBranchPhone()==null?"":br.getBranchPhone());
			
			if(br.getStatus() != null && br.getStatus().equals("1")){
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
			TbMBranch obj = new TbMBranch();
			TbMBranchDAO dao = new TbMBranchDAO();
			
			//Validate Field
			if(txtBranchName.getText().equals("") || txtBranchName.getText() ==null){
				errorMsg("กรุณาระบุ ชื่อสาขา");
				validReqFieldMode = true;
			}else if(txtAddress.getText().equals("") || txtAddress.getText() ==null){
				errorMsg("กรุณาระบุ ที่อยู่");
				validReqFieldMode = true;
			}else if(txtPhone.getText().equals("") || txtPhone.getText() ==null){
				errorMsg("กรุณาระบุ โทรศัพท์");
				validReqFieldMode = true;
			}else{
				validReqFieldMode = false;
			}

			System.out.println("validReqFieldMode : "+validReqFieldMode);
			// No have error about valid field
			if(!validReqFieldMode){
				obj.setBranchName(txtBranchName.getText());
				obj.setBranchAddress(txtAddress.getText());
				obj.setBranchPhone(txtPhone.getText());
				
				if(chkStatus.getSelection()){
				     obj.setStatus("1");  // 1= Active 
				}else{
					 obj.setStatus("0");  // 0 = InActive
				}
	
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.US);
				java.util.Date date = new java.util.Date();
				
				if(editMode){
					
					obj.setId(branchid);
					obj.setUpdated_by(ApplicationMain.USERNAME);
					obj.setUpdated_date(dateFormat.format(date));
					dao.update(conn, obj);
					okMsg("แก้ไขข้อมูลเรียบร้อยแล้ว");
				}else{
					
					obj.setCreated_by(ApplicationMain.USERNAME);
					obj.setCreated_date(dateFormat.format(date));
					int id = dao.insert(conn, obj);
					obj.setId(id);
					branchid = id;
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
