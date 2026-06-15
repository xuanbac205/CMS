package utils;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
public class ValidatorRegistering {
    private static Scanner scanner = new Scanner(System.in);

    private ValidatorRegistering(){
        
    }

    public static void setScanner(Scanner sc) {
        if (sc != null) {
            scanner = sc;
        }
    }

    public static int getInt(String messangeInfo, String messangeErrorOutOfRanger1,String messangeErrorOutOfRanger2,
            String messageErrorNumber,int min,int max){
        do {            
            try {
                System.out.print(messangeInfo);
                int number = Integer.parseInt(scanner.nextLine().trim());
                if(number>=min && number<=max){
                    return number;
                }else if(number> max) {
                    System.out.println(messangeErrorOutOfRanger1);
                }else if(number<min){
                    System.out.println(messangeErrorOutOfRanger2);
                }
            } catch (NumberFormatException e) {
                System.out.println(messageErrorNumber);
            }
        } while (true);
    }

    public static double getDouble(String messangeInfo, String messangeErrorOutOfRanger1,String messangeErrorOutOfRanger2,
            String messageErrorNumber,double min,double max){
        do {            
            try {
                System.out.print(messangeInfo);
                double number = Double.parseDouble(scanner.nextLine().trim());
                if(number>=min && number<=max){
                    return number;
                }else if(number> max) {
                    System.out.println(messangeErrorOutOfRanger1);
                }else if(number<min){
                    System.out.println(messangeErrorOutOfRanger2);
                }
            } catch (NumberFormatException e) {
                System.out.println(messageErrorNumber);
            }
        } while (true);
    }
    public static String getString(String messageInfo, String messageErrorFormat, final String REGEX){ //HExxxxxx  dd/MM/yyyy
        do {            
            System.out.print(messageInfo);
            String str = scanner.nextLine().trim();
            if(str.matches(REGEX)){
                return str;
            }else{
                System.out.println(messageErrorFormat);
            }
        } while (true);
    }
    public static Date getDate(String messangeInfo, String messangeErrorOutOfRanger, final String REGEX,
            String messageErrorDate,Date min,Date max){
            SimpleDateFormat dateFormat = new SimpleDateFormat(REGEX);
            dateFormat.setLenient(false);
        do {            
            System.out.print(messangeInfo);
            try {
                Date date = dateFormat.parse(scanner.nextLine().trim());
                if(date.compareTo(min)>=0 && date.compareTo(max)<=0){
                    return date;
                }else{
                    System.out.println(messangeErrorOutOfRanger);
                }
            } catch (Exception e) {
                System.out.println(messageErrorDate);
            }
        } while (true);
    }
}
