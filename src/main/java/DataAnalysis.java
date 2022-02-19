import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataAnalysis {

    //mean response time (millisecs)
    //median response time (millisecs)
    //throughput = total number of requests/wall time (requests/second)
    //p99 (99th percentile) response time. Here’s a nice article about why percentiles are important and why calculating them is not always easy. (millisecs)
    //min and max response time (millisecs)

    private ResultData resultFile;
    private long wallTime;

    public DataAnalysis(ResultData resultFile, long wallTime) {
        this.resultFile = resultFile;
        this.wallTime = wallTime;
    }

    public List<Long> getAllRespTime() {
        List<Long> allRespTime = new ArrayList<>();
        for(String[] dataLine: resultFile.getDataLines()) {
            Long eachLatency = Long.parseLong(dataLine[2]);
            allRespTime.add(eachLatency);
        }
        return allRespTime;
    }

    public long meanRespTime() {
        List<Long> allRespTime = getAllRespTime();
        long sumLatencies = 0;
        for(long latency: allRespTime) {
            sumLatencies += latency;
        }
        long meanVal = sumLatencies/allRespTime.size();
        return meanVal;
    }

    public long medianrepTime() {
        List<Long> sortedRespTime = getAllRespTime();
        Collections.sort(sortedRespTime);
        long medianVal = 0;
        if (sortedRespTime.size() % 2 == 0) {
            medianVal = (sortedRespTime.get(sortedRespTime.size()/2) + sortedRespTime.get(sortedRespTime.size()/2 - 1))/2;
        }
        else {
            medianVal = sortedRespTime.get(sortedRespTime.size()/2);
        }
        return medianVal;
    }

    //throughput = total number of requests/wall time (requests/second)
    public double throughput() {
        return resultFile.getTotalReq().get()/wallTime;
    }


    public long minRepTime() {
        List<Long> sortedRespTime = getAllRespTime();
        Collections.sort(sortedRespTime);
        return sortedRespTime.get(0);
    }

    public long maxRepTime() {
        List<Long> sortedRespTime = getAllRespTime();
        Collections.sort(sortedRespTime);
        return sortedRespTime.get(sortedRespTime.size()-1);
    }

}
