import java.io.*;
import java.util.Scanner;

public class ContentGenerator {

    // Read MarkDown file directory, read MarkDown file, get out the content for the file
    // to the standard output, get out the file to the standard output
    public static void main(String[] args){
        boolean error = true;
        do {
            try {
                Scanner scanner = new Scanner(System.in);
                String markDownFileName = scanner.next();
                String file = markDownFileReader(markDownFileName);
                System.out.println(headersToContent(markDownFileHeaders(markDownFileName)));
                System.out.println("\n" + file);
                error = false;
            } catch (FileNotFoundException e) {
                System.out.println("Invalid file name suplied, please try again. ");
            }
        }while(error);
    }

    //Take the array of headers and convert it to the content
    public static String headersToContent(String[] headers) {
        // Array of converted headers to content view
        String[] contents = new String[headers.length];
        // Array of int to count the order of headers and subheaders
        int[] count = {1, 1, 1, 1, 1, 1, 1};
        // An Int, to keep the type of the previous header,
        // we need to know if we have done all the subheaders
        int typeOfPreviousHeader = 0;
        // Convert headers to content view
        for (int i = 0; i < contents.length; i++) {
            contents[i] = "";
            int typeOfHeader = 0;
            while (headers[i].charAt(typeOfHeader) == '#') {
                if (typeOfHeader != 0)
                    contents[i] += "\t";
                typeOfHeader++;
            }
            if (typeOfHeader < typeOfPreviousHeader) {
                count[typeOfPreviousHeader] = 1;
            }
            contents[i] += count[typeOfHeader] + ". [" + headers[i].replaceAll("[#]+ ", "")
                    + "](#" + headers[i].toLowerCase().replaceAll("[#]+ ", "").replace(' ',
                    '-') + ")";
            count[typeOfHeader]++;
            typeOfPreviousHeader = typeOfHeader;
        }
        // Pack converted headers to content
        StringBuffer content = new StringBuffer("");
        for(String s : contents){
            content.append(s);
            content.append("\n");
        }
        return content.toString();
    }

    // Take name of the MarkDown file and return an array of headers
    public static String[] markDownFileHeaders(String MarkdownFileName) throws FileNotFoundException {
        File MarkDownFile = new File(MarkdownFileName);
        StringBuffer s = new StringBuffer();
        Scanner reader = new Scanner(MarkDownFile);
        while (reader.hasNextLine()) {
            String s1 = reader.nextLine();
            if (!s1.isEmpty() && s1.charAt(0) == '#') {
                s = s.append(s1);
                s = s.append("\n");
            }
        }
        reader.close();
        return s.toString().split("\n");
    }

    // Take the name of the MarkDown file and return and returns the text into the file
    public static String markDownFileReader(String MarkdownFileName) throws FileNotFoundException {
        File MarkDownFile = new File(MarkdownFileName);
        StringBuffer s = new StringBuffer();
        Scanner reader = new Scanner(MarkDownFile);
        while (reader.hasNextLine()) {
            s = s.append(reader.nextLine());
            s = s.append("\n");
        }
        reader.close();
        return s.toString();
    }
}
