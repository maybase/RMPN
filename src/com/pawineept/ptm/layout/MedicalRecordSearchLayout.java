package com.pawineept.ptm.layout;

import java.sql.Connection;
import java.util.List;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;

import com.pawineept.ptm.app.ApplicationMain;
import com.pawineept.ptm.dao.TbTPatientDAO;
import com.pawineept.ptm.model.TbTPatient;
import com.pawineept.ptm.util.DBUtil;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.wb.swt.SWTResourceManager;

public class MedicalRecordSearchLayout extends Composite {
	private Text txtSearch;
	private Table table;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public MedicalRecordSearchLayout(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(8, false));
		
		Label label = new Label(this, SWT.NONE);
		label.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 8, 1));
		label.setText("ค้นหาทะเบียนเวช");
		new Label(this, SWT.NONE);
		
		Label lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		lblNewLabel.setText("ค้นหา ชื่อ นามสกุล ชื่อเล่น");
		
		txtSearch = new Text(this, SWT.BORDER);
		txtSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				if(txtSearch.getText().trim().length()>0)
					searchMedicalRecord();
				else
					table.removeAll();
			}	
		});
		GridData gd_txtSearch = new GridData(SWT.FILL, SWT.CENTER, true, false, 6, 1);
		gd_txtSearch.widthHint = 217;
		txtSearch.setLayoutData(gd_txtSearch);
		
		
		table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		table.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		table.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String patientId = e.item.getData().toString();
				System.out.println("edit patientId:"+patientId);
				ApplicationMain.closeShell();
				MedicalRecordLayout.modeedit = true;
				MedicalRecordLayout.patientId = patientId;
				ApplicationMain.composite = new MedicalRecordLayout(ApplicationMain.shell, SWT.NULL);
				ApplicationMain.openShell();
			}
		});
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 8, 1));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tableColumn = new TableColumn(table, SWT.NONE);
		tableColumn.setWidth(74);
		tableColumn.setText("รหัส");
		
		TableColumn tableColumn_1 = new TableColumn(table, SWT.NONE);
		tableColumn_1.setWidth(192);
		tableColumn_1.setText("ชือ - นามสกุล");
		
		TableColumn tableColumn_2 = new TableColumn(table, SWT.NONE);
		tableColumn_2.setWidth(100);
		tableColumn_2.setText("ชื่อเล่น");
		
		TableColumn tableColumn_4 = new TableColumn(table, SWT.NONE);
		tableColumn_4.setWidth(100);
		tableColumn_4.setText("เบอร์โทรศัพท์");
		
		TableColumn tableColumn_3 = new TableColumn(table, SWT.NONE);
		tableColumn_3.setWidth(100);
		tableColumn_3.setText("รักษาครั้งล่าสุด");

	}

	protected void searchMedicalRecord() {		
		Connection conn = null;
		try{
			conn= DBUtil.connect();
			TbTPatientDAO dao = new TbTPatientDAO();
			String name[] = txtSearch.getText().split(" ");
			List<TbTPatient> lst = dao.findAllName(conn,name);
			table.removeAll();
			for(int i=0;i<lst.size();i++){
				TbTPatient obj = lst.get(i);
				TableItem tableItem = new TableItem(table, SWT.NONE);
				tableItem.setText(new String[] {obj.getPatientid(), obj.getFirstname()+" "+obj.getLastname(), obj.getNickname(), obj.getMobilephone(), ""});
				tableItem.setData(obj.getPatientid());
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBUtil.close(conn);
		}
		
	}

	@Override
	protected void checkSubclass() {
		
	}
}
