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
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;

import com.pawineept.ptm.app.ApplicationMain;
import com.pawineept.ptm.dao.TbTStaffDAO;
import com.pawineept.ptm.model.TbTStaff;
import com.pawineept.ptm.util.DBUtil;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class StaffSearchLayout extends Composite {
	private Text txtSearch;
	private Button btnAdd;
	private Table table;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public StaffSearchLayout(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(6, false));
		
		Label label = new Label(this, SWT.NONE);
		label.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 5, 1));
		label.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		label.setText("ค้นหาข้อมูลพนักงาน");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Label label_1 = new Label(this, SWT.NONE);
		label_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_1.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		label_1.setText("ค้นหา ชื่อ นามสกุล ตำแหน่งงาน");
		
		txtSearch = new Text(this, SWT.BORDER);
		txtSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				if(txtSearch.getText().trim().length()>0)
					searchStaffRecord();
				else
					table.removeAll();
			}
		});
		txtSearch.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		txtSearch.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(this, SWT.NONE);
		
		btnAdd = new Button(this, SWT.NONE);
		btnAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ApplicationMain.closeShell();
				StaffLayout us = new StaffLayout(ApplicationMain.shell,SWT.NONE);
				ApplicationMain.composite = us;
				ApplicationMain.openShell();
				StaffLayout.editMode = false;
				
			}
		});
		btnAdd.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		btnAdd.setText("เพิ่มข้อมูลพนักงาน");
		
		Button button = new Button(this, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				searchAllStaffRecord();
			}
		});
		button.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		button.setText("แสดงพนักงานทั้งหมด");
		
		table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		table.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String staffid = e.item.getData().toString();
				System.out.println("edit staffid:"+staffid);
				ApplicationMain.closeShell();
				StaffLayout.editMode = true;
				StaffLayout.staffid = Integer.parseInt(staffid);
				ApplicationMain.composite = new StaffLayout(ApplicationMain.shell, SWT.NULL);
				ApplicationMain.openShell();
			}
		});
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 6, 1));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tableColumn = new TableColumn(table, SWT.NONE);
		tableColumn.setWidth(191);
		tableColumn.setText("ชื่อ - นามสกุล");
		
		TableColumn tableColumn_1 = new TableColumn(table, SWT.NONE);
		tableColumn_1.setWidth(325);
		tableColumn_1.setText("ที่อยู่");
		
		TableColumn tableColumn_2 = new TableColumn(table, SWT.NONE);
		tableColumn_2.setWidth(119);
		tableColumn_2.setText("โทรศัพท์");
		
		TableColumn tableColumn_3 = new TableColumn(table, SWT.NONE);
		tableColumn_3.setWidth(187);
		tableColumn_3.setText("ตำแหน่งงาน");
		
		TableColumn tableColumn_4 = new TableColumn(table, SWT.NONE);
		tableColumn_4.setWidth(100);
		tableColumn_4.setText("ประเภทงาน");
		
		TableColumn tableColumn_5 = new TableColumn(table, SWT.NONE);
		tableColumn_5.setWidth(100);
		tableColumn_5.setText("สถานะพนักงาน");

	}

	protected void searchStaffRecord() {		
		Connection conn = null;
		try{
			conn= DBUtil.connect();
			TbTStaffDAO dao = new TbTStaffDAO();
			String name[] = txtSearch.getText().split(" ");
			List<TbTStaff> lst = dao.findAllName(conn,name);
			table.removeAll();
			for(int i=0;i<lst.size();i++){
				TbTStaff obj = lst.get(i);
				TableItem tableItem = new TableItem(table, SWT.NONE);
				tableItem.setText(new String[] {obj.getFirstName()+" "+obj.getLastName(), obj.getAddress(), obj.getPhone(), obj.getPositionName(), obj.getJobTypeName(), obj.getStatus(), ""});
				tableItem.setData(obj.getId().toString());
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBUtil.close(conn);
		}
		
	}
	
	protected void searchAllStaffRecord() {		
		Connection conn = null;
		try{
			conn= DBUtil.connect();
			TbTStaffDAO dao = new TbTStaffDAO();
			String name[] = txtSearch.getText().split(" ");
			List<TbTStaff> lst = dao.findAllList(conn,name);
			table.removeAll();
			for(int i=0;i<lst.size();i++){
				TbTStaff obj = lst.get(i);
				TableItem tableItem = new TableItem(table, SWT.NONE);
				tableItem.setText(new String[] {obj.getFirstName()+" "+obj.getLastName(), obj.getAddress(), obj.getPhone(), obj.getPositionName(), obj.getJobTypeName(), obj.getStatus(), ""});
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
