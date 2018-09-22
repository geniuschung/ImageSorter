import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;




public class ImageSorterEventListener implements MouseListener , SelectionListener, ShellListener ,WindowStateListener {
	private ImageSorter imageSorterSWT= null;
	public ImageSorterEventListener(ImageSorter imageSorter){
		this.imageSorterSWT = imageSorter;
	}
	@Override
	public void windowStateChanged(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void shellActivated(ShellEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void shellClosed(ShellEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void shellDeactivated(ShellEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void shellDeiconified(ShellEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void shellIconified(ShellEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void widgetSelected(SelectionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDoubleClick(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDown(MouseEvent e) {
		System.out.println(e.getSource().toString());
		if(e.getSource() == imageSorterSWT.getOrgDirDialog()){
			String selOrgDir = openDir();
			if(selOrgDir != null){
				
				imageSorterSWT.getOrgDirText().setText(selOrgDir);
				
			}
		}else if(e.getSource() == imageSorterSWT.getTarDirDialog()){
			String selTarDir = openDir();
			if(selTarDir != null){
				imageSorterSWT.getTargetDirText().setText(selTarDir);
			}
		}else if(e.getSource() == imageSorterSWT.getGoImageSort()){
			if(imageSorterSWT.getOrgDirText().getText()!=null ||
					!imageSorterSWT.getOrgDirText().getText().equals("") ||
					imageSorterSWT.getTargetDirText().getText() != null ||
					!imageSorterSWT.getTargetDirText().getText().equals("")){
				File orgFile = new File(imageSorterSWT.getOrgDirText().getText());
				File targetFile = new File(imageSorterSWT.getTargetDirText().getText());
				imageSorterSWT.getInfoText().setText(imageSorterSWT.getInfoText().getText()+"\n\n이미지 정리 진행중");
				ImageSorterService isService = new ImageSorterService(orgFile, targetFile);
				try{
					isService.runImageSort();
					imageSorterSWT.getInfoText().setText(imageSorterSWT.getInfoText().getText()+"\n\n이미지 정리 완료");
				}catch(Exception ex){
					imageSorterSWT.getInfoText().setText(imageSorterSWT.getInfoText().getText()+"\n\n이미지 정리중 오류 발생 \n작업을 완료하지 못했습니다.");
					ex.printStackTrace();
				}
			}else{
				imageSorterSWT.getInfoText().setText(imageSorterSWT.getInfoText().getText()+"\n\n디렉토리 정보를 입력하세요");
			}
			
		}
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseUp(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}	
	
	private String openDir(){
		//FileDialog fd = new FileDialog(imageSorterSWT.getShell(), SWT.Selection);
		DirectoryDialog dd = new  DirectoryDialog(imageSorterSWT.getShell(), SWT.Selection);
		dd.setText("Open");
		dd.setFilterPath("C:/");
        String selected = dd.open();
        return selected;
	}
	
}
