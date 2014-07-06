package com.pawineept.ptm.layout;

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

public class MedicalRecordGroupLayout extends Composite {
	private Text vPrefix;
	private Text vTitle;
	private Combo cmbStatus;
	private Table table;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public MedicalRecordGroupLayout(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(3, false));
		
		Label label = new Label(this, SWT.NONE);
		GridData gd_label = new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1);
		gd_label.widthHint = 411;
		label.setLayoutData(gd_label);
		label.setText("จัดการกลุ่มเวชระเบียน");
		label.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Label label_1 = new Label(this, SWT.NONE);
		label_1.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		label_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_1.setText("ชื่อกลุ่ม");
		
		vTitle = new Text(this, SWT.BORDER);
		vTitle.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		GridData gd_vTitle = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_vTitle.widthHint = 265;
		vTitle.setLayoutData(gd_vTitle);
		new Label(this, SWT.NONE);
		
		Label label_2 = new Label(this, SWT.NONE);
		label_2.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		label_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_2.setText("รหัสนำหน้า");
		
		vPrefix = new Text(this, SWT.BORDER);
		vPrefix.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		new Label(this, SWT.NONE);
		
		Label label_3 = new Label(this, SWT.NONE);
		label_3.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		label_3.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_3.setText("สถานะการใช้งาน");
		
		cmbStatus = new Combo(this, SWT.NONE);
		cmbStatus.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		cmbStatus.setItems(new String[] {"ใช้งาน", "ไม่ใช้งาน"});
		cmbStatus.select(0);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Button btnSave = new Button(this, SWT.NONE);
		btnSave.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		btnSave.setText("บันทึกข้อมูล");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		table.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tableColumn = new TableColumn(table, SWT.NONE);
		tableColumn.setResizable(false);
		tableColumn.setWidth(41);
		tableColumn.setText("#");
		
		TableColumn tableColumn_1 = new TableColumn(table, SWT.NONE);
		tableColumn_1.setResizable(false);
		tableColumn_1.setWidth(148);
		tableColumn_1.setText("ตัวหนังสือนำหน้ารหัส");
		
		TableColumn tableColumn_2 = new TableColumn(table, SWT.NONE);
		tableColumn_2.setWidth(312);
		tableColumn_2.setText("ชื่อกลุ่ม");
		
		TableColumn tableColumn_3 = new TableColumn(table, SWT.NONE);
		tableColumn_3.setWidth(100);
		tableColumn_3.setText("สถานะ");

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
