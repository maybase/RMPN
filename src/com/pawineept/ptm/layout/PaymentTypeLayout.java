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
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import com.pawineept.ptm.app.ApplicationMain;
import com.pawineept.ptm.dao.TbMMedicalGroupDAO;
import com.pawineept.ptm.dao.TbMPaymentTypeDAO;
import com.pawineept.ptm.model.TbMMedicalGroup;
import com.pawineept.ptm.model.TbMPaymentType;
import com.pawineept.ptm.util.DBUtil;

public class PaymentTypeLayout extends Composite {
	private Combo txtMedGroupDesc;
	private Text txtPayType;
	private Button chkStatus;
	private Button btnSave;
	private Button btnClear;
	private Button btnSearch;
	private Button btnOwnerPa;
	private Button btnOwnerOffice;
	private Label txtErrorMsg;
	public static Integer paymenttypeid;
	public static boolean editMode = false;
	private Label lblNewLabel;
	public static boolean validReqFieldMode = false;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public PaymentTypeLayout(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(6, false));
		
		lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 6, 1));
		lblNewLabel.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		lblNewLabel.setText("จัดการประเภทการชำระเงิน");
		
		Label lblNewLabel_1 = new Label(this, SWT.NONE);
		lblNewLabel_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_1.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		lblNewLabel_1.setText("กลุ่มเวชระเบียน");
		
		txtMedGroupDesc = new Combo(this, SWT.NONE);
		GridData gd_txtMedGroupDesc = new GridData(SWT.LEFT, SWT.CENTER, false, false, 5, 1);
		gd_txtMedGroupDesc.widthHint = 288;
		txtMedGroupDesc.setLayoutData(gd_txtMedGroupDesc);
		txtMedGroupDesc.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		
		Label lblNewLabel_2 = new Label(this, SWT.NONE);
		lblNewLabel_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_2.setText("ประเภทของการชำระเงิน");
		lblNewLabel_2.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		
		txtPayType = new Text(this, SWT.BORDER);
		GridData gd_txtPayType = new GridData(SWT.LEFT, SWT.CENTER, false, false, 5, 1);
		gd_txtPayType.widthHint = 319;
		txtPayType.setLayoutData(gd_txtPayType);
		txtPayType.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		
		Label lblNewLabel_3 = new Label(this, SWT.NONE);
		lblNewLabel_3.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_3.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		lblNewLabel_3.setText("ผู้เป็นเจ้าของ");
		
		btnOwnerPa = new Button(this, SWT.RADIO);
		btnOwnerPa.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		btnOwnerPa.setText("ภา");
		
		btnOwnerOffice = new Button(this, SWT.RADIO);
		btnOwnerOffice.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		btnOwnerOffice.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		btnOwnerOffice.setText("บริษัท");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
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
		GridData gd_txtErrorMsg = new GridData(SWT.LEFT, SWT.CENTER, false, false, 5, 1);
		gd_txtErrorMsg.widthHint = 330;
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
				
				if(!PaymentTypeLayout.editMode){
					txtMedGroupDesc.select(0);
					txtPayType.setText("");
					btnOwnerOffice.setSelection(false);
					btnOwnerPa.setSelection(false);
					chkStatus.setSelection(false);
					txtErrorMsg.setText("");
					PaymentTypeLayout.editMode = false;
					paymenttypeid = null;
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
				PaymentTypeSearchLayout pts = new PaymentTypeSearchLayout(ApplicationMain.shell, SWT.NONE);
				ApplicationMain.composite = pts;
				ApplicationMain.openShell();
				PaymentTypeLayout.editMode = false;
				//btnOwnerOffice.setSelection(false);
				//btnOwnerPa.setSelection(false);
			}
		});
		btnSearch.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		btnSearch.setText("ค้นหาประเภทชำระเงิน");
		
		initTitleDesc();
		if(editMode){
			findEditMode();
		}else{
			lblNewLabel.setText("เพิ่มข้อมูลประเภทการชำระเงิน");
		}

	}
	
	private void initTitleDesc() {
		Connection conn = null;
		try{
			conn = DBUtil.connect();
			TbMMedicalGroupDAO dao = new TbMMedicalGroupDAO();
			List<String> lst = dao.findActiveList(conn);
			txtMedGroupDesc.setItems(lst.toArray(new String[lst.size()]));
			txtMedGroupDesc.select(0);
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
			TbMPaymentTypeDAO dao = new TbMPaymentTypeDAO();
			TbMPaymentType us = new TbMPaymentType();
			us.setId(paymenttypeid);
			dao.select(conn,us);
			lblNewLabel.setText("แก้ไขข้อมูลประเภทการชำระเงิน");
			txtMedGroupDesc.setText(us.getMedical_group_name()==null?"":us.getMedical_group_name());
			txtPayType.setText(us.getPay_type_name()==null?"":us.getPay_type_name());
			
			System.out.println(" owner : "+us.getOwner());
			if(us.getOwner() != null && us.getOwner().equals("1")){
				btnOwnerPa.setSelection(true);
				//System.out.println(" btnOwnerPa : "+btnOwnerPa.getSelection());
			}else{
				btnOwnerOffice.setSelection(true);
				//System.out.println(" btnOwnerOffice : "+btnOwnerOffice.getSelection());
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
			TbMPaymentType obj = new TbMPaymentType();
			TbMPaymentTypeDAO dao = new TbMPaymentTypeDAO();
			TbMMedicalGroupDAO medDao = new TbMMedicalGroupDAO();
			TbMMedicalGroup med = new TbMMedicalGroup();
			
			//Validate Require Field
			if(txtPayType.getText().equals("") || txtPayType.getText() ==null){
				errorMsg("กรุณาระบุ ประเภทของการชำระเงิน");
				validReqFieldMode = true;
			}else if(!btnOwnerPa.getSelection() && !btnOwnerOffice.getSelection()){
				errorMsg("กรุณาระบุ ผู้เป็นเจ้าของ");
				validReqFieldMode = true;
			}else{
				validReqFieldMode = false;
			}
			
			System.out.println("validReqFieldMode : "+validReqFieldMode);
			System.out.println("btnOwnerPa.getSelection() : "+btnOwnerPa.getSelection());
			System.out.println("btnOwnerOffice.getSelection() : "+btnOwnerOffice.getSelection());
			// No have error about valid field
			if(!validReqFieldMode){
				med.setMedical_group_name(txtMedGroupDesc.getText());
				med.setId(medDao.findIdForNameTH(conn, txtMedGroupDesc.getText()));
				
				obj.setMedical_group_id(med.getId());
				obj.setPay_type_name(txtPayType.getText());
				
				if(btnOwnerPa.getSelection()){
					// Owner Pa
					obj.setOwner("1");
				}else{
					// Owner Office
					obj.setOwner("0");
				}
				
				if(chkStatus.getSelection()){
				     obj.setStatus("1");  // 1= Active 
				}else{
					 obj.setStatus("0");  // 0 = InActive
				}
	
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.US);
				java.util.Date date = new java.util.Date();
				
				if(editMode){
					
					obj.setId(paymenttypeid);
					obj.setUpdated_by(ApplicationMain.USERNAME);
					obj.setUpdated_date(dateFormat.format(date));
					dao.update(conn, obj);
					okMsg("แก้ไขข้อมูลเรียบร้อยแล้ว");
				}else{
					
					obj.setCreated_by(ApplicationMain.USERNAME);
					obj.setCreated_date(dateFormat.format(date));
					int id = dao.insert(conn, obj);
					obj.setId(id);
					paymenttypeid = id;
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
