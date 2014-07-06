package com.pawineept.ptm.layout;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import com.pawineept.ptm.app.ApplicationMain;
import org.eclipse.wb.swt.SWTResourceManager;

public class ChooseDBLayout extends Composite {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ChooseDBLayout(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(3, false));
		new Label(this, SWT.NONE);
		
		Canvas canvas_2 = new Canvas(this, SWT.NONE);
		canvas_2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));
		new Label(this, SWT.NONE);
		
		Canvas canvas = new Canvas(this, SWT.NONE);
		canvas.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button button = new Button(this, SWT.NONE);
		button.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog dialog = new FileDialog(ApplicationMain.shell, SWT.NULL);
				String path = dialog.open();
				if (path != null) {
					ApplicationMain.dbpath = path;
					System.out.println("PATH DB = "+ApplicationMain.dbpath);
					ApplicationMain.closeShell();
					ApplicationMain.composite = new LoginLayout(ApplicationMain.shell, SWT.NULL);
					ApplicationMain.openShell();					
				}
			}
		});
		button.setText("\u0E40\u0E25\u0E37\u0E2D\u0E01\u0E10\u0E32\u0E19\u0E02\u0E49\u0E2D\u0E21\u0E39\u0E25");
		
		Canvas canvas_1 = new Canvas(this, SWT.NONE);
		canvas_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		new Label(this, SWT.NONE);
		
		Canvas canvas_3 = new Canvas(this, SWT.NONE);
		canvas_3.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));
		new Label(this, SWT.NONE);
		
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
