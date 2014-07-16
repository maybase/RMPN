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
import com.pawineept.ptm.dao.TbMPaymentDetailTypeDAO;
import com.pawineept.ptm.dao.TbMPaymentTypeDAO;
import com.pawineept.ptm.model.TbCTitle;
import com.pawineept.ptm.model.TbMPaymentDetailType;
import com.pawineept.ptm.model.TbMPaymentType;
import com.pawineept.ptm.util.DBUtil;

public class PaymentDetailTypeLayout extends Composite {
	private Combo txtPayTypeDesc;
	private Text txtPayDetailType;
	private Text txtTotalNum;
	private Text txtCost;
	private Button btnSave;
	private Button btnClear;
	private Label txtErrorMsg;
	public static Integer payDetailTypeid;
	public static boolean editMode = false;
	private Label lblNewLabel;
	private Button chkStatus;
	public static boolean validReqFieldMode = false;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public PaymentDetailTypeLayout(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(6, false));
		
		lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		lblNewLabel.setText("จัดการรายละเอียดประเภทการชำระเงิน");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Label lblNewLabel_1 = new Label(this, SWT.NONE);
		lblNewLabel_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_1.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		lblNewLabel_1.setText("ประเภทการชำระเงิน");
		
		txtPayTypeDesc = new Combo(this, SWT.NONE);
		txtPayTypeDesc.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		txtPayTypeDesc.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 5, 1));
		
		Label lblNewLabel_2 = new Label(this, SWT.NONE);
		lblNewLabel_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_2.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		lblNewLabel_2.setText("รายละเอียดประเภทการชำระเงิน");
		
		txtPayDetailType = new Text(this, SWT.BORDER);
		txtPayDetailType.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		txtPayDetailType.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 5, 1));
		
		Label lblNewLabel_3 = new Label(this, SWT.NONE);
		lblNewLabel_3.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_3.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		lblNewLabel_3.setText("จำนวนครั้ง หรือ ชิ้น");
		
		txtTotalNum = new Text(this, SWT.BORDER);
		txtTotalNum.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		txtTotalNum.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 4, 1));
		new Label(this, SWT.NONE);
		
		Label lblNewLabel_5 = new Label(this, SWT.NONE);
		lblNewLabel_5.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_5.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		lblNewLabel_5.setText("ราคา");
		
		txtCost = new Text(this, SWT.BORDER);
		txtCost.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		txtCost.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 4, 1));
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
		gd_txtErrorMsg.widthHint = 397;
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
				
				if(!PaymentDetailTypeLayout.editMode){
					txtPayTypeDesc.select(0);
					txtPayDetailType.setText("");
					txtTotalNum.setText("");
					txtCost.setText("");
					chkStatus.setSelection(false);
					PaymentDetailTypeLayout.editMode = false;
					validReqFieldMode = false;
					payDetailTypeid = null;
					txtErrorMsg.setText("");
				}else{
					errorMsg("ไม่สามารถล้างข้อมูล สำหรับโหมดแก้ไขข้อมูลได้");
				}
			}
		});
		btnClear.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		btnClear.setText("ล้างข้อมูล");
		new Label(this, SWT.NONE);
		
		Button btnNewButton = new Button(this, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ApplicationMain.closeShell();
				PaymentDetailTypeSearchLayout pts = new PaymentDetailTypeSearchLayout(ApplicationMain.shell, SWT.NONE);
				ApplicationMain.composite = pts;
				ApplicationMain.openShell();
				PaymentDetailTypeLayout.editMode = false;
				validReqFieldMode = false;
			}
		});
		btnNewButton.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		btnNewButton.setText("ค้นหารายละเอียดประเภทชำระเงิน");
		
		initTitleDesc();
		if(editMode){
			findEditMode();
		}else{
			lblNewLabel.setText("เพิ่มข้อมูลรายละเอียดประเภทการชำระเงิน");
		}

	}
	
	private void initTitleDesc() {
		Connection conn = null;
		try{
			conn = DBUtil.connect();
			TbMPaymentTypeDAO dao = new TbMPaymentTypeDAO();
			List<String> lst = dao.findActiveList(conn);
			txtPayTypeDesc.setItems(lst.toArray(new String[lst.size()]));
			txtPayTypeDesc.select(0);
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
			TbMPaymentDetailTypeDAO dao = new TbMPaymentDetailTypeDAO();
			TbMPaymentDetailType us = new TbMPaymentDetailType();
			us.setId(payDetailTypeid);
			dao.select(conn,us);
			lblNewLabel.setText("แก้ไขข้อมูลรายละเอียดประเภทการชำระเงิน");
			txtPayTypeDesc.setText(us.getPay_type_name()==null?"":us.getPay_type_name());
			txtPayDetailType.setText(us.getPay_detail_type_name()==null?"":us.getPay_detail_type_name());
			txtTotalNum.setText(us.getTotal_num().toString()==null?"":us.getTotal_num().toString());
			txtCost.setText(us.getCost().toString()==null?"":us.getCost().toString());
			
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
			TbMPaymentDetailType obj = new TbMPaymentDetailType();
			TbMPaymentTypeDAO payDAO = new TbMPaymentTypeDAO();
			TbMPaymentDetailTypeDAO dao = new TbMPaymentDetailTypeDAO();
			TbMPaymentType paytype = new TbMPaymentType();
			
			//Validate Field
			if(txtPayDetailType.getText().equals("") || txtPayDetailType.getText() == null){
				errorMsg("กรุณาระบุ รายละเอียดประเภทการชำระเงิน");
				validReqFieldMode = true;
			}else if(txtTotalNum.getText().equals("") || txtTotalNum.getText() == null){
				errorMsg("กรุณาระบุ จำนวนครั้ง หรือ ชิ้น");
				validReqFieldMode = true;
			}else if(txtCost.getText().equals("") || txtCost.getText() == null){
				errorMsg("กรุณาระบุ ราคา");
				validReqFieldMode = true;
			}else{
				validReqFieldMode = false;
			}
			
			System.out.println("validReqFieldMode : "+validReqFieldMode);
			// No have error about valid field
			if(!validReqFieldMode){
			
				// Set up field before insert / update DB
				paytype.setPay_type_name(txtPayTypeDesc.getText());
				paytype.setId(payDAO.findIdForNameTH(conn, txtPayTypeDesc.getText()));
				
				obj.setPay_type_id(paytype.getId());
				obj.setPay_detail_type_name(txtPayDetailType.getText());
				obj.setTotal_num(Integer.parseInt(txtTotalNum.getText()));
				obj.setCost(Integer.parseInt(txtCost.getText()));
				
				if(chkStatus.getSelection()){
				     obj.setStatus("1");  // 1= Active 
				}else{
					 obj.setStatus("0");  // 0 = InActive
				}
	
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.US);
				java.util.Date date = new java.util.Date();
				
				
				if(editMode){
					
					obj.setId(payDetailTypeid);
					obj.setUpdated_by(ApplicationMain.USERNAME);
					obj.setUpdated_date(dateFormat.format(date));
					dao.update(conn, obj);
					okMsg("แก้ไขข้อมูลเรียบร้อยแล้ว");
				}else{
					
					obj.setCreated_by(ApplicationMain.USERNAME);
					obj.setCreated_date(dateFormat.format(date));
					int id = dao.insert(conn, obj);
					obj.setId(id);
					payDetailTypeid = id;
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
