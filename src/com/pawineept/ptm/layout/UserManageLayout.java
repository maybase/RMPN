package com.pawineept.ptm.layout;

import java.sql.Connection;
import java.util.List;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;

import com.pawineept.ptm.dao.TbCTitleDAO;
import com.pawineept.ptm.util.DBUtil;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class UserManageLayout extends Composite {
	private Combo txtTitleDesc;
	private Text txtUser;
	private Text txtPassword;
	private Text txtFirstName;
	private Text txtLastName;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public UserManageLayout(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(6, false));
		
		Label lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 6, 1));
		lblNewLabel.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		lblNewLabel.setText("จัดการผู้ใช้งาน");
		
		Label lblNewLabel_1 = new Label(this, SWT.NONE);
		lblNewLabel_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_1.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		lblNewLabel_1.setText("คำนำหน้า");
		
		txtTitleDesc = new Combo(this, SWT.NONE);
		txtTitleDesc.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		GridData gd_txtTitleDesc = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_txtTitleDesc.widthHint = 93;
		txtTitleDesc.setLayoutData(gd_txtTitleDesc);
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
		GridData gd_txtUser = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
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
		
		Label lblFirstname = new Label(this, SWT.NONE);
		lblFirstname.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		lblFirstname.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblFirstname.setText("ชื่อ");
		
		txtFirstName = new Text(this, SWT.BORDER);
		txtFirstName.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		GridData gd_txtFirstName = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
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
		
		Button btnNewButton = new Button(this, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
			}
		});
		btnNewButton.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		btnNewButton.setText("บันทึก");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		initTitleDesc();
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

}
