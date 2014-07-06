package com.pawineept.ptm.popup;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;

import com.pawineept.ptm.constant.PTConstant;
import com.pawineept.ptm.dao.TbTScheduleDAO;
import com.pawineept.ptm.model.TbTSchedule;
import com.pawineept.ptm.util.DBUtil;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class ScheduleChangeDialog extends Dialog {

	protected Object result;
	protected Shell shell;
	public TbTSchedule schedule;
	DateTime dateTime;
	Combo combo;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public ScheduleChangeDialog(Shell parent, int style) {
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
		shell = new Shell(getParent(), SWT.DIALOG_TRIM);
		shell.setSize(430, 189);
		shell.setText(getText());
		shell.setLayout(new GridLayout(4, false));
		
		dateTime = new DateTime(shell, SWT.BORDER | SWT.CALENDAR);
		dateTime.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 4));
		
		Label label = new Label(shell, SWT.NONE);
		label.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label.setText("ชื่อ");
		
		Label lblName = new Label(shell, SWT.NONE);
		lblName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		lblName.setText(schedule.getName());
		
		Label label_1 = new Label(shell, SWT.NONE);
		label_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_1.setText("เบอร์โทรศัพท์");
		
		Label lblPhone = new Label(shell, SWT.NONE);
		lblPhone.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		lblPhone.setText(schedule.getTel());
		
		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setText("เวลา");
		
		combo = new Combo(shell, SWT.NONE);
		combo.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		combo.setItems(PTConstant.TIMEWORK);
		combo.select(0);
		new Label(shell, SWT.NONE);
		
		Button btnSave = new Button(shell, SWT.NONE);
		btnSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateSchedule();
			}
		});
		btnSave.setLayoutData(new GridData(SWT.RIGHT, SWT.BOTTOM, true, false, 1, 1));
		btnSave.setText("บันทึก");
		
		Button btnCancel = new Button(shell, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
		});
		btnCancel.setLayoutData(new GridData(SWT.LEFT, SWT.BOTTOM, false, false, 1, 1));
		btnCancel.setText("ยกเลิก");
	}

	protected void updateSchedule() {
		Connection conn =  null;
		try{
			conn = DBUtil.connect();
			TbTScheduleDAO dao = new TbTScheduleDAO();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy",Locale.US);
			Date date = sdf.parse(dateTime.getDay()+"/"+(dateTime.getMonth()+1)+"/"+dateTime.getYear());
			schedule.setDatetime(sdf.format(date)+" "+combo.getText());
			System.out.println("change to date:"+schedule.getDatetime());
			dao.updateDateTime(schedule,conn);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBUtil.close(conn);
			shell.close();
		}
	}

}
