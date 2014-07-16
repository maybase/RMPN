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
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import com.pawineept.ptm.app.ApplicationMain;
import com.pawineept.ptm.dao.TbMBranchDAO;
import com.pawineept.ptm.dao.TbMMedicalGroupDAO;
import com.pawineept.ptm.model.TbMBranch;
import com.pawineept.ptm.model.TbMMedicalGroup;
import com.pawineept.ptm.util.DBUtil;

public class MedicalRecordGroupLayout extends Composite {
	private Text txtGroup;
	private Combo txtBranchDesc;
	private Text txtPrefix;
	private Button chkStatus;
	private Label txtErrorMsg;
	public static Integer medicalgroupid;
	public static boolean editMode = false;
	private Label lblNewLabel;
	private Button btnClear;
	public static boolean validReqFieldMode = false;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public MedicalRecordGroupLayout(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(6, false));
		
		lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 6, 1));
		lblNewLabel.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		lblNewLabel.setText("จัดการกลุ่มเวชระเบียน");
		
		Label lblNewLabel_1 = new Label(this, SWT.NONE);
		lblNewLabel_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_1.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		lblNewLabel_1.setText("สาขา");
		
		txtBranchDesc = new Combo(this, SWT.NONE);
		GridData gd_txtBranchDesc = new GridData(SWT.LEFT, SWT.CENTER, false, false, 5, 1);
		gd_txtBranchDesc.widthHint = 303;
		txtBranchDesc.setLayoutData(gd_txtBranchDesc);
		
		Label lblNewLabel_2 = new Label(this, SWT.NONE);
		lblNewLabel_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_2.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		lblNewLabel_2.setText("ชื่อกลุ่ม");
		
		txtGroup = new Text(this, SWT.BORDER);
		txtGroup.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		GridData gd_txtGroup = new GridData(SWT.LEFT, SWT.CENTER, false, false, 5, 1);
		gd_txtGroup.widthHint = 385;
		txtGroup.setLayoutData(gd_txtGroup);
		
		Label lblNewLabel_3 = new Label(this, SWT.NONE);
		lblNewLabel_3.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		lblNewLabel_3.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_3.setText("รหัสคำนำหน้า");
		
		txtPrefix = new Text(this, SWT.BORDER);
		GridData gd_txtPrefix = new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1);
		gd_txtPrefix.widthHint = 154;
		txtPrefix.setLayoutData(gd_txtPrefix);
		
		Label lblNewLabel_5 = new Label(this, SWT.NONE);
		lblNewLabel_5.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		lblNewLabel_5.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		lblNewLabel_5.setText("( ระบุเฉพาะกรณีต้องการเพิ่มรหัสคนไข้แบบใหม่ )");
		
		Label lblNewLabel_4 = new Label(this, SWT.NONE);
		lblNewLabel_4.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_4.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		lblNewLabel_4.setText("สถานะการใช้งาน");
		
		chkStatus = new Button(this, SWT.CHECK);
		chkStatus.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		chkStatus.setText("ใช้งาน");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		txtErrorMsg = new Label(this, SWT.NONE);
		txtErrorMsg.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		GridData gd_txtErrorMsg = new GridData(SWT.LEFT, SWT.CENTER, false, false, 5, 1);
		gd_txtErrorMsg.widthHint = 396;
		txtErrorMsg.setLayoutData(gd_txtErrorMsg);
		new Label(this, SWT.NONE);
		
		Button btnNewButton = new Button(this, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				setActionSubmit();
			}
		});
		btnNewButton.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		btnNewButton.setText("บันทึกข้อมูล");
		new Label(this, SWT.NONE);
		
		Button btnNewButton_1 = new Button(this, SWT.NONE);
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				if(!MedicalRecordGroupLayout.editMode){
					txtBranchDesc.select(0);
					txtGroup.setText("");
					txtPrefix.setText("");
					chkStatus.setSelection(false);
					MedicalRecordGroupLayout.editMode = false;
					medicalgroupid = null;
					txtErrorMsg.setText("");
				}else{
					errorMsg("ไม่สามารถล้างข้อมูล สำหรับโหมดแก้ไขข้อมูลได้");
				}
			}
		});
		btnNewButton_1.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		btnNewButton_1.setText("ล้างข้อมูล");
		new Label(this, SWT.NONE);
		
		Button btnNewButton_2 = new Button(this, SWT.NONE);
		btnNewButton_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ApplicationMain.closeShell();
				MedicalRecordGroupSearchLayout mgs = new MedicalRecordGroupSearchLayout(ApplicationMain.shell, SWT.NONE);
				ApplicationMain.composite = mgs;
				ApplicationMain.openShell();
				MedicalRecordGroupLayout.editMode = false;
			}
		});
		btnNewButton_2.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		btnNewButton_2.setText("ค้นหากลุ่มเวชระเบียน");
		
		initBranchDesc();
		if(editMode){
			findEditMode();
		}else{
			lblNewLabel.setText("เพิ่มข้อมูลกลุ่มเวชระเบียน");
		}

	}
	
	private void initBranchDesc() {
		Connection conn = null;
		try{
			conn = DBUtil.connect();
			TbMBranchDAO dao = new TbMBranchDAO();
			List<String> lst = dao.findActiveList(conn);
			txtBranchDesc.setItems(lst.toArray(new String[lst.size()]));
			txtBranchDesc.select(0);
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
			TbMMedicalGroupDAO dao = new TbMMedicalGroupDAO();
			TbMMedicalGroup md = new TbMMedicalGroup();
			md.setId(medicalgroupid);
			dao.select(conn,md);
			lblNewLabel.setText("แก้ไขข้อมูลกลุ่มเวชระเบียน");
			txtBranchDesc.setText(md.getBranch_name()==null?"":md.getBranch_name());
			txtGroup.setText(md.getMedical_group_name()==null?"":md.getMedical_group_name());
			txtPrefix.setText(md.getPrefix()==null?"":md.getPrefix());
			
			if(md.getStatus() != null && md.getStatus().equals("1")){
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
			TbMMedicalGroup obj = new TbMMedicalGroup();
			TbMMedicalGroupDAO dao = new TbMMedicalGroupDAO();
			TbMBranchDAO branchdao = new TbMBranchDAO();
			TbMBranch branch = new TbMBranch();
			
			//Validate Require Field
			if(txtGroup.getText().equals("") || txtGroup.getText() ==null){
				errorMsg("กรุณาระบุ ชื่อกลุ่ม");
				validReqFieldMode = true;
			}else{
				validReqFieldMode = false;
			}
			
			System.out.println("validReqFieldMode : "+validReqFieldMode);
			// No have error about valid field
			if(!validReqFieldMode){
				branch.setBranchName(txtBranchDesc.getText());
				branch.setId(branchdao.findIdForNameTH(conn, txtBranchDesc.getText()));
				
				obj.setBranch_name(branch.getBranchName() == null ? "" : branch.getBranchName());
				obj.setBranch_id(branch.getId());
				obj.setMedical_group_name(txtGroup.getText());
				obj.setPrefix(txtPrefix.getText());
				
				if(chkStatus.getSelection()){
				     obj.setStatus("1");  // 1= Active 
				}else{
					 obj.setStatus("0");  // 0 = InActive
				}
	
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.US);
				java.util.Date date = new java.util.Date();
				
				if(editMode){
					
					obj.setId(medicalgroupid);
					obj.setUpdated_by(ApplicationMain.USERNAME);
					obj.setUpdated_date(dateFormat.format(date));
					dao.update(conn, obj);
					okMsg("แก้ไขข้อมูลเรียบร้อยแล้ว");
				}else{
					
					obj.setCreated_by(ApplicationMain.USERNAME);
					obj.setCreated_date(dateFormat.format(date));
					int id = dao.insert(conn, obj);
					obj.setId(id);
					medicalgroupid = id;
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
