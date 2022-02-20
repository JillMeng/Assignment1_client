import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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

//    public void writeToCsvFile(String filePath)
//    {
//        // first create file object for file placed at location
//        // specified by filepath
//        File file = new File(filePath);
//        try {
//            // create FileWriter object with file as parameter
//            FileWriter outputfile = new FileWriter(file);
//
//            // create CSVWriter object filewriter object as parameter
//            CSVWriter writer = new CSVWriter(outputfile);
//
//            // adding header to csv
//            //{start time, request type (ie POST), latency, response code}.
//            String[] header = { "Start time", "Request Type", "Latency", "Response code"};
//            writer.writeNext(header);
//
//            // add data to csv
//            for(int i = 0; i< dataLines.size(); i++) {
//                writer.writeNext(dataLines.get(i));
//            }
//            // closing writer connection
//            writer.close();
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
