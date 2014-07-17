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
import com.pawineept.ptm.dao.TbMPositionDAO;
import com.pawineept.ptm.model.TbMPosition;
import com.pawineept.ptm.util.DBUtil;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class PositionSearchLayout extends Composite {
	private Text txtSearch;
	private Button btnAdd;
	private Table table;
	private TableColumn tableColumn;
	private TableColumn tableColumn_1;
	private TableColumn tableColumn_2;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public PositionSearchLayout(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(5, false));
		
		Label label = new Label(this, SWT.NONE);
		label.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 5, 1));
		label.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		label.setText("ค้นหาข้อมูลตำแหน่งงาน");
		new Label(this, SWT.NONE);
		
		Label label_1 = new Label(this, SWT.NONE);
		label_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_1.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		label_1.setText("ค้นหา ตำแหน่งงาน");
		
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
		txtSearch.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		txtSearch.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(this, SWT.NONE);
		
		btnAdd = new Button(this, SWT.NONE);
		btnAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ApplicationMain.closeShell();
		        PositionLayout us = new PositionLayout(ApplicationMain.shell,SWT.NONE);
				ApplicationMain.composite = us;
				ApplicationMain.openShell();
				PositionLayout.editMode = false;
			}
		});
		btnAdd.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		btnAdd.setText("เพิ่มตำแหน่งงาน");
		
		table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		table.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String positionid = e.item.getData().toString();
				System.out.println("edit positionid:"+positionid);
				ApplicationMain.closeShell();
				PositionLayout.editMode = true;
				PositionLayout.positionid = Integer.parseInt(positionid);
				ApplicationMain.composite = new PositionLayout(ApplicationMain.shell, SWT.NULL);
				ApplicationMain.openShell();
			}
		});
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 5, 1));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		tableColumn = new TableColumn(table, SWT.NONE);
		tableColumn.setWidth(335);
		tableColumn.setText("ตำแหน่งงาน");
		
		tableColumn_1 = new TableColumn(table, SWT.NONE);
		tableColumn_1.setWidth(278);
		tableColumn_1.setText("หมายเหตุ");
		
		tableColumn_2 = new TableColumn(table, SWT.NONE);
		tableColumn_2.setWidth(123);
		tableColumn_2.setText("สถานะตำแหน่ง");

	}
	
	protected void searchUserRecord() {		
		Connection conn = null;
		try{
			conn= DBUtil.connect();
			TbMPositionDAO dao = new TbMPositionDAO();
			String name[] = txtSearch.getText().split(" ");
			List<TbMPosition> lst = dao.findAllName(conn,name);
			table.removeAll();
			for(int i=0;i<lst.size();i++){
				TbMPosition obj = lst.get(i);
				TableItem tableItem = new TableItem(table, SWT.NONE);
				tableItem.setText(new String[] {obj.getPosition_name(), obj.getRemark(), obj.getStatus(), ""});
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
