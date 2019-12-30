package lab6;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVReader {
    BufferedReader reader;
    String delimiter;
    boolean hasHeader;
    List<String> columnLabels = new ArrayList<>();
    Map<String,Integer> columnLabelsToInt = new HashMap<>();
    String[] current;


    public CSVReader(Reader reader, String delimiter, boolean hasHeader){
        setState(reader, delimiter, hasHeader);
        if(hasHeader) {
            parseHeader();
        }

    }
    public CSVReader(String filename, String delimiter, boolean hasHeader) {
        Reader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        setState(reader, delimiter, hasHeader);
        if(hasHeader) {
            parseHeader();
        }
    }
    public CSVReader(String filename, String delimiter){
        this(filename, delimiter, true);
    }
    public CSVReader(String filename){
        this(filename, ";");
    }

    void setState(Reader reader, String delimiter, boolean hasHeader){
        this.reader = (BufferedReader)reader;
        this.delimiter = delimiter;
        this.hasHeader = hasHeader;
    }

    void parseHeader() {
        String line  = null;
        String[] header = new String[0];
        try {
            line = reader.readLine();
        } catch (Exception e /*IOException e*/) {
            e.printStackTrace();
            return;
        }

        if(line != null){
            header = line.split(String.format("%s(?=([^\"]*\"[^\"]*\")*[^\"]*$)", delimiter));
        }
        for(int columnIndex = 0; columnIndex < header.length; columnIndex++){
            String columnLabel = header[columnIndex];
            columnLabels.add(columnLabel);
            columnLabelsToInt.put(columnLabel, columnIndex);
        }
    }

    public List<String> getColumnLabels(){
        List<String> copy = new ArrayList<>(columnLabels);
        return copy;
    }

    public boolean next(){
        String line  = null;
        try {
            line = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(line != null) {
            current = line.split(String.format("%s(?=([^\"]*\"[^\"]*\")*[^\"]*$)", delimiter));
            return true;
        }
        return false;
    }

    public String get(int columnIndex) {
        if(isMissing(columnIndex)) {return "";}
        return current[columnIndex];
    }

    public String get(String name){
        int valueIndex = columnLabelsToInt.get(name);
        return get(valueIndex);
    }

    public int getInt(int index) throws NumberFormatException{
        String value = get(index);
        int valueAsInt = Integer.parseInt(value);
        return valueAsInt;
    }

    public int getInt(String name){
        int valueIndex = columnLabelsToInt.get(name);
        return getInt(valueIndex);
    }

    public double getDouble(int index) throws NumberFormatException{
        String value = get(index);
        double valueAsDouble = Double.parseDouble(value);
        return valueAsDouble;
    }

    public double getDouble(String name){
        int valueIndex = columnLabelsToInt.get(name);
        return getDouble(valueIndex);
    }

    public long getLong(int index) throws NumberFormatException{
        String value = get(index);
        long valueAsLong = Long.parseLong(value);
        return valueAsLong;
    }

    public long getLong(String name){
        int valueIndex = columnLabelsToInt.get(name);
        return getLong(valueIndex);
    }

    public int getRecordLength() {
        return current.length;
    }

    public boolean isMissing(int columnIndex){
        if(columnIndex >= getRecordLength() || current[columnIndex].equals("")){
            return true;
        } else {
            return false;
        }
    }

    public boolean isMissing(String columnLabel){
        int index = columnLabelsToInt.get(columnLabel);
        return isMissing(index);
    }
}