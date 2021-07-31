package kakaoTextsorter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;

import javax.swing.ImageIcon;

public class Main {
	 
    /** Runs a sample program that shows dropped files 
     * @throws IOException */
    public static void main(String[] args) throws IOException {
        javax.swing.JFrame frame = new javax.swing.JFrame("Talk Alignment");
        // javax.swing.border.TitledBorder dragBorder = new
        // javax.swing.border.TitledBorder( "Drop 'em" );
        
        URL imageURL = Main.class.getClassLoader().getResource("kakaoTextsorter/kakao_logo.png");
        ImageIcon img = new ImageIcon(imageURL);
        frame.setIconImage(img.getImage());


        
//        String path = Main.class.getResource("").getPath();
//        frame.setIconImage(ImageIO.read(new File(path+"kakao_logo.png")));
        
        
        final javax.swing.JTextArea text = new javax.swing.JTextArea();
        frame.getContentPane().add(new javax.swing.JScrollPane(text), java.awt.BorderLayout.CENTER);
        
        new FileDrop(System.out, text, /* dragBorder, */ new FileDrop.Listener() {
            public void filesDropped(java.io.File[] files) {
                for (int i = 0; i < files.length; i++) {
                    try {
                    	String inputFilePath = files[i].getCanonicalPath();
                        text.append(inputFilePath + "\n");
                        
                        if(isTextExtension(inputFilePath)) sort(inputFilePath);
                        else {
                        	text.append("확장자가 txt인 파일을 넣어주세요."+ "\n");
                        }
                        
                        
                        
                    } // end try
                    catch (java.io.IOException e) {
                    }
                } // end for: through each dropped file
            } // end filesDropped

			
        }); // end FileDrop.Listener
 
        frame.setBounds(100, 100, 300, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    } // end main
    
    public static boolean isTextExtension(String inputFilePath) {
    	String[] inputFilePathSplt = inputFilePath.split("\\\\");
        
        int lastIndex = inputFilePathSplt.length-1;
        String fileName = inputFilePathSplt[lastIndex];
        String[] fileNameSplt = fileName.split("\\.");
        
        if(fileNameSplt[1].equals("txt")) return true;
        else return false;
    }
    
    
    public static void sort(String inputFilePath) throws IOException{
        try{
            //파일 객체 생성
            File file = new File(inputFilePath);
            
            String[] inputFilePathSplt = inputFilePath.split("\\\\");
            int lastIndex = inputFilePathSplt.length-1;
            String fileName = inputFilePathSplt[lastIndex];
            String[] fileNameSplt = fileName.split("\\.");
            inputFilePathSplt[lastIndex] = fileNameSplt[0] +"_alignment."+ fileNameSplt[1]; 
            // "C:\\Users\\Donald.Lee\\Downloads\\write.txt"
            String outputFilePath="";
            for (int i = 0; i < inputFilePathSplt.length; i++) {
            	if(i==inputFilePathSplt.length-1) outputFilePath=outputFilePath+inputFilePathSplt[i];
            	else outputFilePath=outputFilePath+inputFilePathSplt[i]+"\\";
            	
			}
//            System.out.println(outputFilePath);
            File fileModified = new File(outputFilePath);
            
            
            //입력 버퍼 생성
            // 
//            BufferedReader bufReader = new BufferedReader(new FileReader(file));
//            BufferedWriter bufWriter = new BufferedWriter(new FileWriter(fileModified));
            BufferedReader bufReader =  new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));
            BufferedWriter bufWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileModified), "utf-8"));

 
            String line = "";
            while((line = bufReader.readLine()) != null){
                String[] lineSpltWithComma = line.split(", ");
                
                if(lineSpltWithComma.length>=2) {
                    String[] lineSpltWithColon = lineSpltWithComma[1].split(" : ");
                    bufWriter.newLine();
                    bufWriter.write(lineSpltWithColon[0]);
                    bufWriter.newLine();
                    if(lineSpltWithColon.length>=2) {
                        bufWriter.write("\t"+lineSpltWithColon[1]);
                    }
                    
                }
                else {
                    bufWriter.write("\t"+lineSpltWithComma[0]);
                }
                bufWriter.newLine();
                
            }
            //.readLine()은 끝에 개행문자를 읽지 않는다.            
            bufReader.close();
            bufWriter.close();
        }catch (FileNotFoundException e) {
            // TODO: handle exception
        }catch(IOException e){
            System.out.println(e);
        }
 
    }
 
}