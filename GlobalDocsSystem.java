import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.Queue;

class Document {

    private String name;
    private String type;
    private String country;

    public Document(String name, String type, String country) {
        this.name = name;
        this.type = type;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getCountry() {
        return country;
    }
}


interface DocumentProcessor {

    String process(Document document);

}


class ColombiaProcessor implements DocumentProcessor {

    public String process(Document document) {

        if(document.getType().equals("pdf") || document.getType().equals("docx")){
            return "Colombia: Document " + document.getName() + " processed successfully.";
        }

        return "Colombia: Format not allowed.";
    }

}

class MexicoProcessor implements DocumentProcessor {

    public String process(Document document) {

        if(document.getType().equals("pdf") || document.getType().equals("xlsx")){
            return "Mexico: Document " + document.getName() + " processed successfully.";
        }

        return "Mexico: Format not allowed.";
    }

}

class ArgentinaProcessor implements DocumentProcessor {

    public String process(Document document) {

        if(document.getType().equals("doc") || document.getType().equals("txt")){
            return "Argentina: Document " + document.getName() + " processed successfully.";
        }

        return "Argentina: Format not allowed.";
    }

}

class ChileProcessor implements DocumentProcessor {

    public String process(Document document) {

        if(document.getType().equals("pdf") || document.getType().equals("csv")){
            return "Chile: Document " + document.getName() + " processed successfully.";
        }

        return "Chile: Format not allowed.";
    }

}


class ProcessorFactory {

    public static DocumentProcessor createProcessor(String country){

        switch(country){

            case "Colombia":
                return new ColombiaProcessor();

            case "Mexico":
                return new MexicoProcessor();

            case "Argentina":
                return new ArgentinaProcessor();

            case "Chile":
                return new ChileProcessor();

            default:
                throw new IllegalArgumentException("Country not supported");
        }
    }

}


class DocumentQueueManager {

    private Queue<Document> queue = new LinkedList<>();

    public void addDocument(Document doc){

        queue.add(doc);

    }

    public String processDocuments(){

        StringBuilder result = new StringBuilder();

        while(!queue.isEmpty()){

            Document doc = queue.poll();

            try{

                DocumentProcessor processor =
                        ProcessorFactory.createProcessor(doc.getCountry());

                result.append(processor.process(doc)).append("\n");

            }catch(Exception e){

                result.append("Error processing document: ")
                        .append(doc.getName()).append("\n");

            }

        }

        return result.toString();
    }

}


public class GlobalDocsSystem extends JFrame {

    private JTextField nameField;
    private JComboBox<String> typeBox;
    private JComboBox<String> countryBox;
    private JTextArea outputArea;

    private DocumentQueueManager manager = new DocumentQueueManager();

    public GlobalDocsSystem(){

        setTitle("GlobalDocs Document Processing System");
        setSize(650,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4,2));

        panel.add(new JLabel("Document Name"));

        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Document Type"));

        typeBox = new JComboBox<>(new String[]{
                "pdf","doc","docx","md","csv","txt","xlsx"
        });

        panel.add(typeBox);

        panel.add(new JLabel("Country"));

        countryBox = new JComboBox<>(new String[]{
                "Colombia","Mexico","Argentina","Chile"
        });

        panel.add(countryBox);

        JButton addButton = new JButton("Add Document");
        JButton processButton = new JButton("Process Batch");

        panel.add(addButton);
        panel.add(processButton);

        add(panel,BorderLayout.NORTH);

        outputArea = new JTextArea();
        JScrollPane scroll = new JScrollPane(outputArea);

        add(scroll,BorderLayout.CENTER);

        addButton.addActionListener((ActionEvent e) -> addDocument());
        processButton.addActionListener((ActionEvent e) -> processDocuments());
    }

    private void addDocument(){

        String name = nameField.getText();
        String type = (String) typeBox.getSelectedItem();
        String country = (String) countryBox.getSelectedItem();

        if(name.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please enter document name");
            return;
        }

        Document doc = new Document(name,type,country);

        manager.addDocument(doc);

        outputArea.append("Document added to queue: " + name + "\n");

        nameField.setText("");
    }

    private void processDocuments(){

        outputArea.append("\nProcessing documents...\n");

        String result = manager.processDocuments();

        outputArea.append(result);

        outputArea.append("\nBatch processing finished\n\n");

    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            new GlobalDocsSystem().setVisible(true);

        });

    }

}