import java.io.*;

public class ReadMaps {
    //Variable of type int,
    // used to control the rows and columns of the map.
    private int row=10,col=13;
    //String type variable, used to control the path of the map.
    private String path="221116\\src\\a\\maps\\";

    //Constructor.
    // The parameters are the number of maps
    // to be read and the two-dimensional array of maps to be read.
    public ReadMaps(int gate,int [][]rawMap) throws IOException {
        //String type variable, used to read the map.
        String filepath=path+"map"+gate+".txt";
        File file =new File(filepath);
        FileReader fr=null;
        BufferedReader br = null;

        fr=new FileReader(file);
        br=new BufferedReader(fr);
        //Read the data in the map.
        for (int i=0;i<row;i++)
        {
            //Read the data of the external map by row.
            String line=br.readLine();
            //The data of the external map is recorded in the array in bytes.
            byte[]bits=line.getBytes();
            for (int j = 0; j < col; j++) {
                //The data by row will be stored in a two-dimensional array in the form of numbers.
                rawMap[i][j]=bits[j]-48;
            }
        }

    }
}
