package com.pawineept.ptm.layout;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.TableColumn;

public class UserManageSearchLayout extends Composite {
	private Text txtSearch;
	private Table table;

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
		GridData gd_txtSearch = new GridData(SWT.FILL, SWT.CENTER, true, false, 6, 1);
		gd_txtSearch.widthHint = 340;
		txtSearch.setLayoutData(gd_txtSearch);
		
		table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		table.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		GridData gd_table = new GridData(SWT.FILL, SWT.FILL, true, true, 8, 1);
		gd_table.widthHint = 581;
		table.setLayoutData(gd_table);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tableColumn = new TableColumn(table, SWT.NONE);
		tableColumn.setWidth(69);
		tableColumn.setText("คำนำหน้า");
		
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

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
