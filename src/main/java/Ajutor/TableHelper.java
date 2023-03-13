package Ajutor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.util.Vector;

public class TableHelper {
    private WriteReadFile writeReadFile;
    public TableHelper(){
        writeReadFile=new WriteReadFile();
    }


    public void updateTableFromFile(JTable table, String file, String col1){
        DefaultTableModel model=new DefaultTableModel();
        String[] column=col1.split("\\.");
        for(String col:column ){
            model.addColumn(col);
        }
        try {
            writeReadFile.addRowFromFile(file,model);
        } catch (IOException e) {
            e.printStackTrace();
        }
        table.setModel(model);
    }


    public String getSelectedRows(JTable table) {
        StringBuilder sb = new StringBuilder();
        int[] selectedRows = table.getSelectedRows();
        for (int i : selectedRows) {
            for (int j = 0; j < table.getColumnCount(); j++) {
                Object value = table.getValueAt(i, j);
                sb.append(value);
                sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public String[] getAttr(JTable table){
        String split=getSelectedRows(table);
        return split.split(" ");
    }

    public void updateTableFromString(JTable table,String[] str,String col1){
        DefaultTableModel model=new DefaultTableModel();
        String[] column=col1.split("\\.");
        for(String col:column ){
            model.addColumn(col);
        }
        for(String s:str){
            String[] rev1=s.split(" ");
                Object row=rev1;
                model.addRow((String[]) row);
        }
        table.setAutoCreateRowSorter(true);
        table.setModel(model);

    }
}
