package by.bsuir.app.services.reportFactory;

import static by.bsuir.app.util.constants.Constants.REPORT_FAILURE_MSG;

public class ReportFactory {

    //SINGLETON EAGER
    private static final ReportFactory INSTANCE = new ReportFactory();

    private static int reportPDFCounter = 0;
    private static int reportTXTCounter = 0;

    private ReportFactory() {
    }

    public static ReportFactory getInstance() {
        return INSTANCE;
    }

    public Report getReport(ReportTypes type) {
        Report toReturn = null;

        switch (type) {
            case PDF -> {
                toReturn = new ReportPDF();
                reportPDFCounter++;
            }
            case TXT -> {
                toReturn = new ReportTXT();
                reportTXTCounter++;
            }
            default -> throw new IllegalArgumentException(REPORT_FAILURE_MSG + type);
        }

        return toReturn;
    }

    public static int getReportPDFCounter() {
        return reportPDFCounter;
    }

    public static int getReportTXTCounter() {
        return reportTXTCounter;
    }
}
