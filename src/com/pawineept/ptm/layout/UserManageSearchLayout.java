package com.pawineept.ptm.layout;

import java.sql.Connection;
import java.util.List;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.TableColumn;

import com.pawineept.ptm.app.ApplicationMain;
import com.pawineept.ptm.dao.TbMUserDAO;
import com.pawineept.ptm.model.TbMUser;
import com.pawineept.ptm.util.DBUtil;

import org.eclipse.swt.events.SelectionEvent;

public class UserManageSearchLayout extends Composite {
	private Text txtSearch;
	private Table table;
	private Button btnNewButton;
	

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public UserManageSearchLayout(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(8, false));
		
		Label label = new Label(this, SWT.NONE);
		GridData gd_label = new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1);
		gd_label.widthHint = 65;
		label.setLayoutData(gd_label);
		label.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		label.setText("ค้นหาข้อมูลผู้ใช้งาน");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Label lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		lblNewLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		lblNewLabel.setText("ค้นหา ชื่อ นามสกุล User");
		
		txtSearch = new Text(this, SWT.BORDER);
		txtSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				if(txtSearch.getText().trim().length()>0)
					searchUserRecord();
				else
					table.removeAll();
			}	
		});
		GridData gd_txtSearch = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtSearch.widthHint = 193;
		txtSearch.setLayoutData(gd_txtSearch);
		new Label(this, SWT.NONE);
		
		btnNewButton = new Button(this, SWT.NONE);
		btnNewButton.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ApplicationMain.closeShell();
				UserManageLayout us = new UserManageLayout(ApplicationMain.shell,SWT.NONE);
				ApplicationMain.composite = us;
				ApplicationMain.openShell();
				UserManageLayout.editMode = false;
				
			}
		});
		btnNewButton.setText("เพิ่มผู้ใช้งาน");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		
		table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		table.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		table.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String userid = e.item.getData().toString();
				System.out.println("edit userid:"+userid);
				ApplicationMain.closeShell();
				UserManageLayout.editMode = true;
				UserManageLayout.userid = Integer.parseInt(userid);
				ApplicationMain.composite = new UserManageLayout(ApplicationMain.shell, SWT.NULL);
				ApplicationMain.openShell();
			}
		});
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 8, 1));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tableColumn_1 = new TableColumn(table, SWT.NONE);
		tableColumn_1.setWidth(200);
		tableColumn_1.setText("ชื่อ - นามสกุล");
		
		TableColumn tblclmnUser = new TableColumn(table, SWT.NONE);
		tblclmnUser.setWidth(100);
		tblclmnUser.setText("User");
		
		TableColumn tableColumn_2 = new TableColumn(table, SWT.NONE);
		tableColumn_2.setWidth(100);
		tableColumn_2.setText("วันที่ใช้ล่าสุด");
		
		TableColumn tableColumn_3 = new TableColumn(table, SWT.NONE);
		tableColumn_3.setWidth(100);
		tableColumn_3.setText("สถานะผู้ใช้งาน");

	}

	protected void searchUserRecord() {		
		Connection conn = null;
		try{
			conn= DBUtil.connect();
			TbMUserDAO dao = new TbMUserDAO();
			String name[] = txtSearch.getText().split(" ");
			List<TbMUser> lst = dao.findAllName(conn,name);
			table.removeAll();
			for(int i=0;i<lst.size();i++){
				TbMUser obj = lst.get(i);
				TableItem tableItem = new TableItem(table, SWT.NONE);
				tableItem.setText(new String[] {obj.getFirst_name()+" "+obj.getLast_name(),obj.getUser(), obj.getLast_active(), obj.getStatus(), ""});
				tableItem.setData(obj.getId().toString());
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBUtil.close(conn);
		}
		
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
