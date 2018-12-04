
import static BinaryImage.convertBIXtoRLE;
import static BinaryImage.convertRLEtoBIX;
import static BinaryImage.flipHoriz;
import static BinaryImage.flipVert;
import static BinaryImage.readFromFile;
import static BinaryImage.rotateLeft;
import static BinaryImage.rotateRight;
import java.io.IOException;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Brett Kriz
 */
public class Debug {
    public static void maintest(){
        Scanner in = new Scanner(System.in);
            
            String src = System.getProperty("user.dir");
            out(src);
            out("~~~~~~~~~~~~~~~~~");
            String BIX1 = "BIX 7 13\n" +
                        "0001000\n" +
                        "0001000\n" +
                        "0001000\n" +
                        "0001000\n" +
                        "0001000\n" +
                        "0001000\n" +
                        "0001111\n" +
                        "0000000\n" +
                        "0000000\n" +
                        "0000000\n" +
                        "0000000\n" +
                        "0000000\n" +
                        "0000000";
            String BIX2 = "BIX 20 18\n" +
                        "00000000000000000000\n" +
                        "00000001111110000000\n" +
                        "00000111111111100000\n" +
                        "00001111111111110000\n" +
                        "00011111111111111000\n" +
                        "00011111111111111000\n" +
                        "00111111111111111100\n" +
                        "00111111111111111100\n" +
                        "00111111111111111100\n" +
                        "00111111111111111100\n" +
                        "00111111111111111100\n" +
                        "00111111111111111100\n" +
                        "00011111111111111000\n" +
                        "00011111111111111000\n" +
                        "00001111111111110000\n" +
                        "00000111111111100000\n" +
                        "00000001111110000000\n" +
                        "00000000000000000000";
            String BIX3 = "BIX 7 13\n" +
                        "0000000\n" +
                        "0001100\n" +
                        "0001000\n" +
                        "0001000\n" +
                        "0001000\n" +
                        "0001000\n" +
                        "0001111\n" +
                        "0000000\n" +
                        "0000000\n" +
                        "0000000\n" +
                        "0000000\n" +
                        "0000000\n" +
                        "0000000"; // 13
            String BIX4 = "BIX 13 7\n" + // Is RLE3
                        "0000001000000\n" +
                        "0000001000000\n" +
                        "0000001000010\n" +
                        "0000001111110\n" +
                        "0000000000000\n" +
                        "0000000000000\n" +
                        "0000000000000";// 7
            String RLE1 = "RLE 20 18\n" +
        "9090969039019903790569055907490749074907490749075905690579039901903690909";
            String RLE2 = "RLE 7 13\n" +
                    "90116161616162909090908"; // As given in graded sheet B
            String RLE3 = "RLE 13 7\n"+
                    "909090519036909090904";
            String BIXf1 = "\\" + "test.txt";
            
            //safeTest1(RLE1,"toRLE_Test1.txt");
            //System.out.println(repeatChar('O',6));

            //
            safeReadOut(safeTest2(BIX3,"toRLE_Test3.txt"));
            safeReadOut(safeTest2(BIX2,"toRLE_Test2.txt"));
            safeReadOut(safeTest2(BIX4,"toRLE_Test4.txt"));
            safeReadOut(safeTest1("toRLE_Test3.txt","backtoBIX_Test3.txt"));
            safeReadOut(safeTest1("toRLE_Test2.txt","backtoBIX_Test2.txt"));
            
            safeReadOut(safeTest1(RLE1,"toBIX_Test1.txt"));
            safeReadOut(safeTest1(RLE2,"toBIX_Test2.txt"));
            safeReadOut(safeTest1(RLE3,"toBIX_Test3.txt"));
            safeReadOut(safeTest2("toBIX_Test2.txt","backtoRLE_Test2.txt"));
            safeReadOut(safeTest2("toBIX_Test3.txt","backtoRLE_Test3.txt"));
                    
            //*/
            
            /*
            System.out.println(  );
            // Lets test
            String f1 = "HorizFlip-result.txt", f2 = "VertFlip-result.txt";
            String f3 = "RightTurn-result.txt", f4 = "LeftTurn-result.txt";
            out("TESTING Horiz FLIP <>");
            safeTest3(BIX3,f1);//FLIPHoriz
            safeReadOut(f1);
            Pause();
            safeTest3(f1,f1);//FLIP!agian
            safeReadOut(f1);
            out("~~Horiz FLIP TEST DONE~~");
            //Pause();
            out("~~Vert Flip TEST <>~~");
            safeTest4(BIX3,f2);//flipVert
            safeReadOut(f2);
            Pause();
            safeTest4(f2,f2);//flipVert AGIAn
            safeReadOut(f2);
            out("~~Vert Flip TEST DONE~~");
            Pause();
            out("Turn test on: ");
            out(BIX3);
            out("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            safeTest5(BIX3,f3);// RIGHT 1
            safeReadOut(f3);
            safeTest5(f3,f3);// RIGHT 2
            safeReadOut(f3);
            safeTest5(f3,f3);// RIGHT 3
            safeReadOut(f3);
            safeTest5(f3,f3);// RIGHT 4
            safeReadOut(f3);
            out("~~ Done Rotating Right ~~");
            //Pause();
            out("~~ NOW, LETS GO LEFT ~~");
            safeTest6(BIX3,f4);// LEFT 1
            safeReadOut(f4);
            safeTest6(f4,f4);// LEFT 2 
            safeReadOut(f4);
            safeTest6(f4,f4);// LEFT 3
            safeReadOut(f4);
            safeTest6(f4,f4);// LEFT 4
            safeReadOut(f4);
            out(">> DONE <<");
            //*/
    }
    /**
         * Pause()<p>
         * Yes.
         */
        private static void Pause(){
            if (!DEBUGING_ON){
                return;
            }
            
            Scanner in = new Scanner(System.in);
            System.out.println();
            System.out.print("Press any Enter to continue... ");
            in.next();
            System.out.println();
        }
        private static String safeReadOut(String arg){
            out("");
            String ans = "";
            try{
                ans = readFromFile(arg);
                out(ans);
            }catch (Exception e){
                e.printStackTrace();
                
            }finally{
                return ans;
            }
            
        }
        private static String safeRead(String arg){
            //out("");
            String ans = "";
            try{
                ans = readFromFile(arg);
                //out(ans);
            }catch (Exception e){
                e.printStackTrace();
                
            }finally{
                return ans;
            }
            
        }
        private static String safeTest1(String in, String out){// RLE2BIX
            try{
                convertRLEtoBIX(in, out);
            }catch (Exception e){
                System.out.println("ERROR~!");
                e.printStackTrace();
            }finally{
                return out;
            }
        }
        private static String safeTest2(String in, String out){// BIX2RLE
            try{
                convertBIXtoRLE(in, out);
            }catch (IOException e){
                System.out.println("ERROR~!");
                e.printStackTrace();
            }finally{
                return out;
            }
            
        }
        private static String safeTest3(String in, String out){// flipHoriz
            try{
                flipHoriz(in, out);
            }catch (Exception e){
                System.out.println("ERROR~!");
                e.printStackTrace();
            }finally{
                return out;
            }
            
        }
        private static String safeTest4(String in, String out){// flipVert
            try{
                flipVert(in, out);
            }catch (Exception e){
                System.out.println("ERROR~!");
                e.printStackTrace();
            }finally{
                return out;
            }
            
        }
        private static String safeTest5(String in, String out){// rotateRight
            try{
                rotateRight(in, out);
            }catch (Exception e){
                System.out.println("ERROR~!");
                e.printStackTrace();
            }finally{
                return out;
            }
            
        }
        private static String safeTest6(String in, String out){// rotateLeft
            try{
                rotateLeft(in, out);
            }catch (Exception e){
                System.out.println("ERROR~!");
                e.printStackTrace();
            }finally{
                return out;
            }
            
        }
}
