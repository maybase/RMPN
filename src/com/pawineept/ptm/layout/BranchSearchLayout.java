package com.pawineept.ptm.layout;

import java.sql.Connection;
import java.util.List;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.TableItem;
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

import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;

public class BranchSearchLayout extends Composite {
	private Text txtSearch;
	private Table table;
	private Button btnAddBranch;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public BranchSearchLayout(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(6, false));
		
		Label lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1));
		lblNewLabel.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		lblNewLabel.setText("ค้นหาข้อมูลสาขา");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Label lblNewLabel_1 = new Label(this, SWT.NONE);
		lblNewLabel_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_1.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		lblNewLabel_1.setText("ค้นหา ชื่อ ที่อยู่");
		
		txtSearch = new Text(this, SWT.BORDER);
		txtSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				if(txtSearch.getText().trim().length()>0)
					searchBranchRecord();
				else
					table.removeAll();
			}
		});
		txtSearch.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		txtSearch.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(this, SWT.NONE);
		
		btnAddBranch = new Button(this, SWT.NONE);
		btnAddBranch.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ApplicationMain.closeShell();
				BranchLayout us = new BranchLayout(ApplicationMain.shell,SWT.NONE);
				ApplicationMain.composite = us;
				ApplicationMain.openShell();
				BranchLayout.editMode = false;
			}
		});
		btnAddBranch.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		btnAddBranch.setText("เพิ่มสาขา");
		
		Button btnNewButton = new Button(this, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				searchAllBranchRecord();
			}
		});
		btnNewButton.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		btnNewButton.setText("แสดงสาขาทั้งหมด");
		
		table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		table.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String branchid = e.item.getData().toString();
				System.out.println("edit branchid:"+branchid);
				ApplicationMain.closeShell();
				BranchLayout.editMode = true;
				BranchLayout.branchid = Integer.parseInt(branchid);
				ApplicationMain.composite = new BranchLayout(ApplicationMain.shell, SWT.NULL);
				ApplicationMain.openShell();
			}
		});
		GridData gd_table = new GridData(SWT.FILL, SWT.FILL, true, true, 6, 1);
		gd_table.widthHint = 628;
		table.setLayoutData(gd_table);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tableColumn = new TableColumn(table, SWT.NONE);
		tableColumn.setWidth(253);
		tableColumn.setText("ชื่อสาขา");
		
		TableColumn tableColumn_1 = new TableColumn(table, SWT.NONE);
		tableColumn_1.setWidth(363);
		tableColumn_1.setText("ที่อยู่");
		
		TableColumn tableColumn_2 = new TableColumn(table, SWT.NONE);
		tableColumn_2.setWidth(263);
		tableColumn_2.setText("โทรศัพท์");
		
		TableColumn tableColumn_3 = new TableColumn(table, SWT.NONE);
		tableColumn_3.setWidth(100);
		tableColumn_3.setText("สถานะสาขา");

	}
	
	protected void searchBranchRecord() {		
		Connection conn = null;
		try{
			conn= DBUtil.connect();
			TbMBranchDAO dao = new TbMBranchDAO();
			String name[] = txtSearch.getText().split(" ");
			List<TbMBranch> lst = dao.findAllName(conn,name);
			table.removeAll();
			for(int i=0;i<lst.size();i++){
				TbMBranch obj = lst.get(i);
				TableItem tableItem = new TableItem(table, SWT.NONE);
				tableItem.setText(new String[] {obj.getBranchName(), obj.getBranchAddress(), obj.getBranchPhone(), obj.getStatus()});
				tableItem.setData(obj.getId().toString());
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBUtil.close(conn);
		}
		
	}
	
	protected void searchAllBranchRecord() {		
		Connection conn = null;
		try{
			conn= DBUtil.connect();
			TbMBranchDAO dao = new TbMBranchDAO();
			List<TbMBranch> lst = dao.findAllList(conn);
			table.removeAll();
			for(int i=0;i<lst.size();i++){
				TbMBranch obj = lst.get(i);
				TableItem tableItem = new TableItem(table, SWT.NONE);
				tableItem.setText(new String[] {obj.getBranchName(), obj.getBranchAddress(), obj.getBranchPhone(), obj.getStatus()});
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
