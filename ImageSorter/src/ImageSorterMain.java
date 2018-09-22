import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGDecodeParam;
import com.sun.image.codec.jpeg.JPEGImageDecoder;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

public class ImageSorterMain {
	public static final int TAG_TYPE_CREATE_DATE = 306;

	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new ImageSorterMain();
		
	}
	
	public ImageSorterMain(){
		try{
			File orgDir = new File("F:\\DCIM\\105OLYMP");
			File targetDir = new File("I:\\사진\\사진정리");
			File targetMoveDir = new File("I:\\사진\\동영상");
			
			if(!targetDir.exists()){
				targetDir.mkdir();
			}
			
			File [] files = orgDir.listFiles();
			int totCnt = files.length;
			int index = 0;
			for(File f : files){
				String fileName = f.getName();
				
				String [] fileNameSplit = fileName.split("[.]");
				String fileCreateDate = "";
				
				if(f.isFile()){
					if(fileNameSplit[fileNameSplit.length-1].equals("jpg")  
							|| fileNameSplit[fileNameSplit.length-1].equals("JPG")
							)
							//|| fileNameSplit[fileNameSplit.length-1].equals("avi")
							//|| fileNameSplit[fileNameSplit.length-1].equals("AVI"))
					{
						fileCreateDate = getImgFileCreateDate(f).substring(0,10).replaceAll(":","_" );
						String dateArr [] = fileCreateDate.split("_");
						Calendar cal = Calendar.getInstance();
						cal.set(Integer.parseInt(dateArr[0]), Integer.parseInt(dateArr[1])-1, Integer.parseInt(dateArr[2]));
						int weekNum = cal.get(Calendar.DAY_OF_WEEK);
						
						
						
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
//						System.out.println(dateArr[0]+"_"+dateArr[1]+"_"+dateArr[2]);
//						System.out.println("요일 :"+weekNum);
//						System.exit(0);
						
						File fileCreateDateDir = new File(targetDir+"\\"+fileCreateDate+"("+weekStr+")");
						
						
						if(!fileCreateDateDir.exists()){
							fileCreateDateDir.mkdir();
						}
						File targetFile = new File(fileCreateDateDir+"\\"+f.getName());
						if(targetFile.exists()){
							continue;
						}
						
						fileCopy(f, targetFile );
						System.out.println(++index +"/"+totCnt);
					}
				}
				
				
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
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
		String FileCreateDate = null;
		try{
			
			JPEGImageDecoder jpegDecoder = JPEGCodec.createJPEGDecoder(new FileInputStream(f));
			Metadata metadata = JpegMetadataReader.readMetadata(new FileInputStream(f));
			
			Iterable<Directory> iter = metadata.getDirectories();
			int i = 0;
			for(Directory dir : iter){
				//System.out.println(dir.getName() +" : "+dir.getDescription(i));
				
				Collection<Tag> tags = dir.getTags();
				for(Tag tag : tags){
					if(tag.getTagType() == this.TAG_TYPE_CREATE_DATE){
						FileCreateDate = tag.getDescription();
					}					
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return FileCreateDate;
	}

}
