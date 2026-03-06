import java.util.ArrayList;
import java.util.List;

interface Document {
    String getDocumentType();  
    String getCountry();    
    String getFormat();      
    void   process();       
    boolean validate();       
}




class InvoiceDocument implements Document {
    private String country, format;
    public InvoiceDocument(String country, String format) {
        this.country = country; this.format = format;
    }
    @Override public String getDocumentType() { return "Electronic Invoice"; }
    @Override public String getCountry()      { return country; }
    @Override public String getFormat()       { return format; }
    @Override public boolean validate() {
        return format.equals(".pdf") || format.equals(".xml");
    }
    @Override public void process() {
        if (!validate()) { System.out.println("   ERROR: Invalid format for Invoice → " + format); return; }
        System.out.println("  ✅ Invoice processed       | " + country + " | " + format);
    }
}

class ContractDocument implements Document {
    private String country, format;
    public ContractDocument(String country, String format) {
        this.country = country; this.format = format;
    }
    @Override public String getDocumentType() { return "Legal Contract"; }
    @Override public String getCountry()      { return country; }
    @Override public String getFormat()       { return format; }
    @Override public boolean validate() {
        return format.equals(".pdf") || format.equals(".docx") || format.equals(".doc");
    }
    @Override public void process() {
        if (!validate()) { System.out.println("   ERROR: Invalid format for Contract → " + format); return; }
        System.out.println("   Contract processed      | " + country + " | " + format);
    }
}

class FinancialReport implements Document {
    private String country, format;
    public FinancialReport(String country, String format) {
        this.country = country; this.format = format;
    }
    @Override public String getDocumentType() { return "Financial Report"; }
    @Override public String getCountry()      { return country; }
    @Override public String getFormat()       { return format; }
    @Override public boolean validate() {
        return format.equals(".xlsx") || format.equals(".csv") || format.equals(".pdf");
    }
    @Override public void process() {
        if (!validate()) { System.out.println("   ERROR: Invalid format for Financial Report → " + format); return; }
        System.out.println("   Financial Report processed | " + country + " | " + format);
    }
}

class DigitalCertificate implements Document {
    private String country, format;
    public DigitalCertificate(String country, String format) {
        this.country = country; this.format = format;
    }
    @Override public String getDocumentType() { return "Digital Certificate"; }
    @Override public String getCountry()      { return country; }
    @Override public String getFormat()       { return format; }
    @Override public boolean validate() {
        return format.equals(".pdf");
    }
    @Override public void process() {
        if (!validate()) { System.out.println("   ERROR: Invalid format for Certificate → " + format); return; }
        System.out.println("  ✅ Certificate processed   | " + country + " | " + format);
    }
}

class TaxDeclaration implements Document {
    private String country, format;
    public TaxDeclaration(String country, String format) {
        this.country = country; this.format = format;
    }
    @Override public String getDocumentType() { return "Tax Declaration"; }
    @Override public String getCountry()      { return country; }
    @Override public String getFormat()       { return format; }
    @Override public boolean validate() {
        return format.equals(".pdf") || format.equals(".txt") || format.equals(".md");
    }
    @Override public void process() {
        if (!validate()) { System.out.println("   ERROR: Invalid format for Tax Declaration → " + format); return; }
        System.out.println("  ✅ Tax Declaration processed | " + country + " | " + format);
    }
}



abstract class DocumentFactory {

    public abstract Document createDocument(String documentType, String format);


    public void processDocument(String documentType, String format) {
        System.out.println("\n   Requesting: " + documentType + " [" + format + "]");
        Document doc = createDocument(documentType, format);
        if (doc == null) {
            System.out.println("   ERROR: Document type not supported → " + documentType);
            return;
        }
        doc.process();
    }
}




class ColombiaFactory extends DocumentFactory {
    @Override
    public Document createDocument(String type, String format) {
        switch (type.toLowerCase()) {
            case "invoice":     return new InvoiceDocument("Colombia", format);
            case "contract":    return new ContractDocument("Colombia", format);
            case "financial":   return new FinancialReport("Colombia", format);
            case "certificate": return new DigitalCertificate("Colombia", format);
            case "tax":         return new TaxDeclaration("Colombia", format);
            default:            return null;
        }
    }
}

class MexicoFactory extends DocumentFactory {
    @Override
    public Document createDocument(String type, String format) {
        switch (type.toLowerCase()) {
            case "invoice":     return new InvoiceDocument("Mexico", format);
            case "contract":    return new ContractDocument("Mexico", format);
            case "financial":   return new FinancialReport("Mexico", format);
            case "certificate": return new DigitalCertificate("Mexico", format);
            case "tax":         return new TaxDeclaration("Mexico", format);
            default:            return null;
        }
    }
}

class ArgentinaFactory extends DocumentFactory {
    @Override
    public Document createDocument(String type, String format) {
        switch (type.toLowerCase()) {
            case "invoice":     return new InvoiceDocument("Argentina", format);
            case "contract":    return new ContractDocument("Argentina", format);
            case "financial":   return new FinancialReport("Argentina", format);
            case "certificate": return new DigitalCertificate("Argentina", format);
            case "tax":         return new TaxDeclaration("Argentina", format);
            default:            return null;
        }
    }
}

class ChileFactory extends DocumentFactory {
    @Override
    public Document createDocument(String type, String format) {
        switch (type.toLowerCase()) {
            case "invoice":     return new InvoiceDocument("Chile", format);
            case "contract":    return new ContractDocument("Chile", format);
            case "financial":   return new FinancialReport("Chile", format);
            case "certificate": return new DigitalCertificate("Chile", format);
            case "tax":         return new TaxDeclaration("Chile", format);
            default:            return null;
        }
    }
}



class BatchProcessor {

    private List<String> successLog = new ArrayList<>();
    private List<String> errorLog   = new ArrayList<>();

    // Represents a single document request
    static class DocumentRequest {
        String country, documentType, format;
        DocumentRequest(String country, String documentType, String format) {
            this.country = country; this.documentType = documentType; this.format = format;
        }
    }


    private DocumentFactory getFactory(String country) {
        switch (country.toLowerCase()) {
            case "colombia":  return new ColombiaFactory();
            case "mexico":    return new MexicoFactory();
            case "argentina": return new ArgentinaFactory();
            case "chile":     return new ChileFactory();
            default:          return null;
        }
    }

    public void processBatch(List<DocumentRequest> requests) {
        System.out.println("\n╔══════════════════════════════════════════╗");
        System.out.println("║      GLOBALDOCS — BATCH PROCESSING        ║");
        System.out.println("╚══════════════════════════════════════════╝");
        System.out.println("   Total documents: " + requests.size());

        for (DocumentRequest req : requests) {
            try {
                DocumentFactory factory = getFactory(req.country);

                if (factory == null) {
                    String err = "Country not supported: " + req.country;
                    System.out.println("\n   " + err);
                    errorLog.add(err);
                    continue;
                }

                Document doc = factory.createDocument(req.documentType, req.format);

                if (doc == null) {
                    String err = "Unknown document type: " + req.documentType + " in " + req.country;
                    System.out.println("\n   " + err);
                    errorLog.add(err);
                    continue;
                }

                System.out.println("\n   [" + req.country + "] " + req.documentType + " " + req.format);
                doc.process();

                if (doc.validate()) {
                    successLog.add(req.country + " | " + req.documentType + " | " + req.format);
                } else {
                    errorLog.add("Invalid format: " + req.documentType + " → " + req.format);
                }

            } catch (Exception e) {
                String err = "Exception on " + req.documentType + ": " + e.getMessage();
                System.out.println("   EXCEPTION: " + err);
                errorLog.add(err);
            }
        }

        printReport();
    }

    private void printReport() {
        System.out.println("\n╔══════════════════════════════════════════╗");
        System.out.println("║            PROCESSING REPORT              ║");
        System.out.println("╚══════════════════════════════════════════╝");
        System.out.println("   Successful: " + successLog.size());
        for (String s : successLog) System.out.println("     → " + s);
        System.out.println("   Errors:     " + errorLog.size());
        for (String e : errorLog)   System.out.println("     → " + e);
        System.out.println("  Total: " + (successLog.size() + errorLog.size()));
    }
}

public class GlobalDocs {

    public static void main(String[] args) {

        System.out.println("════════ DEMO 1: Single Documents ════════");

        DocumentFactory colombia  = new ColombiaFactory();
        DocumentFactory mexico    = new MexicoFactory();
        DocumentFactory argentina = new ArgentinaFactory();
        DocumentFactory chile     = new ChileFactory();

        colombia.processDocument("invoice",     ".pdf");
        mexico.processDocument("contract",      ".docx");
        argentina.processDocument("financial",  ".xlsx");
        chile.processDocument("tax",            ".txt");
        colombia.processDocument("certificate", ".pdf");
    
        System.out.println("\n════════ DEMO 2: Error Handling ════════");
        colombia.processDocument("certificate", ".xlsx");  
        mexico.processDocument("invoice",       ".md");    
        chile.processDocument("unknown",        ".pdf");   

        System.out.println("\n════════ DEMO 3: Batch Processing ════════");

        List<BatchProcessor.DocumentRequest> batch = new ArrayList<>();


        batch.add(new BatchProcessor.DocumentRequest("Colombia",  "invoice",     ".pdf"));
        batch.add(new BatchProcessor.DocumentRequest("Mexico",    "contract",    ".docx"));
        batch.add(new BatchProcessor.DocumentRequest("Argentina", "financial",   ".csv"));
        batch.add(new BatchProcessor.DocumentRequest("Chile",     "certificate", ".pdf"));
        batch.add(new BatchProcessor.DocumentRequest("Colombia",  "tax",         ".md"));


        batch.add(new BatchProcessor.DocumentRequest("Brazil",   "invoice",  ".pdf"));  
        batch.add(new BatchProcessor.DocumentRequest("Mexico",   "invoice",  ".xlsx"));  
        batch.add(new BatchProcessor.DocumentRequest("Chile",    "xyz",      ".pdf"));  

        new BatchProcessor().processBatch(batch);
    }
}