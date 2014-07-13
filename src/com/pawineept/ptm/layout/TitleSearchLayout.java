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
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;

import com.pawineept.ptm.app.ApplicationMain;
import com.pawineept.ptm.dao.TbCTitleDAO;
import com.pawineept.ptm.model.TbCTitle;
import com.pawineept.ptm.util.DBUtil;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class TitleSearchLayout extends Composite {
	private Text txtSearch;
	private Table table;
	private Button btnSearch;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public TitleSearchLayout(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(6, false));
		
		Label lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		lblNewLabel.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		lblNewLabel.setText("ค้นหาข้อมูลคำนำหน้า");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Label lblNewLabel_1 = new Label(this, SWT.NONE);
		lblNewLabel_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_1.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		lblNewLabel_1.setText("ค้นหา คำนำหน้า แบบไทย - อังกฤษ");
		
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
		GridData gd_txtSearch = new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1);
		gd_txtSearch.widthHint = 246;
		txtSearch.setLayoutData(gd_txtSearch);
		new Label(this, SWT.NONE);
		
		btnSearch = new Button(this, SWT.NONE);
		GridData gd_btnSearch = new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1);
		gd_btnSearch.widthHint = 97;
		btnSearch.setLayoutData(gd_btnSearch);
		btnSearch.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ApplicationMain.closeShell();
				TitleLayout ti = new TitleLayout(ApplicationMain.shell,SWT.NONE);
				ApplicationMain.composite = ti;
				ApplicationMain.openShell();
				UserManageLayout.editMode = false;
			}
		});
		btnSearch.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		btnSearch.setText("เพิ่มคำนำหน้า");
		
		table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		table.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String titleid = e.item.getData().toString();
				System.out.println("edit titleid:"+titleid);
				ApplicationMain.closeShell();
				TitleLayout.editMode = true;
				TitleLayout.titleid = Integer.parseInt(titleid);
				ApplicationMain.composite = new TitleLayout(ApplicationMain.shell, SWT.NULL);
				ApplicationMain.openShell();
				
			}
		});
		table.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		GridData gd_table = new GridData(SWT.FILL, SWT.FILL, true, true, 6, 1);
		gd_table.widthHint = 706;
		table.setLayoutData(gd_table);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tableColumn = new TableColumn(table, SWT.NONE);
		tableColumn.setWidth(273);
		tableColumn.setText("คำนำหน้าแบบไทย");
		
		TableColumn tableColumn_1 = new TableColumn(table, SWT.NONE);
		tableColumn_1.setWidth(276);
		tableColumn_1.setText("คำนำหน้าแบบอังกฤษ");
		
		TableColumn tableColumn_2 = new TableColumn(table, SWT.NONE);
		tableColumn_2.setWidth(128);
		tableColumn_2.setText("สถานะคำนำหน้า");

	}
	
	protected void searchUserRecord() {		
		Connection conn = null;
		try{
			conn= DBUtil.connect();
			TbCTitleDAO dao = new TbCTitleDAO();
			String name[] = txtSearch.getText().split(" ");
			List<TbCTitle> lst = dao.findAllName(conn,name);
			table.removeAll();
			for(int i=0;i<lst.size();i++){
				TbCTitle obj = lst.get(i);
				TableItem tableItem = new TableItem(table, SWT.NONE);
				tableItem.setText(new String[] {obj.getTitleDescTh(), obj.getTitleDescEn(), obj.getStatus(), ""});
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
