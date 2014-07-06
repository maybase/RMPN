package com.pawineept.ptm.popup;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;

import com.pawineept.ptm.dao.TbTScheduleDAO;
import com.pawineept.ptm.model.TbTSchedule;
import com.pawineept.ptm.util.DBUtil;

import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.TableColumn;

public class ScheduleDetailDialog extends Dialog {
	public static java.util.List<TbTSchedule> scheduleL;
	private final String[] STATUS = new String[]{"ยกเลิก","ยังไม่รับรับบริการ","กำลังเข้ารับบริการ","ชำระเงินบริการแล้ว","เลื่อน"};
	protected Object result;
	protected Shell shell;
	private Table table;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public ScheduleDetailDialog(Shell parent, int style) {
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
		shell.setSize(600, 300);
		shell.setText("รายละเอียดตารางเวลา");
		shell.setLayout(new GridLayout(1, false));
		
		table = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tableColumn = new TableColumn(table, SWT.NONE);
		tableColumn.setWidth(200);
		tableColumn.setText("ชื่อ");
		
		TableColumn tableColumn_1 = new TableColumn(table, SWT.NONE);
		tableColumn_1.setWidth(100);
		tableColumn_1.setText("เบอร์ติดต่อ");
		
		TableColumn tableColumn_2 = new TableColumn(table, SWT.NONE);
		tableColumn_2.setWidth(120);
		tableColumn_2.setText("สถานะ");
		
		TableColumn tableColumn_3 = new TableColumn(table, SWT.NONE);
		tableColumn_3.setWidth(140);
		tableColumn_3.setText("");
		
		genDataList();
		
	}

	private void genDataList() {
		table.removeAll();
		for(int i=0;i<scheduleL.size();i++){
			TbTSchedule sh = scheduleL.get(i);
			shell.setText("รายละเอียดตารางเวลา "+sh.getDatetime());
			TableItem tableItem = new TableItem(table, SWT.NONE);
			tableItem.setText(new String[]{sh.getName(),sh.getTel(),STATUS[Integer.parseInt(sh.getStatus())]});
			tableItem.setData(sh);
			TableEditor editor = new TableEditor(table);
			CCombo combo = new CCombo(table, SWT.NONE);
			combo.setData(sh);
			combo.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
//					System.out.println("widgetSelected:src:"+arg0.getSource());
					CCombo obj = (CCombo) arg0.getSource();
//					System.out.println("widgetSelected select Index:"+obj.getSelectionIndex());
					TbTSchedule shobj = (TbTSchedule) obj.getData();
//					System.out.println("widgetSelected TbTSchedule.ID:"+shobj.getId());
					changeStatus(shobj,obj.getSelectionIndex());
					obj.clearSelection();
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					System.out.println("widgetDefaultSelected");
				}
			});
			combo.setText("เลือกสถานะ");
			combo.add(STATUS[0]);
			combo.add(STATUS[1]);
			combo.add(STATUS[2]);
			combo.add(STATUS[3]);
			combo.add(STATUS[4]);
			editor.grabHorizontal = true;
		    editor.setEditor(combo, tableItem, 3);
		}
	}

	protected void changeStatus(TbTSchedule model, int selectionIndex) {
		Connection conn = null;
		
		try{
			conn = DBUtil.connect();
			TbTScheduleDAO dao = new TbTScheduleDAO();
			if(selectionIndex==4){
				ScheduleChangeDialog dialog = new ScheduleChangeDialog(shell,SWT.NONE);
				dialog.schedule = model;
				dialog.open();
			}else{
				model.setStatus(String.valueOf(selectionIndex));
				dao.updateStatus(model,conn);
			}
			model = scheduleL.get(0);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm",Locale.US);
			scheduleL = dao.findByDateTime(conn,sdf.parse(model.getDatetime()));	
			genDataList();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBUtil.close(conn);
		}
	}
}
