package com.test.scan;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.jface.bindings.keys.SWTKeyLookup;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Tracker;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.DragDetectListener;
import org.eclipse.swt.events.DragDetectEvent;

public class MainDemo {
 
	private static final int EDGE_THICK = 15;
	private int RedLEDOnTime = 1416;
	private int RedLEDOffTime = 1560;
	private int GreenLEDOnTime = 2256;
	private int GreenLEDOffTime = 2400;
	private int BlueLEDOnTime = 576;
	private int BlueLEDOffTime = 720;
	
	Composite cpLEDScan;
	Composite blueLED;
	Composite redLED;
	Composite greenLED;
	Spinner spRLEDRatio;
	Spinner spGLEDRatio;
	Spinner spBLEDRatio;
	Label lbRedOnTime;
	Label lbRedOffTime;
	Label lbRedLEDDuration;
	Label lbGreenOnTime;
	Label lbGreenOffTime;
	Label lbGreenLEDDuration;
	Label lbBlueOnTime;
	Label lbBlueOffTime;
	Label lbBlueLEDDuration;
	private Composite composite;
	private Label lblNewLabel;
	private Text text;
	public MainDemo() {
	}

	/**
	 * Create contents of the view part.
	 */
	@PostConstruct
	public void createControls(Composite parent) {
		GridLayout gl_parent = new GridLayout(1, true);
		gl_parent.horizontalSpacing = 0;
		gl_parent.marginWidth = 0;
		parent.setLayout(gl_parent);
		 
		Composite composite_1 = new Composite(parent, SWT.NONE);
		composite_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		GridLayout gl_composite_1 = new GridLayout(7, false);
		composite_1.setLayout(gl_composite_1);
		  
		Label lbScantime = new Label(composite_1, SWT.NONE);
		lbScantime.setAlignment(SWT.RIGHT);
		lbScantime.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lbScantime.setBounds(0, 0, 56, 15);
		lbScantime.setText("Scan Time (line)");
		  
		Spinner spScantime = new Spinner(composite_1, SWT.BORDER);
		spScantime.setMaximum(2048);
		spScantime.setSelection(720);
		spScantime.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		spScantime.setBounds(0, 0, 47, 22);
		  
		Label lbHolingtime = new Label(composite_1, SWT.NONE);
		lbHolingtime.setAlignment(SWT.RIGHT);
		lbHolingtime.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lbHolingtime.setBounds(0, 0, 56, 15);
		lbHolingtime.setText("Hold Time (line)");
		  
		Spinner spHoldingtime = new Spinner(composite_1, SWT.BORDER);
		spHoldingtime.setMaximum(2048);
		spHoldingtime.setSelection(120);
		spHoldingtime.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		spHoldingtime.setBounds(0, 0, 47, 22);
		  
		Label lbOnelinetime = new Label(composite_1, SWT.NONE);
		lbOnelinetime.setAlignment(SWT.RIGHT);
		lbOnelinetime.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lbOnelinetime.setBounds(0, 0, 56, 15);
		lbOnelinetime.setText("OneLine Time (us)");
		  
		Spinner spOnelinetime = new Spinner(composite_1, SWT.BORDER);
		spOnelinetime.setMaximum(10000);
		spOnelinetime.setSelection(324);
		spOnelinetime.setDigits(2);
		spOnelinetime.setBounds(0, 0, 47, 22);
		
		Button btnScanSet = new Button(composite_1, SWT.NONE);
		btnScanSet.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				cpLEDScan.setEnabled(true);
				int scanTime = Integer.parseInt(spScantime.getText());
				int holdTime = Integer.parseInt(spHoldingtime.getText());;
				int rndWidth = cpLEDScan.getBounds().width;
				
				int select = spBLEDRatio.getSelection();
		        int digit = spBLEDRatio.getDigits();
		        double ratio = select / Math.pow(10, digit) / 100.0;
		        double onTime = rndWidth* 0/ 3.0 + (1-ratio) * rndWidth / 3.0;
				blueLED.setLocation((int)onTime, blueLED.getLocation().y);
				blueLED.setSize((int)(rndWidth*1/3.0 - onTime), blueLED.getSize().y);
				
				lbBlueOnTime.setText(String.valueOf((int)((scanTime + holdTime)*0 +(scanTime+holdTime)*(1-ratio))));
				lbBlueOffTime.setText(String.valueOf((int)((scanTime + holdTime)*1)));
				lbBlueLEDDuration.setText(String.valueOf((int)((scanTime + holdTime)*1 - (scanTime + holdTime)*0-(scanTime+holdTime)*(1-ratio))));
				
				select = spRLEDRatio.getSelection();
		        digit = spRLEDRatio.getDigits();
		        ratio = select / Math.pow(10, digit) / 100.0;
		        onTime = rndWidth* 1/ 3.0 + (1-ratio) * rndWidth / 3.0;
				redLED.setLocation((int)onTime, redLED.getLocation().y);
				redLED.setSize((int)(rndWidth*2/3.0 - onTime), redLED.getSize().y);
				
				lbRedOnTime.setText(String.valueOf((int)((scanTime + holdTime)*1 + (scanTime+holdTime)*(1-ratio))));
				lbRedOffTime.setText(String.valueOf((int)((scanTime + holdTime)*2)));
				lbRedLEDDuration.setText(String.valueOf((int)((scanTime + holdTime)*2 - (scanTime + holdTime)*1- (scanTime+holdTime)*(1-ratio))));
				
				select = spGLEDRatio.getSelection();
		        digit = spGLEDRatio.getDigits();
		        ratio = select / Math.pow(10, digit) / 100.0;
		        onTime = rndWidth*2/3.0 + (1-ratio) * rndWidth / 3.0;
				greenLED.setLocation((int)onTime, greenLED.getLocation().y);
				greenLED.setSize((int)(rndWidth*3/3.0 - onTime), greenLED.getSize().y);
				
				lbGreenOnTime.setText(String.valueOf((int)((scanTime + holdTime)*2 + (scanTime+holdTime)*(1-ratio))));
				lbGreenOffTime.setText(String.valueOf((int)((scanTime + holdTime)*3)));
				lbGreenLEDDuration.setText(String.valueOf((int)((scanTime + holdTime)*3 - (scanTime + holdTime)*2 -(scanTime+holdTime)*(1-ratio))));
			}
		});
		btnScanSet.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 2));
		btnScanSet.setText("Set");
		  
		Label lbRLEDRatio = new Label(composite_1, SWT.NONE);
		lbRLEDRatio.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lbRLEDRatio.setAlignment(SWT.RIGHT);
		lbRLEDRatio.setText("R-LED Ratio(%)");
		  
		spRLEDRatio = new Spinner(composite_1, SWT.BORDER);
		spRLEDRatio.setMaximum(1000);
		spRLEDRatio.setSelection(300);
		spRLEDRatio.setDigits(1);
		spRLEDRatio.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label lbGLEDRatio = new Label(composite_1, SWT.NONE);
		lbGLEDRatio.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lbGLEDRatio.setAlignment(SWT.RIGHT);
		lbGLEDRatio.setText("G-LED Ratio(%)");
		  
		spGLEDRatio = new Spinner(composite_1, SWT.BORDER);
		spGLEDRatio.setDigits(1);
		spGLEDRatio.setMaximum(1000);
		spGLEDRatio.setSelection(300);
		spGLEDRatio.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		  
		Label lbBLEDRatio = new Label(composite_1, SWT.NONE);
		lbBLEDRatio.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lbBLEDRatio.setAlignment(SWT.RIGHT);
		lbBLEDRatio.setText("B-LED Ratio(%)");
		  
		spBLEDRatio = new Spinner(composite_1, SWT.BORDER);
		spBLEDRatio.setMaximum(1000);
		spBLEDRatio.setSelection(300);
		spBLEDRatio.setDigits(1);
		spBLEDRatio.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		  
		cpLEDScan = new Composite(parent, SWT.NONE);
		cpLEDScan.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		cpLEDScan.setEnabled(false);
		GridLayout gl_cpLEDScan = new GridLayout(7, true);
		gl_cpLEDScan.verticalSpacing = 0;
		gl_cpLEDScan.marginWidth = 0;
		gl_cpLEDScan.horizontalSpacing = 0;
		cpLEDScan.setLayout(gl_cpLEDScan);
		cpLEDScan.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 7, 1));
  		new Label(cpLEDScan, SWT.NONE);
  		  
  		blueLED = new Composite(cpLEDScan, SWT.NONE);
  		blueLED.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
  		blueLED.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_BLUE));
  		GridLayout gl_blueLED = new GridLayout(2, false);
  		gl_blueLED.verticalSpacing = 2;
  		blueLED.setLayout(gl_blueLED);
  		  
  		lbBlueLEDDuration = new Label(blueLED, SWT.NONE);
  		lbBlueLEDDuration.setAlignment(SWT.CENTER);
  		lbBlueLEDDuration.setFont(SWTResourceManager.getFont("���� ����", 9, SWT.BOLD));
  		lbBlueLEDDuration.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
  		lbBlueLEDDuration.setText("144");
  		  
  		lbBlueOnTime = new Label(blueLED, SWT.NONE);
  		lbBlueOnTime.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
  		lbBlueOnTime.setLayoutData(new GridData(SWT.LEFT, SWT.BOTTOM, true, true, 1, 1));
  		lbBlueOnTime.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
  		lbBlueOnTime.setText("576");
  		  
		lbBlueOffTime = new Label(blueLED, SWT.NONE);
		lbBlueOffTime.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbBlueOffTime.setLayoutData(new GridData(SWT.RIGHT, SWT.BOTTOM, true, true, 1, 1));
		lbBlueOffTime.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lbBlueOffTime.setText("720");
  		  
		blueLED.addListener(SWT.DragDetect, new Listener() {
			public void handleEvent(Event e) {
		    	//System.out.println(String.format("(%d %d) (%d %d) (%d %d)", 
		    	//		e.x, e.y, 
		    	//		blueLED.getBounds().x, blueLED.getBounds().y, blueLED.getBounds().width, blueLED.getBounds().height));
		    	BlueLEDOnTime =  Integer.parseInt(lbBlueOnTime.getText());
		    	BlueLEDOffTime =  Integer.parseInt(lbBlueOffTime.getText());
			
		  		if(e.x < EDGE_THICK) {
					Tracker tracker = new Tracker(blueLED.getParent(), SWT.RESIZE | SWT.LEFT );
				        //tracker.setStippled(true);
					tracker.setStippled(true);
					//tracker.setCursor(new Cursor(parent.getDisplay(), SWT.CURSOR_SIZEE));
					tracker.addControlListener( new ControlAdapter() {
						@Override
					    public void controlResized(ControlEvent moveEvent) {
					   		Tracker tk = (Tracker)moveEvent.getSource();
							int delta = blueLED.getBounds().x - tk.getRectangles()[0].x; 
							int cBlueOnTime = BlueLEDOnTime - delta;
							lbBlueOnTime.setText(String.valueOf(cBlueOnTime));
							lbBlueLEDDuration.setText(String.valueOf(BlueLEDOffTime-cBlueOnTime));
					   }
					});
					Rectangle rect = blueLED.getBounds();
					tracker.setRectangles(new Rectangle[] { rect });
					if (tracker.open()) {
						Rectangle after = tracker.getRectangles()[0];
						blueLED.setBounds(after);
					}
					tracker.dispose();
		  		} else if (e.x > (blueLED.getBounds().width-EDGE_THICK) ) {
		  			Tracker tracker = new Tracker(blueLED.getParent(), SWT.RESIZE | SWT.RIGHT);
		    		//tracker.setStippled(true);
		  			tracker.setStippled(true);
					tracker.addControlListener( new ControlAdapter() {
						@Override
						public void controlResized(ControlEvent moveEvent) {
							Tracker tk = (Tracker)moveEvent.getSource();
							int delta = tk.getRectangles()[0].width - blueLED.getBounds().width;
							int cBlueOffTime = BlueLEDOffTime + delta;
							lbBlueOffTime.setText(String.valueOf(cBlueOffTime));
							lbBlueLEDDuration.setText(String.valueOf(cBlueOffTime-BlueLEDOnTime));   
						}
					});
					Rectangle rect = blueLED.getBounds();
					tracker.setRectangles(new Rectangle[] { rect });
					if (tracker.open()) {
						Rectangle after = tracker.getRectangles()[0];
						blueLED.setBounds(after);
					}
					tracker.dispose();
		  		} else {
		      		Tracker tracker = new Tracker(blueLED.getParent(), SWT.NONE | SWT.RIGHT | SWT.LEFT );
		      		tracker.setStippled(true);
		      		tracker.addControlListener( new ControlAdapter() {
				    	@Override
				    	public void controlMoved(ControlEvent moveEvent) {
				        	Tracker tk = (Tracker)moveEvent.getSource();
				        	int delta = tk.getRectangles()[0].x - blueLED.getBounds().x;
				        	lbBlueOnTime.setText(String.valueOf(BlueLEDOnTime + delta));
				        	lbBlueOffTime.setText(String.valueOf(BlueLEDOffTime + delta));
				        }
		      		});
				    Rectangle rect = blueLED.getBounds();
				    tracker.setRectangles(new Rectangle[] { rect });
				    if (tracker.open()) {
				    	Rectangle after = tracker.getRectangles()[0];
				        blueLED.setBounds(after);
				    }
				    tracker.dispose();
		  		}
		    }
	  	});
		new Label(cpLEDScan, SWT.NONE);
				  		  		  
		redLED = new Composite(cpLEDScan, SWT.NONE);
		redLED.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		redLED.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_RED));
		redLED.setLayout(new GridLayout(2, false));
		  
		lbRedLEDDuration = new Label(redLED, SWT.NONE);
		lbRedLEDDuration.setAlignment(SWT.CENTER);
		lbRedLEDDuration.setFont(SWTResourceManager.getFont("���� ����", 9, SWT.BOLD));
		lbRedLEDDuration.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		lbRedLEDDuration.setText("144");
					  
		lbRedOnTime = new Label(redLED, SWT.NONE);
		lbRedOnTime.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbRedOnTime.setLayoutData(new GridData(SWT.LEFT, SWT.BOTTOM, true, true, 1, 1));
		lbRedOnTime.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lbRedOnTime.setText(String.valueOf(RedLEDOnTime));
					  
		lbRedOffTime = new Label(redLED, SWT.NONE);
		lbRedOffTime.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbRedOffTime.setLayoutData(new GridData(SWT.RIGHT, SWT.BOTTOM, true, true, 1, 1));
		lbRedOffTime.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lbRedOffTime.setText(String.valueOf(RedLEDOffTime));
		redLED.addListener(SWT.DragDetect, new Listener() {
			public void handleEvent(Event e) {
				//System.out.println(String.format("(%d %d) (%d %d) (%d %d)", 
				//		e.x, e.y, 
				//		redLED.getBounds().x, redLED.getBounds().y, redLED.getBounds().width, redLED.getBounds().height));
	  		  	RedLEDOnTime =  Integer.parseInt(lbRedOnTime.getText());
	  		  	RedLEDOffTime =  Integer.parseInt(lbRedOffTime.getText());

	  		  	if(e.x < EDGE_THICK) {
	  		  		Tracker tracker = new Tracker(redLED.getParent(), SWT.RESIZE | SWT.LEFT );
					tracker.setStippled(false);
					tracker.addControlListener( new ControlAdapter() {
						@Override
						public void controlResized(ControlEvent moveEvent) {
							Tracker tk = (Tracker)moveEvent.getSource();
							int delta = redLED.getBounds().x - tk.getRectangles()[0].x; 
							int cRedOnTime = RedLEDOnTime - delta;
							//System.out.println(String.format("%d %d %d",cRedOnTime, RedLEDOffTime, delta));
							        	
				       		lbRedOnTime.setText(String.valueOf(cRedOnTime));
			        		lbRedLEDDuration.setText(String.valueOf(RedLEDOffTime-cRedOnTime));
				        }
					});

					Rectangle rect = redLED.getBounds();
					tracker.setRectangles(new Rectangle[] { rect });
					if (tracker.open()) {
						Rectangle after = tracker.getRectangles()[0];
						redLED.setBounds(after);
					}
					tracker.dispose();
				} else if (e.x > (redLED.getBounds().width-EDGE_THICK)) {
	  		  		Tracker tracker = new Tracker(redLED.getParent(), SWT.RESIZE | SWT.RIGHT);
					tracker.setStippled(false);
	  		  		tracker.addControlListener( new ControlAdapter() {
	  		  			@Override
	  		  			public void controlResized(ControlEvent moveEvent) {
							Tracker tk = (Tracker)moveEvent.getSource();
							int delta = tk.getRectangles()[0].width - redLED.getBounds().width;
							int cRedOffTime = RedLEDOffTime + delta;
							lbRedOffTime.setText(String.valueOf(cRedOffTime));
							lbRedLEDDuration.setText(String.valueOf(cRedOffTime-RedLEDOnTime));   
						}
					});
  		  		  	Rectangle rect = redLED.getBounds();
					tracker.setRectangles(new Rectangle[] { rect });
					if (tracker.open()) {
						Rectangle after = tracker.getRectangles()[0];
						redLED.setBounds(after);
					}
					tracker.dispose();
				} else {
  		  			Tracker tracker = new Tracker(redLED.getParent(), SWT.NONE | SWT.RIGHT | SWT.LEFT );
					tracker.setStippled(true);
					tracker.addControlListener( new ControlAdapter() {
						@Override
						public void controlMoved(ControlEvent moveEvent) {
							Tracker tk = (Tracker)moveEvent.getSource();
							int delta = tk.getRectangles()[0].x - redLED.getBounds().x;
							lbRedOnTime.setText(String.valueOf(RedLEDOnTime + delta));
							lbRedOffTime.setText(String.valueOf(RedLEDOffTime + delta));
						}
					});
					Rectangle rect = redLED.getBounds();
					tracker.setRectangles(new Rectangle[] { rect });
					if (tracker.open()) {
						Rectangle after = tracker.getRectangles()[0];
						redLED.setBounds(after);
					}
					tracker.dispose();
  		  		}
  		  	}
		});
  		new Label(cpLEDScan, SWT.NONE);
  		  		  		  		  
		greenLED = new Composite(cpLEDScan, SWT.NONE);
		greenLED.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		greenLED.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_GREEN));
		greenLED.setLayout(new GridLayout(2, false));

		lbGreenLEDDuration = new Label(greenLED, SWT.NONE);
		lbGreenLEDDuration.setAlignment(SWT.CENTER);
		lbGreenLEDDuration.setFont(SWTResourceManager.getFont("���� ����", 9, SWT.BOLD));
		lbGreenLEDDuration.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		lbGreenLEDDuration.setText("144");
		lbGreenLEDDuration.setEnabled(false);
		
		lbGreenOnTime = new Label(greenLED, SWT.NONE);
		lbGreenOnTime.setEnabled(false);
		/*
		lbGreenOnTime.addDragDetectListener(new DragDetectListener() {
			public void dragDetected(DragDetectEvent e) {
				//Label source=(Label)e.getSource();
				//source.getParent().dragDetect(e);
				
				//greenLED.dragDetect(e);
				greenLED.notifyListeners(SWT.DragDetect, e);
				System.out.println("dragDetect~~~");
		  		
				//Composite tmp = source.getParent();
			
				//System.out.println(source.getParent().toString());
				//System.out.println(source.getText());
				//source.getParent().dispatchEvent(e);
			}
		});
		*/
		lbGreenOnTime.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbGreenOnTime.setLayoutData(new GridData(SWT.LEFT, SWT.BOTTOM, true, false, 1, 1));
		lbGreenOnTime.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lbGreenOnTime.setText(String.valueOf(GreenLEDOnTime));
		  
		lbGreenOffTime = new Label(greenLED, SWT.NONE);
		lbGreenOffTime.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		  
		lbGreenOffTime.setLayoutData(new GridData(SWT.RIGHT, SWT.BOTTOM, true, true, 1, 1));
		lbGreenOffTime.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lbGreenOffTime.setText(String.valueOf(GreenLEDOffTime));
		lbGreenOffTime.setEnabled(false);
		//greenLED.getDisplay().addListener(SWT.DragDetect, new Listener() {
		//	public void handleEvent(Event e) {
  		greenLED.addDragDetectListener(new DragDetectListener() {
			public void dragDetected(DragDetectEvent e) {
		//	}
		//});
		//greenLED.addListener(SWT.DragDetect, new Listener() {
		//	public void handleEvent(Event e) {
				System.out.println("ok : " + e.x + ", "+ e.y);
				GreenLEDOnTime =  Integer.parseInt(lbGreenOnTime.getText());
		  		GreenLEDOffTime =  Integer.parseInt(lbGreenOffTime.getText());
									        
		  		if(e.x < EDGE_THICK) {
			  		Tracker tracker = new Tracker(greenLED.getParent(), SWT.RESIZE | SWT.LEFT );
			  		tracker.setStippled(true);
					tracker.addControlListener( new ControlAdapter() {
					@Override
					public void controlResized(ControlEvent moveEvent) {
						Tracker tk = (Tracker)moveEvent.getSource();
						int delta = greenLED.getBounds().x - tk.getRectangles()[0].x; 
						int cGreenOnTime = GreenLEDOnTime - delta;
						lbGreenOnTime.setText(String.valueOf(cGreenOnTime));
						lbGreenLEDDuration.setText(String.valueOf(GreenLEDOffTime-cGreenOnTime));
						}
					});
										        
					Rectangle rect = greenLED.getBounds();					     
					tracker.setRectangles(new Rectangle[] { rect });
											        
					if (tracker.open()) {
						Rectangle after = tracker.getRectangles()[0];
						greenLED.setBounds(after);
					}
					tracker.dispose();
			  	} else if(e.x > (greenLED.getBounds().width-EDGE_THICK)) {
			  		Tracker tracker = new Tracker(greenLED.getParent(), SWT.RESIZE | SWT.RIGHT );
					tracker.setStippled(true);
					tracker.addControlListener( new ControlAdapter() {
						@Override
						public void controlResized(ControlEvent moveEvent) {
							Tracker tk = (Tracker)moveEvent.getSource();
							int delta = tk.getRectangles()[0].width - greenLED.getBounds().width;
							int cGreenOffTime = GreenLEDOffTime + delta;
							lbGreenOffTime.setText(String.valueOf(cGreenOffTime));
							lbGreenLEDDuration.setText(String.valueOf(cGreenOffTime-GreenLEDOnTime));   
						}
					});
	
					Rectangle rect = greenLED.getBounds();
					tracker.setRectangles(new Rectangle[] { rect });
		
					if (tracker.open()) {
						Rectangle after = tracker.getRectangles()[0];
						greenLED.setBounds(after);
					}									        
					tracker.dispose();					
			  	} else {
			  		Tracker tracker = new Tracker(greenLED.getParent(), SWT.NONE | SWT.RIGHT | SWT.LEFT );
			        tracker.setStippled(true);
		        	tracker.addControlListener( new ControlAdapter() {
		        		@Override
		            	public void controlMoved(ControlEvent moveEvent) {
		        			Tracker tk = (Tracker)moveEvent.getSource();
		        			int delta = tk.getRectangles()[0].x - greenLED.getBounds().x;
		        			lbGreenOnTime.setText(String.valueOf(GreenLEDOnTime + delta));
		        			lbGreenOffTime.setText(String.valueOf(GreenLEDOffTime + delta));
		        		}
		        	});
		        	Rectangle rect = greenLED.getBounds();
		        	tracker.setRectangles(new Rectangle[] { rect });
		        	if (tracker.open()) {
		        		Rectangle after = tracker.getRectangles()[0];
		          		greenLED.setBounds(after);
		        	}
		        	tracker.dispose();
			  	}	
			}
		});
		new Label(cpLEDScan, SWT.NONE);
  
		Label label = new Label(cpLEDScan, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 7, 1));
  
		composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
  
		lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setText("LED Current");
  		  		  		  		  		  		  		  
  		text = new Text(composite, SWT.BORDER);
  		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
	}

	@PreDestroy
	public void dispose() {
	}

	@Focus
	public void setFocus() {
		// TODO	Set the focus to control
	}
}
