package com.pawineept.ptm.popup;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.List;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import com.pawineept.ptm.constant.PTConstant;
import com.pawineept.ptm.dao.TbTPatientDAO;
import com.pawineept.ptm.dao.TbTScheduleDAO;
import com.pawineept.ptm.model.TbTPatient;
import com.pawineept.ptm.model.TbTSchedule;
import com.pawineept.ptm.util.DBUtil;

public class ScheduleRegisterDialog extends Dialog {

	public static String dateText;
	protected Object result;
	public Shell shell;
	private Text txtoldsearch;
	private Table tableOldpt;
	private Text txtOName;
	private Text txtOTel;
	private Text txtNName;
	private Text txtNTel;
	private Text txtOid;
	private Combo comOTime;
	private Combo comNTime;
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public ScheduleRegisterDialog(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shell.setSize(623, 399);
		shell.setText("ลงเวลาจอง วันที่ "+dateText);
		shell.setLayout(new GridLayout(1, false));
		
		Group group = new Group(shell, SWT.NONE);
		group.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		group.setText("คนไข้เก่า");
		group.setLayout(new GridLayout(15, false));
		
		Label label = new Label(group, SWT.NONE);
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label.setText("ค้นหา");
		
		txtoldsearch = new Text(group, SWT.BORDER | SWT.SEARCH);
		txtoldsearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				if(txtoldsearch.getText().trim().length()>0)
					searchMedicalRecord();
				else
					tableOldpt.removeAll();
			}
		});
		GridData gd_txtoldsearch = new GridData(SWT.LEFT, SWT.CENTER, true, false, 14, 1);
		gd_txtoldsearch.widthHint = 226;
		txtoldsearch.setLayoutData(gd_txtoldsearch);
		
		tableOldpt = new Table(group, SWT.BORDER | SWT.FULL_SELECTION);
		GridData gd_tableOldpt = new GridData(SWT.FILL, SWT.FILL, true, true, 15, 2);
		gd_tableOldpt.heightHint = 146;
		tableOldpt.setLayoutData(gd_tableOldpt);
		tableOldpt.setHeaderVisible(true);
		tableOldpt.setLinesVisible(true);
		tableOldpt.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TbTPatient patient = (TbTPatient) e.item.getData();
				System.out.println("edit patientId:"+patient.getPatientid());
				txtOid.setText(patient.getPatientid());
				txtOName.setText(patient.getFirstname()+" "+patient.getLastname());
				txtOTel.setText(patient.getMobilephone()==null?patient.getTelephone():patient.getMobilephone());
			}
		});
		TableColumn tableColumn = new TableColumn(tableOldpt, SWT.NONE);
		tableColumn.setWidth(204);
		tableColumn.setText("ชื่อ");
		
		TableColumn tableColumn_1 = new TableColumn(tableOldpt, SWT.NONE);
		tableColumn_1.setWidth(110);
		tableColumn_1.setText("ชื่อเล่น");
		
		TableColumn tableColumn_2 = new TableColumn(tableOldpt, SWT.NONE);
		tableColumn_2.setWidth(100);
		tableColumn_2.setText("เบอร์โทรศัพท์");
		
		TableColumn tableColumn_3 = new TableColumn(tableOldpt, SWT.NONE);
		tableColumn_3.setWidth(167);
		tableColumn_3.setText("รักษาล่าสุด");
		
		Label lblPtno = new Label(group, SWT.NONE);
		lblPtno.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPtno.setText("PT.NO.");
		
		txtOid = new Text(group, SWT.BORDER | SWT.READ_ONLY);
		txtOid.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		
		Label label_1 = new Label(group, SWT.NONE);
		label_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 2, 1));
		label_1.setText("ชื่อ");
		
		txtOName = new Text(group, SWT.BORDER | SWT.READ_ONLY);
		GridData gd_txtOName = new GridData(SWT.LEFT, SWT.CENTER, false, false, 5, 1);
		gd_txtOName.widthHint = 191;
		txtOName.setLayoutData(gd_txtOName);
		
		Label label_2 = new Label(group, SWT.NONE);
		label_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_2.setText("เบอร์โทรศัพท์");
		
		txtOTel = new Text(group, SWT.BORDER | SWT.READ_ONLY);
		GridData gd_txtOTel = new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1);
		gd_txtOTel.widthHint = 100;
		txtOTel.setLayoutData(gd_txtOTel);
		new Label(group, SWT.NONE);
		new Label(group, SWT.NONE);
		
		Label label_3 = new Label(group, SWT.NONE);
		label_3.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_3.setText("เวลา");
		
		comOTime = new Combo(group, SWT.NONE);
		comOTime.setItems(PTConstant.TIMEWORK);
		comOTime.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		comOTime.select(0);
		
		Button buttonO = new Button(group, SWT.NONE);
		buttonO.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				register(true);
			}
		});
		buttonO.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1));
		buttonO.setText("ลงทะเบียนจอง");
		new Label(group, SWT.NONE);
		new Label(group, SWT.NONE);
		new Label(group, SWT.NONE);
		new Label(group, SWT.NONE);
		new Label(group, SWT.NONE);
		new Label(group, SWT.NONE);
		new Label(group, SWT.NONE);
		new Label(group, SWT.NONE);
		new Label(group, SWT.NONE);
		
		Group group_1 = new Group(shell, SWT.NONE);
		group_1.setLayout(new GridLayout(6, false));
		group_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		group_1.setText("คนไข้ใหม่");
		
		Label label_4 = new Label(group_1, SWT.RIGHT);
		GridData gd_label_4 = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_label_4.widthHint = 41;
		label_4.setLayoutData(gd_label_4);
		label_4.setText("ชื่อ");
		
		txtNName = new Text(group_1, SWT.BORDER);
		GridData gd_txtNName = new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1);
		gd_txtNName.widthHint = 179;
		txtNName.setLayoutData(gd_txtNName);
		
		Label label_5 = new Label(group_1, SWT.NONE);
		label_5.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_5.setText("เบอร์โทรศัพท์");
		
		txtNTel = new Text(group_1, SWT.BORDER);
		GridData gd_txtNTel = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_txtNTel.widthHint = 96;
		txtNTel.setLayoutData(gd_txtNTel);
		
		Label label_6 = new Label(group_1, SWT.NONE);
		label_6.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_6.setText("เวลา");
		
		comNTime = new Combo(group_1, SWT.NONE);
		GridData gd_comNTime = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_comNTime.widthHint = 60;
		comNTime.setLayoutData(gd_comNTime);
		comNTime.setItems(PTConstant.TIMEWORK);
		comNTime.select(0);
		
		Button buttonN = new Button(group_1, SWT.NONE);
		buttonN.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				register(false);
			}
		});
		buttonN.setText("ลงทะเบียนจอง");
		new Label(group_1, SWT.NONE);
		new Label(group_1, SWT.NONE);
		new Label(group_1, SWT.NONE);

	}

	protected void register(boolean b) {
		TbTSchedule obj = new TbTSchedule();
		Connection conn = null;
		try{
			if(b){
				System.out.println("Old PT");
				obj.setDatetime(dateText+" "+comOTime.getText());
				obj.setPatientId(txtOid.getText());
				obj.setName(txtOName.getText());
				obj.setTel(txtOTel.getText());
			}else{
				System.out.println("New PT");
				obj.setDatetime(dateText+" "+comNTime.getText());
				obj.setName(txtNName.getText());
				obj.setTel(txtNTel.getText());
			}
			System.out.println("NAME:"+obj.getName());
			System.out.println("NAME:"+obj.getTel());
			conn= DBUtil.connect();
			TbTScheduleDAO dao = new TbTScheduleDAO();
			dao.insert(obj,conn);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBUtil.close(conn);
		}
		shell.close();
	}

	protected void searchMedicalRecord() {		
		Connection conn = null;
		try{
			conn= DBUtil.connect();
			TbTPatientDAO dao = new TbTPatientDAO();
			String name[] = txtoldsearch.getText().split(" ");
			List<TbTPatient> lst = dao.findAllName(conn,name);
			tableOldpt.removeAll();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			for(int i=0;i<lst.size();i++){
				TbTPatient obj = lst.get(i);
				TableItem tableItem = new TableItem(tableOldpt, SWT.NONE);
				tableItem.setText(new String[] {obj.getFirstname()+" "+obj.getLastname(), obj.getNickname(), obj.getMobilephone(), obj.getLastservice()==null?"":sdf.format(obj.getLastservice())});
				tableItem.setData(obj);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBUtil.close(conn);
		}
		
	}
}
