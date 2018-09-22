import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.sun.image.codec.jpeg.JPEGImageDecoder;

import com.drew.metadata.Tag;
import com.sun.image.codec.jpeg.JPEGCodec;

public class ImageSorterService {
	
	public static final int TAG_TYPE_CREATE_DATE = 306;
	File orgDir = null;
	File targetDir = null;
	File targetMovieDir = null;
	
	
	public ImageSorterService(){}
	public ImageSorterService(File orgDir, File targetDir){
		this.orgDir = orgDir;
		this.targetDir = targetDir;
	}
	
	
	public ImageSorterService(File orgDir, File targetDir, File targetMovieDir){
		this.orgDir = orgDir;
		this.targetDir = targetDir;
		this.targetMovieDir = targetMovieDir;
	}
	
	public void runImageSort() throws Exception{
		
		setTargetDirectory();
		
		File [] files = orgDir.listFiles();
		int totCnt = files.length;
		int index = 0;
		for(File f : files){
			
			String fileCreateDate = "";
			
			if(f.isFile()){
				if(isImage(f)) {
					fileCreateDate = getImgFileCreateDate(f).substring(0,10).replaceAll(":","_" );
					
					int weekNum = getWeekNum(fileCreateDate);
					
					String weekStr = getWeek(weekNum);

					
					File fileCreateDateDir = createTargetDirectory(fileCreateDate, weekStr);
					
					File targetFile = new File(fileCreateDateDir+"\\"+f.getName());
					
					if(targetFile.exists()){
						continue;
					}
					
					fileCopy(f, targetFile );
					
				}
			}
			
			
			
		}
	}
	private boolean isImage(File f) {
		return f.getName().split("[.]")[f.getName().split("[.]").length-1].equals("jpg")  
				|| f.getName().split("[.]")[f.getName().split("[.]").length-1].equals("JPG");
	}
	private void setTargetDirectory() {
		if(!targetDir.exists()){
			targetDir.mkdir();
		}
	}
	private File createTargetDirectory(String fileCreateDate, String weekStr) {
		File fileCreateDateDir = new File(targetDir+"\\"+fileCreateDate+"("+weekStr+")");
		
		
		if(!fileCreateDateDir.exists()){
			fileCreateDateDir.mkdir();
		}
		return fileCreateDateDir;
	}
	
	private int getWeekNum(String fileCreateDate) {
		String dateArr [] = fileCreateDate.split("_");
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(dateArr[0]), Integer.parseInt(dateArr[1])-1, Integer.parseInt(dateArr[2]));
		int weekNum = cal.get(Calendar.DAY_OF_WEEK);
		return weekNum;
	}
	
	private String getWeek(int weekNum) {
		String weekStr = "";
		switch(weekNum){
			case Calendar.SUNDAY : weekStr = "일요일"; break;
			case Calendar.MONDAY : weekStr = "월요일"; break;
			case Calendar.TUESDAY : weekStr = "화요일"; break;
			case Calendar.WEDNESDAY : weekStr = "수요일"; break;
			case Calendar.THURSDAY : weekStr = "목요일"; break;
			case Calendar.FRIDAY : weekStr = "금요일"; break;
			case Calendar.SATURDAY : weekStr = "토요일"; break;
			
			
		}
		return weekStr;
	}
	
	
	public boolean fileCopy(File orgFile, File targetFile){
		boolean result = false;
		try{
			FileInputStream is = new FileInputStream(orgFile);
			FileOutputStream out = new FileOutputStream(targetFile);
			
			byte[] byteArray = new byte[1024];   
			int readLength = 0 ;
			//isr.read(byteArray, 0, byteArray.length)
			
			while ( (readLength = is.read(byteArray,0,byteArray.length)) != -1 ){
				
				out.write(byteArray, 0, readLength);   
			}   
			out.flush();  
			out.close(); 
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	public String getImgFileCreateDate(File f){
		String fileCreateDate = null;
		try{
			
			//JPEGImageDecoder jpegDecoder = JPEGCodec.createJPEGDecoder(new FileInputStream(f));
			Metadata metadata = JpegMetadataReader.readMetadata(new FileInputStream(f));
			
			Iterable<Directory> iter = metadata.getDirectories();
			int i = 0;
			for(Directory dir : iter){
				//System.out.println(dir.getName() +" : "+dir.getDescription(i));
				
				Collection<Tag> tags = dir.getTags();
				for(Tag tag : tags){
					if(tag.getTagType() == this.TAG_TYPE_CREATE_DATE){
						fileCreateDate = tag.getDescription();
					}					
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if (fileCreateDate == null) {
			fileCreateDate = noMetaDataImageSetFileData(f, fileCreateDate);
		}
		return fileCreateDate;
	}
	private String noMetaDataImageSetFileData(File f, String fileCreateDate) {
		try {
			BasicFileAttributes attrs = Files.readAttributes(f.toPath(), BasicFileAttributes.class);
		    FileTime time = attrs.creationTime();
		    
		    String pattern = "yyyy:MM:dd hh24:MM:ss";
		    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			
		    fileCreateDate = simpleDateFormat.format( new Date( time.toMillis() ) );
		}catch(Exception e) {
			e.printStackTrace();
		}
		return fileCreateDate;
	}

}
