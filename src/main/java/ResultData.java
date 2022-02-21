
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;


import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ResultData {

    private AtomicInteger successfulReq;
    private AtomicInteger failedReq;
    private AtomicInteger totalReq;
    private List<String[]> dataLines;

    public ResultData() {

    }

    public ResultData(AtomicInteger successfulReq, AtomicInteger failedReq, AtomicInteger totalReq, List<String[]> dataLines) {
        this.successfulReq = successfulReq;
        this.failedReq = failedReq;
        this.totalReq = totalReq;
        this.dataLines = dataLines;
    }

    public void addDataLine(String[] dataLine) {
        dataLines.add(dataLine);
    }

    public void addSuccessfulReq(int delta) {
        successfulReq.addAndGet(delta);
    }

    public void addFailedReq(int delta) {
        failedReq.addAndGet(delta);
    }

    public void addTotalReq(int delta) {
        totalReq.addAndGet(delta);
    }

    public AtomicInteger getSuccessfulReq() {
        return successfulReq;
    }

    public AtomicInteger getFailedReq() {
        return failedReq;
    }

    public AtomicInteger getTotalReq() {
        return totalReq;
    }

    public List<String[]> getDataLines() {
        return dataLines;
    }

    public void writeToCsvFile() {
        try {
            FileWriter sw = new FileWriter("outputData.csv");
            CSVPrinter printer = new CSVPrinter(sw, CSVFormat.DEFAULT);

            // adding header to csv -> {start time, request type (ie POST), latency, response code}.
            String[] header = {"Start Time", "Request Type", "Latency", "Response Code"};
            printer.printRecord(header);
            //add data line by line
            for (int i = 0; i < dataLines.size(); i++) {
                printer.printRecord(dataLines.get(i));
            }
            printer.flush();
            printer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
