import java.util.ArrayList;
import java.io.*;
import java.util.Collections;
import java.util.Scanner;

class main {

    static String parentFolder = "E:\\Music";
    static String saveLocation = "E:\\Music\\!BandcampDownloader\\last";

    public static void main(String[] args) {
        ArrayList<String> currentSet = findFiles();
        ArrayList<String> savedSet = loadFiles();
        ArrayList<String> compared;

        String choice;
        Scanner input = new Scanner(System.in);
        while(true){
            System.out.println("(a) to compare, (b) to overwrite");
            choice = input.nextLine();
            choice.toLowerCase();
            switch(choice){
                case "a":
                    compared = compare(currentSet, savedSet);
                    for (String i : compared){
                        System.out.println(i);
                    }
                    System.out.println();
                    break;
                case "b":
                    saveFiles(currentSet);
                    System.out.println("done");
                    System.exit(1);
            }
        }
    }

    static ArrayList<String> findFiles(){
        ArrayList<String> out = new ArrayList<String>();
        File fParentFolder = new File(parentFolder);
        System.out.println("Scanning " + fParentFolder.getAbsolutePath());
        System.out.println("Folder exists: " + fParentFolder.exists());
        File[] folderList = fParentFolder.listFiles();
        File[] folderList2;
        File[] folderList3;
        System.out.println("Number of files/folders: " + folderList.length);
        System.out.println("-----------------------------------");
        for(int i=0; i<folderList.length; i++){
            if(folderList[i].isDirectory() && !folderList[i].getName().contains("!")){
                if(!folderList[i].getName().contains("ignore")){
                    folderList2 = folderList[i].listFiles();
                    for(int f=0; f<folderList2.length; f++){
                        if (folderList2[f].isDirectory()){
                            folderList3 = folderList2[f].listFiles();
                            for(int j=0; j<folderList3.length; j++){
                                //if(folderList3[j].isDirectory() && folderList3[j].list().length < 2){
                                //  System.out.printLineln(folderList3[j].getAbsolutePath() + "\t\t");
                                //done by folders, would need anotehr layer to do by files
                                if(!folderList3[j].isDirectory()) {
                                    System.out.println("ignored " + folderList3[j].getAbsolutePath());
                                } else {
                                    out.add("E" + folderList3[j].getAbsolutePath().substring(1));
                                }
                            }
                        } else {
                            System.out.println("ignored " + folderList2[f].getAbsolutePath());
                        }
                    }
                } else {
                    System.out.println("ignored " + folderList[i].getAbsolutePath());
                }
            }
        }
        return out;
    }

    public static ArrayList<String> loadFiles(){
        ArrayList<String> out = new ArrayList<String>();
        File savedProg = new File(saveLocation);
        if (!savedProg.exists()) {
            try {
                savedProg.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String savedProgOut = null;
        BufferedReader savedProgRead = null;

        try {
            savedProgRead = new BufferedReader(new FileReader(savedProg));
            while ((savedProgOut = savedProgRead.readLine()) != null) {
                out.add(savedProgOut);
            }
            savedProgRead.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out;
    }

    public static void saveFiles(ArrayList<String> input){
        try {
            PrintWriter pw = new PrintWriter(saveLocation, "UTF-8");
            for (int i = 0; i < input.size(); i++) {
                pw.println(input.get(i));
            }
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<String> compare(ArrayList<String> currentSet, ArrayList<String> savedSet) {
        ArrayList<String> output = new ArrayList<>();

        if (currentSet.equals(savedSet)){
            output.add("no change");
            return output;
        }
        output.add("----------------------------------------------\nCopy from PC\n----------------------------------------------");
        for (String i : currentSet){
            if (!savedSet.contains(i)){
                output.add(i);
            }
        }
        output.add("----------------------------------------------\nDelete from Ipod\n----------------------------------------------");
        for (String i : savedSet){
            if (!currentSet.contains(i)){
                output.add(i);
            }
        }

        return output;
    }
}