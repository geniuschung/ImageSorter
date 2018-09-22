import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;




public class ImageSorter {

	protected Shell shell;
	private Text orgDirLabel;
	private Text orgDirText;
	private Text targetDirLabel;
	private Text targetDirText;
	private Button orgDirDialog;
	private Button tarDirDialog;
	private ImageSorterEventListener evtListener;
	private Text infoText;
	private Button goImageSort;
	
	

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ImageSorter window = new ImageSorter();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		evtListener = new ImageSorterEventListener(this);
		createContents();
		shell.open();
		shell.layout();
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(564, 268);
		shell.setText("이미지 Sorter");
		shell.setLayout(new FormLayout());
		
		orgDirLabel = new Text(shell, SWT.BORDER | SWT.READ_ONLY);
		orgDirLabel.setText("\uC6D0\uBCF8\uC774\uBBF8\uC9C0\uACBD\uB85C");
		FormData fd_orgDirLabel = new FormData();
		fd_orgDirLabel.left = new FormAttachment(0, 10);
		orgDirLabel.setLayoutData(fd_orgDirLabel);
		
		orgDirText = new Text(shell, SWT.BORDER);
		fd_orgDirLabel.top = new FormAttachment(orgDirText, 0, SWT.TOP);
		FormData fd_orgDirText = new FormData();
		fd_orgDirText.left = new FormAttachment(orgDirLabel, 22);
		fd_orgDirText.top = new FormAttachment(0, 10);
		orgDirText.setLayoutData(fd_orgDirText);
		
		orgDirDialog = new Button(shell, SWT.NONE);
		fd_orgDirText.right = new FormAttachment(orgDirDialog, -1);
		FormData fd_orgDirDialog = new FormData();
		fd_orgDirDialog.top = new FormAttachment(0, 10);
		fd_orgDirDialog.right = new FormAttachment(100, -10);
		orgDirDialog.setLayoutData(fd_orgDirDialog);
		orgDirDialog.setText("원본파일 경로");
		
		
		targetDirLabel = new Text(shell, SWT.BORDER | SWT.READ_ONLY);
		targetDirLabel.setText("\uD0C0\uAC9F\uC774\uBBF8\uC9C0\uACBD\uB85C");
		FormData fd_targetDirLabel = new FormData();
		fd_targetDirLabel.top = new FormAttachment(orgDirLabel, 16);
		fd_targetDirLabel.right = new FormAttachment(orgDirLabel, 0, SWT.RIGHT);
		targetDirLabel.setLayoutData(fd_targetDirLabel);
		
		targetDirText = new Text(shell, SWT.BORDER);
		FormData fd_targetDirText = new FormData();
		fd_targetDirText.right = new FormAttachment(orgDirText, 0, SWT.RIGHT);
		fd_targetDirText.bottom = new FormAttachment(targetDirLabel, 0, SWT.BOTTOM);
		fd_targetDirText.left = new FormAttachment(orgDirText, 0, SWT.LEFT);
		targetDirText.setLayoutData(fd_targetDirText);
		
		tarDirDialog = new Button(shell, SWT.NONE);
		tarDirDialog.setText("\uD0C0\uAC9F\uD30C\uC77C \uACBD\uB85C");
		FormData fd_tarDirDialog = new FormData();
		fd_tarDirDialog.bottom = new FormAttachment(targetDirLabel, 0, SWT.BOTTOM);
		fd_tarDirDialog.left = new FormAttachment(orgDirDialog, 0, SWT.LEFT);
		tarDirDialog.setLayoutData(fd_tarDirDialog);
		
		infoText = new Text(shell, SWT.BORDER);
		FormData fd_infoText = new FormData();
		fd_infoText.right = new FormAttachment(orgDirDialog, 0, SWT.RIGHT);
		fd_infoText.bottom = new FormAttachment(targetDirLabel, 81, SWT.BOTTOM);
		fd_infoText.top = new FormAttachment(targetDirLabel, 25);
		fd_infoText.left = new FormAttachment(orgDirLabel, 0, SWT.LEFT);
		infoText.setLayoutData(fd_infoText);
		
		goImageSort = new Button(shell, SWT.NONE);
		FormData fd_goImageSort = new FormData();
		fd_goImageSort.bottom = new FormAttachment(100, -10);
		fd_goImageSort.right = new FormAttachment(orgDirDialog, 0, SWT.RIGHT);
		goImageSort.setLayoutData(fd_goImageSort);
		goImageSort.setText("\uC774\uBBF8\uC9C0 \uC815\uB9AC \uC2DC\uC791");
		
		
		orgDirDialog.addMouseListener(evtListener);
		goImageSort.addMouseListener(evtListener);
		tarDirDialog.addMouseListener(evtListener);

	}

	public Text getOrgDirText() {
		return orgDirText;
	}

	public void setOrgDirText(Text orgDirText) {
		this.orgDirText = orgDirText;
	}

	public Text getTargetDirText() {
		return targetDirText;
	}

	public void setTargetDirText(Text targetDirText) {
		this.targetDirText = targetDirText;
	}

	public Button getOrgDirDialog() {
		return orgDirDialog;
	}

	public void setOrgDirDialog(Button orgDirDialog) {
		this.orgDirDialog = orgDirDialog;
	}

	public Button getTarDirDialog() {
		return tarDirDialog;
	}

	public void setTarDirDialog(Button tarDirDialog) {
		this.tarDirDialog = tarDirDialog;
	}

	public Button getGoImageSort() {
		return goImageSort;
	}

	public void setGoImageSort(Button goImageSort) {
		this.goImageSort = goImageSort;
	}

	public Text getInfoText() {
		return infoText;
	}

	public void setInfoText(Text infoText) {
		this.infoText = infoText;
	}

	public Shell getShell() {
		return shell;
	}

	public void setShell(Shell shell) {
		this.shell = shell;
	}
}
