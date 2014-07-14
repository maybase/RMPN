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
import com.pawineept.ptm.dao.TbMMedicalGroupDAO;
import com.pawineept.ptm.dao.TbMUserDAO;
import com.pawineept.ptm.model.TbMMedicalGroup;
import com.pawineept.ptm.util.DBUtil;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class MedicalRecordGroupSearchLayout extends Composite {
	private Text txtSearch;
	private Table table;
	private Button btnAdd;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public MedicalRecordGroupSearchLayout(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(5, false));
		
		Label lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 5, 1));
		lblNewLabel.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		lblNewLabel.setText("ค้นหาข้อมูลกลุ่มเวชระเบียน");
		new Label(this, SWT.NONE);
		
		Label lblNewLabel_1 = new Label(this, SWT.NONE);
		lblNewLabel_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_1.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		lblNewLabel_1.setText("ค้นหา ชื่อกลุ่ม สาขา");
		
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
				MedicalRecordGroupLayout mg = new MedicalRecordGroupLayout(ApplicationMain.shell, SWT.NONE);
				ApplicationMain.composite = mg;
				ApplicationMain.openShell();
				MedicalRecordGroupLayout.editMode = false;
			}
		});
		btnAdd.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		btnAdd.setText("เพิ่มข้อมูลกลุ่ม");
		
		table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		table.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String medicalgroupid = e.item.getData().toString();
				System.out.println("edit medicalgroupid:"+medicalgroupid);
				ApplicationMain.closeShell();
				MedicalRecordGroupLayout.editMode = true;
				MedicalRecordGroupLayout.medicalgroupid = Integer.parseInt(medicalgroupid);
				ApplicationMain.composite = new MedicalRecordGroupLayout(ApplicationMain.shell, SWT.NULL);
				ApplicationMain.openShell();
			}
		});
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 5, 1));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tableColumn = new TableColumn(table, SWT.NONE);
		tableColumn.setWidth(213);
		tableColumn.setText("สาขา");
		
		TableColumn tblclmnNewColumn = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn.setWidth(277);
		tblclmnNewColumn.setText("ชื่อกลุ่ม");
		
		TableColumn tblclmnNewColumn_1 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_1.setWidth(130);
		tblclmnNewColumn_1.setText("รหัสคำนำหน้า");
		
		TableColumn tableColumn_1 = new TableColumn(table, SWT.NONE);
		tableColumn_1.setWidth(122);
		tableColumn_1.setText("สถานะกลุ่ม");

	}
	
	protected void searchUserRecord() {		
		Connection conn = null;
		try{
			conn= DBUtil.connect();
			TbMMedicalGroupDAO dao = new TbMMedicalGroupDAO();
			String name[] = txtSearch.getText().split(" ");
			List<TbMMedicalGroup> lst = dao.findAllName(conn,name);
			table.removeAll();
			for(int i=0;i<lst.size();i++){
				TbMMedicalGroup obj = lst.get(i);
				TableItem tableItem = new TableItem(table, SWT.NONE);
				tableItem.setText(new String[] {obj.getBranch_name(), obj.getMedical_group_name(), obj.getPrefix(), obj.getStatus(), ""});
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
